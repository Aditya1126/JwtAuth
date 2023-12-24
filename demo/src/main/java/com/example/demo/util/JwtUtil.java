package com.example.demo.util;

import com.example.demo.service.UserDetailsImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;

import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Log4j2
public class JwtUtil {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationMs}")
    private int jwtExpiration;

    public String generateJwtToken(Authentication authentication){
        UserDetailsImpl userDetails= (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder().setSubject(userDetails.getEmail())
                .setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime()+jwtExpiration))
                .signWith(SignatureAlgorithm.HS512,jwtSecret).compact();
    }

    public String getUserNameFromJwt(String token){
        String email=null;
        try{
            email=Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
        }catch (Exception e){
            log.error("Some error occurred",e.getMessage());
        }
        return email;
    }

    public boolean validateJwtToken(String token){
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }catch (Exception e){
            log.error("Invalid Jwt token",e.getMessage());
        }
        return false;
    }

}
