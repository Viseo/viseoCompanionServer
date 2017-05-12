package com.viseo.companion.controller;

import com.viseo.companion.domain.Event;
import com.viseo.companion.domain.Uzer;
import com.viseo.companion.service.EventService;
import com.viseo.companion.service.UzerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventController {
    @Autowired
    private EventService eventService;
    @Autowired
    private UzerService userSrvice;

    @CrossOrigin
    @RequestMapping(value = "${endpoint.addEvent}", method = RequestMethod.POST)
    public Boolean addEvent(@RequestParam(value="host", required=true) long host,@RequestBody Event event) {
        Uzer user = userSrvice.getUser(host);
        event.setHost(user);
        eventService.addEvent(event);
        return true;
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.getEvents}", method = RequestMethod.GET)
    public List<Event> getEvents() {
        return eventService.getEvents();
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.getEventsExpired}", method = RequestMethod.GET)
    public List<Event> getEventsExpired() {
        return eventService.getEventsExpired();
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.getEvent}", method = RequestMethod.GET)
    public Event getEvent(@PathVariable("eventId") long eventId) {
        return eventService.getEvent(eventId);
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.addEventParticipant}", method = RequestMethod.POST)
    public Boolean addParticipant(@PathVariable("eventId") long eventId, @PathVariable("userId") long userId) {
        return eventService.addParticipant(eventId, userId);
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.removeEventParticipant}", method = RequestMethod.DELETE)
    public Boolean removeParticipant(@PathVariable("eventId") long eventId, @PathVariable("userId") long userId) {
        return eventService.removeParticipant(eventId, userId);
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.deleteEvent}", method = RequestMethod.DELETE)
    public Boolean removeEvent(@PathVariable("eventId") long eventId) {
        return eventService.deleteEvent(eventId);
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.updateEvent}", method = RequestMethod.PUT)
    public Event updateEvent(@RequestBody Event event) {
        try {
            return eventService.updateEvent(event);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.getEventParticipants}", method = RequestMethod.GET)
    public List<Uzer> getParticipants(@PathVariable("eventId") long eventId) {
        return eventService.getParticipants(eventId);
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.getEventsByRegisteredUser}", method = RequestMethod.GET)
    public List<Event> getEventsByRegisteredUser(@PathVariable("userId") long userId) {
        return eventService.getEventsByRegisteredUser(userId);
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.getEventParticipant}", method = RequestMethod.GET)
    public Uzer getParticipant(@PathVariable("eventId") long eventId, @PathVariable("userId") long userId) {
        return eventService.getParticipant(eventId, userId);
    }
}