package com.learning.OnlineLearning.JwtUtities;

import com.learning.OnlineLearning.Entities.Authority;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private final String SECRET_KEY= "5LQ87BymC8FrtZaZwbP2B4NFRRguwXM4";

    public Key getSignKey(){
        byte[] keyInBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyInBytes);
    }

    public String generateJWT(UserDetails username){
        return generateJWT(username,new HashMap<>());
    }

    public String generateJWT(UserDetails user, Map<String ,Object> extraClaims){

        List<String> authorities = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .claim("authorities",authorities)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+100000))
                .signWith(getSignKey())
                .compact();
    }

    public Claims extractClaims(String Jwt){
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(Jwt)
                .getBody();
    }

    public <T> T extractOneClaim(String Jwt, Function<Claims,T> function){
        Claims claims = extractClaims(Jwt);
        return function.apply(claims);
    }

    public String getSubject(String Jwt){
        String username =extractOneClaim(Jwt,Claims::getSubject);
        return username;
    }

    public boolean isExpired(String Jwt){
        Date expiration =extractOneClaim(Jwt,Claims::getExpiration);
        return expiration.before(new Date(System.currentTimeMillis()));
    }


}
