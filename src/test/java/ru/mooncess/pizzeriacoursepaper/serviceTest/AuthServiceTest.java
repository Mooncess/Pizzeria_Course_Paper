package ru.mooncess.pizzeriacoursepaper.serviceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import ru.mooncess.pizzeriacoursepaper.dto.JwtRequest;
import ru.mooncess.pizzeriacoursepaper.dto.JwtResponse;
import ru.mooncess.pizzeriacoursepaper.dto.RegistrationUserDto;
import ru.mooncess.pizzeriacoursepaper.dto.UserDto;
import ru.mooncess.pizzeriacoursepaper.entities.User;
import ru.mooncess.pizzeriacoursepaper.exceptions.AppError;
import ru.mooncess.pizzeriacoursepaper.service.AuthService;
import ru.mooncess.pizzeriacoursepaper.service.UserService;
import ru.mooncess.pizzeriacoursepaper.utils.JwtTokenUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtTokenUtils jwtTokenUtils;

    @Mock
    private AuthenticationManager authenticationManager;

    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authService = new AuthService(userService, jwtTokenUtils, authenticationManager);
    }

    @Test
    void createAuthToken_WithValidJwtRequest_ShouldReturnJwtResponse() {
        // Arrange
        JwtRequest authRequest = new JwtRequest();
        authRequest.setUsername("test@example.com");
        authRequest.setPassword("password");

        UserDetails userDetails = mock(UserDetails.class);
        String token = "test-token";

        when(userService.loadUserByUsername(authRequest.getUsername())).thenReturn(userDetails);
        when(jwtTokenUtils.generateToken(userDetails)).thenReturn(token);

        // Act
        ResponseEntity<?> responseEntity = authService.createAuthToken(authRequest);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(new JwtResponse(token), responseEntity.getBody());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void createAuthToken_WithInvalidJwtRequest_ShouldReturnUnauthorized() {
        // Arrange
        JwtRequest authRequest = new JwtRequest();
        authRequest.setUsername("test@example.com");
        authRequest.setPassword("wrong-password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(BadCredentialsException.class);

        // Act
        ResponseEntity<?> responseEntity = authService.createAuthToken(authRequest);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals(new AppError(HttpStatus.UNAUTHORIZED.value(), "Incorrect email address or password"), responseEntity.getBody());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void createNewUser_WithValidRegistrationUserDto_ShouldReturnUserDto() {
        // Arrange
        RegistrationUserDto registrationUserDto = new RegistrationUserDto();
        registrationUserDto.setUsername("test@example.com");
        registrationUserDto.setPassword("password");
        registrationUserDto.setConfirmPassword("password");

        User user = new User();
        user.setId(1L);
        user.setUsername(registrationUserDto.getUsername());

        when(userService.createNewUser(registrationUserDto)).thenReturn(user);

        // Act
        ResponseEntity<?> responseEntity = authService.createNewUser(registrationUserDto);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(new UserDto(user.getId(), user.getUsername()), responseEntity.getBody());
        verify(userService, times(1)).createNewUser(registrationUserDto);
    }

    @Test
    void createNewUser_WithInvalidEmail_ShouldReturnBadRequest() {
        // Arrange
        RegistrationUserDto registrationUserDto = new RegistrationUserDto();
        registrationUserDto.setUsername("test");
        registrationUserDto.setPassword("password");
        registrationUserDto.setConfirmPassword("password");

        // Act
        ResponseEntity<?> responseEntity = authService.createNewUser(registrationUserDto);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        verify(userService, never()).createNewUser(any());
    }

    @Test
    void createNewUser_WithMismatchedPasswords_ShouldReturnBadRequest() {
        // Arrange
        RegistrationUserDto registrationUserDto = new RegistrationUserDto();
        registrationUserDto.setUsername("test@example.com");
        registrationUserDto.setPassword("password");
        registrationUserDto.setConfirmPassword("wrong-password");

        // Act
        ResponseEntity<?> responseEntity = authService.createNewUser(registrationUserDto);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(new AppError(HttpStatus.BAD_REQUEST.value(), "Passwords do not match"), responseEntity.getBody());
        verify(userService, never()).createNewUser(any());
    }

    @Test
    void createNewUser_WithExistingUsername_ShouldReturnBadRequest() {
        // Arrange
        RegistrationUserDto registrationUserDto = new RegistrationUserDto();
        registrationUserDto.setUsername("test@example.com");
        registrationUserDto.setPassword("password");
        registrationUserDto.setConfirmPassword("password");

        when(userService.findByUsername(registrationUserDto.getUsername())).thenReturn(Optional.of(new User()));

        // Act
        ResponseEntity<?> responseEntity = authService.createNewUser(registrationUserDto);

        // Assert
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(new AppError(HttpStatus.BAD_REQUEST.value(), "A user with the specified email address already exists"), responseEntity.getBody());
        verify(userService, never()).createNewUser(any());
    }
}
