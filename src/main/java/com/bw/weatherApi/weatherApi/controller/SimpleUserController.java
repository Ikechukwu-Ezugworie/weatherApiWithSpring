package com.bw.weatherApi.weatherApi.controller;


import com.bw.weatherApi.weatherApi.Exceptions.CustomException;
import com.bw.weatherApi.weatherApi.dto.SimpleUserDto;
import com.bw.weatherApi.weatherApi.models.SimpleUser;
import com.bw.weatherApi.weatherApi.service.SimpleUserService;
import com.bw.weatherApi.weatherApi.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SimpleUserController extends BaseController {

    @Autowired
    SimpleUserService simpleUserService;

    @PostMapping("user/simpleuser")
    public ResponseEntity<?> addUser(@RequestBody SimpleUserDto simpleUserDto){

        try {
            SimpleUser simpleUser = simpleUserService.addUserToPortalUser(simpleUserDto);
            SimpleUserDto response = simpleUserService.toDto(simpleUser);
            return ResponseEntity.ok(new ApiResponse<>("200", "Added user",response));
        }catch (CustomException ex){
            ex.printStackTrace();
            return ResponseEntity.ok(new ApiResponse<>("409", ex.getMessage()));
        }

    }


    @DeleteMapping("/user/simpleuser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long userId){

        try {
            simpleUserService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse<>("200", "Deleted the user"));
        }catch (CustomException ex){
            ex.printStackTrace();
            return ResponseEntity.ok(new ApiResponse<>("409", ex.getMessage()));
        }
    }


    @GetMapping("user/simpleuser")
    public ResponseEntity<?> findAllSimple(){
        List<SimpleUserDto> response = simpleUserService.getAllSimpleUsersDtos();
        return ResponseEntity.ok(new ApiResponse<List<SimpleUserDto>>("200", "found simple users",response));
    }

}
