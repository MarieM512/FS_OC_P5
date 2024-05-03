// package com.openclassrooms.starterjwt.controllers;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;

// import java.util.ArrayList;
// import java.util.List;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;

// import com.openclassrooms.starterjwt.dto.SessionDto;
// import com.openclassrooms.starterjwt.mapper.SessionMapper;
// import com.openclassrooms.starterjwt.models.Session;
// import com.openclassrooms.starterjwt.services.SessionService;

// public class SessionControllerTest {
    
//     @InjectMocks
//     private SessionController controller;

//     @Mock
//     private SessionService service;

//     @Mock
//     private SessionMapper mapper;

//     @BeforeEach
//     void setUp() {
//         MockitoAnnotations.openMocks(this);
//     }

//     @Test
//     void testFindSessionByIdSuccessfull() {
//         Session session = new Session();
//         when(service.getById(1L)).thenReturn(session);

//         ResponseEntity<?> response = controller.findById("1");

//         assertEquals(HttpStatus.OK, response.getStatusCode());
//         verify(mapper).toDto(session);
//     }

//     @Test
//     void testFindSessionByIdFailedNotFound() {
//         when(service.getById(1L)).thenReturn(null);

//         ResponseEntity<?> response = controller.findById("1");

//         assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//     }

//     @Test
//     void testFindSessionByIdBadRequest() {
//         ResponseEntity<?> response = controller.findById("notId");

//         assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//     }

//     @Test
//     void testFindAllSession() {
//         List<Session> sessions = new ArrayList<Session>();
//         when(service.findAll()).thenReturn(sessions);

//         ResponseEntity<?> response = controller.findAll();

//         assertEquals(HttpStatus.OK, response.getStatusCode());
//         verify(service).findAll();
//         verify(mapper).toDto(sessions);
//     }

//     @Test
//     void testCreateSession() {
//         SessionDto sessionDto = new SessionDto();
//         Session session = new Session();
//         when(mapper.toEntity(sessionDto)).thenReturn(session);
//         when(service.create(session)).thenReturn(session);

//         ResponseEntity<?> response = controller.create(sessionDto);

//         assertEquals(HttpStatus.OK, response.getStatusCode());
//         verify(mapper).toEntity(sessionDto);
//         verify(service).create(session);
//         verify(mapper).toDto(session);
//     }

//     @Test
//     void testUpdateSessionSuccessfull() {
//         SessionDto sessionDto = new SessionDto();
//         Session session = new Session();
//         when(mapper.toEntity(sessionDto)).thenReturn(session);
//         when(service.update(1L, session)).thenReturn(session);

//         ResponseEntity<?> response = controller.update("1", sessionDto);

//         assertEquals(HttpStatus.OK, response.getStatusCode());
//         verify(mapper).toEntity(sessionDto);
//         verify(service).update(1L, session);
//         verify(mapper).toDto(session);
//     }

//     @Test
//     void testUpdateSessionBadRequest() {
//         SessionDto sessionDto = new SessionDto();

//         ResponseEntity<?> response = controller.update("notId", sessionDto);

//         assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//     }

//     @Test
//     void testSaveSessionSuccessfull() {
//         Session session = new Session();

//         when(service.getById(1L)).thenReturn(session);

//         ResponseEntity<?> response = controller.save("1");

//         assertEquals(HttpStatus.OK, response.getStatusCode());
//         verify(service).delete(1L);
//     }

//     @Test
//     void testSaveSessionNotFound() {
//         when(service.getById(1L)).thenReturn(null);

//         ResponseEntity<?> response = controller.save("1");

//         assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//     }

    // @Test
    // void testSaveSessionBadRequest() {
    //     ResponseEntity<?> response = controller.save("notId");

    //     assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    // }

//     @Test
//     void testParticipateSuccessfull() {
//         ResponseEntity<?> response = controller.participate("1", "1");

//         assertEquals(HttpStatus.OK, response.getStatusCode());
//         verify(service).participate(1L, 1L);
//     }

    // @Test
    // void testParticipateBadRequest() {
    //     ResponseEntity<?> response = controller.participate("notId", "notId");

    //     assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    // }

//     @Test
//     void testNoLongerParticipateSuccessfull() {
//         ResponseEntity<?> response = controller.noLongerParticipate("1", "1");

//         assertEquals(HttpStatus.OK, response.getStatusCode());
//         verify(service).noLongerParticipate(1L, 1L);
//     }

    // @Test
    // void testNoLongerParticipateBadRequest() {
    //     ResponseEntity<?> response = controller.noLongerParticipate("notId", "notId");

    //     assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    // }
// }
