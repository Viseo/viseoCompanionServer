package com.viseo.companion.dto;

import java.util.ArrayList;
import java.util.List;

public class ActionDTO extends BaseDTO {
    private String name;
    private String detail;
    private double minGain;
    private double maxGain;
    private boolean status;
    private String theme;
    private boolean asEvent;
    private List<ActionMeansDTO> means = new ArrayList<ActionMeansDTO>();

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

    public List<ActionMeansDTO> getMeans() {
        return means;
    }

    public void setMeans(List<ActionMeansDTO> means) {
        this.means = means;
    }

    public void addMean(ActionMeansDTO mean) {
        means.add(mean);
    }

    public boolean isAsEvent() {
        return asEvent;
    }

    public void setAsEvent(boolean asEvent) {
        this.asEvent = asEvent;
    }
}