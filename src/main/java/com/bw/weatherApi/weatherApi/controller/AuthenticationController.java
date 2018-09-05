package com.bw.weatherApi.weatherApi.controller;

import com.bw.weatherApi.weatherApi.dto.SignUpRequestDto;
import com.bw.weatherApi.weatherApi.service.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController extends BaseController {

    @Autowired
    AccessService accessService;

    @PostMapping("signup")
    public ResponseEntity<?> signUp(@RequestBody  SignUpRequestDto signUpRequestDto){
        // check if user exists
        return ResponseEntity.ok(accessService.save(signUpRequestDto));

    }
}
