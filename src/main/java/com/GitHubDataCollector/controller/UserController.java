package com.GitHubDataCollector.controller;

import com.GitHubDataCollector.model.User;
import com.GitHubDataCollector.model.UserIn;
import com.GitHubDataCollector.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/User")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(userService.all(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getOneUser(@PathVariable Long id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> insertUser(@RequestBody UserIn userIn) {
        User user = userIn.toUser();
        user = userService.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserIn user) {
        Optional<User> dbUser = userService.findById(id);
        if (dbUser.isEmpty()) throw new RuntimeException("User with id: " + id + " not found");
        user.updateUser(dbUser.get());
        User updatedUser = userService.save(dbUser.get());
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Optional<User> dbUser = userService.findById(id);
        if (dbUser.isEmpty()) throw new RuntimeException("User with id: " + id + " not found");
        userService.delete(dbUser.get());
        return new ResponseEntity<>("DELETED", HttpStatus.OK);
    }
}