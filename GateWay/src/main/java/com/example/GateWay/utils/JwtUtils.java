package com.example.GateWay.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtUtils {
    @Value("${app.jwt.secret}")
    public String SECRET;

    public void validateToken(final String token) {
        Jwts.parserBuilder().setSigningKey(secretKey()).build().parseClaimsJws(token);
    }



    private SecretKey secretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
