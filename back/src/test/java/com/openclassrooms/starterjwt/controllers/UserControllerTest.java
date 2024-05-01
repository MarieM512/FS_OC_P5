package com.openclassrooms.starterjwt.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.UserService;

public class UserControllerTest {
    
    @InjectMocks
    private UserController controller;

    @Mock
    private UserService service;

    @Mock
    private UserMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindUserByIdSuccessfull() {
        User user = new User();

        when(service.findById(1L)).thenReturn(user);

        ResponseEntity<?> response = controller.findById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service).findById(1L);
        verify(mapper).toDto(user);
    }

    @Test
    void testFindUserByIdNotFound() {
        when(service.findById(1L)).thenReturn(null);

        ResponseEntity<?> response = controller.findById("1");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(service).findById(1L);
    }

    @Test
    void testFindUserByIdBadRequest() {
        ResponseEntity<?> response = controller.findById("notId");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testSaveUserSuccessfull() {
        User user = new User(1L, "test@oc.com", "Doe", "John", "password", false, null, null);
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(service.findById(1L)).thenReturn(user);

        ResponseEntity<?> response = controller.save("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service).delete(1L);
    }

    @Test
    void testSaveUserUnauthorized() {
        User user = new User(1L, "test@oc.com", "Doe", "John", "password", false, null, null);
        UserDetails userDetails = new org.springframework.security.core.userdetails.User("other@oc.com", user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(service.findById(1L)).thenReturn(user);

        ResponseEntity<?> response = controller.save("1");

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void testSaveUserNotFound() {
        when(service.findById(1L)).thenReturn(null);

        ResponseEntity<?> response = controller.save("1");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testSaveUserBadRequest() {
        ResponseEntity<?> response = controller.save("notId");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
