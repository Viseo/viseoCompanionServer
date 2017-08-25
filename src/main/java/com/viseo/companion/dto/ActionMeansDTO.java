package com.viseo.companion.dto;


public class ActionMeansDTO extends BaseDTO {

    long meanId;
    Integer quantity;

    public ActionMeansDTO(){

    }

    public ActionMeansDTO(long id,Integer quantity){
        this.meanId=id;
        this.quantity=quantity;
    }
    public long getMeanId() {
        return meanId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setMeanId(long meanId) {
        this.meanId = meanId;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
