package com.viseo.companion.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Entity
public class ActivityMeans extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;
    @ManyToOne
    @JoinColumn(name = "mean_id")
    private Mean mean;

    private Integer quantity;

    public ActivityMeans() {
        super();
    }

    public ActivityMeans(Activity activity, Mean mean, Integer quantity) {
        this.activity = activity;
        this.mean = mean;
        this.quantity = quantity;
    }

    public Activity getActivity() {
        return activity;
    }

    public Mean getMean() {
        return mean;
    }

    public Integer getQuantity() {
        return quantity;
    }


    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setMean(Mean mean) {
        this.mean = mean;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


}
