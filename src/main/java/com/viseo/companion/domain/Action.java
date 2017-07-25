package com.viseo.companion.domain;

import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

public class Action extends BaseEntity {

    private String name;
    private String detail;
    private double minGain;
    private double maxGain;
    private boolean status;

    @ManyToMany
    private List<String> meanIds = new ArrayList<String>();
    private String theme;

    public Action() {
    }

    public Action(String name, String detail, double minGain, double maxGain, boolean status, List<String> meanIds, String theme) {
        this.name = name;
        this.detail = detail;
        this.minGain = minGain;
        this.maxGain = maxGain;
        this.status = status;
        this.meanIds = meanIds;
        this.theme = theme;
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

    public List<String> getMeanIds() {
        return meanIds;
    }

    public void setMeanIds(List<String> meanIds) {
        this.meanIds = meanIds;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
