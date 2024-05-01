package com.openclassrooms.starterjwt.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;

public class UserServiceTest {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    private User mockUser = new User();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDelete() {
        service.delete(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void testFindById() {
        when(repository.findById(1L)).thenReturn(Optional.of(mockUser));

        User user = service.findById(1L);

        verify(repository).findById(1L);
        assertEquals(mockUser, user);
    }
}
