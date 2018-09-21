/**

    Author: Oluwatobi Adenekan
    email:  tadenekan@byteworks.com.ng
    organisation: Byteworks Technology solution

**/
package com.bw.weatherApi.weatherApi.service;

import com.bw.weatherApi.weatherApi.dto.PortalUserDto;
import com.bw.weatherApi.weatherApi.dto.SignUpRequestDto;
import com.bw.weatherApi.weatherApi.models.PortalUser;
import org.springframework.stereotype.Service;

@Service
public interface AccessService {

    PortalUser save(SignUpRequestDto signUpRequestDto);

    PortalUser getPrincipal();

    PortalUserDto toDto(PortalUser portalUser);
}
