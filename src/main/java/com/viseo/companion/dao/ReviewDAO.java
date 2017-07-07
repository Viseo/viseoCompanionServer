package com.viseo.companion.dao;

import com.viseo.companion.domain.Event;
import com.viseo.companion.domain.Review;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class ReviewDAO {
    @PersistenceContext
    EntityManager em;

    public Review addReview(Review review) {
        em.persist(review);
        return review;
    }

    public String[] getEventRating(long eventId) {
        String[] rating=new String[2];
        String count=em.createQuery(
                "select count(n) from Review n join n.event e where e.id=:id", Long.class)
                .setParameter("id", eventId)
                .getSingleResult().toString();
        String average= em.createQuery(
                "select COALESCE(avg(rv.rating),0) from Review rv left join rv.event ev where ev.id=:id ", Double.class)
                .setParameter("id", eventId).getSingleResult().toString();
        rating[0]=count;
        rating[1]=average;
        return rating;
    }

    public Review updateReview(Review review) {
        return em.merge(review);
    }

    public Review getReview(long id) {
        List<Review> result = em.createQuery("select n from Review n where n.id = :id", Review.class)
                .setParameter("id", id)
                .getResultList();
        if (result.iterator().hasNext()) {
            return result.iterator().next();
        }
        return null;
    }


    public List<Event> getEventsReviewed(long id) {
        List<Event> result = em.createQuery("select r.event from Review r where r.user.id = :id", Event.class)
                .setParameter("id", id)
                .getResultList();
        return result;
    }
}
