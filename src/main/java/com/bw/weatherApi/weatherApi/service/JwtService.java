/**

    Author: Oluwatobi Adenekan
    email:  tadenekan@byteworks.com.ng
    organisation: Byteworks Technology solution

**/
package com.bw.weatherApi.weatherApi.service;

import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public interface JwtService {
    String generateToken (String subject) throws Exception;
    String generateToken(String subject, String issuer, Date expiration) throws Exception;
    String generateToken(String subject, String issuer, Date expiration, Map<String, Object> claims) throws Exception;
    Claims decodeToken(String token);
}
