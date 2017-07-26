package com.viseo.companion.converter;

import com.viseo.companion.domain.Activity;
import com.viseo.companion.dto.ActivityDTO;
import com.viseo.companion.service.ActionService;
import com.viseo.companion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class ActivityConverter {
    static private long NULL = -1;
    static private long NEW = 0;

    @Autowired
    UserService userService;
    @Autowired
    ActionService actionService;
    public ActivityDTO getDTO(Activity activity) {
        ActivityDTO dto = new ActivityDTO();
        dto.setAddress(activity.getAddress());
        dto.setDateCreation(activity.getDateCreation());
        dto.setDateEnd(activity.getDateEnd());
        dto.setDateRelease(activity.getDateRelease());
        dto.setDateStart(activity.getDateStart());
        dto.setDateValidation(activity.getDateValidation());
        dto.setActionId(activity.getAction().getId());
        dto.setPractice(activity.getPractice());
        dto.setDescription(activity.getDescription());
        dto.setEtat(activity.getEtat());
        dto.setTitle(activity.getTitle());
        dto.setUserId(activity.getUser().getId());
        dto.setVizzWon(activity.getVizzWon());
        dto.setRecurrence(activity.getRecurrence());
        dto.setPublicationType(activity.getPublicationType());
        dto.setReadingTime(activity.getReadingTime());
        activity.getMeans().stream().forEach(m -> dto.addMean(m.getId()));
        return dto;
    }


    public void apply(ActivityDTO dto, Activity activity) {
        if (dto.getId() != NEW && activity.getVersion() != dto.getVersion()) {
            throw new RuntimeException("Entity " + activity + " was updated since DTO was built.");
        }
        activity.setAddress(dto.getAddress());
        activity.setDateCreation(dto.getDateCreation());
        activity.setDateEnd(dto.getDateEnd());
        activity.setDateRelease(dto.getDateRelease());
        activity.setDateStart(dto.getDateStart());
        activity.setDateValidation(dto.getDateValidation());
        activity.setAction(actionService.getActionById(dto.getActionId()));
        activity.setPractice(dto.getPractice());
        activity.setDescription(dto.getDescription());
        activity.setEtat(dto.getEtat());
        activity.setTitle(dto.getTitle());
        activity.setUser(userService.getUser(dto.getUserId()));
        activity.setVizzWon(dto.getVizzWon());
        activity.setRecurrence(dto.getRecurrence());
        activity.setPublicationType(dto.getPublicationType());
        activity.setReadingTime(dto.getReadingTime());
    }
    
}
