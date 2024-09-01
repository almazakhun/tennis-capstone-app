package com.almaz.capstone_project.service;

import com.almaz.capstone_project.model.Registration;
import com.almaz.capstone_project.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAllUsers();
    User createUser(User user);
    User findUserById(long id);
    void deleteUser(long id);
    void assignRegistration(long id, Registration registration);
    Optional<User> findByEmail(String email);

    User findByUsername(String username);
}
