package com.viseo.companion.controller;

import com.viseo.companion.dto.ActivityDTO;
import com.viseo.companion.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @CrossOrigin
    @RequestMapping(value = "${endpoint.addActivity}", method = POST)
    public ActivityDTO addActivity(@RequestBody ActivityDTO activityDTO) {
        return activityService.addActivity(activityDTO);
    }
}
