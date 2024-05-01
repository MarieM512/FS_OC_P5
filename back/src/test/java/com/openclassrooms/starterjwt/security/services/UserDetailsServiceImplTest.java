package com.openclassrooms.starterjwt.security.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;

public class UserDetailsServiceImplTest {
    
    @InjectMocks
    private UserDetailsServiceImpl service;

    @Mock
    private UserRepository repository;

    private User mockUser = new User();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsernameSuccessfull() {
        mockUser.setId(1L);
        mockUser.setEmail("test@oc.com");
        mockUser.setLastName("Doe");
        mockUser.setFirstName("John");
        mockUser.setPassword("password");

        when(repository.findByEmail(mockUser.getEmail())).thenReturn(Optional.of(mockUser));

        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) service.loadUserByUsername(mockUser.getEmail());

        assertEquals(mockUser.getId(), userDetailsImpl.getId());
        assertEquals(mockUser.getEmail(), userDetailsImpl.getUsername());
        assertEquals(mockUser.getLastName(), userDetailsImpl.getLastName());
        assertEquals(mockUser.getFirstName(), userDetailsImpl.getFirstName());
        assertEquals(mockUser.getPassword(), userDetailsImpl.getPassword());
    }

    @Test
    void testLoadUserByUsernameFailed() {
        mockUser.setEmail("notexist@oc.com");
        when(repository.findByEmail(mockUser.getEmail())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.loadUserByUsername(mockUser.getEmail()))
        .isInstanceOf(UsernameNotFoundException.class)
        .hasMessage("User Not Found with email: " + mockUser.getEmail());
    }
}
