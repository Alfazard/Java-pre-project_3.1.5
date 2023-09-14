package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

/**
 * @author Alfazard on 08.07.2023
 */
public interface UserDao {
    List<User> findAll();

    void save(User user);

    User findById(Long id);
    User findByEmail(String username);

    void deleteById(Long id);
}
