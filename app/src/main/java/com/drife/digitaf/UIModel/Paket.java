package com.drife.digitaf.UIModel;

import java.io.Serializable;

public class Paket implements Serializable {
    private String id;
    private String image;
    private String text;
    private boolean selected;
    private String description;
    private String catagoryGroupId;

    public Paket() {
    }

    public Paket(String id, String image, String text, boolean selected) {
        this.id = id;
        this.image = image;
        this.text = text;
        this.selected = selected;
    }

    public Paket(String id, String image, String text, boolean selected, String description, String catagoryGroupId) {
        this.id = id;
        this.image = image;
        this.text = text;
        this.selected = selected;
        this.description = description;
        this.catagoryGroupId = catagoryGroupId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCatagoryGroupId() {
        return catagoryGroupId;
    }

    public void setCatagoryGroupId(String catagoryGroupId) {
        this.catagoryGroupId = catagoryGroupId;
    }
}
