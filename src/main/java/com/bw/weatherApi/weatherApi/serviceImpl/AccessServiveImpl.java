/**

    Author: Oluwatobi Adenekan
    email:  tadenekan@byteworks.com.ng
    organisation: Byteworks Technology solution

**/
package com.bw.weatherApi.weatherApi.serviceImpl;

import com.bw.weatherApi.weatherApi.Exceptions.CustomException;
import com.bw.weatherApi.weatherApi.dao.PortalUserDao;
import com.bw.weatherApi.weatherApi.dto.PortalUserDto;
import com.bw.weatherApi.weatherApi.dto.SignUpRequestDto;
import com.bw.weatherApi.weatherApi.models.PortalUser;
import com.bw.weatherApi.weatherApi.service.AccessService;
import com.bw.weatherApi.weatherApi.utils.WeatherApiUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Component
public class AccessServiveImpl implements AccessService {

    private static final Logger logger = LoggerFactory.getLogger(AccessServiveImpl.class);

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    PortalUserDao portalUserDao;





    @Override
    @Transactional
    public PortalUser save(SignUpRequestDto signUpRequestDto) {

        Optional<PortalUser> checker =  portalUserDao.findByUsername(signUpRequestDto.getUsername());
        if (checker.isPresent()){
            logger.info("SimpleUser already exists");
            throw new CustomException("user with username already exist", HttpStatus.CONFLICT);
        }

        PortalUser portalUser = new PortalUser();
        portalUser.setUsername(signUpRequestDto.getUsername());
        portalUser.setPassword(bCryptPasswordEncoder.encode(signUpRequestDto.getPassword()));
        portalUser.setAuthKey(String.valueOf(WeatherApiUtils.generateRandomInt(60)));
        portalUser.setFirstName(signUpRequestDto.getFirstName());
        portalUser.setLastName(signUpRequestDto.getLastName());
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
        portalUserDto.setDateCreated(WeatherApiUtils.timeToString(portalUser.getDateCreated(),"MM/dd/yyyy"));
        portalUserDto.setDateUpdated(WeatherApiUtils.timeToString(portalUser.getDateUpdated(),"MM/dd/yyyy"));
        return portalUserDto;

    }}
