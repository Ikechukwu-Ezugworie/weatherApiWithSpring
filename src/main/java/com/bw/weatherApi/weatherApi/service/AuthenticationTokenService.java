/**

    Author: Oluwatobi Adenekan
    email:  tadenekan@byteworks.com.ng
    organisation: Byteworks Technology solution

**/
package com.bw.weatherApi.weatherApi.service;

import com.bw.weatherApi.weatherApi.models.PortalUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public interface AuthenticationTokenService {
    void sendToken(User user, HttpServletResponse response, HttpServletRequest request);
    PortalUser getPortalUser(HttpServletRequest request, HttpServletResponse response);
}
