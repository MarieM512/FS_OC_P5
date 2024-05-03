package com.openclassrooms.starterjwt.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.openclassrooms.starterjwt.controllers.SessionController;
import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;

@SpringBootTest
public class SessionControllerIntegrationTest {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionController sessionController;

    @Autowired
    private TeacherRepository teacherRepository;

    private Teacher savedTeacher;

    @BeforeEach
    void setUp() {
        Teacher mockTeacher = new Teacher();
        mockTeacher.setId(1L);
        mockTeacher.setFirstName("John");
        mockTeacher.setLastName("Doe");

        savedTeacher = teacherRepository.save(mockTeacher);
    }

    @AfterEach
    void cleanUp() {
        sessionRepository.deleteAll();
        userRepository.deleteAll();
        teacherRepository.deleteAll();
    }

    @Test
    @DirtiesContext
    void testFindSessionByIdSuccessfull() {
        Session mockSession = new Session();
        mockSession.setId(1L);
        mockSession.setName("Session");
        mockSession.setDate(new Date());
        mockSession.setDescription("Description");
        mockSession.setTeacher(savedTeacher);

        sessionRepository.save(mockSession);

        ResponseEntity<?> response = sessionController.findById(String.valueOf(mockSession.getId()));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(SessionDto.class, response.getBody());

        SessionDto sessionDto = (SessionDto) response.getBody();
        assertNotNull(sessionDto);
        assertEquals(mockSession.getId(), sessionDto.getId());
        assertEquals(mockSession.getName(), sessionDto.getName());
        assertEquals(mockSession.getDate(), sessionDto.getDate());
        assertEquals(mockSession.getDescription(), sessionDto.getDescription());
        assertEquals(mockSession.getTeacher().getId(), sessionDto.getTeacher_id());
    }

