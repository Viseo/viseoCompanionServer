package com.viseo.companion.dao;

import com.viseo.companion.domain.Comment;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
    public List<Comment> getComments() {
        return em.createQuery(
                "select parentComment from Comment parentComment where parentComment not in (select children from Comment comment join comment.children children)", Comment.class)
                .getResultList();
    }

    @Transactional
    public Comment getComment(long id) {
        List<Comment> result = em.createQuery("select a from Comment a where a.id = :id", Comment.class)
                .setParameter("id", id)
                .getResultList();
        if (result.size() > 0)
            return result.iterator().next();
        return null;
    }


    @Transactional
    public List<Comment> getCommentsByEvent(Long eventId) {
        return em.createQuery("select a from  Comment a left join fetch a.event p where p.id = :id order by a.datetime", Comment.class)
                .setParameter("id", eventId)
                .getResultList();
    }

    @Transactional
    public Comment getParentFromChildId(long childCommentId) {
        List<Comment> result = em.createQuery(
                "select a from Comment a left join fetch a.children c where c.id = :id", Comment.class)
                .setParameter("id", childCommentId)
                .getResultList();
        if(result.iterator().hasNext()){
            return  result.iterator().next();
        }
        return null;
    }

    @Transactional
    public Comment updateComment(Comment comment) {
        try {
            return em.merge(comment);
        } catch (EntityExistsException e) {
            throw new RuntimeException(e);
        }
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
}
