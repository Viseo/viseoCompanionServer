package com.viseo.companion.domain;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Action extends BaseEntity {

    private String name;
    private String detail;
    private double minGain;
    private double maxGain;
    private boolean status;

    @ManyToMany
    private Set<Mean> meanIds = new HashSet<Mean>();

    private String theme;

    public Action() {
    }

    public Action(String name, String detail, double minGain, double maxGain, boolean status, Set<Mean> meanIds, String theme) {
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

    public Set<Mean> getMeanIds() {
        return meanIds;
    }


    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
