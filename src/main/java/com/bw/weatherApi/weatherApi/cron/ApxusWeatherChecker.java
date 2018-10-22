package com.bw.weatherApi.weatherApi.cron;

import com.bw.weatherApi.weatherApi.MailerService.MailService;
import com.bw.weatherApi.weatherApi.dao.SimpleUserDao;
import com.bw.weatherApi.weatherApi.dto.WeatherResponseDto;
import com.bw.weatherApi.weatherApi.models.SimpleUser;
import com.bw.weatherApi.weatherApi.service.CityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@EnableAsync
public class ApxusWeatherChecker {

    @Autowired
    private SimpleUserDao simpleUserDao;

    @Autowired
    CityService cityService;

    @Autowired
    MailService mailService;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private static final Logger log = LoggerFactory.getLogger(ApxusWeatherChecker.class);

    @Scheduled(fixedDelay = 5000)
    public void checkForWeather() {
        log.info("About starting cron");
        StringBuilder builder = new StringBuilder();
        builder.append("Hello,");
        List<SimpleUser> simpleUsers = simpleUserDao.findAll();
        WeatherResponseDto weatherResponseDto = null;
        for (SimpleUser simpleUser : simpleUsers) {
            try {
                weatherResponseDto = cityService.fetchWeatherFromApi(simpleUser.getCity().getName().replaceAll("\\s+",""));
                builder.append(simpleUser.getFullName()).append("\n\n\n")
                        .append("The current weather condition in ")
                        .append(simpleUser.getCity().getName())
                        .append(" is ")
                        .append(weatherResponseDto.getCurrent().getTemp_c().toUpperCase())
                        .append("\n\n\n")
                        .append("Best Regards\n").append("From your friends at Byte weather");

                Float currentTemperature = Float.parseFloat(weatherResponseDto.getCurrent().getTemp_c());

                if (Math.round(currentTemperature) < 20){
                    try {
                       //mailService.sendSimpleMail(simpleUser.getEmail(),"Current Weather in " + simpleUser.getCity().getName(),builder.toString());

                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }

            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        log.info("The time is now {}", dateFormat.format(new Date()));
    }
}
