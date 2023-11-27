package ru.mooncess.pizzeriacoursepaper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mooncess.pizzeriacoursepaper.dto.RegistrationUserDto;
import ru.mooncess.pizzeriacoursepaper.dto.UserDto;
import ru.mooncess.pizzeriacoursepaper.entities.Basket;
import ru.mooncess.pizzeriacoursepaper.entities.User;
import ru.mooncess.pizzeriacoursepaper.exceptions.AppError;
import ru.mooncess.pizzeriacoursepaper.repositories.BasketRepository;
import ru.mooncess.pizzeriacoursepaper.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private BasketRepository basketRepository;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setBasketRepository(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("The user '%s' is not found.", username)
        ));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
        );
    }

    public User createNewUser(RegistrationUserDto registrationUserDto) {
        User user = new User();
        Basket basket = new Basket();
        basket = basketRepository.save(basket);
        user.setUsername(registrationUserDto.getUsername());
        user.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));
        user.setRoles(List.of(roleService.getUserRole()));
        user.setBasket(basket);
        return userRepository.save(user);
    }

    public ResponseEntity<?> changeUsername(String username, String newUsername) {
        if (!newUsername.contains("@")) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Incorrect email address"), HttpStatus.BAD_REQUEST);
        }
        if (findByUsername(newUsername).isPresent()) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "A user with the specified email address already exists"), HttpStatus.BAD_REQUEST);
        }
        Optional<User> optionalUser = findByUsername(username);
        User user = optionalUser.get();
        user.setUsername(newUsername);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(new UserDto(user.getId(), user.getUsername()));
    }
}
