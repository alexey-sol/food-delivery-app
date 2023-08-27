package com.github.alexeysol.gateway.util;

import com.github.alexeysol.common.model.dto.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; // 24 hour

    @Value("${service.app.jwt.secret}") // TODO use Properties
    private String JWT_SECRET;

    public String generateAccessToken(UserDto user) {
        return Jwts.builder()
//            .setSubject(String.format("%s,%s", user.getId(), user.getEmail()))
            .setSubject(String.valueOf(user.getId()))
            .setIssuer("gateway") // TODO needed?
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
            .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
            .compact();
    }

    public boolean isValidAccessToken(String accessToken) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(accessToken);
            return true;
        } catch (Exception exception) {
            // TODO log or smthng
        }
//        } catch (ExpiredJwtException ex) {
//            LOGGER.error("JWT expired", ex.getMessage());
//        } catch (IllegalArgumentException ex) {
//            LOGGER.error("Token is null, empty or only whitespace", ex.getMessage());
//        } catch (MalformedJwtException ex) {
//            LOGGER.error("JWT is invalid", ex);
//        } catch (UnsupportedJwtException ex) {
//            LOGGER.error("JWT is not supported", ex);
//        } catch (SignatureException ex) {
//            LOGGER.error("Signature validation failed");
//        }

        return false;
    }

    public Claims getSubject(String accessToken) {
        return Jwts.parser()
            .setSigningKey(JWT_SECRET)
            .parseClaimsJws(accessToken)
            .getBody();
    }
}
