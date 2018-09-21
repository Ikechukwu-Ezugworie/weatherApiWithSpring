/**

    Author: Oluwatobi Adenekan
    email:  tadenekan@byteworks.com.ng
    organisation: Byteworks Technology solution

**/

package com.bw.weatherApi.weatherApi.serviceImpl;


import com.bw.weatherApi.weatherApi.Exceptions.CityNotFoundException;
import com.bw.weatherApi.weatherApi.Exceptions.CustomException;
import com.bw.weatherApi.weatherApi.dao.CityDao;
import com.bw.weatherApi.weatherApi.dao.PortalUserDao;
import com.bw.weatherApi.weatherApi.dto.AddCityRequestDto;
import com.bw.weatherApi.weatherApi.dto.CityDto;
import com.bw.weatherApi.weatherApi.dto.WeatherResponseDto;
import com.bw.weatherApi.weatherApi.models.City;
import com.bw.weatherApi.weatherApi.models.PortalUser;
import com.bw.weatherApi.weatherApi.service.AccessService;
import com.bw.weatherApi.weatherApi.service.CityService;
import com.bw.weatherApi.weatherApi.utils.WeatherApiUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javassist.bytecode.stackmap.BasicBlock;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URI;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Component
public class CityServiceImpl implements CityService {
    @Autowired
    CityDao cityDao;

    @Autowired
    AccessService accessService;

    @Autowired
    PortalUserDao portalUserDao;

    @Autowired
    private Environment env;


    Logger logger = LoggerFactory.getLogger(CityServiceImpl.class);

    public static final String CITY_FILE_PATH = "classpath:files/cities.xlsx";


    @Override
    public void loadCities() {

        List<CityDto> cities = readCitiesFromExcel();

        cities.stream().forEach(cityDto -> {
            Optional<City> cityOption = cityDao.findByName(cityDto.getName());
            if(!cityOption.isPresent()){

                City city = new City();
                city.setName(cityDto.getName());
                city.setDateCreated(Timestamp.from(Instant.now()));
                city.setDateUpdated(Timestamp.from(Instant.now()));
                cityDao.save(city);
            }
        } );



    }


    @Override
    public Boolean updateUserCity(AddCityRequestDto addCityRequestDto){
        PortalUser portalUser = accessService.getPrincipal();
        List<City> cities = new ArrayList<>();
        List<Long> notPresentCities = new ArrayList<>();

        for (Long id : addCityRequestDto.getId()) {
            Optional<City> cityChecker = cityDao.findById(id);
            if (!cityChecker.isPresent()) {
                notPresentCities.add(id);
            }

            if (notPresentCities.size() > 0){
                throw new CityNotFoundException("City with id does not exist", notPresentCities,HttpStatus.CONFLICT);
            }

            cities.add(cityChecker.get());
        }

//        removeCities(); // remove all the existing cities before updating.
//        cities.forEach(this::cityUpdate);

        cities.forEach(city -> {
            if(!userHasCity(city.getId())){
                cityUpdate(city);
            }
        });
        return true;
    }


    @Override
    @Transactional
    public void cityUpdate(City city){

        PortalUser portalUser = accessService.getPrincipal();
        portalUser.getCities().add(city);
        portalUserDao.save(portalUser);
    }


    @Override
    public List<City> getAllUserCities(){
        List<Long> cityIds = new ArrayList<>();
        PortalUser portalUser = accessService.getPrincipal();
        Set<City> cities = portalUser.getCities();
        cities.forEach(city -> cityIds.add(city.getId()));
        return cityDao.findByIdInOrderByNameAsc(cityIds);

    }

    @Override
    public List<CityDto> toListDto(List<City> cities){
        List<CityDto> cityDtos = new ArrayList<>();
        cities.forEach(city -> cityDtos.add(toDto(city)));
        return cityDtos;
    }



    @Override
    public CityDto toDto(City city){
        CityDto cityDto = new CityDto();
        cityDto.setName(city.getName());
        cityDto.setId(city.getId());
        return cityDto;
    }

    @Override
    public List<City> getAllCities() {
        return cityDao.findAll(new Sort(Sort.Direction.ASC,"name"));
    }


    /**
     * Fetch list of cities from the excel
     * @return a list of city dto
     */
    private List<CityDto> readCitiesFromExcel(){
        List<CityDto> cities = new ArrayList<>();
        File cityFiles = null;

        try {
            cityFiles = ResourceUtils.getFile( CITY_FILE_PATH );
            FileInputStream fileInputStream = new FileInputStream(cityFiles);
            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheet("cities");
            Iterator<Row> rows = sheet.iterator();
            rows.next();


            while (rows.hasNext()){
                Row currentRow = rows.next();


                for (Cell currentCell : currentRow) {
                    String cityName = currentCell.getStringCellValue();
                    logger.info("found city is " + cityName);

                    CityDto cityDto = new CityDto();
                    cityDto.setName(cityName);
                    cities.add(cityDto);
                }
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }

        return cities;

    }

    @Override
    @Transactional
    public void removeCities(){
        PortalUser portalUser = accessService.getPrincipal();
        portalUser.getCities().clear();
        portalUserDao.save(portalUser);
    }



    @Override
    @Transactional
    public Boolean removeCity(Long cityId){
        Optional<City> cityChecker = cityDao.findById(cityId);
        if (!cityChecker.isPresent()){
            throw new CityNotFoundException("City with id does not exist",cityId,HttpStatus.CONFLICT);
        }


        Boolean isExist = userHasCity(cityId);

        if(!isExist){
            throw new CityNotFoundException("City with id does not exist for this exist for this user",cityId,HttpStatus.CONFLICT);
        }

        City foundCity = cityChecker.get();
        PortalUser portalUser =  accessService.getPrincipal();
        portalUser.getCities().remove(foundCity);
        portalUserDao.save(portalUser);
        return true;
    }

    public boolean userHasCity(Long cityId){

        List<City> cities = getAllUserCities();

       return cities.stream().anyMatch(city -> String.valueOf(city.getId()).equals(String.valueOf(cityId)));
    }


    @Override
    public WeatherResponseDto fetchWeatherFromApi(String city){
        String scheme = "http";
        String host= env.getProperty("application.weatherApiHost");
        String key = env.getProperty("application.weatherApikey");
        String response = null;
        WeatherResponseDto weatherResponseDto = null;

        UriBuilder uriBuilder = UriComponentsBuilder.newInstance();



        URI uri = uriBuilder.host(host).scheme(scheme)
                .queryParam("Key",key).pathSegment("v1","current.json")
                .queryParam("q",city).build();
        try {
            logger.info(uri.toURL().toString());
            response = WeatherApiUtils.makeGetRequest(uri.toURL());
            weatherResponseDto = new Gson().fromJson(response,WeatherResponseDto.class);
            logger.info(weatherResponseDto.getCurrent().toString());
        }catch (IOException ex){
            ex.printStackTrace();
            throw new CustomException(ex.getMessage(),HttpStatus.BAD_REQUEST);

        }

        return weatherResponseDto;

    }

}
