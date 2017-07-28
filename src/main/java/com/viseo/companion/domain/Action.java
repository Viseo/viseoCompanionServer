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
    private String theme;
    private boolean asEvent;

    @ManyToMany
    private Set<Mean> means = new HashSet<Mean>();

    public Action() {
    }

    public Action(String name, String detail, double minGain, double maxGain, boolean status, Set<Mean> meanIds, String theme, boolean asEvent) {
        this.name = name;
        this.detail = detail;
        this.minGain = minGain;
        this.maxGain = maxGain;
        this.status = status;
        this.means = means;
        this.theme = theme;
        this.asEvent = asEvent;
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

    public Set<Mean> getMeans() {
        return means;
    }


    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void addMean(Mean mean) { means.add(mean); }

    public boolean isAsEvent() {
        return asEvent;
    }

    public void setAsEvent(boolean asEvent) {
        this.asEvent = asEvent;
    }
}
