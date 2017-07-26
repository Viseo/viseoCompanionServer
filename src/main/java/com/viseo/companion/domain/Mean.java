package com.viseo.companion.domain;

import javax.persistence.Entity;

@Entity
public class Mean extends BaseEntity {

    private String type;
    private String name;
    private char privilege;
    private boolean obligatory;
    private boolean shopMean;
    private boolean actionMean;

    private int vizzsPerMean;

    public Mean() {
    }

    public Mean(String type, String name, char privilege, boolean obligatory, boolean shopMean,
                boolean actionMean, int vizzsPerMean) {
        this.type = type;
        this.name = name;
        this.privilege = privilege;
        this.obligatory = obligatory;
        this.shopMean = shopMean;
        this.actionMean = actionMean;
        this.vizzsPerMean = vizzsPerMean;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getPrivilege() {
        return privilege;
    }

    public void setPrivilege(char privilege) {
        this.privilege = privilege;
    }

    public boolean isObligatory() {
        return obligatory;
    }

    public void setObligatory(boolean obligatory) {
        this.obligatory = obligatory;
    }

    public boolean isShopMean() {
        return shopMean;
    }

    public void setShopMean(boolean shopMean) {
        this.shopMean = shopMean;
    }

    public boolean isActionMean() {
        return actionMean;
    }

    public void setActionMean(boolean actionMean) {
        this.actionMean = actionMean;
    }

    public int getVizzsPerMean() {
        return vizzsPerMean;
    }

    public void setVizzsPerMean(int vizzsPerMean) {
        this.vizzsPerMean = vizzsPerMean;
    }
}
