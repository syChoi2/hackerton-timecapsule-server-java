package com.coronacapsule.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.coronacapsule.api.service.JwtService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtService jwtService;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    private String[] AUTH_WHITELIST = {
            "/webjars/**", "/configuration/**", "/", "/error", "/csrf"
            , "/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/swagger", "/h2/**", "/h2-console/**"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable() // rest api 이므로 기본설정 사용안함. 기본설정은 비인증시 로그인폼 화면으로 리다이렉트 된다.
            .csrf().disable() // rest api이므로 csrf 보안이 필요없으므로 disable처리.
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt token으로 인증하므로 세션은 필요없으므로 생성안함.
            .and()
            .authorizeRequests() // 다음 리퀘스트에 대한 사용권한 체크
            .anyRequest().permitAll()// 그외 나머지 요청은 모두 인증된 회원만 접근 가능.and()
            ;
        
        http.addFilterBefore(new JwtAuthenticationFilter(jwtService), UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
           .antMatchers(AUTH_WHITELIST);
    }
}
