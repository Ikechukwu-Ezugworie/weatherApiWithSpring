package com.bw.weatherApi.weatherApi.service;


import com.bw.weatherApi.weatherApi.dto.SimpleUserDto;
import com.bw.weatherApi.weatherApi.models.SimpleUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SimpleUserService {
    boolean addUserToPortalUser(SimpleUserDto simpleUserDto);
    boolean deleteUser(Long userId);
    List<SimpleUser> findAllSimpleUsers();
    List<SimpleUserDto> toDtos(List<SimpleUser> simpleUsers);
    List<SimpleUserDto> getAllSimpleUsersDtos();
}
