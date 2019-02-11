package com.drife.digitaf.retrofitmodule.SubmitParam;

import com.drife.digitaf.retrofitmodule.Model.PVPremi;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class EmailParam implements Serializable{

    @SerializedName("email")
    private String email;

    @SerializedName("brand")
    private String brand;

    @SerializedName("model")
    private String model;

    @SerializedName("branch")
    private String branch;

    @SerializedName("pv_premi")
    private String pv_premi;

    @SerializedName("paket")
    private String paket;

    @SerializedName("otr")
    private String otr;

    @SerializedName("first_instalment")
    private String first_instalment;

    @SerializedName("dp_nett")
    private String dp_nett;

    @SerializedName("flat_rate")
    private String flat_rate;

    @SerializedName("admin_fee")
    private String admin_fee;

    @SerializedName("tdp")
    private String tdp;

    @SerializedName("angsuran")
    private String angsuran;

    @SerializedName("tenor")
    private String tenor;

    @SerializedName("pcl")
    private String pcl;

    @SerializedName("pcl_name")
    private String pcl_name;

    @SerializedName("total_hutang")
    private String total_hutang;

    @SerializedName("pokok_hutang")
    private String pokok_hutang;

    @SerializedName("pv_list")
    private List<InsuranceList> pv_list;

    @SerializedName("pv_total")
    private String pv_total;

    @SerializedName("pv_type")
    private String pv_type;

    @SerializedName("dp_precentage")
    private String dp_precentage;

    public EmailParam() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getPv_premi() {
        return pv_premi;
    }

    public void setPv_premi(String pv_premi) {
        this.pv_premi = pv_premi;
    }

    public String getPaket() {
        return paket;
    }

    public void setPaket(String paket) {
        this.paket = paket;
    }

    public String getOtr() {
        return otr;
    }

    public void setOtr(String otr) {
        this.otr = otr;
    }

    public String getFirst_instalment() {
        return first_instalment;
    }

    public void setFirst_instalment(String first_instalment) {
        this.first_instalment = first_instalment;
    }

    public String getDp_nett() {
        return dp_nett;
    }

    public void setDp_nett(String dp_nett) {
        this.dp_nett = dp_nett;
    }

    public String getFlat_rate() {
        return flat_rate;
    }

    public void setFlat_rate(String flat_rate) {
        this.flat_rate = flat_rate;
    }

    public String getAdmin_fee() {
        return admin_fee;
    }

    public void setAdmin_fee(String admin_fee) {
        this.admin_fee = admin_fee;
    }

    public String getTdp() {
        return tdp;
    }

    public void setTdp(String tdp) {
        this.tdp = tdp;
    }

    public String getAngsuran() {
        return angsuran;
    }

    public void setAngsuran(String angsuran) {
        this.angsuran = angsuran;
    }

    public String getTenor() {
        return tenor;
    }

    public void setTenor(String tenor) {
        this.tenor = tenor;
    }

    public String getPcl() {
        return pcl;
    }

    public void setPcl(String pcl) {
        this.pcl = pcl;
    }

    public String getPcl_name() {
        return pcl_name;
    }

    public void setPcl_name(String pcl_name) {
        this.pcl_name = pcl_name;
    }

    public String getTotal_hutang() {
        return total_hutang;
    }

    public void setTotal_hutang(String total_hutang) {
        this.total_hutang = total_hutang;
    }

    public String getPokok_hutang() {
        return pokok_hutang;
    }

    public void setPokok_hutang(String pokok_hutang) {
        this.pokok_hutang = pokok_hutang;
    }

    public List<InsuranceList> getPv_list() {
        return pv_list;
    }

    public void setPv_list(List<InsuranceList> pv_list) {
        this.pv_list = pv_list;
    }

    public String getPv_total() {
        return pv_total;
    }

    public void setPv_total(String pv_total) {
        this.pv_total = pv_total;
    }

    public String getPv_type() {
        return pv_type;
    }

    public void setPv_type(String pv_type) {
        this.pv_type = pv_type;
    }

    public String getDp_precentage() {
        return dp_precentage;
    }

    public void setDp_precentage(String dp_precentage) {
        this.dp_precentage = dp_precentage;
    }
}
