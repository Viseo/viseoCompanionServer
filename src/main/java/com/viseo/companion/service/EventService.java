package com.viseo.companion.service;

import com.viseo.companion.dao.EventRepository;
import com.viseo.companion.domain.Event;
import com.viseo.companion.domain.Notification;
import com.viseo.companion.domain.Uzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UzerService userService;


    public Event addEvent(Event event) {
        try {
            eventRepository.addEvent(event);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        /*Notification notif = new Notification(event, "/topics/newEvent/");
        notif.sendNotification();*/
        return event;
    }


    public Event getEvent(long id) {
        Event result = eventRepository.getEvent(id);
        return result;
    }

    public boolean deleteEvent(Long eventId) {
        if (eventRepository.getEvent(eventId) != null) {
            eventRepository.deleteEvent(eventRepository.getEvent(eventId));
            return true;
        }
        return false;
    }

    public Event updateEvent(Event event) {
        return eventRepository.updateEvent(event);
    }

    public List<Event> getEvents(String before, String after) {
        List<Event> events = null;
        try {
            if (before != null && after != null) {
                events = eventRepository.getEventsBetween(before, after);
            } else if (before == null && after != null) {
                events = eventRepository.getEventsAfter(after);
            } else if (before != null) {
                events = eventRepository.getEventsBefore(before);
            } else {
                events = eventRepository.getEvents();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return events;
    }

    public List<Event> getEventsByRegisteredUser(long userId) {
        return eventRepository.getEventsByRegisteredUser(userId);
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
        return eventRepository.removeParticipant(eventId, userId);
    }

    public boolean addParticipant(long eventId, long userId) {
        return eventRepository.addParticipant(eventId, userId);
    }
}
