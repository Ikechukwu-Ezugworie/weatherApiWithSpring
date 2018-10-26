/**

    Author: Oluwatobi Adenekan
    email:  tadenekan@byteworks.com.ng
    organisation: Byteworks Technology solution

**/
package com.bw.weatherApi.weatherApi.controller;

import com.bw.weatherApi.weatherApi.Exceptions.CustomException;
import com.bw.weatherApi.weatherApi.dao.PortalUserDao;
import com.bw.weatherApi.weatherApi.dto.PortalUserDto;
import com.bw.weatherApi.weatherApi.dto.SignUpRequestDto;
import com.bw.weatherApi.weatherApi.models.PortalUser;
import com.bw.weatherApi.weatherApi.models.Role;
import com.bw.weatherApi.weatherApi.service.AccessService;
import com.bw.weatherApi.weatherApi.dto.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/")
public class AuthenticationController /*extends BaseController*/ {
    Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    AccessService accessService;

    @Autowired
    PortalUserDao portalUserDao;

    @PostMapping("signup")
    public ResponseEntity<?> signUp(@RequestBody  SignUpRequestDto signUpRequestDto){

        try{
            PortalUser portalUser = accessService.save(signUpRequestDto);
            PortalUserDto portalUserDto = accessService.toDto(portalUser);

            return ResponseEntity.ok(new ApiResponse<>("200", "successfully created new user", portalUserDto));
        }catch (CustomException ex){
            ex.printStackTrace();
            return ResponseEntity.ok(new ApiResponse<>("409", ex.getMessage(), null));
        }

    }

    @GetMapping("roles")
    public ResponseEntity<?> signUp(){

        try{
            List<Role> roles = accessService.getAllRoles();

            return ResponseEntity.ok(new ApiResponse<List<Role>>("200", "successfully created new user", roles));
        }catch (CustomException ex){
            ex.printStackTrace();
            return ResponseEntity.ok(new ApiResponse<>("409", ex.getMessage(), null));
        }

    }
}
