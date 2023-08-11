package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RegistrationService;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;

/**
 * @author Alfazard on 10.08.2023
 */
@Controller
public class AuthController {

    private final UserValidator userValidator;
    private final RegistrationService registrationService;
    private final RoleService roleService;

    public AuthController(UserValidator userValidator, RegistrationService registrationService, RoleService roleService) {
        this.userValidator = userValidator;
        this.registrationService = registrationService;
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
    public String userRegistrationPage(@ModelAttribute("user") User user, Model role) {
        role.addAttribute("rolesList", roleService.getRolesList());

        return "registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("user") @Valid User user,
                                      BindingResult bindingResult, Model role){

        userValidator.validate(user, bindingResult);
        if(bindingResult.hasErrors()) {
            role.addAttribute("rolesList", roleService.getRolesList());
            return "registration";
        }
        registrationService.userRegistration(user);

        return "redirect:/login";
    }


}