package com.viseo.companion.dao;

import com.viseo.companion.domain.PasswordResetToken;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class PasswordTokenRepository {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public boolean addToken(PasswordResetToken token){
        try {
            em.persist(token);
        } catch (EntityExistsException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
