package com.viseo.companion.dao;

import com.viseo.companion.domain.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class UserRepository {

    @PersistenceContext
    EntityManager em;

    public User addUser(User user) {
        em.persist(user);
        return user;
    }

    public List<User> getUsers() {
        return em.createQuery("select distinct a from User a left join fetch a.roles", User.class).getResultList();
    }

    public User getUser(long id) {
        List<User> result = em.createQuery("select a from User a left join fetch a.roles where a.id = :id", User.class)
                .setParameter("id", id)
                .getResultList();
        if (result.iterator().hasNext())
            return result.iterator().next();
        return null;
    }

    public User getUserByEmail(String email) {
        List<User> result = em.createQuery("select c from User c where c.email like :email", User.class)
                .setParameter("email", "%" + email + "%")
                .getResultList();
        if (result.iterator().hasNext())
            return result.iterator().next();
        return null;
    }

    public User updateUser(User user) {
        return em.merge(user);
    }

    public void deleteUser(User user) {
        user = em.find(User.class, user.getId());
        em.remove(user);
    }
}
