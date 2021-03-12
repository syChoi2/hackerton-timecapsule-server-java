package com.coronacapsule.api.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.coronacapsule.api.configuration.Secret;
import com.coronacapsule.api.dto.JwtAuthentication;
import com.coronacapsule.api.entity.Users;
import com.coronacapsule.api.exception.BusinessException;
import com.coronacapsule.api.exception.ErrorCode;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {
    
	private final String header;
    private final String issuer;
    private final String clientSecret;
    
    private final UserService userService;
    
    public JwtService(@Lazy UserService userService, Secret secret) {
        this.userService = userService;
        this.header = secret.getHEADER();
        this.issuer = secret.getISSUER();
        this.clientSecret = secret.getJWT_SECRET_KEY();
    }
    
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
                .signWith(SignatureAlgorithm.HS256, clientSecret)
                .compact();
    }
    

    public String resolveToken(HttpServletRequest req) {
        return req.getHeader("X-ACCESS-TOKEN");
    }

	public boolean validateToken(String jwtToken) {
		Jws<Claims> claims = Jwts.parser().setSigningKey(clientSecret).parseClaimsJws(jwtToken);
		if (claims.getBody().getExpiration() == null) return true;
		return !claims.getBody().getExpiration().before(new Date());
	}

    public Authentication getAuthentication(String token) throws Exception {
        Long userId = getUserId(token);
        Users users = userService.findById(userId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        return new UsernamePasswordAuthenticationToken(
                new JwtAuthentication(users.getUserId(), users.getNickname()),
                "",
                users.getAuthorities()
        );
    }
    public Long getUserId(String token) {
        return Long.valueOf(Jwts.parser()
                .setSigningKey(clientSecret)
                .parseClaimsJws(token)
                .getBody()
                .get("userId")
                .toString()
        );
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
                    .setSigningKey(clientSecret)
                    .parseClaimsJws(accessToken);
        } catch (Exception ignored) {
            throw new BusinessException("Invalid Token", ErrorCode.TOKEN_ERROR);
        }

        // 3. userId 추출
        return claims.getBody().get("userId", Long.class);
    }
}
