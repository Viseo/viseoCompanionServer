package com.viseo.companion.controller;

import com.viseo.companion.dto.ActionDTO;
import com.viseo.companion.dto.CommentDTO;
import com.viseo.companion.service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
public class ActionController {

    @Autowired
    private ActionService actionService;


    @CrossOrigin
    @RequestMapping(value = "${endpoint.addAction}", method = POST)
    public ActionDTO addAction(@RequestBody ActionDTO action) {

        ActionDTO returnAction = new ActionDTO();
        if (actionService.getActions().stream().filter(act -> act.getName().equals(action.getName())).count() == 0) {
            returnAction = actionService.addAction(action);
        }
        return returnAction;
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.getActions}", method = GET)
    public List<ActionDTO> getActions() {

        return actionService.getActions();
    }
}
