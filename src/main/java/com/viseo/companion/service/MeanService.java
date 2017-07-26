package com.viseo.companion.service;

import com.viseo.companion.dao.MeanDAO;
import com.viseo.companion.domain.Mean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeanService {

    @Autowired
    private MeanDAO meanDAO;

    public Mean addMean(Mean mean) {
        try {
            mean = meanDAO.addMean(mean);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return mean;
    }

    public List<Mean> getMeans() {
        try {
            return meanDAO.getMeans();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public Mean getMeanById(long id) {
        try {
            return meanDAO.getMeanById(id);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
