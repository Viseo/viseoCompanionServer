package com.viseo.companion.service;

import com.viseo.companion.dao.ActivityDAO;
import com.viseo.companion.domain.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {

    @Autowired
    private ActivityDAO activityDAO;

    public Activity addActivity(Activity activity) {
        try {
            activity = activityDAO.addActivity(activity);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return activity;
    }
}
