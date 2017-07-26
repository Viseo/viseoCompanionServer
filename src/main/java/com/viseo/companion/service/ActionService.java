package com.viseo.companion.service;

import com.viseo.companion.converter.ActionConverter;
import com.viseo.companion.dao.ActionDAO;
import com.viseo.companion.domain.Action;
import com.viseo.companion.dto.ActionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActionService {

    @Autowired
    private ActionDAO actionDAO;

    @Autowired
    private MeanService meanService;

    private ActionConverter converter = new ActionConverter();

    public ActionDTO addAction(ActionDTO actionDTO) {
        try {
        Action action = toAction(actionDTO);
            if (action != null) {
                action = actionDAO.addAction(action);
                return converter.getDTO(action);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    public List<Action> getActions() {
        return actionDAO.getActions();
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
        actionDTO.getMeans().forEach(p -> action.addMean(meanService.getMeanById(p)));
        if (actionDTO != null) {
            converter.apply(actionDTO, action);
            return action;
        }
        return null;
    }
}
