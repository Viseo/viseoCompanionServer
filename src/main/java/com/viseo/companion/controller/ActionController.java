package com.viseo.companion.controller;

import com.viseo.companion.domain.Action;
import com.viseo.companion.dto.ActionDTO;
import com.viseo.companion.service.ActionService;
import com.viseo.companion.service.MeanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

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
    public List<Action> getActions() {
        return actionService.getActions();
    }
}
