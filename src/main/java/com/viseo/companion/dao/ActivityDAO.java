package com.viseo.companion.dao;

import com.viseo.companion.domain.Action;
import com.viseo.companion.domain.Activity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class ActivityDAO {


    @PersistenceContext
    EntityManager em;

    public Activity addActivity (Activity activity) {
        em.persist(activity);
        return activity;
    }

    public List<Activity> getActivities () {
        return em.createQuery(
                "select distinct a from Activity a", Activity.class)
                .getResultList();
    }
    public void deleteActivity(Activity activity) {
        activity = em.find(Activity.class, activity.getId());
        em.remove(activity);
    }
    public Activity getActivityById (long id) {
        return em.find(Activity.class, id);
    }

    public Activity updateActivity(Activity activity) {
        return em.merge(activity);
    }
}
