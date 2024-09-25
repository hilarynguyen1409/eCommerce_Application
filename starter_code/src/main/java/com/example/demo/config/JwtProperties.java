package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtProperties {

    // Load secret key from environment variable or application properties
    @Value("${jwt.secret}")
    public static String SECRET;

    // You can also define the expiration time and other properties in application properties
    @Value("${jwt.expirationTime}")
    public static int EXPIRATION_TIME; // e.g., 864_000_000 for 10 days

    public static String TOKEN_PREFIX = "Bearer ";
    public static String HEADER_STRING = "Authorization";
}
