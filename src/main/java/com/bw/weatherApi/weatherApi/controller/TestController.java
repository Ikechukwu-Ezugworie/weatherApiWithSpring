package com.bw.weatherApi.weatherApi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController extends BaseController {
    Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping("testing")
    public ResponseEntity<?> test(){
        logger.info("---------------------- Hitting the test unit ------------------------------");
       return  ResponseEntity.ok("Every thing is very ok");
    }

}
