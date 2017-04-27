package com.viseo.companion.service;

/**
 * Created by IBO3693 on 21/04/2017.
 */


import com.viseo.companion.ViseocompanionserverApplication;
import com.viseo.companion.domain.Event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;


import com.viseo.companion.domain.Uzer;
import com.viseo.companion.exception.CompanionException;
import org.hibernate.LazyInitializationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ViseocompanionserverApplication.class)
public class EventServiceTest {


    @Autowired
    EventService eventService;
    @Autowired
    UzerService uzerService;

    @Test
    public void addEventTest() {
        final Event event = new Event();
        event.setCategory(0);
        Calendar now = Calendar.getInstance();
        event.setDatetime(now);
        event.setDescription("NADAAAAA");
        event.setName("ibtissamEventttt");
        event.setKeyWords("HELLLLL");
        event.setPlace("HELLLLLL");
        final Uzer uzer = uzerService.getUser(1L);

        event.addParticipant(uzer);
        try {

            final Event newEvent = eventService.addEvent(event);
            Assert.assertNotNull(newEvent.getId());
            Assert.assertEquals(event.getId(), newEvent.getId());
            // Assert.fail();
        } catch (final CompanionException ex) {
            Assert.assertEquals("l'evenement que vous souhaitez ajouter exsite déja ", ex.getMessage());


        }

    }

    @Test
    public final void deletEventTest() {
        final Long id = 2L;
        try {
            eventService.deleteEvent(id);
        } catch (final CompanionException ex) {
            Assert.assertEquals("Cant delete evenement", ex.getMessage());
        }
    }

    @Test
    public final void updateEventTest() {
        final Long id = 2L;

        final Event event = eventService.getEvent(id);
        event.setCategory(0);
        Calendar now = Calendar.getInstance();
        event.setDatetime(now);
        event.setDescription("HALLLLLLLLLLLLLLLLLLLLLLA");
        event.setName("ibtissadddddddddddddddm");
        event.setKeyWords("HELLLLLLLLL");
        event.setPlace("HELLrrrrrrrrrrr");
        Uzer user = uzerService.getUser(3L);

        event.addParticipant(user);
        Event newEvent=eventService.updateEvent(event);

        try {


           // Assert.assertNotNull(newEvent.getId());
            Assert.assertEquals(event.getName(), newEvent.getName());
            // Assert.fail();
        } catch (final CompanionException ex) {
            Assert.assertEquals("l'evenement que vous souhaitez modifier n'exsite pas", ex.getMessage());
        }
    }

    @Test
    public void getEventsTest() {
        final List<Event> lisEvents = eventService.getEvents();
        try {
            Assert.assertEquals(1,lisEvents.size());
            //Assert.assertEquals(2, lisEvents.size());
        } catch (final CompanionException ex) {
            Assert.assertEquals("liste d'evenements introuvables", ex.getMessage());
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
            Assert.assertEquals("liste d'evenements introuvables", ex.getMessage());
        }
    }



   @Test
    public void removeParticipant() {
        Long userId = 1L;
        Long eventId = 4L;
        try {
            Assert.assertTrue(eventService.removeParticipant(eventId, userId));
        } catch (final CompanionException ex) {
            Assert.assertEquals("impossible supprimer le participant", ex.getMessage());
        }
    }

    @Test
    public void getParticipants() {

        Long eventId = 4L;

        try {
            Assert.assertNotNull(eventService.getParticipants(eventId));
            Assert.assertEquals(0, eventService.getParticipants(eventId).size());
        } catch (final CompanionException ex) {
            Assert.assertEquals("impossible de supprimer le participant", ex.getMessage());
        }


    }


}