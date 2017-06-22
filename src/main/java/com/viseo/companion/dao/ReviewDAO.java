package com.viseo.companion.dao;

import com.viseo.companion.domain.Event;
import com.viseo.companion.domain.Review;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    public String getEventRating(long eventId) {
        return em.createQuery(
                "select avg(n.rating) from Review n left join n.event e where e.id=:id", Double.class)
                .setParameter("id", eventId)
                .getSingleResult().toString();
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
