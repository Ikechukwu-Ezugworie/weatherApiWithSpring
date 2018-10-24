/**

    Author: Oluwatobi Adenekan
    email:  tadenekan@byteworks.com.ng
    organisation: Byteworks Technology solution

**/
package com.bw.weatherApi.weatherApi.serviceImpl;

import com.bw.weatherApi.weatherApi.Exceptions.CustomException;
import com.bw.weatherApi.weatherApi.dao.PortalUserDao;
import com.bw.weatherApi.weatherApi.dto.PortalUserDto;
import com.bw.weatherApi.weatherApi.dto.RoleDto;
import com.bw.weatherApi.weatherApi.dto.SignUpRequestDto;
import com.bw.weatherApi.weatherApi.models.PortalAccount;
import com.bw.weatherApi.weatherApi.models.PortalUser;
import com.bw.weatherApi.weatherApi.models.Role;
import com.bw.weatherApi.weatherApi.service.AccessService;
import com.bw.weatherApi.weatherApi.service.PortalAccountService;
import com.bw.weatherApi.weatherApi.utils.WeatherApiUtils;
import org.hibernate.validator.constraints.EAN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Component
public class AccessServiveImpl implements AccessService {

    private static final Logger logger = LoggerFactory.getLogger(AccessServiveImpl.class);

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    PortalUserDao portalUserDao;

    @Autowired
    EntityManager entityManager;

    @Autowired
    PortalAccountService portalAccountService;





    @Override
    @Transactional
    public PortalUser save(SignUpRequestDto signUpRequestDto) {

        Optional<PortalUser> checker =  portalUserDao.findByUsername(signUpRequestDto.getUsername());
        if (checker.isPresent()){
            logger.info("SimpleUser already exists");
            throw new CustomException("user with username already exist", HttpStatus.CONFLICT);
        }

        Role role = entityManager.find(Role.class,Long.valueOf(signUpRequestDto.getRoleId()));

        PortalAccount portalAccount = portalAccountService.findbyPortalUser();

        PortalUser portalUser = new PortalUser();
        portalUser.setUsername(signUpRequestDto.getUsername());
        portalUser.setPassword(bCryptPasswordEncoder.encode(signUpRequestDto.getPassword()));
        portalUser.setAuthKey(String.valueOf(WeatherApiUtils.generateRandomInt(60)));
        portalUser.setFirstName(signUpRequestDto.getFirstName());
        portalUser.setLastName(signUpRequestDto.getLastName());
        portalUser.setRole(role);
        portalUser.setPortalAccount(portalAccount);
        portalUser.setEmail(signUpRequestDto.getEmail());
        portalUser.setDateCreated(Timestamp.from(Instant.now()));
        portalUser.setDateUpdated(Timestamp.from(Instant.now()));
        return portalUserDao.save(portalUser);
    }

    @Override
    public PortalUser getPrincipal() {
        PortalUser portalUser = null;
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<PortalUser> checker = portalUserDao.findByUsername(user.getUsername());
        if(checker.isPresent()){
             portalUser = checker.get();
        }

        return portalUser;
    }

    @Override
    public PortalUserDto toDto(PortalUser portalUser){
        System.out.println(portalUser.toString());
        PortalUserDto portalUserDto = new PortalUserDto();
        portalUserDto.setId(portalUser.getId());
        portalUserDto.setEmail(portalUser.getEmail());
        portalUserDto.setUsername(portalUser.getUsername());
        portalUserDto.setFirstName(portalUser.getFirstName());
        portalUserDto.setLastName(portalUser.getLastName());
        portalUserDto.setPassword(portalUser.getPassword());
        portalUserDto.setAuthKey(portalUser.getAuthKey());
        if(portalUser.getRole() != null){ // This will cater for the updated table
            Role role = entityManager.find(Role.class,portalUser.getRole().getId());
            portalUserDto.setRoleName(role.getName());
        }
        portalUserDto.setDateCreated(WeatherApiUtils.timeToString(portalUser.getDateCreated(),"MM/dd/yyyy"));
        portalUserDto.setDateUpdated(WeatherApiUtils.timeToString(portalUser.getDateUpdated(),"MM/dd/yyyy"));
        return portalUserDto;

    }

    @Transactional
    public void loadRoles(){
        RoleDto[] roles  = RoleDto.values();

        for (RoleDto role : roles) {
            String roleName = role.name();
            logger.info("role is " + roleName);
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Role> cq = criteriaBuilder.createQuery(Role.class);
            Root<Role> roleRoot = cq.from(Role.class);

            cq.select(roleRoot).where(criteriaBuilder.equal(roleRoot.get("name"),roleName));
            Boolean isEmpty = entityManager.createQuery(cq).getResultList().isEmpty();
            if(isEmpty){
                Role newRole = new Role();
                newRole.setName(roleName);
                logger.info("Creating a new role with " + roleName);
                entityManager.persist(newRole);
            }
        }

    }


    @Override
    @Transactional
    public void createDefaultUser(){

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Role> criteriaQuery = criteriaBuilder.createQuery(Role.class);
        Root<Role> roleRoot = criteriaQuery.from(Role.class);
        criteriaQuery.select(roleRoot).where(criteriaBuilder.equal(roleRoot.get("name"),"ADMIN"));
        Role role = entityManager.createQuery(criteriaQuery).getResultList().get(0);

        PortalAccount portalAccount = new PortalAccount();
        portalAccount.setName("Byteworks Technology solutions");
        entityManager.persist(portalAccount);


        PortalUser portalUser = new PortalUser();
        portalUser.setUsername("byteAdmin");
        portalUser.setPassword(bCryptPasswordEncoder.encode("school123"));
        portalUser.setAuthKey(String.valueOf(WeatherApiUtils.generateRandomInt(60)));
        portalUser.setFirstName("byte");
        portalUser.setLastName("admin");
        portalUser.setRole(role);
        portalUser.setPortalAccount(portalAccount);
        portalUser.setEmail("admin@byteworks.com.ng");
        portalUser.setDateCreated(Timestamp.from(Instant.now()));
        portalUser.setDateUpdated(Timestamp.from(Instant.now()));
        portalUserDao.save(portalUser);
    }

    public List<Role> getAllRoles(){

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Role> cq = cb.createQuery(Role.class);
        Root<Role> root = cq.from(Role.class);
        return (List<Role> ) entityManager.createQuery(cq.select(root));


    }
}
