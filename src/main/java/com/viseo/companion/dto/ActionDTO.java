package com.viseo.companion.dto;

import com.viseo.companion.domain.Mean;

import java.util.ArrayList;
import java.util.List;

public class ActionDTO extends BaseDTO {
    private String name;
    private String detail;
    private double minGain;
    private double maxGain;
    private boolean status;
    private String theme;

    private List<Long> means = new ArrayList<Long>();

    public ActionDTO() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public double getMinGain() {
        return minGain;
    }

    public void setMinGain(double minGain) {
        this.minGain = minGain;
    }

    public double getMaxGain() {
        return maxGain;
    }

    public void setMaxGain(double maxGain) {
        this.maxGain = maxGain;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public List<Long> getMeans() {
        return means;
    }

    public void setMeans(List<Long> means) {
        this.means = means;
    }

    public void addMean(Long meanId) {
        means.add(meanId);
    }
}