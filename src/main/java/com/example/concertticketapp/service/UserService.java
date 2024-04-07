package com.example.concertticketapp.service;

import com.example.concertticketapp.model.User;
import com.example.concertticketapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User insertUser(User user) {
        return userRepository.save(user);
    }

    public List<User> insertUsers(List<User> users) {
        return userRepository.saveAll(users);
    }

    public User getUser(long id) {
        User user = userRepository.findById(id).orElse(null);

        return userRepository.findById(id).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(User user) {
        User existingUser = userRepository.findById(user.getId()).orElse(null);
        if (existingUser == null) {
            return null;
        }
        existingUser.setEmail(user.getEmail());
        existingUser.setName(user.getName());
        userRepository.save(existingUser);
        return existingUser;
    }

    public boolean deleteUserById(long id) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser == null) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }
}
