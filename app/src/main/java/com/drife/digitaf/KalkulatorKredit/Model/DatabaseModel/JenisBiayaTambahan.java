package com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel;

import com.orm.SugarRecord;

public class JenisBiayaTambahan extends SugarRecord {
    private String IdJenisTambahan;
    private String Nama;
    private String Description;

    public JenisBiayaTambahan() {
    }

    public JenisBiayaTambahan(String idBiayaTambahan, String nama, String description) {
        IdJenisTambahan = idBiayaTambahan;
        Nama = nama;
        Description = description;
    }

    public String getIdJenisTambahan() {
        return IdJenisTambahan;
    }

    public void setIdJenisTambahan(String idJenisTambahan) {
        IdJenisTambahan = idJenisTambahan;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
