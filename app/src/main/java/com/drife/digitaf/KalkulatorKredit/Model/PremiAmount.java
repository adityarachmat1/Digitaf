package com.drife.digitaf.KalkulatorKredit.Model;

import java.io.Serializable;

public class PremiAmount implements Serializable {
    private long amount;
    private String type;

    public PremiAmount() {
    }

    public PremiAmount(long amount, String type) {
        this.amount = amount;
        this.type = type;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
