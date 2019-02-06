package com.mycompany.myapp.service.classlib;

public class Protocolret {
    String solvent;
    Double amount, reagentcost, wastecost;
    public Protocolret(String solvent, Double z, Double x, Double y) {
        this.solvent = solvent;
        amount = z;
        reagentcost = x;
        wastecost = y;
    }
    public String getSolvent() {
        return solvent;
    }

    public void setSolvent(String data) {
        this.solvent = data;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double data) {
        this.amount = data;
    }

    public Double getReagentcost() {
        return reagentcost;
    }

    public void setReagentcost(Double data) {
        this.reagentcost = data;
    }

    public Double getWastecost() {
        return wastecost;
    }

    public void setWastecost(Double data) {
        this.wastecost = data;
    }
    
}