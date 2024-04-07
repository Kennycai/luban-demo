package com.example.commonutils.utils;

import com.example.commonutils.dto.UserRoleInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.Date;
import java.util.List;

public class JwtUtils {
    public static final String header = "Authorization";
    public static final String prefix = "Bearer ";
    public static String createToken(String secretKey, String username, List<String> authoritiesList) {
        return Jwts.builder()
                .setSubject(username)
                .claim("authorities", authoritiesList)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + 60 * 60 * 1000)) // Set token to expire in 1 hour
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public static UserRoleInfo getUser(String secretKey, ServerHttpRequest request) {
        String tokenWithPrefix = request.getHeaders().getFirst(header);
        if (tokenWithPrefix == null || tokenWithPrefix.isEmpty()) {
            return null;
        }
        String token = tokenWithPrefix.replaceFirst(prefix, "");
        Claims body = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        UserRoleInfo userRoleInfo = new UserRoleInfo();
        userRoleInfo.setUsername(body.getSubject());
        ObjectMapper mapper = new ObjectMapper();
        List<String> authoritiesList = mapper.convertValue(body.get("authorities"), new TypeReference<>() {
        });
        userRoleInfo.setRoles(authoritiesList);
        return userRoleInfo;
    }
}
