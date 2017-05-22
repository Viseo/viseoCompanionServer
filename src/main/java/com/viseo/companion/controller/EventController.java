package com.viseo.companion.controller;

import com.viseo.companion.domain.Event;
import com.viseo.companion.domain.Uzer;
import com.viseo.companion.service.EventService;
import com.viseo.companion.service.UzerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class EventController {
    @Autowired
    private EventService eventService;
    @Autowired
    private UzerService userSrvice;

    //TODO : remove the @cross origin where we don't need it

    @CrossOrigin
    @RequestMapping(value = "${endpoint.addEvent}", method = RequestMethod.POST)
    public Boolean addEvent(@RequestParam(value = "host") long host, @RequestBody Event event) {
        Uzer user = userSrvice.getUser(host);
        event.setHost(user);
        boolean b = eventService.addEvent(event) != null ? true : false;
        return b;

    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.uploadImage}", method = RequestMethod.POST)
    public String uploadImage(@RequestParam("file") MultipartFile image) {
        try {
            return eventService.uploadImage(image.getOriginalFilename(), image);
        } catch (IOException e) {

            throw new RuntimeException(e.getMessage());
        }

    }

    private void validateImage(MultipartFile image) {
        if (!image.getContentType().equals("image/jpeg")) {
            throw new RuntimeException("Only JPG images are accepted");
        }
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.getEvents}", method = RequestMethod.GET)
    public List<Event> getEvents(@RequestParam(value = "before", required = false) String
                                         before, @RequestParam(value = "after", required = false) String after) {
        return eventService.getEvents(before, after);
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
    public Boolean addParticipant(@PathVariable("eventId") long eventId, @PathVariable("uzerId") long userId) {
        return eventService.addParticipant(eventId, userId);
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.removeEventParticipant}", method = RequestMethod.DELETE)
    public Boolean removeParticipant(@PathVariable("eventId") long eventId, @PathVariable("uzerId") long userId) {
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