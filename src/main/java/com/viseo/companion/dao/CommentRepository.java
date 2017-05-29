package com.viseo.companion.dao;

import com.viseo.companion.domain.Comment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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

    public List<Comment> getCommentsByEvent(Long eventId) {
        return em.createQuery("select a from  Comment a left join fetch a.event p where p.id = :id order by a.datetime", Comment.class)
                .setParameter("id", eventId)
                .getResultList();
    }

    public Comment getParentFromChildId(long childCommentId) {
        List<Comment> result = em.createQuery(
                "select a from Comment a left join fetch a.children c where c.id = :id", Comment.class)
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
