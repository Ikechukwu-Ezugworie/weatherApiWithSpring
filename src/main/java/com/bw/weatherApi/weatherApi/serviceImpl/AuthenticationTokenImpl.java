/**

    Author: Oluwatobi Adenekan
    email:  tadenekan@byteworks.com.ng
    organisation: Byteworks Technology solution

**/
package com.bw.weatherApi.weatherApi.serviceImpl;

import com.bw.weatherApi.weatherApi.dao.PortalUserDao;
import com.bw.weatherApi.weatherApi.dto.LoginResponseDto;
import com.bw.weatherApi.weatherApi.models.PortalUser;
import com.bw.weatherApi.weatherApi.service.AccessService;
import com.bw.weatherApi.weatherApi.service.AuthenticationTokenService;
import com.bw.weatherApi.weatherApi.service.JwtService;
import com.bw.weatherApi.weatherApi.dto.ApiResponse;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Component
public class AuthenticationTokenImpl implements AuthenticationTokenService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationTokenImpl.class);

    @Autowired
    JwtService jwtService;

    @Autowired
    HttpServletRequest request;

    @Autowired
    PortalUserDao portalUserDao;

    @Autowired
    AccessService accessService;


    public static final String HEADER_STRING = "Authorization";



    @Override
    public void sendToken(User user, HttpServletResponse response, HttpServletRequest request) {
        String token = null;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 60*60*60*60*60);
        Date expiresIn = calendar.getTime();
        PortalUser portalUser = null;


        Optional<PortalUser> optionalPortalUser = portalUserDao.findByUsername(user.getUsername());
        LoginResponseDto loginResponseDto = new LoginResponseDto();

        if(optionalPortalUser.isPresent()){
            portalUser = optionalPortalUser.get();
        }else {

        }

        logger.info("------------Logged in user is --------" + portalUser.getUsername());

        try {
            Map<String, Object> claims = new HashMap<>();
            claims.put("sub",String.valueOf(portalUser.getId()));
            claims.put("authKey",portalUser.getAuthKey());
            token = jwtService.generateToken(String.valueOf(portalUser.getId()),request.getRemoteAddr(), expiresIn,claims);
        }catch (Exception ex){
            ex.printStackTrace();
            throw new AuthenticationServiceException("error creating a token");
        }


        loginResponseDto.setLoggedInUser(accessService.toDto(portalUser));
        loginResponseDto.setToken(token);

        if (request.getRequestURI().contains("api/v1/login")){
            try {
                response.setHeader("Authorization",loginResponseDto.getToken());
                // Using the cookies
                Cookie cookie = new Cookie("Authorization",loginResponseDto.getToken());
                response.addCookie(cookie);
                response.setContentType("application/json");

                response.getWriter().write(new Gson().toJson(new ApiResponse<LoginResponseDto>("200","Login successfully",loginResponseDto)));
            }catch (Exception ex){
                ex.printStackTrace();
                throw new AuthenticationServiceException("Error while trying to return a response");
            }

        }
    }

    @Override
    public PortalUser getPortalUser(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Trying to get portal user");
         String token = null;

//        String token = request.getHeader(HEADER_STRING);

        // Get token from the response header

        Cookie[] cookies = request.getCookies();
        List<Cookie> cookieList = Arrays.asList(cookies);
        for (Cookie cookie : cookieList) {
            String cookieName = cookie.getName();
            if (cookieName.equalsIgnoreCase(HEADER_STRING)) {
                token = cookie.getValue();
            }
        }




        Claims claims = null;
        if (token == null || token.isEmpty()){
            return null;
        }
        try {
            logger.info("Token is "+ token.trim());
             claims =  jwtService.decodeToken(token.trim());
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }

        String authKey = (String) claims.get("authKey");
        Long userId = Long.valueOf(claims.getSubject());

        Optional<PortalUser> portalUser = portalUserDao.findById(userId);

       if (!portalUser.isPresent()) {
           return null;
       }
       return portalUser.get();
    }




}
