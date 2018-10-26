/**
    Author: Oluwatobi Adenekan
    email:  tadenekan@byteworks.com.ng
    organisation: Byteworks Technology solution

**/
package com.bw.weatherApi.weatherApi.dto;

import com.bw.weatherApi.weatherApi.models.PortalUser;

public class LoginResponseDto {
    private String token;
    private PortalUserDto loggedInUser;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public PortalUserDto getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(PortalUserDto loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
}
