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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
public class AuthenticationController extends BaseController {

    @Autowired
    AccessService accessService;

    @Autowired
    PortalUserDao portalUserDao;

    @PostMapping("signup")
    public ResponseEntity<?> signUp(@RequestBody  SignUpRequestDto signUpRequestDto){

        Optional<PortalUser> portalUser =  portalUserDao.findByUsername(signUpRequestDto.getUsername());
        if (portalUser.isPresent()){
            return ResponseEntity.ok(new ApiResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()), "user with username already exist"));
        }
        return ResponseEntity.ok(accessService.save(signUpRequestDto));

    }
}
