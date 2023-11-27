package ru.mooncess.pizzeriacoursepaper.serviceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.mooncess.pizzeriacoursepaper.dto.UserDto;
import ru.mooncess.pizzeriacoursepaper.entities.User;
import ru.mooncess.pizzeriacoursepaper.repositories.BasketRepository;
import ru.mooncess.pizzeriacoursepaper.repositories.UserRepository;
import ru.mooncess.pizzeriacoursepaper.service.RoleService;
import ru.mooncess.pizzeriacoursepaper.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BasketRepository basketRepository;

    @Mock
    private RoleService roleService;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService();
        userService.setUserRepository(userRepository);
        userService.setBasketRepository(basketRepository);
        userService.setRoleService(roleService);
        userService.setPasswordEncoder(passwordEncoder);
    }

    @Test
    void findByUsername_WithExistingUsername_ShouldReturnUserOptional() {
        // Arrange
        String username = "testUser";
        User user = new User();
        user.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = userService.findByUsername(username);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void findByUsername_WithNonExistingUsername_ShouldReturnEmptyOptional() {
        // Arrange
        String username = "testUser";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act
        Optional<User> result = userService.findByUsername(username);

        // Assert
        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void loadUserByUsername_WithNonExistingUsername_ShouldThrowUsernameNotFoundException() {
        // Arrange
        String username = "testUser";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(username));
        verify(userRepository, times(1)).findByUsername(username);
    }

    // Другие тесты для остальных методов класса UserService

    @Test
    void getAllUsers_ShouldReturnListOfUserDto() {
        // Arrange
        List<User> userList = new ArrayList<>();
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("user1");
        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("user2");
        userList.add(user1);
        userList.add(user2);

        when(userRepository.findAll()).thenReturn(userList);

        // Act
        List<UserDto> result = userService.getAllUsers();

        // Assert
        assertEquals(userList.size(), result.size());
        for (int i = 0; i < userList.size(); i++) {
            assertEquals(userList.get(i).getId(), result.get(i).getId());
            assertEquals(userList.get(i).getUsername(), result.get(i).getUsername());
        }
        verify(userRepository, times(1)).findAll();
    }
}
