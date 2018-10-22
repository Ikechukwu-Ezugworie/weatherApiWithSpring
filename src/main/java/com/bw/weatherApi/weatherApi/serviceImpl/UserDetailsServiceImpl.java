/**

    Author: Oluwatobi Adenekan
    email:  tadenekan@byteworks.com.ng
    organisation: Byteworks Technology solution

**/
package com.bw.weatherApi.weatherApi.serviceImpl;

import com.bw.weatherApi.weatherApi.dao.PortalUserDao;
import com.bw.weatherApi.weatherApi.dto.RoleDto;
import com.bw.weatherApi.weatherApi.models.PortalUser;
import com.bw.weatherApi.weatherApi.models.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    public final static Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    @Autowired
    PortalUserDao portalUserDao;

    @Autowired
    EntityManager entityManager;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<PortalUser> portalUser = portalUserDao.findByUsername(username);



        if(!portalUser.isPresent()){
            logger.info("errrrr");
            throw new UsernameNotFoundException(username);
        }

        // Add roles if this user will be having priviledges

        List<GrantedAuthority> authorities = new ArrayList<>();
        Role role = entityManager.find(Role.class, portalUser.get().getRole().getId());
        logger.info(role.getName());
        authorities.add(role);

        return new User(portalUser.get().getUsername(),portalUser.get().getPassword(),authorities);
    }



}
