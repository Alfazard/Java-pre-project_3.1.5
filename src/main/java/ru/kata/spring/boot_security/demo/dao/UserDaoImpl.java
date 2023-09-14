package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Alfazard on 08.07.2023
 */
@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext(unitName = "entityManagerFactory")
    private final EntityManager entityManager;

    public UserDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("FROM User", User.class).getResultList();
    }

    @Override
    public void save(User user) {
        if (user.getId() != null) {
            entityManager.merge(user);
        } else {
            entityManager.persist(user);
        }
    }

    @Override
    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    public User findByEmail(String email) {
        return entityManager
                .createQuery(
                        "SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.email=:email", User.class
                )
                .setParameter("email", email)
                .getResultList()
                .stream().findFirst().orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        entityManager.find(User.class, id).getRoles().clear();
        entityManager
                .createQuery("DELETE FROM User WHERE id =:userId")
                .setParameter("userId", id)
                .executeUpdate();
    }
}
