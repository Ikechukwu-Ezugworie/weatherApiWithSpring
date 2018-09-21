package com.bw.weatherApi.weatherApi.serviceImpl;

import com.bw.weatherApi.weatherApi.Exceptions.CustomException;
import com.bw.weatherApi.weatherApi.dao.CityDao;
import com.bw.weatherApi.weatherApi.dao.PortalUserDao;
import com.bw.weatherApi.weatherApi.dao.SimpleUserDao;
import com.bw.weatherApi.weatherApi.dto.SimpleUserDto;
import com.bw.weatherApi.weatherApi.models.City;
import com.bw.weatherApi.weatherApi.models.PortalUser;
import com.bw.weatherApi.weatherApi.models.SimpleUser;
import com.bw.weatherApi.weatherApi.service.AccessService;
import com.bw.weatherApi.weatherApi.service.SimpleUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class SimpleUserServiceImpl implements SimpleUserService {

    Logger logger = LoggerFactory.getLogger(SimpleUserServiceImpl.class);
    @Autowired
    AccessService accessService;

    @Autowired
    CityDao cityDao;

    @Autowired
    SimpleUserDao simpleUserDao;

    @Autowired
    PortalUserDao portalUserDao;



    @Override
    @Transactional
    public boolean addUserToPortalUser(SimpleUserDto simpleUserDto){
        PortalUser portalUser = accessService.getPrincipal();

        SimpleUser simpleUser = simpleUserDao.findByEmail(simpleUserDto.getEmail());


        if(simpleUser != null){
            Optional<SimpleUser> simpleUserHasCity = simpleUserDao.findByCity(simpleUser.getCity());
            Optional<PortalUser> portalUserToSimpleChecker =  portalUserDao.findById(simpleUserHasCity.get().getPortalUser().getId());
            if (portalUserToSimpleChecker.isPresent()){
                throw new CustomException("Cannot add user again", HttpStatus.CONFLICT);
            }
        }

        logger.info(simpleUserDto.toString());
        Optional<City> cityChecker = cityDao.findById(Long.valueOf(simpleUserDto.getCityId()));

        if(!cityChecker.isPresent()){
            throw  new CustomException("City with Id does not exist", HttpStatus.FORBIDDEN);
        }


        SimpleUser newSimpleUser = new SimpleUser();
        newSimpleUser.setEmail(simpleUserDto.getEmail());
        newSimpleUser.setPortalUser(portalUser);
        newSimpleUser.setCity(cityChecker.get());
        newSimpleUser.setEmail(simpleUserDto.getEmail());
        newSimpleUser.setFullName(simpleUserDto.getFullName());

        simpleUserDao.save(newSimpleUser);

        return true;
    }

    @Override
    @Transactional
    public boolean deleteUser(Long userId){
        Optional<SimpleUser> userChecker = simpleUserDao.findById(userId);

        SimpleUser foundSimpleUser = userChecker.orElseThrow(() -> {
            return new CustomException("SimpleUser with id does not exist", HttpStatus.FORBIDDEN);
        });

        simpleUserDao.delete(foundSimpleUser);
        return true;
    }

    @Override
    public List<SimpleUser> findAllSimpleUsers(){
        PortalUser portalUser = accessService.getPrincipal();
        List<SimpleUser> simpleUsers = simpleUserDao.findByPortalUser(portalUser);
        return simpleUsers;
    }


    @Override
    public List<SimpleUserDto> toDtos(List<SimpleUser> simpleUsers){
        List<SimpleUserDto> simpleUserDtos = new ArrayList<>();
        SimpleUserDto simpleUserDto = new SimpleUserDto();
        simpleUsers.forEach(simpleUser -> {
            City city = cityDao.getOne(simpleUser.getCity().getId());
            simpleUserDto.setCityId(String.valueOf(city.getId()));
            simpleUserDto.setId(simpleUser.getId());
            simpleUserDto.setEmail(simpleUser.getEmail());
            simpleUserDto.setFullName(simpleUser.getFullName());
            simpleUserDto.setCityId(String.valueOf(simpleUser.getId()));
            simpleUserDto.setCityName(city.getName());
            simpleUserDtos.add(simpleUserDto);
        });

        return simpleUserDtos;
    }

    @Override
    public List<SimpleUserDto> getAllSimpleUsersDtos(){
        List<SimpleUser> simpleUsers = findAllSimpleUsers();
        return toDtos(simpleUsers);
    }


}
