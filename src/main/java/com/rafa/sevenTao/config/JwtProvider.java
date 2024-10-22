package com.rafa.sevenTao.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtProvider {

    public boolean validateJwt(String jwt) {
        final String userName = findUserNameFromJwt(jwt);
        return userName!=null&&isNotExpiredFromJwt(jwt);
    }

    public String findUserNameFromJwt(String jwt) {
        String token = jwt.substring(7).trim();
        Claims claims = parseClaimsFromToken(token);
        System.out.println("from provider my claims: " + claims);
        System.out.println("from provider my userName: " + claims.getSubject());
        return claims.getSubject();
    }

    public Claims parseClaimsFromToken(String token) {
        return Jwts
                .parser()
                .verifyWith(keyParser())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private boolean isNotExpiredFromJwt(String jwt) {
        String token = jwt.substring(7).trim();
        Claims claims = parseClaimsFromToken(token);
        Date expirationDate = claims.getExpiration();
        return expirationDate.after(new Date());
    }

    public SecretKey keyParser() {
        return Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
    }

    public String generateToken(Authentication auth) {
        Map<String, Object> claims = new HashMap<>();

        Collection<? extends GrantedAuthority> authority = auth.getAuthorities();

        for (GrantedAuthority author : authority) {
            claims.put("authority", author.getAuthority());
        }

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(auth.getName())
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + 1000 * 60 * 60 * 24))
                .and()
                .signWith(keyParser())
                .compact();
    }


}
