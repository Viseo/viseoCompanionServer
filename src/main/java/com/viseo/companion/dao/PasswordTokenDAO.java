package com.viseo.companion.dao;

import com.viseo.companion.domain.PasswordResetToken;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class PasswordTokenDAO {

    @PersistenceContext
    EntityManager em;

    public void addToken(PasswordResetToken token) {
        em.persist(token);
    }

    public PasswordResetToken getTokenFromUserId(long id) {
        List<PasswordResetToken> result = em.createQuery("select a from PasswordResetToken a left join fetch a.user u where u.id = :id", PasswordResetToken.class)
                .setParameter("id", id)
                .getResultList();
        return result.iterator().hasNext() ?
                result.iterator().next() :
                null;
    }

    public PasswordResetToken getTokenFromGuid(String guid) {
        List<PasswordResetToken> result = em.createQuery("select a from PasswordResetToken a where a.guid like :guid", PasswordResetToken.class)
                .setParameter("guid", guid)
                .getResultList();
        return result.iterator().hasNext() ?
                result.iterator().next() :
                null;
    }

    public void deleteToken(String guid) {
        PasswordResetToken myToken = getTokenFromGuid(guid);
        em.remove(myToken);
    }
}
