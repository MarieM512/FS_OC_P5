package com.openclassrooms.starterjwt.integration;

import com.openclassrooms.starterjwt.controllers.AuthController;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import com.openclassrooms.starterjwt.payload.response.JwtResponse;
import com.openclassrooms.starterjwt.payload.response.MessageResponse;
import com.openclassrooms.starterjwt.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class AuthControllerIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthController authController;

    String email = "test@oc.com";
    String password = "password1234";
    String firstName = "John";
    String lastName = "Doe";

    private User mockUser = new User();
    private LoginRequest mockLoginRequest = new LoginRequest();
    private SignupRequest mockSignupRequest = new SignupRequest();

    @AfterEach
    void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    void testAuthenticateUser() {
        mockLoginRequest.setEmail(email);
        mockLoginRequest.setPassword(password);
        mockUser.setEmail(email);
        mockUser.setLastName(lastName);
        mockUser.setFirstName(firstName);
        mockUser.setPassword(passwordEncoder.encode(password));
        mockUser.setAdmin(true);

        userRepository.save(mockUser);

        ResponseEntity<?> response = authController.authenticateUser(mockLoginRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(JwtResponse.class, response.getBody());

        JwtResponse jwtResponse = (JwtResponse) response.getBody();
        assertNotNull(jwtResponse);
        assertEquals(email, jwtResponse.getUsername());
        assertEquals(firstName, jwtResponse.getFirstName());
        assertEquals(lastName, jwtResponse.getLastName());
        assertTrue(jwtResponse.getAdmin());
        assertNotNull(jwtResponse.getToken());
    }

    @Test
    void testRegisterUser() {
        mockSignupRequest.setEmail(email);
        mockSignupRequest.setPassword(password);
        mockSignupRequest.setLastName(lastName);
        mockSignupRequest.setFirstName(firstName);

        ResponseEntity<?> response = authController.registerUser(mockSignupRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(MessageResponse.class, response.getBody());

        MessageResponse messageResponse = (MessageResponse) response.getBody();
        assertNotNull(messageResponse);
        assertEquals("User registered successfully!", messageResponse.getMessage());
    }

    @Test
    void testRegisterWithEmailAlreadyTaken() {
        mockSignupRequest.setEmail(email);
        mockSignupRequest.setPassword(password);
        mockSignupRequest.setLastName(lastName);
        mockSignupRequest.setFirstName(firstName);

        mockUser.setEmail(email);
        mockUser.setLastName(lastName);
        mockUser.setFirstName(firstName);
        mockUser.setPassword(passwordEncoder.encode(password));
        mockUser.setAdmin(true);

        userRepository.save(mockUser);

        ResponseEntity<?> response = authController.registerUser(mockSignupRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertInstanceOf(MessageResponse.class, response.getBody());

        MessageResponse messageResponse = (MessageResponse) response.getBody();
        assertNotNull(messageResponse);
        assertEquals("Error: Email is already taken!", messageResponse.getMessage());
    }
}