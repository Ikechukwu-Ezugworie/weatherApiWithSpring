/**

    Author: Oluwatobi Adenekan
    email:  tadenekan@byteworks.com.ng
    organisation: Byteworks Technology solution

**/
package com.bw.weatherApi.weatherApi.SecurityConfig;
import com.bw.weatherApi.weatherApi.models.PortalUser;
import com.bw.weatherApi.weatherApi.service.AuthenticationTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


public class JwtAuthorisationFilter extends BasicAuthenticationFilter {

    Logger logger = LoggerFactory.getLogger(JwtAuthorisationFilter.class);


    @Autowired
    AuthenticationTokenService authenticationTokenService;

    public JwtAuthorisationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        PortalUser portalUser = authenticationTokenService.getPortalUser(request);
        UsernamePasswordAuthenticationToken token = null;

        if (portalUser != null){
            // add the logged in user so that it can be gotten from the security context holder
             token = new UsernamePasswordAuthenticationToken(new User(portalUser.getUsername(),portalUser.getPassword(),new ArrayList<>()),null, new ArrayList<>());
        }

        SecurityContextHolder.getContext().setAuthentication(null);

//        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();



        chain.doFilter(request,response);

    }
}
