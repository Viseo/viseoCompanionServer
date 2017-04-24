package com.viseo.companion.service;

import com.viseo.companion.dao.EventRepository;
import com.viseo.companion.domain.Event;
import com.viseo.companion.domain.Notification;
import com.viseo.companion.domain.Uzer;
import com.viseo.companion.exception.CompanionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by IBO3693 on 21/04/2017.
 */
@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event addEvent(Event event) {
        if (eventRepository.exists(event.getId())) {
            throw new CompanionException("Evenement exsite déja ");
        }

        Notification notif = new Notification(event);
        notif.sendNotification();
        return eventRepository.save(event);

    }

    public boolean deletEvent(Event event) {
        if (eventRepository.exists(event.getId())) {
            eventRepository.delete(event.getId());
            return true;
        }
        return false;
    }

    public Event updateEvent(Event event) {
       return eventRepository.save(event);

    }

    public boolean addParticipant(long eventId, long userId) {
        Event event = getEvent(eventId);
        if (event != null) {
           /* Uzer user = userDao.getUser(userId);
            if (user != null) {
                event.addParticipant(user);
                return true;
            }*/
        }
        return false;
    }


    public Event getEvent(long id) {
        Event result = eventRepository.getEvent(id);
        return result;
    }

    public List<Event> getEvents() {
        return eventRepository.getEvents();
    }

    public List<Event> getEventsByRegisteredUser(long userId) {

        return (List<Event>) eventRepository.getEventsByRegisteredUser(userId);
    }

    public final void deleteEvent(final Long idEvent) {
        eventRepository.delete(idEvent);
    }

    public Uzer getParticipant(long eventId, long userId) {
        Event event = getEvent(eventId);
        if (event != null) {
            for (Uzer user : event.getParticipants()) {
                if (user.getId() == userId)
                    return user;
            }
        }
        return null;
    }

    public List<Uzer> getParticipants(long eventId) {
        List<Uzer> participants = new ArrayList<Uzer>();
        Event event = getEvent(eventId);
        if (event != null) {
            participants.addAll(event.getParticipants());
        }
        return participants;
    }


    public boolean removeParticipant(long eventId, long userId) {
        Event event = getEvent(eventId);
        if (event != null) {
            /*Uzer user = userDao.getUser(userId);
            if (user != null) {
                event.removeParticipant(user);
                return true;
            }*/
        }
        return false;
    }
}
