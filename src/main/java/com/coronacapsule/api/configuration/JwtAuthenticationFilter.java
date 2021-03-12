package com.coronacapsule.api.configuration;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.coronacapsule.api.service.JwtService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtService jwtService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        String token = jwtService.resolveToken((HttpServletRequest) request);
        if (token != null && jwtService.validateToken(token)) {
			try {
				Authentication authentication = jwtService.getAuthentication(token);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        filterChain.doFilter(request, response);
    }

}
