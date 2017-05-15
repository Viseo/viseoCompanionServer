package com.viseo.companion.dao;

import com.viseo.companion.domain.Event;
import com.viseo.companion.domain.Uzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static javax.persistence.TemporalType.DATE;

@Repository
public class EventRepository {

    @Autowired
    UzerRepository userDao;

    @PersistenceContext
    EntityManager em;

    public void addEvent(String name, Calendar date, String description, String KeyWords, String place) {
        Event event = new Event();
        event.setDatetime(date);
        event.setDescription(description);
        event.setName(name);
        event.setPlace(place);
        event.setKeyWords(KeyWords);
        em.persist(event);
    }

    @Transactional
    public boolean addEvent(Event event) {
        try {
            em.persist(event);

        } catch (EntityExistsException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Transactional
    public boolean deleteEvent(Event event) {
        try {
            em.remove(em.contains(event) ? event : em.merge(event));
            em.flush();
        } catch (EntityExistsException e) {
            return false;
        }
        return true;
    }

    @Transactional
    public Event updateEvent(Event event) {
        Event eventToUpdate = getEvent(event.getId());
        eventToUpdate.setName(event.getName());
        eventToUpdate.setCategory(event.getCategory());
        eventToUpdate.setDatetime(event.getDatetime());
        eventToUpdate.setDescription(event.getDescription());
        eventToUpdate.setKeyWords(event.getKeyWords());
        eventToUpdate.setPlace(event.getPlace());
        try {
            eventToUpdate = em.merge(eventToUpdate);

        } catch (EntityExistsException e) {
            throw new RuntimeException(e);
        }
        return eventToUpdate;
    }

    @Transactional
    public Event getEvent(long id) {
        List<Event> eventList = em.createQuery(
                "select a from Event a left join fetch a.participants p left join fetch p.roles where a.id = :id", Event.class)
                .setParameter("id", id)
                .getResultList();
        if (eventList.iterator().hasNext())
            return eventList.iterator().next();
        return null;
    }

    @Transactional
    public List<Event> getEvents() {
        return em.createQuery(
                "select distinct a from Event a left join fetch a.participants p left join fetch p.roles order by a.datetime", Event.class)
                .getResultList();
    }

    @Transactional
    public List<Event> getEventsBetween(String before, String after) {
        return em.createQuery(
                "SELECT a FROM Event a LEFT JOIN FETCH a.participants p LEFT JOIN FETCH p.roles WHERE a.datetime >= :after AND a.datetime <= :before order by a.datetime", Event.class)
                .setParameter("before", new Date(Long.valueOf(before)), DATE )
                .setParameter("after", new Date(Long.valueOf(after)), DATE )
                .getResultList();
    }

    @Transactional
    public List<Event> getEventsAfter(String after) {
        return em.createQuery(
                "SELECT a from Event a LEFT JOIN FETCH a.participants p LEFT JOIN FETCH p.roles WHERE a.datetime >= :after order by a.datetime", Event.class)
                .setParameter("after", new Date(Long.valueOf(after)), DATE )
                .getResultList();
    }

    @Transactional
    public List<Event> getEventsBefore(String before){
        return em.createQuery(
                "SELECT a FROM Event a LEFT JOIN FETCH a.participants p LEFT JOIN FETCH p.roles WHERE a.datetime <= :before ORDER BY a.datetime", Event.class)
                .setParameter("before", new Date(Long.valueOf(before)), DATE )
                .getResultList();
    }

    @Transactional
    public List<Event> getEventsByRegisteredUser(long userId) {
        Query query = em.createQuery(
                "select a from Event a left join fetch a.participants p left join fetch p.roles where p.id = :id order by a.datetime")
                .setParameter("id", userId);
        return (List<Event>) query.getResultList();
    }

    @Transactional
    public boolean addParticipant(long eventId, long userId) {
        Event event = getEvent(eventId);
        if (event != null) {
            Uzer user = userDao.getUzer(userId);
            if (user != null) {
                event.addParticipant(user);
                return true;
            }
        }
        return false;
    }

    @Transactional
    public boolean removeParticipant(long eventId, long userId) {
        Event event = getEvent(eventId);
        if (event != null) {
            Uzer user = userDao.getUzer(userId);
            if (user != null) {
                event.removeParticipant(user);
                return true;
            }
        }
        return false;
    }

    @Transactional
    public List<Event> getEventsExpired() {
        return em.createQuery(
                "select distinct a from Event a left join fetch a.participants p left join fetch p.roles  where a.datetime < CURRENT_DATE order by a.datetime", Event.class)
                .getResultList();

    }

}
