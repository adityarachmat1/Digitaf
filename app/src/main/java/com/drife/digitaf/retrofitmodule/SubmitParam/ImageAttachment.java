package com.drife.digitaf.retrofitmodule.SubmitParam;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ImageAttachment implements Serializable{

    @SerializedName("type")
    private String type;
    @SerializedName("file")
    private String file;

    public ImageAttachment() {
    }

    public ImageAttachment(String type, String file) {
        this.type = type;
        this.file = file;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
