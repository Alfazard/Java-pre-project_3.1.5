package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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
    public List<Role> findAll() {
        return entityManager.createQuery("FROM Role", Role.class).getResultList();
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void save(Role role) {
        entityManager.persist(role);
    }
}
