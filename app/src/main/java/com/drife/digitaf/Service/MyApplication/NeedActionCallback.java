package com.drife.digitaf.Service.MyApplication;

import com.drife.digitaf.Module.inquiry.fragment.model.InquiryItem;
import com.drife.digitaf.Module.myapplication.model.DraftItem;
import com.drife.digitaf.Module.myapplication.model.NeedActionItem;

import java.util.List;

public interface NeedActionCallback {
    void onSuccessGetNeedAction(List<InquiryItem> needActionItems);
    void onFailedGetNeedAction(String message);
}
