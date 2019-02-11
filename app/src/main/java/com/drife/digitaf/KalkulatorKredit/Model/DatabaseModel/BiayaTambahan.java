package com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel;

import com.orm.SugarRecord;

public class BiayaTambahan extends SugarRecord{
    private String IdBiayaTambahan;
    private String IdJenisTambahan;
    private String IdTenor;
    private int Price;

    public BiayaTambahan() {
    }

    public BiayaTambahan(String idBiayaTambahan, String idJenisTambahan, String idTenor, int price) {
        IdBiayaTambahan = idBiayaTambahan;
        IdJenisTambahan = idJenisTambahan;
        IdTenor = idTenor;
        Price = price;
    }

    public String getIdBiayaTambahan() {
        return IdBiayaTambahan;
    }

    public void setIdBiayaTambahan(String idBiayaTambahan) {
        IdBiayaTambahan = idBiayaTambahan;
    }

    public String getIdJenisTambahan() {
        return IdJenisTambahan;
    }

    public void setIdJenisTambahan(String idJenisTambahan) {
        IdJenisTambahan = idJenisTambahan;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public String getIdTenor() {
        return IdTenor;
    }

    public void setIdTenor(String idTenor) {
        IdTenor = idTenor;
    }
}
