package ru.kata.spring.boot_security.demo.init;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * @author Alfazard on 10.09.2023
 */
@Component
public class DataBaseInit {

    private final UserService userService;

    public DataBaseInit(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    @Transactional(rollbackFor = Exception.class)
    public void postConstruct() {
        var roleAdmin = Set.of(new Role("ROLE_ADMIN"));
        var roleUser = Set.of(new Role("ROLE_USER"));

        User admin = new User("admin", "admin",
                20, "admin@mail.ru", "admin", roleAdmin);
        User user = new User("user", "user",
                30, "user@mail.ru", "user", roleUser);
        userService.saveUser(admin);
        userService.saveUser(user);
    }
}