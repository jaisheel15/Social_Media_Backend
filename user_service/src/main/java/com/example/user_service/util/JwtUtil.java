package com.example.user_service.util;



import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

        private static final String SECRET = 
            "mysecretkeymysecretkeymysecretkeymysecretkey"; 



    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                ;
    }
        public Claims validateToken(String token) {
        return extractClaims(token);
    }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    

}


