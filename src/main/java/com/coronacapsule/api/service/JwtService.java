package com.coronacapsule.api.service;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.coronacapsule.api.configuration.Secret;
import com.coronacapsule.api.exception.BusinessException;
import com.coronacapsule.api.exception.ErrorCode;
import com.coronacapsule.api.exception.ErrorResponse;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import io.jsonwebtoken.*;

@Service
public class JwtService {
	
	/**
     * JWT 생성
     * @param userId
     * @return String
     */
    public String createJwt(long userId) {
        Date now = new Date();
        return Jwts.builder()
                .claim("userId", userId)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, Secret.JWT_SECRET_KEY)
                .compact();
    }

    /**
     * Header에서 X-ACCESS-TOKEN 으로 JWT 추출
     * @return String
     */
    public String getJwt() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("X-ACCESS-TOKEN");
    }

    /**
     * JWT에서 userId 추출
     * @return long
     * @throws BaseException
     */
    public long getUserId() throws Exception {
        // 1. JWT 추출
        String accessToken = getJwt();
        if (accessToken == null || accessToken.length() == 0) {
            throw new BusinessException("Empty Token", ErrorCode.TOKEN_ERROR);
        }

        // 2. JWT parsing
        Jws<Claims> claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(Secret.JWT_SECRET_KEY)
                    .parseClaimsJws(accessToken);
        } catch (Exception ignored) {
            throw new BusinessException("Invalid Token", ErrorCode.TOKEN_ERROR);
        }

        // 3. userId 추출
        return claims.getBody().get("userId", Long.class);
    }
}
