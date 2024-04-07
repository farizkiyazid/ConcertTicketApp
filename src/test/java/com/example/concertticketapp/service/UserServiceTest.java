package com.example.concertticketapp.service;

import com.example.concertticketapp.model.User;
import com.example.concertticketapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void insertUser() {
        User user = new User();
        user.setName("Test");
        user.setEmail("test@example.com");

        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.insertUser(user);

        assertNotNull(savedUser);
        assertEquals("Test", savedUser.getName());
        assertEquals("test@example.com", savedUser.getEmail());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void getUserById() {
        long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setName("Test");
        user.setEmail("test@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User retrievedUser = userService.getUser(userId);

        assertNotNull(retrievedUser);
        assertEquals("Test", retrievedUser.getName());
        assertEquals("test@example.com", retrievedUser.getEmail());
    }

    @Test
    void getAllUsers() {
        User user1 = new User();
        user1.setId(1L);
        user1.setName("Test1");
        user1.setEmail("test1@example.com");

        User user2 = new User();
        user2.setId(2L);
        user2.setName("Test2");
        user2.setEmail("test2@example.com");

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<User> userList = userService.getAllUsers();

        assertNotNull(userList);
        assertEquals(2, userList.size());
        assertEquals("Test1", userList.get(0).getName());
        assertEquals("Test2", userList.get(1).getName());
    }

    @Test
    void updateUser() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setName("Existing");
        existingUser.setEmail("existing@example.com");

        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setName("Updated");
        updatedUser.setEmail("updated@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(updatedUser);

        User result = userService.updateUser(updatedUser);

        assertNotNull(result);
        assertEquals("Updated", result.getName());
        assertEquals("updated@example.com", result.getEmail());
    }

    @Test
    void updateUser_UserNotFound() {
        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setName("Updated");
        updatedUser.setEmail("updated@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        User result = userService.updateUser(updatedUser);

        assertNull(result);
    }

    @Test
    void deleteUserById() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setName("Existing");
        existingUser.setEmail("existing@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));

        boolean result = userService.deleteUserById(1L);

        assertTrue(result);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteUserById_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = userService.deleteUserById(1L);

        assertFalse(result);
        verify(userRepository, never()).deleteById(1L);
    }

    @Test
    void insertUsers() {
        User user1 = new User();
        user1.setName("Test1");
        user1.setEmail("test1@example.com");

        User user2 = new User();
        user2.setName("Test2");
        user2.setEmail("test2@example.com");

        when(userRepository.saveAll(Arrays.asList(user1, user2))).thenReturn(Arrays.asList(user1, user2));

        List<User> savedUsers = userService.insertUsers(Arrays.asList(user1, user2));

        assertNotNull(savedUsers);
        assertEquals(2, savedUsers.size());
        assertEquals("Test1", savedUsers.get(0).getName());
        assertEquals("Test2", savedUsers.get(1).getName());

        verify(userRepository, times(1)).saveAll(Arrays.asList(user1, user2));
    }
}