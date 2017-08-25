package com.viseo.companion.service;

import com.viseo.companion.converter.ActivityConverter;
import com.viseo.companion.dao.ActivityDAO;
import com.viseo.companion.dao.ActivityMeansDAO;
import com.viseo.companion.domain.Activity;
import com.viseo.companion.domain.ActivityMeans;
import com.viseo.companion.dto.ActivityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {

    @Autowired
    private ActivityDAO activityDAO;
    @Autowired
    private ActionService actionService;
    @Autowired
    private UserService userService;
    @Autowired
    private MeanService meanService;
    @Autowired
    private ActivityMeansDAO activitynMeansDAO;


    private ActivityConverter converter = new ActivityConverter();

    public ActivityDTO addActivity(ActivityDTO activityDTO) {
        try {
            Activity activity = toActivity(activityDTO);
            activity = activityDAO.addActivity(activity);
            if (activity != null) {
                long idActivity=activity.getId();
                activityDTO.getMeans().stream().forEach((m) -> {
                    ActivityMeans activityMean = new ActivityMeans( activityDAO.getActivityById(idActivity), meanService.getMeanById(m.getMeanId()) , m.getQuantity());
                    activitynMeansDAO.addMeansByActivity(activityMean);
                });

                return activityDTO;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    public List<Activity> getActivities() {
        return activityDAO.getActivities();
    }

    private Activity toActivity(ActivityDTO activityDTO) {
        Activity activity = new Activity();
        activity.setAction(actionService.getActionById(activityDTO.getActionId()));
        activity.setUser(userService.getUser(activityDTO.getUserId()));
        if (activityDTO != null) {
            converter.apply(activityDTO, activity);
            return activity;
        }
        return null;
    }
}
