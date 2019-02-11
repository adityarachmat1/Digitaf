package com.drife.digitaf.ORM.Database;

import com.orm.SugarRecord;

import java.io.Serializable;

public class Draft extends SugarRecord implements Serializable{
    private String CustomerId;
    private String NamaCustomer;
    private String LastSaved;
    private String Data;
    private Boolean Npwp;
    private String isNonPaket;
    private int PaymentType;
    private String PokokHutangAkhir;
    private String Otr;
    private String pvPremiAmount;
    private String User_id;
    private String Is_corporate;
    private String Is_simulasi_paket;
    private String Is_simulasi_budget;
    private String Is_non_paket;
    private String Is_npwp;
    private String Payment_type_service_package;
    private String Coverage_type;

    public Draft() {
    }

    public Draft(String customerId, String namaCustomer, String lastSaved, String data, Boolean npwp, String isnonPaket
            ,int paymentType, String pokokHutangAkhir, String otr, String PvPremiAmount, String user_id, String is_corporate
            , String is_simulasi_paket, String is_simulasi_budget, String is_non_paket, String is_npwp,
                 String payment_type_service_package, String coverage_type) {
        Is_corporate = is_corporate;
        Is_simulasi_paket = is_simulasi_paket;
        Is_simulasi_budget = is_simulasi_budget;
        Is_non_paket = is_non_paket;
        Is_npwp = is_npwp;
        Payment_type_service_package = payment_type_service_package;
        Coverage_type = coverage_type;
        CustomerId = customerId;
        NamaCustomer = namaCustomer;
        LastSaved = lastSaved;
        Data = data;
        Npwp = npwp;
        isNonPaket = isnonPaket;
        PaymentType = paymentType;
        PokokHutangAkhir = pokokHutangAkhir;
        Otr = otr;
        pvPremiAmount = PvPremiAmount;
        User_id = user_id;
    }

    public String getIs_corporate() {
        return Is_corporate;
    }

    public void setIs_corporate(String is_corporate) {
        Is_corporate = is_corporate;
    }

    public String getIs_simulasi_paket() {
        return Is_simulasi_paket;
    }

    public void setIs_simulasi_paket(String is_simulasi_paket) {
        Is_simulasi_paket = is_simulasi_paket;
    }

    public String getIs_simulasi_budget() {
        return Is_simulasi_budget;
    }

    public void setIs_simulasi_budget(String is_simulasi_budget) {
        Is_simulasi_budget = is_simulasi_budget;
    }

    public String getIs_non_paket() {
        return Is_non_paket;
    }

    public void setIs_non_paket(String is_non_paket) {
        Is_non_paket = is_non_paket;
    }

    public String getIs_npwp() {
        return Is_npwp;
    }

    public void setIs_npwp(String is_npwp) {
        Is_npwp = is_npwp;
    }

    public String getPayment_type_service_package() {
        return Payment_type_service_package;
    }

    public void setPayment_type_service_package(String payment_type_service_package) {
        Payment_type_service_package = payment_type_service_package;
    }

    public String getCoverage_type() {
        return Coverage_type;
    }

    public void setCoverage_type(String coverage_type) {
        Coverage_type = coverage_type;
    }

    public String getUser_id() {
        return User_id;
    }

    public void setUser_id(String user_id) {
        User_id = user_id;
    }

    public String getIsNonPaket() {
        return isNonPaket;
    }

    public void setIsNonPaket(String isNonPaket) {
        this.isNonPaket = isNonPaket;
    }

    public int getPaymentType() {
        return PaymentType;
    }

    public void setPaymentType(int paymentType) {
        PaymentType = paymentType;
    }

    public String getPokokHutangAkhir() {
        return PokokHutangAkhir;
    }

    public void setPokokHutangAkhir(String pokokHutangAkhir) {
        PokokHutangAkhir = pokokHutangAkhir;
    }

    public String getOtr() {
        return Otr;
    }

    public void setOtr(String otr) {
        Otr = otr;
    }

    public String getPvPremiAmount() {
        return pvPremiAmount;
    }

    public void setPvPremiAmount(String pvPremiAmount) {
        this.pvPremiAmount = pvPremiAmount;
    }

    public Boolean getNpwp() {
        return Npwp;
    }

    public void setNpwp(Boolean npwp) {
        Npwp = npwp;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public String getNamaCustomer() {
        return NamaCustomer;
    }

    public void setNamaCustomer(String namaCustomer) {
        NamaCustomer = namaCustomer;
    }

    public String getLastSaved() {
        return LastSaved;
    }

    public void setLastSaved(String lastSaved) {
        LastSaved = lastSaved;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }
}
