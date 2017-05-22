package com.viseo.companion.dao;

import com.viseo.companion.domain.Comment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class CommentRepository {

    @PersistenceContext
    EntityManager em;

@Transactional
    public boolean addComment(Comment comment) {
        try {
            em.persist(comment);
        } catch (EntityExistsException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    @Transactional
    public boolean deleteComment(Comment comment) {
        try {
            em.remove(em.contains(comment) ? comment : em.merge(comment));
            em.flush();
        } catch (EntityExistsException e) {
            return false;
        }
        return true;
    }



    @Transactional
    public Comment getComment(long id) {
        Query query = em.createQuery("select a from Comment a left join fetch a.evenement where a.id = :id");
        query.setParameter("id", id);
        List<Comment> result = query.getResultList();
        if (result.size() > 0)
            return result.iterator().next();
        return null;
    }


    @Transactional
    public boolean updateComment(Comment comment) {
        try {
            em.merge(comment);
            return true;
        } catch (EntityExistsException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    // refactor to getEventComments
    public List<Comment> getCommentsByEvent(Long eventId) {
        Query query = em.createQuery("select a from  Comment a left join fetch a.evenement p where p.id = :id order by a.datetime");
        query.setParameter("id", eventId);
        return (List<Comment>) query.getResultList();
    }

    @Transactional
    public List<Comment> getComments() {
        return em.createQuery("select distinct a from Comment a left join fetch a.evenement order by a.datetime").getResultList();
    }

}
