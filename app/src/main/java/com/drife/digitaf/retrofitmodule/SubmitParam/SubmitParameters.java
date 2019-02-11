package com.drife.digitaf.retrofitmodule.SubmitParam;

import com.drife.digitaf.ORM.Database.Draft;

import java.io.Serializable;

public class SubmitParameters implements Serializable {
    public static InquiryParam inquiryParam = new InquiryParam();
    public static EmailParam emailParam = new EmailParam();
    public static Draft draft = new Draft();
}
