package com.example.concertticketapp.controller;

import com.example.concertticketapp.model.APIResponse;
import com.example.concertticketapp.model.ResponseStatus;
import com.example.concertticketapp.model.User;
import com.example.concertticketapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/")
    public String getDefault() {
        return "Hello world!";
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/user/{id}")
    public APIResponse<User> getUser(@PathVariable long id) {
        User user = userService.getUser(id);
        if (user == null) {
            return new APIResponse<>(
                  com.example.concertticketapp.model.ResponseStatus.ERROR,
                  "User doesn't exist",
                  null
            );
        } else {
            return new APIResponse<>(
                  ResponseStatus.SUCCESS,
                  null,
                  user
            );
        }
    }

    @PostMapping("/user/add")
    public APIResponse<User> addUser(@RequestBody User user) {
        User newUser = userService.insertUser(user);
        if (newUser == null) {
            return new APIResponse<>(
                  com.example.concertticketapp.model.ResponseStatus.ERROR,
                  "Insert user failed",
                  null
            );
        } else {
            return new APIResponse<>(
                  ResponseStatus.SUCCESS,
                  null,
                  newUser
            );
        }
    }

    @PostMapping("/users/add")
    public APIResponse<List<User>> addUser(@RequestBody List<User> users) {
        List<User> insertedUsers = userService.insertUsers(users);
        if (insertedUsers == null || insertedUsers.isEmpty()) {
            return new APIResponse<>(
                  ResponseStatus.ERROR,
                  "Failed inserting all users",
                  null
            );
        }
        return new APIResponse<>(
              ResponseStatus.SUCCESS,
              null,
              insertedUsers
        );
    }

    @PutMapping("/user/update")
    public APIResponse<User> updateUser(@RequestBody User user) {
        User updatedUser = userService.updateUser(user);
        if (updatedUser == null) {
            return new APIResponse<>(
                  com.example.concertticketapp.model.ResponseStatus.ERROR,
                  "Update user failed",
                  null
            );
        } else {
            return new APIResponse<>(
                  ResponseStatus.SUCCESS,
                  null,
                  updatedUser
            );
        }
    }

    @DeleteMapping("/user/remove/{id}")
    public APIResponse removeUser(@PathVariable long id) {
        boolean removalStatus = userService.deleteUserById(id);
        return removalStatus ? new APIResponse(ResponseStatus.SUCCESS, null, null)
              : new APIResponse(ResponseStatus.ERROR, "User with id " + id + " does not exist", null);
    }

}
