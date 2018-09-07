/**

    Author: Oluwatobi Adenekan
    email:  tadenekan@byteworks.com.ng
    organisation: Byteworks Technology solution

**/
package com.bw.weatherApi.weatherApi.controller;

import com.bw.weatherApi.weatherApi.dao.PortalUserDao;
import com.bw.weatherApi.weatherApi.dto.SignUpRequestDto;
import com.bw.weatherApi.weatherApi.models.PortalUser;
import com.bw.weatherApi.weatherApi.service.AccessService;
import com.bw.weatherApi.weatherApi.urils.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
public class AuthenticationController extends BaseController {
    Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    AccessService accessService;

    @Autowired
    PortalUserDao portalUserDao;

    @PostMapping("signup")
    public ResponseEntity<?> signUp(@RequestBody  SignUpRequestDto signUpRequestDto){
        PortalUser response = accessService.save(signUpRequestDto);
        return ResponseEntity.ok("Success");

    }
}
