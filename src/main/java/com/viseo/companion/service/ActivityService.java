package com.viseo.companion.service;

import com.viseo.companion.converter.ActivityConverter;
import com.viseo.companion.dao.ActivityDAO;
import com.viseo.companion.domain.Activity;
import com.viseo.companion.dto.ActivityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {

    @Autowired
    private ActivityDAO activityDAO;

    private ActivityConverter converter = new ActivityConverter();

    public ActivityDTO addActivity(ActivityDTO activityDTO) {
        try {
            Activity activity = toActivity(activityDTO);
            if (activity != null) {
                activity = activityDAO.addActivity(activity);
                return converter.getDTO(activity);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    private Activity toActivity(ActivityDTO activityDTO) {
        Activity activity = new Activity();
        if (activityDTO != null) {
            converter.apply(activityDTO, activity);
            return activity;
        }
        return null;
    }
}
