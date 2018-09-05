package com.bw.weatherApi.weatherApi.dto;

import com.bw.weatherApi.weatherApi.models.PortalUser;

public class LoginResponseDto {
    private String token;
    private PortalUser loggedInUser;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public PortalUser getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(PortalUser loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
}
