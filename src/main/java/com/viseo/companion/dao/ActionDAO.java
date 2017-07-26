package com.viseo.companion.dao;

import com.viseo.companion.domain.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class ActionDAO {


    @PersistenceContext
    EntityManager em;

    public Action addAction (Action action) {
        em.persist(action);
        return action;
    }

    public List<Action> getActions () {
        return em.createQuery(
                "select distinct a from Action a", Action.class)
                .getResultList();
    }
}
