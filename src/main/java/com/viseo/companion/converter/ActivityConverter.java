package com.viseo.companion.converter;

import com.viseo.companion.domain.Activity;
import com.viseo.companion.dto.ActivityDTO;

public class ActivityConverter {
    static private long NULL = -1;
    static private long NEW = 0;


    public ActivityDTO getDTO(Activity activity) {
        ActivityDTO dto = new ActivityDTO();
        dto.setAddress(activity.getAddress());
        dto.setDateCreation(activity.getDateCreation());
        dto.setDateEnd(activity.getDateEnd());
        dto.setDateRelease(activity.getDateRelease());
        dto.setDateStart(activity.getDateStart());
        dto.setDateValidation(activity.getDateValidation());
        dto.setPractice(activity.getPractice());
        dto.setDescription(activity.getDescription());
        dto.setEtat(activity.getEtat());
        dto.setTitle(activity.getTitle());
        dto.setVizzWon(activity.getVizzWon());
        dto.setRecurrence(activity.getRecurrence());
        dto.setPublicationType(activity.getPublicationType());
        dto.setReadingTime(activity.getReadingTime());

        dto.setActionId(activity.getAction().getId());
        dto.setUserId(activity.getUser().getId());
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
        activity.setPractice(dto.getPractice());
        activity.setDescription(dto.getDescription());
        activity.setEtat(dto.getEtat());
        activity.setTitle(dto.getTitle());
        activity.setVizzWon(dto.getVizzWon());
        activity.setRecurrence(dto.getRecurrence());
        activity.setPublicationType(dto.getPublicationType());
        activity.setReadingTime(dto.getReadingTime());

    }
    
}
