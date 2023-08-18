package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

/**
 * @author Alfazard on 10.08.2023
 */
public interface UserService {

    List<User> showAllUsers();

    void addUser(User user);

    User getUserById(Long id);

    User getUserByUsername(String username);

    void deleteUser(Long id);

    void editUser(Long id, User updatedUser);

}