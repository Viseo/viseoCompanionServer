package com.viseo.companion.service;

import com.viseo.companion.dao.ActionDAO;
import com.viseo.companion.domain.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActionService {

    @Autowired
    private ActionDAO actionDAO;

    public Action addAction(Action action) {
        try {
            action = actionDAO.addAction(action);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return action;
    }

    public List<Action> getActions() {
        return actionDAO.getActions();
    }
}
