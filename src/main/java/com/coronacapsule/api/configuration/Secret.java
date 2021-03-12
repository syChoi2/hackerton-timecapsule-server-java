package com.coronacapsule.api.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Component
@ConfigurationProperties(prefix = "jwt.token")
@Getter
@Setter
@ToString
public class Secret {
	
	public String JWT_SECRET_KEY;
	public String HEADER;
	public String ISSUER;
//	public String USER_INFO_PASSWORD_KEY = "o9pqYVC9-F8_.PEzEiw!L9F6.AYj9jcfVJ*_i.ifXYnyE68kix@Q2dL6rw*bV-rpdZYwcqZG-jPF-fw3CiJyKsfZ778ks-*jnZn";

}
