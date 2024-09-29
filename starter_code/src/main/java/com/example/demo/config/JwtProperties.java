package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    public static String SECRET;
    public static long EXPIRATION_TIME;
    public static String TOKEN_PREFIX;
    public static String HEADER_STRING;
    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        JwtProperties.SECRET = secret;
    }

    @Value("${jwt.expiration_time}")
    public void setExpirationTime(long expirationTime) {
        JwtProperties.EXPIRATION_TIME = expirationTime;
    }

    @Value("${jwt.token_prefix}")
    public void setTokenPrefix(String tokenPrefix) {
        JwtProperties.TOKEN_PREFIX = tokenPrefix;
    }

    @Value("${jwt.header_string}")
    public void setHeaderString(String headerString) {
        JwtProperties.HEADER_STRING = headerString;
    }

}
