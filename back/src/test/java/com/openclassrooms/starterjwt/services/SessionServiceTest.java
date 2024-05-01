package com.openclassrooms.starterjwt.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.openclassrooms.starterjwt.exception.BadRequestException;
import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;

public class SessionServiceTest {

    @InjectMocks
    private SessionService service;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private UserRepository userRepository;

    private Session mockSession = new Session();
    private Session mockSessionUpdated = new Session();
    private User mockUser = new User();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void testCreateSession() {
        when(sessionRepository.save(mockSession)).thenReturn(mockSession);

        Session session = service.create(mockSession);

        verify(sessionRepository).save(mockSession);
        assertEquals(mockSession, session);
    }

    @Test
    void testDeleteSession() {
        service.delete(1L);

        verify(sessionRepository).deleteById(1L);
    }

    @Test
    void testFindAll() {
        List<Session> sessions = new ArrayList<Session>();
        when(sessionRepository.findAll()).thenReturn(sessions);

        List<Session> sessionResult = service.findAll();

        verify(sessionRepository).findAll();
        assertEquals(sessions, sessionResult);
    }

    @Test
    void testGetById() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(mockSession));

        Session session = service.getById(1L);

        verify(sessionRepository).findById(1L);
        assertEquals(mockSession, session);
    }

    @Test
    void testUpdate() {
        when(sessionRepository.save(mockSession)).thenReturn(mockSessionUpdated);

        Session session = service.update(1L, mockSession);

        verify(sessionRepository).save(mockSession);
        assertEquals(mockSessionUpdated, session);
    }

    @Test
    void testParticipate() {
        List<User> users = new ArrayList<User>();
        mockSession.setUsers(users);
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(mockSession));
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(sessionRepository.save(mockSession)).thenReturn(mockSession);

        service.participate(1L, 1L);

        verify(sessionRepository).findById(1L);
        verify(userRepository).findById(1L);
        verify(sessionRepository).save(mockSession);
    }

    @Test
    void testParticipateNotFound() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.participate(1L, 1L));
    }

    @Test
    void testParticipateBadRequest() {
        List<User> users = new ArrayList<User>();
        mockUser.setId(1L);
        users.add(mockUser);
        mockSession.setUsers(users);
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(mockSession));
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        assertThrows(BadRequestException.class, () -> service.participate(1L, 1L));
    }

    @Test
    void testNoLongerParticipate() {
        List<User> users = new ArrayList<User>();
        mockUser.setId(1L);
        users.add(mockUser);
        mockSession.setUsers(users);
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(mockSession));
        when(sessionRepository.save(mockSession)).thenReturn(mockSession);

        service.noLongerParticipate(1L, 1L);

        verify(sessionRepository).findById(1L);
        verify(sessionRepository).save(mockSession);
    }

    @Test
    void testNoLongerParticipateNotFound() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.noLongerParticipate(1L, 1L));
    }

    @Test
    void testNoLongerParticipateBadRequest() {
        List<User> users = new ArrayList<User>();
        mockUser.setId(1L);
        users.add(mockUser);
        mockSession.setUsers(users);
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(mockSession));
        when(sessionRepository.save(mockSession)).thenReturn(mockSession);

        assertThrows(BadRequestException.class, () -> service.noLongerParticipate(1L, 2L));
    }
}
