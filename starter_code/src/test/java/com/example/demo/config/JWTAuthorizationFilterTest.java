package com.example.demo.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.FilterChain;

import java.util.Date;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class JWTAuthorizationFilterTest {

    private AuthenticationManager authenticationManager;
    private JWTAuthorizationFilter jwtAuthorizationFilter;
    private final String secret = "yourSuperSecretKey";
    private final Algorithm algorithm = Algorithm.HMAC512(secret.getBytes());

    @BeforeEach
    void setUp() {
        JwtProperties.SECRET = "mock-secret";
        JwtProperties.EXPIRATION_TIME = 86400000;
        JwtProperties.TOKEN_PREFIX = "Bearer ";
        JwtProperties.HEADER_STRING = "Authorization";

        authenticationManager = mock(AuthenticationManager.class);
        jwtAuthorizationFilter = new JWTAuthorizationFilter(authenticationManager);

    }

    @Test
    public void doFilterInternal_validToken_authenticationSet() throws Exception {
        // Create a valid mock JWT token
        String token = JWT.create()
                .withSubject("testuser")
                .sign(Algorithm.HMAC512(JwtProperties.SECRET.getBytes()));

        // Create mock request with JWT token
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + token);

        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);

        // Call the filter method
        jwtAuthorizationFilter.doFilterInternal(request, response, chain);

        // Assert that the authentication was set
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void doFilterInternal_invalidToken_noAuthentication() throws Exception {
        String invalidToken = "invalidToken"; // This is not a valid JWT token.

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + invalidToken);

        MockHttpServletResponse response = new MockHttpServletResponse();

        // Create a filter chain mock
        FilterChain filterChain = mock(FilterChain.class);

        // Execute the filter
        jwtAuthorizationFilter.doFilterInternal(request, response, filterChain);

        // Verify that no authentication is set in the security context
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain, times(1)).doFilter(request, response);
    }


}
