package com.viseo.companion.dao;

import com.viseo.companion.domain.Event;
import com.viseo.companion.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

import static javax.persistence.TemporalType.DATE;
import static javax.persistence.TemporalType.TIMESTAMP;

@Repository
@Transactional
public class EventRepository {

    @Autowired
    UserRepository userDao;

    @PersistenceContext
    EntityManager em;

    public Event addEvent(Event event) {
        em.persist(event);
        return event;
    }

    public Event getEvent(long id) {
        List<Event> eventList = em.createQuery(
                "select a from Event a left join fetch a.participants p left join fetch p.roles where a.id = :id", Event.class)
                .setParameter("id", id)
                .getResultList();
        if (eventList.iterator().hasNext())
            return eventList.iterator().next();
        return null;
    }

    public List<Event> getEvents() {
        return em.createQuery(
                "select distinct a from Event a left join fetch a.participants p left join fetch p.roles order by a.datetime", Event.class)
                .getResultList();
    }

    public List<Event> getEventsBetween(String before, String after) {
        return em.createQuery(
                "SELECT a FROM Event a LEFT JOIN FETCH a.participants p LEFT JOIN FETCH p.roles WHERE a.datetime >= :after AND a.datetime <= :before order by a.datetime", Event.class)
                .setParameter("before", new Date(Long.valueOf(before)), TIMESTAMP)
                .setParameter("after", new Date(Long.valueOf(after)), DATE)
                .getResultList();
    }

    public List<Event> getEventsAfter(String after) {
        return em.createQuery(
                "SELECT a from Event a LEFT JOIN FETCH a.participants p LEFT JOIN FETCH p.roles WHERE a.datetime >= :after order by a.datetime", Event.class)
                .setParameter("after", new Date(Long.valueOf(after)), DATE)
                .getResultList();
    }

    public List<Event> getEventsBefore(String before) {
        return em.createQuery(
                "SELECT a FROM Event a LEFT JOIN FETCH a.participants p LEFT JOIN FETCH p.roles WHERE a.datetime <= :before ORDER BY a.datetime", Event.class)
                .setParameter("before", new Date(Long.valueOf(before)), TIMESTAMP)
                .getResultList();
    }

    public List<Event> getEventsByRegisteredUser(long userId) {
        return em.createQuery(
                "select a from Event a left join fetch a.participants p left join fetch p.roles where p.id = :id order by a.datetime", Event.class)
                .setParameter("id", userId)
                .getResultList();
    }

    public Event updateEvent(Event event) {
        return em.merge(event);
    }

    public void deleteEvent(Event event) {
        event = em.find(Event.class, event.getId());
        em.remove(event);
    }

    public Event addParticipant(long eventId, long userId) {
        Event event = getEvent(eventId);
        if (event != null) {
            User user = userDao.getUser(userId);
            if (user != null) {
                event.addParticipant(user);
            }
        }
        return event;
    }

    public void removeParticipant(long eventId, long userId) {
        Event event = getEvent(eventId);
        if (event != null) {
            User user = userDao.getUser(userId);
            if (user != null) {
                event.removeParticipant(user);
            }
        }
    }
}
