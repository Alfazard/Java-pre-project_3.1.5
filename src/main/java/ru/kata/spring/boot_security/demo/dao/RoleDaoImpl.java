package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Alfazard on 14.08.2023
 */
@Repository
public class RoleDaoImpl implements RoleDao{
    @PersistenceContext(unitName = "entityManagerFactory")
    private final EntityManager entityManager;

    public RoleDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Set<Role> findAll() {
        return entityManager.createQuery("FROM Role", Role.class).getResultStream().collect(Collectors.toSet());
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void save(Role role) {
        entityManager.persist(role);
    }
}
