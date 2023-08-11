package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;


/**
 * @author Alfazard on 08.07.2023
 */
@Service
public class UserValidatorService {
    private final UserRepository userRepository;
    public UserValidatorService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User checkByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}