package com.viseo.companion.dao;

import com.viseo.companion.domain.Notation;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class NotationRepository {
    @PersistenceContext
    EntityManager em;

    public Notation addNotation(Notation notation) {
        em.persist(notation);
        return notation;
    }

    public String getNotationByEvent(long eventId) {

        return em.createQuery(
                "select avg(n.notation) from Notation n left join n.event e where e.id=:id", Double.class)
                .setParameter("id", eventId)
                .getSingleResult().toString();
    }

    public Notation updateNotation(Notation notation) {
        return em.merge(notation);
    }

    public Notation getNotation(long id) {
        List<Notation> result = em.createQuery("select n from Notation n where n.id = :id", Notation.class)
                .setParameter("id", id)
                .getResultList();
        if (result.iterator().hasNext())
            return result.iterator().next();
        return null;
    }
}
