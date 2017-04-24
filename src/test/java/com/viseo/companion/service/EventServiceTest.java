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
import com.viseo.companion.service.EventService;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ViseocompanionserverApplication.class)
public class EventServiceTest {


    @Autowired
    EventService eventService;

    @Test
    public void addEventTest() {
        final Event event = new Event();
        event.setCategory(0);
        Calendar now = Calendar.getInstance();
        event.setDatetime(now);
        event.setDescription("NADA");
        event.setName("ibtissam");
        event.setKeyWords("");
        event.setPlace("HELL");
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
    public final void  deletEvent() {
        final Long id = 1L;
        final Event event= eventService.getEvent(id);
        try {
            eventService. deletEvent(event);
        } catch (final CompanionException  ex) {
            Assert.assertEquals("Cant delete evenement", ex.getMessage());
        }
    }

    @Test
    public void updateEvent() {
        final Long id = 1L;

        final Event event = eventService.getEvent(id);

        try{
            Set<Uzer> listUsers=event.getParticipants();
            for (Uzer uzer : listUsers) {
                System.out.println(uzer.getId());

            }

        }catch(LazyInitializationException ex)
        {
            ex.printStackTrace();
        }

        event.setCategory(0);
        Calendar now = Calendar.getInstance();
        event.setDatetime(now);
        event.setDescription("NADALLLLLLLLLLLLLLLLLLLLLL");
        event.setName("ibtissadddddddddddddddm");
        event.setKeyWords("HELLLLLLLLL");
        event.setPlace("HELLrrrrrrrrrrr");

        try {
            final  Event newEvent = eventService.updateEvent(event);

            Assert.assertNotNull(newEvent.getId());
            Assert.assertEquals(event.getName(), newEvent.getName());
            // Assert.fail();
        } catch (final  CompanionException ex) {
            Assert.assertEquals("l'evenement que vous souhaitez modifier n'exsite pas", ex.getMessage());
        }
    }


}
