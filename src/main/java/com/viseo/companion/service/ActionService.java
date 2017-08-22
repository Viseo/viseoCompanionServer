package com.viseo.companion.service;

import com.viseo.companion.converter.ActionConverter;
import com.viseo.companion.dao.ActionDAO;
import com.viseo.companion.dao.ActionMeansDAO;
import com.viseo.companion.dao.MeanDAO;
import com.viseo.companion.domain.Action;
import com.viseo.companion.domain.ActionMeans;
import com.viseo.companion.dto.ActionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActionService {

    @Autowired
    private ActionDAO actionDAO;

    @Autowired
    private ActionMeansDAO actionMeansDAO;
    @Autowired
    private MeanDAO meansDAO;

    private ActionConverter converter = new ActionConverter();

    public ActionDTO addAction(ActionDTO actionDTO) {
        try {
            Action action = toAction(actionDTO);
            if (action != null) {
                action = actionDAO.addAction(action);
                long actionId=action.getId();
                actionDTO.getMeans().stream().forEach((m) -> {
                    ActionMeans actionMean = new ActionMeans( actionDAO.getActionsById(actionId), meansDAO.getMeanById(m.getMeanId()) , m.getQuantity());
                    actionMeansDAO.addMeansByAction(actionMean);
                });
                List<ActionMeans> means = actionMeansDAO.getMeansByAction(action.getId());
                return converter.getDTO(action, means);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    public List<ActionDTO> getActions() {
        List<Action> actions = actionDAO.getActions();
        List<ActionDTO> actionDtoList = new ArrayList<ActionDTO>();
        actions.stream().forEach(action ->
                actionDtoList.add(converter.getDTO(action, actionMeansDAO.getMeansByAction(action.getId()))));
        return actionDtoList;
    }

    public Action getActionById(long actionId) {
        try {
            return actionDAO.getActionsById(actionId);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private Action toAction(ActionDTO actionDTO) {
        Action action = new Action();
//        actionDTO.getMeans().forEach(p -> action.addMean(meanService.getMeanById(p)));
        if (actionDTO != null) {
            converter.apply(actionDTO, action);
            return action;
        }
        return null;
    }
}
