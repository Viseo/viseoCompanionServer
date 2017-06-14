package com.viseo.companion.controller;

import com.viseo.companion.domain.Notation;
import com.viseo.companion.service.NotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
public class NotationController {
    @Autowired
    private NotationService notationService;

    @RequestMapping(value = "${endpoint.addNotation}", method = POST)
    public Notation addNotation(@RequestBody Notation notation) {

        return notationService.addNotation(notation);
    }

    @RequestMapping(value = "${endpoint.getNotation}", method = GET)
    public String getNotation(@PathVariable("eventId") long eventId) {

        return notationService.getNotation(eventId);
    }

    @RequestMapping(value = "${endpoint.updateNotation}", method = PUT)
    public Notation getNotation(@RequestBody Notation notation) {

        return notationService.updateNotation(notation);
    }
}
