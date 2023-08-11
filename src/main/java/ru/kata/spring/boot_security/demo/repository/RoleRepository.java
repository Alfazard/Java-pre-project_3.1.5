package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

/**
 * @author Alfazard on 09.08.2023
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}