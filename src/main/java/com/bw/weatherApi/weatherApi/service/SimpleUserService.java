package com.bw.weatherApi.weatherApi.service;


import com.bw.weatherApi.weatherApi.dto.SimpleUserDto;
import com.bw.weatherApi.weatherApi.models.SimpleUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SimpleUserService {
    SimpleUser addUserToPortalUser(SimpleUserDto simpleUserDto);
    SimpleUserDto toDto(SimpleUser simpleUser);
    boolean deleteUser(Long userId);
    List<SimpleUser> findAllSimpleUsers();
    List<SimpleUserDto> toDtos(List<SimpleUser> simpleUsers);
    List<SimpleUserDto> getAllSimpleUsersDtos();
}
