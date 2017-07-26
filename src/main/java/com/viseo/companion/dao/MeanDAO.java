package com.viseo.companion.dao;

import com.viseo.companion.domain.Mean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class MeanDAO {


    @PersistenceContext
    EntityManager em;

    public Mean addMean (Mean mean) {
        em.persist(mean);
        return mean;
    }

    public List<Mean> getMeans () {
        return em.createQuery(
                "select distinct a from Mean a", Mean.class).getResultList();
    }

    public Mean getMeanById(long id) {
        return em.find(Mean.class, id);
    }
}