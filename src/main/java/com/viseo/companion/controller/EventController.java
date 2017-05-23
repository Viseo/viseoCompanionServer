package com.viseo.companion.controller;

import com.viseo.companion.domain.Event;
import com.viseo.companion.domain.Uzer;
import com.viseo.companion.service.EventService;
import com.viseo.companion.service.UzerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class EventController {
    @Autowired
    private EventService eventService;
    @Autowired
    private UzerService userSrvice;

    //TODO : remove the @cross origin where we don't need it
    @CrossOrigin
    @RequestMapping(value = "${endpoint.addEvent}", method = POST)
    public Boolean addEvent(@RequestParam(value = "host", required = true) long host, @RequestBody Event event) {
        Uzer user = userSrvice.getUser(host);
        event.setHost(user);
        eventService.addEvent(event);
        return true;
    }

    @CrossOrigin
    @CrossOrigin
    @RequestMapping(value = "${endpoint.getEvents}", method = GET)
    public List<Event> getEvents(@RequestParam(value = "before", required = false) String before, @RequestParam(value = "after", required = false) String after) {
        return eventService.getEvents(before, after);
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.getEvent}", method = GET)
    public Event getEvent(@PathVariable("eventId") long eventId) {
        return eventService.getEvent(eventId);
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.addEventParticipant}", method = POST)
    public Boolean addParticipant(@PathVariable("eventId") long eventId, @PathVariable("uzerId") long userId) {
        return eventService.addParticipant(eventId, userId);
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.removeEventParticipant}", method = DELETE)
    public Boolean removeParticipant(@PathVariable("eventId") long eventId, @PathVariable("uzerId") long userId) {
        return eventService.removeParticipant(eventId, userId);
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.deleteEvent}", method = DELETE)
    public Boolean removeEvent(@PathVariable("eventId") long eventId) {
        return eventService.deleteEvent(eventId);
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.updateEvent}", method = PUT)
    public Event updateEvent(@RequestBody Event event) {
        try {
            return eventService.updateEvent(event);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.getEventParticipants}", method = GET)
    public List<Uzer> getParticipants(@PathVariable("eventId") long eventId) {
        return eventService.getParticipants(eventId);
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.getEventsByRegisteredUser}", method = GET)
    public List<Event> getEventsByRegisteredUser(@PathVariable("uzerId") long userId) {
        return eventService.getEventsByRegisteredUser(userId);
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.getEventParticipant}", method = GET)
    public Uzer getParticipant(@PathVariable("eventId") long eventId, @PathVariable("userId") long userId) {
        return eventService.getParticipant(eventId, userId);
    }
}