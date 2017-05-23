package com.viseo.companion.dao;

import com.viseo.companion.domain.Uzer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class UzerRepository {

    @PersistenceContext
    EntityManager em;

    public Uzer addUzer(Uzer uzer) {
        em.persist(uzer);
        return uzer;
    }

    public boolean deleteUzer(Uzer uzer) {
        uzer = em.find(Uzer.class, uzer.getId());
        if (uzer != null) {
            em.remove(uzer);
            return true;
        }
        return false;
    }

    public Uzer updateUzer(Uzer uzer) {
        return em.merge(uzer);
    }

    public Uzer getUzer(long id) {
        List<Uzer> result = em.createQuery("select a from Uzer a left join fetch a.roles where a.id = :id", Uzer.class)
                .setParameter("id", id)
                .getResultList();
        if (result.iterator().hasNext())
            return result.iterator().next();
        return null;
    }

    public List<Uzer> getUzers() {
        return em.createQuery("select distinct a from Uzer a left join fetch a.roles", Uzer.class).getResultList();
    }

    public Uzer getUserByEmail(String email) {
        List<Uzer> result = em.createQuery("select c from Uzer c where c.email like :email", Uzer.class)
                .setParameter("email", "%" + email + "%")
                .getResultList();
        if (result.iterator().hasNext())
            return result.iterator().next();
        return null;
    }
}
