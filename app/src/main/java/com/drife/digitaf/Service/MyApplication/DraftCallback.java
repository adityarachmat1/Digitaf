package com.drife.digitaf.Service.MyApplication;

import com.drife.digitaf.Module.inquiry.fragment.model.InquiryItem;
import com.drife.digitaf.Module.myapplication.model.DraftItem;

import java.util.List;

public interface DraftCallback {
    void onSuccessGetDraft(List<DraftItem> draftItems);
    void onFailedGetDraft(String message);
}
