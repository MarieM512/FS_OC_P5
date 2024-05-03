package com.openclassrooms.starterjwt.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;

import com.openclassrooms.starterjwt.controllers.AuthController;
import com.openclassrooms.starterjwt.controllers.UserController;
import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.repository.UserRepository;

@SpringBootTest
public class UserControllerIntegrationTest {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthController authController;

    @Autowired
    private UserController userController;

    @AfterEach
    void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    @DirtiesContext
    void testFindUserByIdSuccessfull() {
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("test@oc.com");
        mockUser.setFirstName("John");
        mockUser.setLastName("Doe");

        userRepository.save(mockUser);

        ResponseEntity<?> response = userController.findById(String.valueOf(mockUser.getId()));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(UserDto.class, response.getBody());

        UserDto userDto = (UserDto) response.getBody();
        assertEquals(mockUser.getId(), userDto.getId());
        assertEquals(mockUser.getEmail(), userDto.getEmail());
        assertEquals(mockUser.getFirstName(), userDto.getFirstName());
        assertEquals(mockUser.getLastName(), userDto.getLastName());
    }

    @Test
    @DirtiesContext
    void testFindUserByIdNotFound() {
        ResponseEntity<?> response = userController.findById("1");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DirtiesContext
    void testFindUserByIdBadRequest() {
        ResponseEntity<?> response = userController.findById("notId");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DirtiesContext
    void testSaveUserSuccessfull() {
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("test@oc.com");
        mockUser.setFirstName("John");
        mockUser.setLastName("Doe");
        mockUser.setPassword(passwordEncoder.encode("password1234"));

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(mockUser.getEmail());
        loginRequest.setPassword("password1234");

        userRepository.save(mockUser);
        authController.authenticateUser(loginRequest);

        ResponseEntity<?> response = userController.save(String.valueOf(mockUser.getId()));

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DirtiesContext
    void testSaveUserUnauthorized() {
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("test@oc.com");
        mockUser.setFirstName("John");
        mockUser.setLastName("Doe");
        mockUser.setPassword(passwordEncoder.encode("password1234"));

        User mockUser2 = new User();
        mockUser2.setId(2L);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(mockUser.getEmail());
        loginRequest.setPassword("password1234");

        userRepository.save(mockUser);
        userRepository.save(mockUser2);
        authController.authenticateUser(loginRequest);

        ResponseEntity<?> response = userController.save(String.valueOf(mockUser2.getId()));

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    @DirtiesContext
    void testSaveUserNotFound() {
        ResponseEntity<?> response = userController.save("1");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DirtiesContext
    void testSaveUserBadRequest() {
        ResponseEntity<?> response = userController.save("notId");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
