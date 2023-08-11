package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

/**
 * @author Alfazard on 09.08.2023
 */
public interface RoleService {

    List<Role> getRolesList();
}