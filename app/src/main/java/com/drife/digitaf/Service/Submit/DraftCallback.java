package com.drife.digitaf.Service.Submit;

public interface DraftCallback {
    void onSuccessDraft(int formId, String date);
    void onFailedDraft(String message);
}
