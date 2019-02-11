package com.drife.digitaf.UIModel;

import java.io.Serializable;

public class ToDo implements Serializable{
    private String id;
    private String title;
    private String quantity;
    private String tooltipInfo;

    public ToDo() {
    }

    public ToDo(String id, String title, String quantity) {
        this.id = id;
        this.title = title;
        this.quantity = quantity;
    }

    public ToDo(String id, String title, String quantity, String tooltipInfo) {
        this.id = id;
        this.title = title;
        this.quantity = quantity;
        this.tooltipInfo = tooltipInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTooltipInfo() {
        return tooltipInfo;
    }

    public void setTooltipInfo(String tooltipInfo) {
        this.tooltipInfo = tooltipInfo;
    }
}
