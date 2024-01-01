package com.springboot.blogapp.blogapprestapi.security;

import com.springboot.blogapp.blogapprestapi.exception.BlogAPIException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.jwtSecret}")
    private String jwtSecret;
    @Value("${app.jwtExpirationInMs}")
    private long jwtExpirationInMs;

    //generate token
    public String generateToken(Authentication authentication) {

        String username = authentication.getName();
        Date currentDate = new Date();
        Date expiryDate = new Date(currentDate.getTime() + jwtExpirationInMs);
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expiryDate)
                .signWith(key())
                .compact();
        return token;
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    // get username from token
    public String getUsernameFromToken(String token) {
      Claims claims = Jwts.parser().setSigningKey(key()).build().parseClaimsJws(token).getBody();
        String username = claims.getSubject();
        return username;
    }
    // validate token
    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(key()).build().parse(token);
            return true;
        }catch (MalformedJwtException ex){
           throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Invalid token");
        }
        catch (ExpiredJwtException ex){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Expired token");
        }
        catch (UnsupportedJwtException ex){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Unsupported token");
        }
        catch (IllegalArgumentException ex){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Jwt claims string is empty");
        }
        catch (SignatureException ex){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Invalid signature");
        }

    }

}
