package com.viseo.companion.service;

/**
 * Created by IBO3693 on 21/04/2017.
 */


import com.viseo.companion.ViseocompanionserverApplication;
import com.viseo.companion.domain.Event;
import java.util.Calendar;


import com.viseo.companion.exception.SPIException;
import org.junit.Assert;
        import org.junit.Test;
        import org.junit.runner.RunWith;
        import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

        import com.viseo.companion.ViseocompanionserverApplication;
        import com.viseo.companion.domain.Event;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=ViseocompanionserverApplication.class)
public class EventServiceTest {


    @Autowired
    EventService eventService;

    @Test
    public void addEventTest(){
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
        } catch (final SPIException ex) {
            Assert.assertEquals("l'evenement que vous souhaitez ajouter exsite déja ", ex.getMessage());
        }

    }



}
