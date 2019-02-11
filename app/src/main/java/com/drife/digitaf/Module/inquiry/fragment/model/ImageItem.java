package com.drife.digitaf.Module.inquiry.fragment.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ImageItem implements Serializable{
    @SerializedName("id")
    public String id = "";
    @SerializedName("image_type")
    public String image_type = "";
    @SerializedName("filename")
    public String filename = "";
    @SerializedName("created_at")
    public String created_at = "";
    @SerializedName("updated_at")
    public String updated_at = "";

    public ImageItem() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageType() {
        return image_type;
    }

    public void setImageType(String image_type) {
        this.image_type = image_type;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(String updated_at) {
        this.updated_at = updated_at;
    }
}
