package com.drife.digitaf.ORM.Database;

import com.orm.SugarRecord;

import java.io.Serializable;

public class Outbox_submit extends SugarRecord implements Serializable {
    private String OutboxId;
    private String Form;
    private String Attach;
    private String CustomerName;
    private String Date;
    private String Status;
    private String User_id;
    private String AttachPDF;
    private String Dp_percentage;


    public Outbox_submit() {
    }

    public Outbox_submit(String outboxId, String form, String attach, String customerName, String date, String status, String user_id, String attachPDF, String dp_percentage) {
        OutboxId = outboxId;
        Form = form;
        Attach = attach;
        CustomerName = customerName;
        Date = date;
        Status = status;
        User_id = user_id;
        AttachPDF = attachPDF;
        User_id = user_id;
        Dp_percentage = dp_percentage;
    }

    public String getUser_id() {
        return User_id;
    }

    public void setUser_id(String user_id) {
        User_id = user_id;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getOutboxId() {
        return OutboxId;
    }

    public void setOutboxId(String outboxId) {
        OutboxId = outboxId;
    }

    public String getForm() {
        return Form;
    }

    public void setForm(String form) {
        Form = form;
    }

    public String getAttach() {
        return Attach;
    }

    public void setAttach(String attach) {
        Attach = attach;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getAttachPDF() {
        return AttachPDF;
    }

    public void setAttachPDF(String attachPDF) {
        AttachPDF = attachPDF;
    }

    public String getDp_percentage() {
        return Dp_percentage;
    }

    public void setDp_percentage(String dp_percentage) {
        Dp_percentage = dp_percentage;
    }
}

