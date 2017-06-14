package com.viseo.companion.controller;

import com.viseo.companion.domain.Event;
import com.viseo.companion.domain.User;
import com.viseo.companion.service.EventService;
import com.viseo.companion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class EventController {
    @Autowired
    private EventService eventService;
    @Autowired
    private UserService userService;

    @CrossOrigin
    @RequestMapping(value = "${endpoint.addEvent}", method = POST)
    public Event addEvent(@RequestParam(value = "host") long host, @RequestBody Event event) {
        User user = userService.getUser(host);
        event.setHost(user);
        return eventService.addEvent(event);
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
    @RequestMapping(value = "${endpoint.getEvents}", method = GET)
    public List<Event> getEvents(
            @RequestParam(value = "before", required = false) String before,
            @RequestParam(value = "after", required = false) String after) {
        return eventService.getEvents(before, after);
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.getEvent}", method = GET)
    public Event getEvent(@PathVariable("eventId") long eventId) {
        return eventService.getEvent(eventId);
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.getEventsByRegisteredUser}", method = GET)
    public List<Event> getEventsByRegisteredUser(@PathVariable("userId") long userId) {
        return eventService.getEventsByRegisteredUser(userId);
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.updateEvent}", method = PUT)
    public Event updateEvent(@RequestBody Event event) {
        return eventService.updateEvent(event);
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.deleteEvent}", method = DELETE)
    public void removeEvent(@PathVariable("eventId") long eventId) {
        eventService.deleteEvent(eventId);
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.addEventParticipant}", method = POST)
    public Event addParticipant(@PathVariable("eventId") long eventId, @PathVariable("userId") long userId) {
        return eventService.addParticipant(eventId, userId);
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.removeEventParticipant}", method = DELETE)
    public void removeParticipant(@PathVariable("eventId") long eventId, @PathVariable("userId") long userId) {
        eventService.removeParticipant(eventId, userId);
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.getEventParticipants}", method = GET)
    public List<User> getParticipants(@PathVariable("eventId") long eventId) {
        return eventService.getParticipants(eventId);
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.getEventParticipant}", method = GET)
    public User getParticipant(@PathVariable("eventId") long eventId, @PathVariable("userId") long userId) {
        return eventService.getParticipant(eventId, userId);
    }
}