package com.bw.weatherApi.weatherApi.serviceImpl;


import com.bw.weatherApi.weatherApi.dao.CityDao;
import com.bw.weatherApi.weatherApi.dto.CityDto;
import com.bw.weatherApi.weatherApi.models.City;
import com.bw.weatherApi.weatherApi.service.CityService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Component
public class CityServiceImpl implements CityService {
    @Autowired
    CityDao cityDao;


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
                Iterator<Cell> cellsInRow = currentRow.iterator();


                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
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



}
