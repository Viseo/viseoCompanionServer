package com.viseo.companion.dao;

import com.viseo.companion.domain.Commentaire;
import com.viseo.companion.domain.Event;
import com.viseo.companion.domain.Role;
import com.viseo.companion.domain.Uzer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class CommentaireRepository {

    @PersistenceContext
    EntityManager em;

@Transactional
    public boolean addCommentaire(Commentaire commentaire) {
        try {
            em.persist(commentaire);
        } catch (EntityExistsException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    @Transactional
    public boolean deleteCommentaire(Commentaire commentaire) {
        try {
            em.remove(em.contains(commentaire) ? commentaire : em.merge(commentaire));
            em.flush();
        } catch (EntityExistsException e) {
            return false;
        }
        return true;
    }



    @Transactional
    public Commentaire getComment(long id) {
        Query query = em.createQuery("select a from Commentaire a left join fetch a.evenement where a.id = :id");
        query.setParameter("id", id);
        List<Commentaire> result = query.getResultList();
        if (result.size() > 0)
            return result.iterator().next();
        return null;
    }


    @Transactional
    public Commentaire updateCommentaire(Commentaire commentaire) {
        try {
            commentaire = em.merge(commentaire);
        } catch (EntityExistsException e) {
            throw new RuntimeException(e);
        }
        return commentaire;
    }

    @Transactional
    // refactor to getEventComments
    public List<Commentaire> getCommentsByEvent(Long eventId) {
        Query query = em.createQuery("select a from  Commentaire a left join fetch a.evenement p where p.id = :id order by a.datetime");
        query.setParameter("id", eventId);
        return (List<Commentaire>) query.getResultList();
    }

    @Transactional
    public List<Commentaire> getComments() {
        return em.createQuery("select distinct a from Commentaire a left join fetch a.evenement order by a.datetime").getResultList();
    }

}
