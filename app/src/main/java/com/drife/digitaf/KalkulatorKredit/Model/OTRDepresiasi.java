package com.drife.digitaf.KalkulatorKredit.Model;

import com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.Depresiasi;

public class OTRDepresiasi {
    private Depresiasi Depresiasi;
    private long OTR;

    public OTRDepresiasi() {
    }

    public OTRDepresiasi(com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.Depresiasi depresiasi, long OTR) {
        Depresiasi = depresiasi;
        this.OTR = OTR;
    }

    public com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.Depresiasi getDepresiasi() {
        return Depresiasi;
    }

    public void setDepresiasi(com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.Depresiasi depresiasi) {
        Depresiasi = depresiasi;
    }

    public long getOTR() {
        return OTR;
    }

    public void setOTR(long OTR) {
        this.OTR = OTR;
    }
}
