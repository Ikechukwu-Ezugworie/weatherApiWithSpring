package com.bw.weatherApi.weatherApi.serviceImpl;

import com.bw.weatherApi.weatherApi.dao.PortalUserDao;
import com.bw.weatherApi.weatherApi.dto.SignUpRequestDto;
import com.bw.weatherApi.weatherApi.models.PortalUser;
import com.bw.weatherApi.weatherApi.service.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;

@Component
public class AccessServiveImpl implements AccessService {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    PortalUserDao portalUserDao;



    @Override
    public PortalUser save(SignUpRequestDto signUpRequestDto) {

        PortalUser portalUser = new PortalUser();
        portalUser.setUsername(signUpRequestDto.getUsername());
        portalUser.setPassword(bCryptPasswordEncoder.encode(signUpRequestDto.getPassword()));
        portalUser.setAuthKey("hello world");
        portalUser.setEmail(signUpRequestDto.getEmail());
        portalUser.setDateCreated(Timestamp.from(Instant.now()));
        portalUser.setDateUpdated(Timestamp.from(Instant.now()));
        portalUserDao.save(portalUser);
        return portalUser;
    }


}
