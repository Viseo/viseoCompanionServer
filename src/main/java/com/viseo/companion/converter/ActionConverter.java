package com.viseo.companion.converter;

import com.viseo.companion.domain.Action;
import com.viseo.companion.domain.ActionMeans;
import com.viseo.companion.dto.ActionDTO;
import com.viseo.companion.dto.ActionMeansDTO;

import java.util.List;

public class ActionConverter {
    static private long NULL = -1;
    static private long NEW = 0;

    public ActionDTO getDTO(Action action, List<ActionMeans> means) {
        ActionDTO actionDto = new ActionDTO();
        actionDto.setId(action.getId());
        actionDto.setName(action.getName());
        actionDto.setDetail(action.getDetail());
        actionDto.setMinGain(action.getMinGain());
        actionDto.setMaxGain(action.getMaxGain());
        actionDto.setStatus(action.isStatus());
        means.stream().forEach(m ->{
            ActionMeansDTO dto=new ActionMeansDTO(m.getMean().getId(),m.getQuantity());
            actionDto.addMean(dto);
        });
        actionDto.setTheme(action.getTheme());
        actionDto.setAsEvent(action.isAsEvent());

        return actionDto;
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
