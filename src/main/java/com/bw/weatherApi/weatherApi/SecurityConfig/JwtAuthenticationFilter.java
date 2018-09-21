/**

    Author: Oluwatobi Adenekan
    email:  tadenekan@byteworks.com.ng
    organisation: Byteworks Technology solution

**/
package com.bw.weatherApi.weatherApi.SecurityConfig;

import com.bw.weatherApi.weatherApi.dto.LoginRequest;
import com.bw.weatherApi.weatherApi.service.AccessService;
import com.bw.weatherApi.weatherApi.service.AuthenticationTokenService;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


//@Component
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationTokenService authenticationTokenService;

    @Autowired
    AccessService accessService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        if(authenticationManager==null){
            throw new RuntimeException("authenticationManager is null");
        }
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        logger.info("Attempting to login");

       try {

           InputStream in = request.getInputStream();
           InputStreamReader reader = new InputStreamReader(in);
           LoginRequest loginRequest = new Gson().fromJson(new JsonReader(reader),LoginRequest.class);

           String username = loginRequest.getUsername().trim();
           String password = loginRequest.getPassword().trim();
           UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
           this.setDetails(request, authRequest);
           return this.getAuthenticationManager().authenticate(authRequest);

       }catch (IOException ex){
           ex.printStackTrace();
           throw  new RuntimeException();

       }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User principal = (User) authResult.getPrincipal();
        authenticationTokenService.sendToken(principal, response,request);
        response.addHeader( "Access-Control-Allow-Origin", "*" );
        response.addHeader( "Access-Control-Allow-Methods", "POST" );
        response.addHeader( "Access-Control-Max-Age", "100" );
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
//           response.sendError(HttpServletResponse.SC_UNAUTHORIZED, failed.getMessage());
        //Add more descriptive message
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                "Authentication Failed");
//        request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, failed.getLocalizedMessage());
    }
}
