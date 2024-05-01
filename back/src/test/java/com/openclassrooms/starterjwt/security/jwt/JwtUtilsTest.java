package com.openclassrooms.starterjwt.security.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;

import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;

@SpringBootTest
public class JwtUtilsTest {
    
    @Autowired
    private JwtUtils jwtUtils;

    @MockBean
    private Authentication authentication;

    @BeforeEach
    public void setup(){
        UserDetailsImpl userDetailsImpl = UserDetailsImpl
            .builder()
            .id(1L)
            .username("Johnny")
            .lastName("Doe")
            .firstName("John")
            .password("password")
            .build();

        when(authentication.getPrincipal()).thenReturn(userDetailsImpl);
        when(authentication.isAuthenticated()).thenReturn(false);
    }

    @Test
    void givenAuthentication(){
        String jwtToken = jwtUtils.generateJwtToken(authentication);
        assertNotNull(jwtToken);
        assertEquals("Johnny", jwtUtils.getUserNameFromJwtToken(jwtToken));
        assertTrue(jwtUtils.validateJwtToken(jwtToken));
    }
}
