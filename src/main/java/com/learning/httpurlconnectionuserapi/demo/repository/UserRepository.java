package com.learning.httpurlconnectionuserapi.demo.repository;

import com.learning.httpurlconnectionuserapi.demo.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {
    private List<User> users = new ArrayList<>();
    private long nextId = 1;

    public List<User> findAll() {
        return users;
    }

    public Optional<User> findById(Long id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst();
    }

    public User save(User user) {
        user.setId(nextId++);
        users.add(user);
        return user;
    }

    public void delete(User user) {
        users.remove(user);
    }

    public User update(User updatedUser) {
        Optional<User> userOpt = findById(updatedUser.getId());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            return user;
        } else {
            return null;
        }
    }
}
