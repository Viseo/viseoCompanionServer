package com.viseo.companion.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class EventController {

    @RequestMapping(value = "${endpoint.addEvent}", method = POST)
    public void addEvent(BindingResult bindingResult) {
    }

    @RequestMapping(value = "${endpoint.getEvents}", method = GET)
    public void getEvents() {
    }

    @RequestMapping(value = "${endpoint.getEvent}", method = GET)
    public void getEvent(@PathVariable("eventId") long eventId) {
    }

    @RequestMapping(value = "${endpoint.addEventParticipant}", method = POST)
    public void addParticipant(@PathVariable("eventId") long eventId, @PathVariable("userId") long userId) {
    }

    @RequestMapping(value = "${endpoint.removeEventParticipant}", method = DELETE)
    public void removeParticipant(@PathVariable("eventId") long eventId, @PathVariable("userId") long userId) {
    }

    @RequestMapping(value = "${endpoint.getEventParticipants}", method = GET)
    public void getParticipants(@PathVariable("eventId") long eventId) {
    }

    @RequestMapping(value = "${endpoint.getEventsByRegisteredUser}", method = GET)
    public void getEventsByRegisteredUser(@PathVariable("userId") long userId) {
    }

    @RequestMapping(value = "${endpoint.getEventParticipant}", method = GET)
    public void getParticipant(@PathVariable("eventId") long eventId, @PathVariable("userId") long userId) {
    }
}