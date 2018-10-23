/**

    Author: Oluwatobi Adenekan
    email:  tadenekan@byteworks.com.ng
    organisation: Byteworks Technology solution

**/
package com.bw.weatherApi.weatherApi.service;

import com.bw.weatherApi.weatherApi.dto.PortalUserDto;
import com.bw.weatherApi.weatherApi.dto.SignUpRequestDto;
import com.bw.weatherApi.weatherApi.models.PortalUser;
import com.bw.weatherApi.weatherApi.models.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccessService {

    PortalUser save(SignUpRequestDto signUpRequestDto);

    PortalUser getPrincipal();

    PortalUserDto toDto(PortalUser portalUser);

    void loadRoles();

    void createDefaultUser();

    List<Role> getAllRoles();


}
