/**

    Author: Oluwatobi Adenekan
    email:  tadenekan@byteworks.com.ng
    organisation: Byteworks Technology solution

**/
package com.bw.weatherApi.weatherApi.serviceImpl;

import com.bw.weatherApi.weatherApi.dao.PortalUserDao;
import com.bw.weatherApi.weatherApi.models.PortalUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    public final static Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    @Autowired
    PortalUserDao portalUserDao;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<PortalUser> portalUser = portalUserDao.findByUsername(username);



        if(!portalUser.isPresent()){
            logger.info("errrrr");
            throw new UsernameNotFoundException(username);
        }

        // Add roles if this user will be having priviledges

        return new User(portalUser.get().getUsername(),portalUser.get().getPassword(),new ArrayList<>());
    }
}
