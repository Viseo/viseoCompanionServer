package com.viseo.companion.controller;

import com.viseo.companion.dto.NotationDTO;
import com.viseo.companion.service.NotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class NotationController {
    @Autowired
    private NotationService notationService;

    @RequestMapping(value = "${endpoint.addNotation}", method = POST)
    public NotationDTO addNotation(@RequestBody NotationDTO notation) {
        return notationService.addNotation(notation);
    }

    @RequestMapping(value = "${endpoint.getNotation}", method = GET)
    public String getNotation(@PathVariable("eventId") long eventId) {
        return notationService.getNotationByEvent(eventId);
    }

    @RequestMapping(value = "${endpoint.updateNotation}", method = PUT)
        public NotationDTO updateNotation(@RequestBody NotationDTO notation) {
        return notationService.updateNotation(notation);
    }
}
