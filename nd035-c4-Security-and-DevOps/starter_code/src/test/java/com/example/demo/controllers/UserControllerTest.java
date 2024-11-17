package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.example.demo.controllers.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {
    private UserController userController;

    private final UserRepository userRepository = mock(UserRepository.class);

    private final CartRepository cartRepository = mock(CartRepository.class);

    private final BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void setUp() {
        userController = new UserController();
        TestUtils.injectObjects(userController, "userRepository", userRepository);
        TestUtils.injectObjects(userController, "cartRepository", cartRepository);
        TestUtils.injectObjects(userController, "bCryptPasswordEncoder", encoder);
    }

    @Test
    public void createUser() {
        when(encoder.encode(RAW_PASSWORD)).thenReturn(HASHED_PASSWORD);
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername(USER_NAME);
        r.setPassword(RAW_PASSWORD);
        r.setConfirmPassword(RAW_PASSWORD);
        final ResponseEntity<User> response = userController.createUser(r);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        User user = response.getBody();
        assertNotNull(user);
        assertEquals(0, user.getId());
        assertEquals(USER_NAME, user.getUsername());
        assertEquals(HASHED_PASSWORD, user.getPassword());
    }

    @Test
    public void findById() {
        when(encoder.encode(RAW_PASSWORD)).thenReturn(HASHED_PASSWORD);
        CreateUserRequest req = new CreateUserRequest();
        req.setUsername(USER_NAME);
        req.setPassword(RAW_PASSWORD);
        req.setConfirmPassword(RAW_PASSWORD);
        final ResponseEntity<User> response = userController.createUser(req);
        User user = response.getBody();
        when(userRepository.findById(0L)).thenReturn(java.util.Optional.ofNullable(user));

        final ResponseEntity<User> userResponseEntity = userController.findById(0L);

        User foundUser = userResponseEntity.getBody();
        assertNotNull(foundUser);
        assertEquals(0, foundUser.getId());
        assertEquals(USER_NAME, foundUser.getUsername());
        assertEquals(HASHED_PASSWORD, foundUser.getPassword());
    }

    @Test
    public void findByUserName() {
        when(encoder.encode(RAW_PASSWORD)).thenReturn(HASHED_PASSWORD);
        CreateUserRequest createReq = new CreateUserRequest();
        createReq.setUsername(USER_NAME);
        createReq.setPassword(RAW_PASSWORD);
        createReq.setConfirmPassword(RAW_PASSWORD);
        final ResponseEntity<User> response = userController.createUser(createReq);
        User user = response.getBody();
        when(userRepository.findByUsername(USER_NAME)).thenReturn(user);

        final ResponseEntity<User> userResponseEntity = userController.findByUserName(USER_NAME);

        User foundUser = userResponseEntity.getBody();
        assertNotNull(foundUser);
        assertEquals(0, foundUser.getId());
        assertEquals(USER_NAME, foundUser.getUsername());
        assertEquals(HASHED_PASSWORD, foundUser.getPassword());
    }
}