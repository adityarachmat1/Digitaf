package com.drife.digitaf.Service.Inquiry;

import com.drife.digitaf.Module.inquiry.fragment.model.InquiryItem;
import com.drife.digitaf.retrofitmodule.Model.PackageRule;

import java.util.List;

public interface InquiryCallback {
    void onSuccessGetInquiry(List<InquiryItem> inquiryItems);
    void onFailedGetInquiry(String message);
}
