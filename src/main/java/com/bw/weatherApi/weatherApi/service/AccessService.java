package com.bw.weatherApi.weatherApi.service;

import com.bw.weatherApi.weatherApi.dto.SignUpRequestDto;
import com.bw.weatherApi.weatherApi.models.PortalUser;
import org.springframework.stereotype.Service;

@Service
public interface AccessService {

    PortalUser save(SignUpRequestDto signUpRequestDto);
}
