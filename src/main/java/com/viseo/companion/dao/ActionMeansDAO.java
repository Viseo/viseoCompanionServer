package com.viseo.companion.dao;

import com.viseo.companion.domain.ActionMeans;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class ActionMeansDAO {
    @PersistenceContext
    EntityManager em;
    public List<ActionMeans> getMeansByAction (long actionId) {

        List<ActionMeans> meansByActions= em.createQuery(
                "select am from ActionMeans am where am.action.id = :id", ActionMeans.class)
                .setParameter("id", actionId)
                .getResultList();
        return meansByActions;
    }

    public ActionMeans addMeansByAction(ActionMeans  mean) {
        try {
            em.persist(mean);

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return mean;
    }
}
