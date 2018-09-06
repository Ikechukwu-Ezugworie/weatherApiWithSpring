package com.bw.weatherApi.weatherApi.StartupActions;

import com.bw.weatherApi.weatherApi.service.CityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CityLoaderStartUpActions {

    Logger logger = LoggerFactory.getLogger(CityLoaderStartUpActions.class);

    @Autowired
    CityService cityService;


    @PostConstruct
    public void loadCitiesToDb(){
        logger.info("application is starting up");
        cityService.loadCities();

    }
}
