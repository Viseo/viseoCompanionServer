package com.viseo.companion.controller;

import com.viseo.companion.domain.Activity;
import com.viseo.companion.dto.ActivityDTO;
import com.viseo.companion.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Arrays;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class ActivityController {

    @Autowired
    ActivityService activityService;

    @CrossOrigin
    @RequestMapping(value = "${endpoint.addActivity}", method = POST)
    public ActivityDTO addActivity(@RequestBody ActivityDTO activityDTO) {
        try {

            activityDTO = activityService.addActivity(activityDTO);
            if(activityDTO!=null)
            return activityDTO;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return null;
    }

    @CrossOrigin
    @RequestMapping(value = "${endpoint.getActivities}", method = GET)
    public List<Activity> getActivities() {
        return activityService.getActivities();
    }
}