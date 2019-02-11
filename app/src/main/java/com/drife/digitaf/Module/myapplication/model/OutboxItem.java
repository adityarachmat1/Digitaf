package com.drife.digitaf.Module.myapplication.model;

/**
 * Created by ferdinandprasetyo on 10/1/18.
 */

public class OutboxItem {
    public String name = "";
    public String lastAttempt = "";
    public int idOutbox = 0;

    public OutboxItem(String name, String lastAttempt, int idOutbox) {
        this.name = name;
        this.lastAttempt = lastAttempt;
        this.idOutbox = idOutbox;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastAttempt() {
        return lastAttempt;
    }

    public void setLastAttempt(String lastAttempt) {
        this.lastAttempt = lastAttempt;
    }

    public int getIdOutbox() {
        return idOutbox;
    }

    public void setIdOutbox(int idOutbox) {
        this.idOutbox = idOutbox;
    }
}
