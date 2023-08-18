package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.User;


/**
 * @author Alfazard on 08.07.2023
 */
@Service
public class UserValidatorService {
    private final UserDao userDao;
    public UserValidatorService(UserDao userDao) {
        this.userDao = userDao;
    }
    public User checkByUsername(String username) {
        return userDao.findByUsername(username);
    }
}