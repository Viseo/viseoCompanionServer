package com.viseo.companion.dao;

import com.viseo.companion.domain.Notation;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class NotationRepository {
    @PersistenceContext
    EntityManager em;

    public Notation addNotation(Notation notation) {
        em.persist(notation);
        return notation;
    }

    public String getNotation(long eventId) {
        return em.createQuery(
                "select avg(n.notation) from Notation n where n.event=:id", Notation.class)
                .setParameter("id", eventId)
                .getSingleResult().toString();
    }

    public Notation updateNotation(Notation notation) {
        return em.merge(notation);
    }


}
