package com.bw.weatherApi.weatherApi.service;

import com.bw.weatherApi.weatherApi.models.PortalAccount;
import org.springframework.stereotype.Service;

@Service
public interface PortalAccountService {

    PortalAccount findbyPortalUser();
}
