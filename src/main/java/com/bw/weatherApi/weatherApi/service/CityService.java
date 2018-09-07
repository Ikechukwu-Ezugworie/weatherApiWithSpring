package com.bw.weatherApi.weatherApi.service;

import com.bw.weatherApi.weatherApi.dto.AddCityRequestDto;
import com.bw.weatherApi.weatherApi.dto.CityDto;
import com.bw.weatherApi.weatherApi.models.City;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CityService{

    void loadCities();
    Boolean updateUserCity(AddCityRequestDto addCityRequestDto);
    List<City> getAllUserCities();
    List<CityDto> toListDto(List<City> cities);
    void cityUpdate(City city);
    void removeCities();
}
