package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;


/**
 * @author Alfazard on 10.08.2023
 */
@Controller
public class AuthController {

    private final UserValidator userValidator;
    private final UserService userService;
    private final RoleService roleService;

    public AuthController(UserValidator userValidator, UserService userService, RoleService roleService) {
        this.userValidator = userValidator;
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/index")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/registration")
    public String userRegistrationPage(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("rolesList", roleService.getRolesList());
        return "registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("user") @Valid User user,
                                      BindingResult bindingResult, Model model){
        userValidator.validate(user, bindingResult);
        if(bindingResult.hasErrors()) {
            model.addAttribute("rolesList", roleService.getRolesList());
            return "registration";
        }

        userService.addUser(user);
        return "redirect:/login";
    }


}