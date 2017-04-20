package com.viseo.companion.controller;

import com.viseo.companion.domain.Notification;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
public class EventController {

    @CrossOrigin
    @RequestMapping(value = "${endpoint.addEvent}", method = RequestMethod.POST)
    @ResponseBody
    public void addEvent(BindingResult bindingResult) {
        Notification noti = new Notification();
    }

    @RequestMapping(value = "${endpoint.getEvents}", method = RequestMethod.GET)
    @ResponseBody
    public void getEvents() {
    }

    @RequestMapping(value = "${endpoint.getEvent}", method = RequestMethod.GET)
    @ResponseBody
    public void getEvent(@PathVariable("eventId") long eventId) {
    }

    @RequestMapping(value = "${endpoint.addEventParticipant}", method = RequestMethod.POST)
    @ResponseBody
    public void addParticipant(@PathVariable("eventId") long eventId, @PathVariable("userId") long userId) {
    }

    @RequestMapping(value = "${endpoint.removeEventParticipant}", method = RequestMethod.DELETE)
    @ResponseBody
    public void removeParticipant(@PathVariable("eventId") long eventId, @PathVariable("userId") long userId) {
    }

    @RequestMapping(value = "${endpoint.getEventParticipants}", method = RequestMethod.GET)
    @ResponseBody
    public void getParticipants(@PathVariable("eventId") long eventId) {
    }

    @RequestMapping(value = "${endpoint.getEventsByRegisteredUser}", method = RequestMethod.GET)
    @ResponseBody
    public void getEventsByRegisteredUser(@PathVariable("userId") long userId) {
    }

    @RequestMapping(value = "${endpoint.getEventParticipant}", method = RequestMethod.GET)
    @ResponseBody
    public void getParticipant(@PathVariable("eventId") long eventId, @PathVariable("userId") long userId) {
    }
}