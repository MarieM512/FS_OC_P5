package com.openclassrooms.starterjwt.services;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.repository.SessionRepository;

public class SessionServiceTest {

    @Mock
    private SessionRepository repository;

    @InjectMocks
    private SessionService service;

    private Session mockSession = new Session();

     @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void testCreateSession() {
        when(repository.save(mockSession)).thenReturn(mockSession);

        service.create(mockSession);

        verify(repository).save(mockSession);
    }

    @Test
    void testDeleteSession() {
        service.delete(1L);

        verify(repository).deleteById(1L);
    }
}
