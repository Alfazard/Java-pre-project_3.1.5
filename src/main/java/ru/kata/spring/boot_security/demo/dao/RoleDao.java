package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

/**
 * @author Alfazard on 14.08.2023
 */
public interface RoleDao {
    List<Role> findAll();
    void save(Role role);
}
