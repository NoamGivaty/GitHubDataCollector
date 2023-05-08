package com.GitHubDataCollector.service;

import com.GitHubDataCollector.model.User;
import com.GitHubDataCollector.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    public Iterable<User> all() {
        return repository.findAll();
    }

    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    public User save(User user) {
        Optional<User> optionalUser = repository.findByUsername(user.getUsername());
        if (optionalUser.isPresent()) {
            user.updateUser(optionalUser.get());
            return repository.save(optionalUser.get());
        }
        return repository.save(user);
    }

    public void delete(User user) {
        repository.delete(user);
    }
}
