package com.viseo.companion.service;

import com.viseo.companion.dao.EventRepository;
import com.viseo.companion.domain.Event;
import com.viseo.companion.domain.Notification;
import com.viseo.companion.domain.Uzer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UzerService userService;


    public Boolean addEvent(Event event) {
        try {
            eventRepository.addEvent(event);

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        Notification notif = new Notification(event, "/topics/newEvent/");
        notif.sendNotification();
        return true;
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

    public List<Event> getEvents() {
        List<Event> events = null;
        try {
            events = eventRepository.getEvents();

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

    public boolean addParticipant(long eventId, long userId) {
        Event event = getEvent(eventId);
        if (event != null) {
            Uzer user = userService.getUser(userId);
            if (user != null) {
                event.addParticipant(user);
                return true;
            }
        }
        return false;
    }
}