    @Test
    @DirtiesContext
    void testFindSessionByIdFailedNotFound() {
        ResponseEntity<?> response = sessionController.findById("1");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @DirtiesContext
    void testFindSessionByIdBadRequest() {
        ResponseEntity<?> response = sessionController.findById("notId");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @DirtiesContext
    void testFindAllSession() {
        Session mockSession = new Session();
        mockSession.setId(1L);
        mockSession.setName("Session");
        mockSession.setDate(new Date());
        mockSession.setDescription("Description");
        mockSession.setTeacher(savedTeacher);
        List<Session> mockSessionList = new ArrayList<Session>();
        mockSessionList.add(mockSession);

        sessionRepository.save(mockSession);

        ResponseEntity<?> response = sessionController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(ArrayList.class, response.getBody());

        List<SessionDto> sessionDtoList = (List<SessionDto>) response.getBody();
        assertThat(sessionDtoList).isNotEmpty();
        assertEquals(mockSessionList.size(), sessionDtoList.size());
        assertEquals(mockSession.getId(), sessionDtoList.get(0).getId());
        assertEquals(mockSession.getName(), sessionDtoList.get(0).getName());
        assertEquals(mockSession.getDate(), sessionDtoList.get(0).getDate());
        assertEquals(mockSession.getDescription(), sessionDtoList.get(0).getDescription());
        assertEquals(mockSession.getTeacher().getId(), sessionDtoList.get(0).getTeacher_id());
    }

    @Test
    @DirtiesContext
    void testCreateSession() {
        SessionDto mockSessionDto = new SessionDto();
        mockSessionDto.setId(1L);
        mockSessionDto.setName("Session");
        mockSessionDto.setDate(new Date());
        mockSessionDto.setDescription("Description");
        mockSessionDto.setTeacher_id(savedTeacher.getId());

        Session mockSession = new Session();
        mockSession.setId(1L);
        mockSession.setName("Session");
        mockSession.setDate(new Date());
        mockSession.setDescription("Description");
        mockSession.setTeacher(savedTeacher);

        ResponseEntity<?> response = sessionController.create(mockSessionDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(SessionDto.class, response.getBody());

        SessionDto sessionDto = (SessionDto) response.getBody();
        assertEquals(mockSessionDto.getId(), sessionDto.getId());
        assertEquals(mockSessionDto.getName(), sessionDto.getName());
        assertEquals(mockSessionDto.getDate(), sessionDto.getDate());
        assertEquals(mockSessionDto.getDescription(), sessionDto.getDescription());
        assertEquals(mockSessionDto.getTeacher_id(), sessionDto.getId());
    }

    @Test
    @DirtiesContext
    void testUpdateSessionSuccessfull() {
        SessionDto mockSessionDto = new SessionDto();
        mockSessionDto.setId(1L);
        mockSessionDto.setName("Session update");
        mockSessionDto.setDate(new Date());
        mockSessionDto.setDescription("Description update");
        mockSessionDto.setTeacher_id(savedTeacher.getId());

        Session mockSession = new Session();
        mockSession.setId(1L);
        mockSession.setName("Session");
        mockSession.setDate(new Date());
        mockSession.setDescription("Description");
        mockSession.setTeacher(savedTeacher);

        sessionRepository.save(mockSession);

        ResponseEntity<?> response = sessionController.update(String.valueOf(mockSession.getId()), mockSessionDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(SessionDto.class, response.getBody());

        SessionDto sessionDto = (SessionDto) response.getBody();
        assertEquals(mockSessionDto.getId(), sessionDto.getId());
        assertEquals(mockSessionDto.getName(), sessionDto.getName());
        assertEquals(mockSessionDto.getDate(), sessionDto.getDate());
        assertEquals(mockSessionDto.getDescription(), sessionDto.getDescription());
        assertEquals(mockSessionDto.getTeacher_id(), sessionDto.getId());
    }

    @Test
    @DirtiesContext
    void testUpdateSessionBadRequest() {
        SessionDto mockSessionDto = new SessionDto();

        ResponseEntity<?> response = sessionController.update("notId", mockSessionDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DirtiesContext
    void testSaveSessionSuccessfull() {
        Session mockSession = new Session();
        mockSession.setId(1L);
        mockSession.setName("Session");
        mockSession.setDate(new Date());
        mockSession.setDescription("Description");
        mockSession.setTeacher(savedTeacher);

        sessionRepository.save(mockSession);

        ResponseEntity<?> response = sessionController.save(String.valueOf(mockSession.getId()));

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DirtiesContext
    void testSaveSessionNotFound() {
        ResponseEntity<?> response = sessionController.save("1");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DirtiesContext
    void testSaveSessionBadRequest() {
        ResponseEntity<?> response = sessionController.save("notId");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DirtiesContext
    void testParticipateSuccessfull() {
        User mockUser = new User();
        mockUser.setId(1L);

        userRepository.save(mockUser);

        Session mockSession = new Session();
        mockSession.setId(1L);
        mockSession.setName("Session");
        mockSession.setDate(new Date());
        mockSession.setDescription("Description");
        mockSession.setTeacher(savedTeacher);

        sessionRepository.save(mockSession);

        ResponseEntity<?> response = sessionController.participate(String.valueOf(mockSession.getId()), String.valueOf(mockUser.getId()));

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DirtiesContext
    void testParticipateBadRequest() {
        ResponseEntity<?> response = sessionController.participate("notId", "notId");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DirtiesContext
    void testNoLongerParticipateSuccessfull() {
        User mockUser = new User();
        mockUser.setId(1L);

        Session mockSession = new Session();
        mockSession.setId(1L);
        mockSession.setName("Session");
        mockSession.setDate(new Date());
        mockSession.setDescription("Description");
        mockSession.setTeacher(savedTeacher);
        mockSession.setUsers(Arrays.asList(mockUser));

        userRepository.save(mockUser);
        sessionRepository.save(mockSession);

        ResponseEntity<?> response = sessionController.noLongerParticipate(String.valueOf(mockSession.getId()), String.valueOf(mockUser.getId()));

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testNoLongerParticipateBadRequest() {
        ResponseEntity<?> response = sessionController.noLongerParticipate("notId", "notId");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
