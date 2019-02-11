package com.drife.digitaf.GeneralUtility.ImageUtility;

import android.graphics.Bitmap;

public class Image {
    private Bitmap bitmap;
    private String name;
    private String path;

    public Image() {
    }

    public Image(Bitmap bitmap, String name) {
        this.bitmap = bitmap;
        this.name = name;
    }

    public Image(Bitmap bitmap, String name, String path) {
        this.bitmap = bitmap;
        this.name = name;
        this.path = path;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
