package com.viseo.companion.dao;

import com.viseo.companion.domain.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class ActivityDAO {

    @Autowired
    ActivityDAO activityDAO;

    @PersistenceContext
    EntityManager em;

    public Activity addActivity (Activity activity) {
        em.persist(activity);
        return activity;
    }
}
