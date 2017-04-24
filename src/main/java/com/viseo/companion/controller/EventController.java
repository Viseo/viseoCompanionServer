package com.viseo.companion.controller;

import com.viseo.companion.domain.Event;
import com.viseo.companion.domain.Uzer;
import com.viseo.companion.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class EventController {
    @Autowired
    private EventService eventService;

    @RequestMapping(value = "${endpoint.addEvent}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> addEvent(@Valid @RequestBody Event event, BindingResult bindingResult) {
        if(eventService.addEvent(event)!=null){
            return new ResponseEntity<String>("Event added.", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Event not added.", HttpStatus.CONFLICT);
        }
    }

   /* @RequestMapping(value = "${endpoint.getEvents}", method = RequestMethod.GET)
    @ResponseBody
    public List<Event> getEvents() {
        return eventService.getEvents();
    }

    @RequestMapping(value = "${endpoint.getEvent}", method = RequestMethod.GET)
    @ResponseBody
    public Event getEvent(@PathVariable("eventId") long eventId) {
        return  eventService.getEvent(eventId);
    }

    @RequestMapping(value = "${endpoint.addEventParticipant}", method = RequestMethod.POST)
    @ResponseBody
    public Boolean addParticipant(@PathVariable("eventId") long eventId, @PathVariable("userId") long userId) {
        return eventService.addParticipant(eventId,userId);
    }

    @RequestMapping(value = "${endpoint.removeEventParticipant}", method = RequestMethod.DELETE)
    @ResponseBody
    public Boolean removeParticipant(@PathVariable("eventId") long eventId, @PathVariable("userId") long userId) {
        return eventService.removeParticipant(eventId, userId);
    }

    @RequestMapping(value = "${endpoint.getEventParticipants}", method = RequestMethod.GET)
    @ResponseBody
    public  List<Uzer>  getParticipants(@PathVariable("eventId") long eventId) {
        return eventService.getParticipants(eventId);
    }

    @RequestMapping(value = "${endpoint.getEventsByRegisteredUser}", method = RequestMethod.GET)
    @ResponseBody
    public  List<Event> getEventsByRegisteredUser(@PathVariable("userId") long userId) {
        return eventService.getEventsByRegisteredUser(userId);
    }

    @RequestMapping(value = "${endpoint.getEventParticipant}", method = RequestMethod.GET)
    @ResponseBody
    public Uzer getParticipant(@PathVariable("eventId") long eventId, @PathVariable("userId") long userId) {
        return eventService.getParticipant(eventId, userId);
    }*/
}