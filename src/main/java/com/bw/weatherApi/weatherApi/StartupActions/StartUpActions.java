package com.bw.weatherApi.weatherApi.StartupActions;

import com.bw.weatherApi.weatherApi.service.AccessService;
import com.bw.weatherApi.weatherApi.service.CityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class StartUpActions {

    Logger logger = LoggerFactory.getLogger(StartUpActions.class);

    @Autowired
    CityService cityService;

    @Autowired
    AccessService accessService;


    @PostConstruct
    public void loadCitiesToDb(){
        logger.info("application is starting up");
        cityService.loadCities();
        accessService.loadRoles();
        accessService.createDefaultUser();
    }
}
