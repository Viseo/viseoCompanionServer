package com.viseo.companion.converter;

import com.viseo.companion.domain.Action;
import com.viseo.companion.domain.ActionMeans;
import com.viseo.companion.dto.ActionDTO;

import java.util.List;

public class ActionConverter {
    static private long NULL = -1;
    static private long NEW = 0;

    public ActionDTO getDTO(Action action, List<ActionMeans> means) {
        ActionDTO dto = new ActionDTO();
        dto.setId(action.getId());
        dto.setName(action.getName());
        dto.setDetail(action.getDetail());
        dto.setMinGain(action.getMinGain());
        dto.setMaxGain(action.getMaxGain());
        dto.setStatus(action.isStatus());
       // action.getMeans().stream().forEach(m -> dto.addMean(m.getId()));
        dto.setTheme(action.getTheme());
        dto.setAsEvent(action.isAsEvent());

        return dto;
    }


    public void apply(ActionDTO dto, Action action) {
        if (dto.getId() != NEW && action.getVersion() != dto.getVersion()) {
            throw new RuntimeException("Entity " + action + " was updated since DTO was built.");
        }
        action.setName(dto.getName());
        action.setDetail(dto.getDetail());
        action.setMinGain(dto.getMinGain());
        action.setMaxGain(dto.getMaxGain());
        action.setStatus(dto.isStatus());
        action.setTheme(dto.getTheme());
        action.setAsEvent(dto.isAsEvent());
    }


}
