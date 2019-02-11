package com.drife.digitaf.retrofitmodule.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DocumentImage implements Serializable {
    @SerializedName("id")
    private String id;

    @SerializedName("image_type")
    private String image_type;

    @SerializedName("filename")
    private String filename;

    @SerializedName("created_at")
    private String created_at;

    @SerializedName("updated_at")
    private String updated_at;

    public DocumentImage() {
    }

    public DocumentImage(String id, String image_type, String filename, String created_at, String updated_at) {
        this.id = id;
        this.image_type = image_type;
        this.filename = filename;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage_type() {
        return image_type;
    }

    public void setImage_type(String image_type) {
        this.image_type = image_type;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
