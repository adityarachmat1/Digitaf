package com.drife.digitaf.Module.InputKredit.UploadDocument.Model;

/**
 * Created by ferdinandprasetyo on 10/4/18.
 */

public class ToggleMenuItem {
    String title = "";
    String dueDate = "";
    boolean isChecked = false;
    boolean isVisible = false;

    public ToggleMenuItem() {
    }

    public ToggleMenuItem(String title, String dueDate, boolean isChecked) {
        this.title = title;
        this.dueDate = dueDate;
        this.isChecked = isChecked;
    }

    public ToggleMenuItem(String title, String dueDate, boolean isChecked, boolean isVisible) {
        this.title = title;
        this.dueDate = dueDate;
        this.isChecked = isChecked;
        this.isVisible = isVisible;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
