package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.service.AdminService;

import java.security.Principal;

/**
 * @author Alfazard on 09.08.2023
 */
@Controller
public class UserController {
    private final AdminService adminService;

    public UserController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/user")
    public String userPage(Model model, Principal principal) {
        model.addAttribute("user",adminService.getUserByUsername(principal.getName()));
        return "user";
    }
}