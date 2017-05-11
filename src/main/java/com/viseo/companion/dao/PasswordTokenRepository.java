package com.viseo.companion.dao;

import com.viseo.companion.domain.PasswordResetToken;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class PasswordTokenRepository {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public boolean addToken(PasswordResetToken token) {
        try {
            em.persist(token);
        } catch (EntityExistsException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Transactional
    public PasswordResetToken getTokenFromUzerId(long id) {
        try {
            Query query = em.createQuery("select a from PasswordResetToken a left join fetch a.uzer u where u.id = :id");
            query.setParameter("id", id);
            List<PasswordResetToken> result = query.getResultList();
            if (result.iterator().hasNext())
                return result.iterator().next();
            return null;
        } catch (EntityExistsException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public PasswordResetToken getTokenFromGuid(String guid) {
        try {
            Query query = em.createQuery("select a from PasswordResetToken a where a.guid like :guid");
            query.setParameter("guid", guid);
            List<PasswordResetToken> result = query.getResultList();
            return result.iterator().hasNext() ?
                    result.iterator().next() :
                    null;
        } catch (EntityExistsException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public boolean deleteToken(String guid) {
        try {
            PasswordResetToken myToken = getTokenFromGuid(guid);
            em.remove(myToken);
            em.flush();
        } catch (EntityExistsException e) {
            return false;
        }
        return true;
    }
}
