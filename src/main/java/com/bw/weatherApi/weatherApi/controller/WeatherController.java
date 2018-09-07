package com.bw.weatherApi.weatherApi.controller;

import com.bw.weatherApi.weatherApi.Exceptions.CityNotFoundException;
import com.bw.weatherApi.weatherApi.Exceptions.CustomException;
import com.bw.weatherApi.weatherApi.dto.AddCityRequestDto;
import com.bw.weatherApi.weatherApi.dto.CityDto;
import com.bw.weatherApi.weatherApi.models.City;
import com.bw.weatherApi.weatherApi.service.CityService;
import com.bw.weatherApi.weatherApi.urils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WeatherController extends BaseController  {

    @Autowired
    CityService cityService;

    @PostMapping("user/city")
    public ResponseEntity<?> addCityToUser(@RequestBody AddCityRequestDto addCityRequestDto){

        try {
            Boolean isUpdated = cityService.updateUserCity(addCityRequestDto);
            return ResponseEntity.ok(new ApiResponse<>("200", "cities updated"));
        }catch (CityNotFoundException ex){
            return ResponseEntity.ok(new ApiResponse<>("409", "An id does not exist",ex.getNotFoudCityIds()));
        }

    }

    @GetMapping("user/city")
    public ResponseEntity<?> getUserCities(){
        List<City> cities = cityService.getAllUserCities();
        List<CityDto> response = cityService.toListDto(cities);
        return ResponseEntity.ok(new ApiResponse<List<CityDto>>("200","found cities",response));
    }

}
