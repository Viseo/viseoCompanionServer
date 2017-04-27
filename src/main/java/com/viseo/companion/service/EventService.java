package com.viseo.companion.service;

import com.viseo.companion.dao.EventRepository;
import com.viseo.companion.domain.Event;
import com.viseo.companion.domain.Notification;
import com.viseo.companion.domain.Uzer;
import com.viseo.companion.exception.CompanionException;
import org.hibernate.LazyInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IBO3693 on 21/04/2017.
 */
@Service

public class EventService {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UzerService userService;

    @Transactional
    public Event addEvent(Event event) {
        /*Notification notif = new Notification(event);
        notif.sendNotification();*/
        Event ev = null;
        try {
            ev = eventRepository.save(event);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ev;

    }

    @Transactional
    public Event getEvent(long id) {
        Event result = eventRepository.findOne(id);
        return result;
    }

    @Transactional
    public boolean deleteEvent(Long eventId) {
        if (eventRepository.exists(eventId)) {
            eventRepository.delete(eventId);
            return true;
        }
        return false;
    }

    @Transactional
    public Event updateEvent(Event event) {
        return eventRepository.save(event);

    }

    @Transactional
    public List<Event> getEvents() {
        List<Event> events = null;

        try {
            events = eventRepository.getEvents();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return events;
    }
    @Transactional
    public List<Event> getEventsByRegisteredUser(long userId) {

        return eventRepository.getEventsByRegisteredUser(userId);
    }

    @Transactional
    public List<Uzer> getParticipants(long eventId) {
        List<Uzer> participants = new ArrayList<Uzer>();
        Event event = getEvent(eventId);
        if (event != null) {
            participants.addAll(event.getParticipants());
        }
        return participants;
    }

    @Transactional
    public boolean removeParticipant(long eventId, long userId) {
        Event event = getEvent(eventId);
        if (event != null) {
            Uzer user = userService.getUser(userId);
            if (user != null) {
                event.removeParticipant(user);
                return true;
            }
        }
        return false;
    }


}
