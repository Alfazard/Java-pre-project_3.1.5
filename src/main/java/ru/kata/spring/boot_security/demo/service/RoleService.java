package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.Set;

/**
 * @author Alfazard on 09.08.2023
 */
public interface RoleService {

    Set<Role> getRolesList();

}