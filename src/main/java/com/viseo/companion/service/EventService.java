package com.viseo.companion.service;

import com.viseo.companion.dao.EventRepository;
import com.viseo.companion.domain.Event;
import com.viseo.companion.domain.Uzer;
import com.viseo.companion.exception.SPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

/**
 * Created by IBO3693 on 21/04/2017.
 */
@Service
@Transactional
public  class EventService  {

    @Autowired
    private EventRepository eventRepository;

    public  Event addEvent(Event event) {
        if (eventRepository.exists(event.getId())) {
            throw new SPIException("Evenement exsite déja ");
        }
        return eventRepository.save(event);
    }
}
