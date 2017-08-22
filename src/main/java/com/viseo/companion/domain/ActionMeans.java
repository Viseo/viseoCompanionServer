package com.viseo.companion.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Entity
public class ActionMeans extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "action_id")
    private Action action;
    @ManyToOne
    @JoinColumn(name = "mean_id")
    private Mean mean;


    private Integer quantity;

    public ActionMeans() {
        super();
    }

    public ActionMeans(Action action, Mean mean, Integer quantity) {
        this.action = action;
        this.mean = mean;
        this.quantity = quantity;
    }

    public Action getAction() {
        return action;
    }

    public Mean getMean() {
        return mean;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public void setMean(Mean mean) {
        this.mean = mean;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
