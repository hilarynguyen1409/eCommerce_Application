package com.example.demo.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.model.requests.CreateUserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.FilterChain;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JWTAuthenticationFilterTest {

    private AuthenticationManager authenticationManager;
    private JWTAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    void setUp() {
        authenticationManager = mock(AuthenticationManager.class);
        jwtAuthenticationFilter = new JWTAuthenticationFilter(authenticationManager);
        JwtProperties.SECRET = "mock-secret"; // Set the secret to avoid NullPointerException
        JwtProperties.EXPIRATION_TIME = 86400000; // Set expiration time for the mock
        JwtProperties.TOKEN_PREFIX = "Bearer ";
        JwtProperties.HEADER_STRING = "Authorization";
    }

    @Test
    void attemptAuthentication_validCredentials_success() throws Exception {
        CreateUserRequest creds = new CreateUserRequest();
        creds.setUsername("testUser");
        creds.setPassword("testPassword");

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContent(new ObjectMapper().writeValueAsBytes(creds));

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mock(Authentication.class));

        Authentication authentication = jwtAuthenticationFilter.attemptAuthentication(request, new MockHttpServletResponse());

        assertNotNull(authentication);
    }

    @Test
    void attemptAuthentication_invalidRequestBody_throwsException() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContent("invalid body".getBytes());

        assertThrows(RuntimeException.class, () -> jwtAuthenticationFilter.attemptAuthentication(request, new MockHttpServletResponse()));
    }

    @Test
    void successfulAuthentication_generatesToken() throws Exception {
        MockHttpServletResponse response = new MockHttpServletResponse();
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("testUser");

        Authentication authResult = mock(Authentication.class);
        when(authResult.getPrincipal()).thenReturn(userDetails);

        jwtAuthenticationFilter.successfulAuthentication(
                new MockHttpServletRequest(), response, mock(FilterChain.class), authResult);

        String token = response.getHeader(JwtProperties.HEADER_STRING);
        assertNotNull(token);
        assertTrue(token.startsWith(JwtProperties.TOKEN_PREFIX));
    }
}
