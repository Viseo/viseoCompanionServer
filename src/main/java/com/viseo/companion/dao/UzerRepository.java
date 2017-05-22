package com.viseo.companion.dao;

import com.viseo.companion.domain.Role;
import com.viseo.companion.domain.Uzer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;

@Repository
public class UzerRepository {

    @PersistenceContext
    EntityManager em;

    public void addUzer(String email, String firstName, String lastName, String password) {
        Uzer uzer = new Uzer();
        uzer.setPassword(password);
        uzer.setFirstName(firstName);
        uzer.setLastName(lastName);
        uzer.setEmail(email);
        Role role = new Role();
        uzer.getRoles().add(role);
        em.persist(uzer);
    }

    @Transactional
    public boolean addUzer(Uzer uzer) {
        try {
            uzer = em.merge(uzer);
            em.persist(uzer);
        } catch (EntityExistsException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Transactional
    public boolean deleteUzer(Uzer uzer) {
        try {
            em.remove(em.contains(uzer) ? uzer : em.merge(uzer));
            em.flush();
        } catch (EntityExistsException e) {
            return false;
        }
        return true;
    }

    @Transactional
    public Uzer updateUzer(Uzer uzer) {
        try {
            uzer = em.merge(uzer);

        } catch (EntityExistsException e) {
            throw new RuntimeException(e);
        }
        return uzer;
    }

    @Transactional
    public Uzer getUzer(long id) {
        Query query = em.createQuery("select a from Uzer a left join fetch a.roles where a.id = :id");
        query.setParameter("id", id);
        List<Uzer> result = query.getResultList();
        if (result.size() > 0)
            return result.iterator().next();
        return null;
    }

    @Transactional
    public List<Uzer> getUzers() {
        return em.createQuery("select distinct a from Uzer a left join fetch a.roles", Uzer.class).getResultList();
    }

    @Transactional
    public Uzer getUserByEmail(String email) {
        Query query = em.createQuery("select c from Uzer c where c.email like :email");
        query.setParameter("email", "%" + email + "%");
        List<Uzer> result = query.getResultList();
        if (result.size() > 0)
            return result.iterator().next();
        return null;
    }

    @Transactional
    public Uzer checkCredentials(String email, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Uzer user = getUserByEmail(email);
            if (encoder.matches(password, user.getPassword()))
                return user;
        return null;
    }

//    @Transactional
//    public Uzer getUserIdByEmail(String email) {
//        Collection<Uzer> list = getUserByEmail(email);
//        return getUserByEmail(email) ? getUserByEmail(email).getUzerId() : null;
//    }
}
