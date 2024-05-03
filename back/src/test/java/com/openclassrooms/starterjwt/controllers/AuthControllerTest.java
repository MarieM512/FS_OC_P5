// package com.openclassrooms.starterjwt.controllers;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.mock;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;

// import java.util.Optional;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.crypto.password.PasswordEncoder;

// import com.openclassrooms.starterjwt.models.User;
// import com.openclassrooms.starterjwt.payload.request.LoginRequest;
// import com.openclassrooms.starterjwt.payload.request.SignupRequest;
// import com.openclassrooms.starterjwt.payload.response.JwtResponse;
// import com.openclassrooms.starterjwt.payload.response.MessageResponse;
// import com.openclassrooms.starterjwt.repository.UserRepository;
// import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
// import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;

// public class AuthControllerTest {

//     @InjectMocks
//     private AuthController controller;

//     @Mock
//     private AuthenticationManager authenticationManager;

//     @Mock
//     private JwtUtils jwtUtils;

//     @Mock
//     private UserRepository repository;

//     @Mock
//     private PasswordEncoder passwordEncoder;

//     @BeforeEach
//     void setUp() {
//         MockitoAnnotations.openMocks(this);
//     }

//     @Test
//     void testLogin() {
//         LoginRequest mockLoginRequest = new LoginRequest();
//         mockLoginRequest.setEmail("test@oc.com");
//         mockLoginRequest.setPassword("password");

//         Authentication authentication = mock(Authentication.class);
//         UsernamePasswordAuthenticationToken mockAuthenticationToken = new UsernamePasswordAuthenticationToken(mockLoginRequest.getEmail(), mockLoginRequest.getPassword());

//         String jwtToken = "JwtToken";

//         UserDetailsImpl mockUserDetailsImpl = new UserDetailsImpl(1L, mockLoginRequest.getEmail(), "John", "Doe", true, mockLoginRequest.getPassword());
//         User mockUser = new User(mockLoginRequest.getEmail(), "Doe", "John", mockLoginRequest.getPassword(), true);
//         mockUser.setId(1L);
       
//         authenticationManager.authenticate(mockAuthenticationToken);
//         when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
//         when(jwtUtils.generateJwtToken(authentication)).thenReturn(jwtToken);
//         when(authentication.getPrincipal()).thenReturn(mockUserDetailsImpl);
//         when(repository.findByEmail(mockLoginRequest.getEmail())).thenReturn(Optional.of(mockUser));

//         ResponseEntity<?> response = controller.authenticateUser(mockLoginRequest);

//         assertEquals(HttpStatus.OK, response.getStatusCode());
//         JwtResponse jwtResponse = (JwtResponse) response.getBody();
//         assertEquals(jwtToken, jwtResponse.getToken());

//         assertEquals(mockUser.getId(), jwtResponse.getId());
//         assertEquals(mockUser.getEmail(), jwtResponse.getUsername());
//         assertEquals(mockUser.getFirstName(), jwtResponse.getFirstName());
//         assertEquals(mockUser.getLastName(), jwtResponse.getLastName());
//     }

//     @Test
//     void testRegisterSuccessfull() {
//         SignupRequest mockSignupRequest = new SignupRequest();
//         mockSignupRequest.setEmail("test@oc.com");
//         mockSignupRequest.setFirstName("John");
//         mockSignupRequest.setLastName("Doe");
//         mockSignupRequest.setPassword("password");

//         String passwordEncoded = "passwordEncoded";
//         when(passwordEncoder.encode(any(CharSequence.class))).thenReturn(passwordEncoded);
//         User mockUser = new User(mockSignupRequest.getEmail(), mockSignupRequest.getLastName(), mockSignupRequest.getFirstName(), passwordEncoder.encode(mockSignupRequest.getPassword()), false);
        
//         when(repository.existsByEmail(mockSignupRequest.getEmail())).thenReturn(false);

//         ResponseEntity<?> response = controller.registerUser(mockSignupRequest);

//         assertEquals(HttpStatus.OK, response.getStatusCode());
//         MessageResponse messageResponse = (MessageResponse) response.getBody();
//         assertEquals("User registered successfully!", messageResponse.getMessage());

//         verify(repository).existsByEmail(mockSignupRequest.getEmail());
//         verify(repository).save(mockUser);
//     }

//     @Test
//     void testRegisterWithEmailAlreadyTaken() {
//         SignupRequest mockSignupRequest = new SignupRequest();
//         mockSignupRequest.setEmail("test@oc.com");
//         mockSignupRequest.setFirstName("John");
//         mockSignupRequest.setLastName("Doe");
//         mockSignupRequest.setPassword("password");
        
//         when(repository.existsByEmail(mockSignupRequest.getEmail())).thenReturn(true);

//         ResponseEntity<?> response = controller.registerUser(mockSignupRequest);

//         assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//         MessageResponse messageResponse = (MessageResponse) response.getBody();
//         assertEquals("Error: Email is already taken!", messageResponse.getMessage());

//         verify(repository).existsByEmail(mockSignupRequest.getEmail());
//     }
// }
