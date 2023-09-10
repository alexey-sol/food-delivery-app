package com.github.alexeysol.gateway.util;

import com.github.alexeysol.common.feature.user.model.dto.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    @Value("${service.jwt.secret}")
    private String JWT_SECRET;

    @Value("${service.jwt.expire-duration}")
    private long EXPIRE_DURATION;

    public String generateAccessToken(UserDto user) {
        return Jwts.builder()
            .setSubject(String.valueOf(user.getId()))
            .setIssuer("gateway")
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
            .signWith(SIGNATURE_ALGORITHM, JWT_SECRET)
            .compact();
    }

    public boolean isValidAccessToken(String accessToken) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(accessToken);
            return true;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        return false;
    }

    public Claims getSubject(String accessToken) {
        return Jwts.parser()
            .setSigningKey(JWT_SECRET)
            .parseClaimsJws(accessToken)
            .getBody();
    }
}
