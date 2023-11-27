package ru.mooncess.pizzeriacoursepaper.serviceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.mooncess.pizzeriacoursepaper.entities.Role;
import ru.mooncess.pizzeriacoursepaper.repositories.RoleRepository;
import ru.mooncess.pizzeriacoursepaper.service.RoleService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    private RoleService roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        roleService = new RoleService(roleRepository);
    }

    @Test
    void getUserRole_ShouldReturnUserRole() {
        // Arrange
        String roleName = "ROLE_USER";
        Role userRole = new Role();
        userRole.setName(roleName);

        when(roleRepository.findByName(roleName)).thenReturn(Optional.of(userRole));

        // Act
        Role result = roleService.getUserRole();

        // Assert
        assertEquals(userRole, result);
        verify(roleRepository, times(1)).findByName(roleName);
    }
}
