package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Alfazard on 10.08.2023
 */
@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public AdminServiceImpl(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public List<User> showAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void addUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> userFromDb = userRepository.findById(id);
        return userFromDb.orElse(new User());
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void deleteUser(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        }
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void editUser(Long id, User updatedUser) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User editUser = optionalUser.get();
            editUser.setId(updatedUser.getId());
            editUser.setUsername(updatedUser.getUsername());
            editUser.setEmail(updatedUser.getEmail());
            editUser.setRoles(updatedUser.getRoles());
            if (!editUser.getPassword().equals(updatedUser.getPassword())) {
                editUser.setPassword(encoder.encode(updatedUser.getPassword()));
            }
            userRepository.save(editUser);
        }
    }
}