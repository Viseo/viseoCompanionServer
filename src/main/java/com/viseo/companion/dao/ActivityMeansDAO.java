package com.viseo.companion.dao;


import com.viseo.companion.domain.ActivityMeans;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class ActivityMeansDAO {
    @PersistenceContext
    EntityManager em;
    public List<ActivityMeans> getMeansByActivity (long activityId) {

        List<ActivityMeans> meansByActivitys= em.createQuery(
                "select am from ActivityMeans am where am.activity.id = :id", ActivityMeans.class)
                .setParameter("id", activityId)
                .getResultList();
        return meansByActivitys;
    }

    public ActivityMeans addMeansByActivity(ActivityMeans  mean) {
        try {
            em.persist(mean);

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return mean;
    }
}
