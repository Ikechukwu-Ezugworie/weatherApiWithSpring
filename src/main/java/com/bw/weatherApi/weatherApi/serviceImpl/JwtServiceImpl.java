/**

    Author: Oluwatobi Adenekan
    email:  tadenekan@byteworks.com.ng
    date:    24/08/2018

**/
package com.bw.weatherApi.weatherApi.serviceImpl;

import com.bw.weatherApi.weatherApi.Exceptions.AuthenticationException;
import com.bw.weatherApi.weatherApi.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Component
public class JwtServiceImpl implements JwtService {

    private static final Logger logger = LoggerFactory.getLogger(JwtServiceImpl.class);


    @Resource
    public Environment env;


    Key key;
    byte[] encodedSecret;


    @Autowired
    public JwtServiceImpl( ) {
        this.encodedSecret = generateEncodedSecret();
        this.key = Keys.hmacShaKeyFor(this.encodedSecret);

    }

    @Override
    public String generateToken (String subject) throws Exception{
        return Jwts.builder()
                .setSubject(subject)
                .signWith(key)
                .compact();
    }

    @Override
    public String generateToken(String subject, String issuer, Date expiration) throws Exception{
        return Jwts.builder()
                .setIssuer(issuer)
                .setSubject(subject)
                .setExpiration(expiration) //a java.util.Date
                .setIssuedAt(new Date())
                .signWith(key)
                .compact();// for example, now
    }




    @Override
    public String generateToken(String subject, String issuer, Date expiration, Map<String, Object> claims) throws Exception{
        String generatedString = UUID.randomUUID().toString().replace("-", "");
        logger.info("generated string is "+ generatedString);
        logger.info("signing in with " + key.getFormat());
        return Jwts.builder()
                .setClaims(claims)
                .setId(generatedString)
                .setIssuer(issuer)
                .setSubject(subject)
                .setExpiration(expiration) //a java.util.Date
                .setIssuedAt(new Date())
                .signWith(key)
                .compact();// for example, now
    }

    @Override
    public Claims decodeToken(String token){
        Claims claims;

        try {
            claims = Jwts.parser()         // (1)
                    .setSigningKey(key)         // (2)
                    .parseClaimsJws(token).getBody(); // (3)
            logger.info("<-------------------- Token has been detokinised");
            return claims;
        }
        catch (JwtException ex) {
            ex.printStackTrace();
            throw new AuthenticationException("Cannot create a token");
        }

    }


    private byte[] generateEncodedSecret(){
        byte[] encodedSecret = null;
        try {
//            logger.info(env.getProperty("application.secret"));
            encodedSecret = "e39286d9-f666-48e2-b7fd-8ea0961f545a".getBytes();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return encodedSecret;
    }




}
