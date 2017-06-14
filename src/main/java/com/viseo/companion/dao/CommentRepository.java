package com.viseo.companion.dao;

import com.viseo.companion.domain.Comment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

import static javax.persistence.TemporalType.TIMESTAMP;

@Repository
@Transactional
public class CommentRepository {

    @PersistenceContext
    EntityManager em;

    public Comment addComment(Comment comment) {
        em.persist(comment);
        return comment;
    }

    public List<Comment> getComments() {
        return em.createQuery(
                "select parentComment from Comment parentComment where parentComment not in (select children from Comment comment join comment.children children)", Comment.class)
                .getResultList();
    }

    public Comment getComment(long id) {
        List<Comment> result = em.createQuery("select a from Comment a where a.id = :id", Comment.class)
                .setParameter("id", id)
                .getResultList();
        if (result.iterator().hasNext())
            return result.iterator().next();
        return null;
    }

    public List<Comment> getCommentsByEvent(long eventId) {
        return em.createQuery("select a from  Comment a left join fetch a.event p where p.id = :id  and a.publish = true " +
                "and a not in (select c from Comment comment join comment.children c where c.publish = true) order by a.datetime", Comment.class)
                .setParameter("id", eventId)
                .getResultList();
    }

    public List<Comment> getAllCommentsByEvent(Long eventId) {
        return em.createQuery("select a from  Comment a left join fetch a.event p where p.id = :id " +
                "and a not in (select c from Comment comment join comment.children c)  order by a.datetime", Comment.class)
                .setParameter("id", eventId)
                .getResultList();
    }


    public List<Comment> getCommentsByEventAfterDate(long eventId, String after) {
        //todo 1/ remove exclusion of the child, and handle them with a Hashset 2/ Charge users with list to prevent N+1
        return em.createQuery("" +
                "select a from  Comment a left join fetch a.event p left outer join fetch a.children where p.id = :id " +
                "and a.datetime > :after " +
                "and a not in (select c from Comment comment join comment.children c) order by a.datetime", Comment.class)
                .setParameter("id", eventId)
                .setParameter("after", new Date(Long.valueOf(after)), TIMESTAMP)
                .getResultList();
    }

    public Comment getParentFromChildId(long childCommentId) {
        List<Comment> result = em.createQuery(
                "select a from Comment a left join fetch a.children c join a.children x where x.id = :id", Comment.class)
                .setParameter("id", childCommentId)
                .getResultList();
        if (result.iterator().hasNext()) {
            return result.iterator().next();
        }
        return null;
    }

    public Comment updateComment(Comment comment) {
        return em.merge(comment);
    }

    public void deleteComment(Comment comment) {
        comment = em.find(Comment.class, comment.getId());
        em.remove(comment);
    }
}
