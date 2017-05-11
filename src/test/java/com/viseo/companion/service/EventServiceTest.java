package com.viseo.companion.service;

/**
 * Created by IBO3693 on 21/04/2017.
 */


import com.viseo.companion.ViseocompanionserverApplication;
import com.viseo.companion.domain.Event;

import java.util.Calendar;
import java.util.List;

import com.viseo.companion.domain.Uzer;
import com.viseo.companion.exception.CompanionException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class EventServiceTest {

    @Autowired
    EventService eventService;
    @Autowired
    UzerService uzerService;

    static Event event;

    @Test
    public void main() {
        addEventTest();
        updateEventTest();
    }

    @Test
    public void addEventTest() {
        event = new Event();
        event.setCategory(0);
        Calendar now = Calendar.getInstance();
        event.setDatetime(now);
        event.setDescription("NADAl");
        event.setName("ibtisam");
        event.setKeyWords("Haifa");
        event.setPlace("Haifa");
        try {
            Event newEvent = eventService.addEvent(event);
            Assert.assertNotNull(newEvent.getId());
            Assert.assertEquals(event.getName(), newEvent.getName());
            event = newEvent;
        } catch (CompanionException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Test
    public void updateEventTest() {
        event.getId();
        event = eventService.getEvent(event.getId());
        event.setCategory(0);
        Calendar now = Calendar.getInstance();
        event.setDatetime(now);
        event.setDescription("HOOOOOOOOOOOOOOOOOOOOOOOOOOO");
        event.setName("ibtissadddddddddddddddm");
        event.setKeyWords("HELLLLLLLLL");
        event.setPlace("HELLrrrrrrrrrrr");
        Event newEvent = eventService.updateEvent(event);
        try {
            Assert.assertEquals(newEvent.getName(), event.getName());
            event = newEvent;
        } catch (CompanionException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Test
    public final void getEventsTest() {
        final List<Event> lisEvents = eventService.getEvents();
        try {
            Assert.assertEquals(9, lisEvents.size());
        } catch (final CompanionException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Test
    public void getEventsByRegisteredUser() {
        Long userId = 4L;
        List<Event> events = eventService.getEventsByRegisteredUser(userId);
        try {
            Assert.assertNotNull(events);
            Assert.assertNotNull(events.size());
        } catch (final CompanionException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Test
    public void addParticipant() {
        event = eventService.getEvent(65L);
        final Uzer uzer = uzerService.getUser(43L);
        event.addParticipant(uzer);
        try {
            Assert.assertNotNull(uzer);
        } catch (final CompanionException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Test
    public final void getParticipants() {
        event = eventService.getEvent(65L);
        try {
            Assert.assertNotNull(eventService.getParticipants(event.getId()));
            Assert.assertEquals(0, eventService.getParticipants(event.getId()).size());
        } catch (final CompanionException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Test
    public void removeParticipant() {
        event = eventService.getEvent(65L);
        Long userId = 43L;
        try {
            Assert.assertTrue(eventService.removeParticipant(event.getId(), userId));
        } catch (final CompanionException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Test
    public final void deleteEventTest() {
        event = eventService.getEvent(65L);
        try {
            eventService.deleteEvent(event.getId());
        } catch (final CompanionException ex) {
            throw new RuntimeException(ex);
        }
    }
}