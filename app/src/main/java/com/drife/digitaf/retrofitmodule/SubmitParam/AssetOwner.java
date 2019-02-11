package com.drife.digitaf.retrofitmodule.SubmitParam;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AssetOwner implements Serializable {
    @SerializedName("owner_name")
    private String owner_name;
    @SerializedName("id_no")
    private String id_no;
    @SerializedName("addr")
    private String addr;
    @SerializedName("rt")
    private String rt;
    @SerializedName("rw")
    private String rw;
    @SerializedName("kelurahan")
    private String kelurahan;
    @SerializedName("kecamatan")
    private String kecamatan;
    @SerializedName("city")
    private String city;
    @SerializedName("zipcode")
    private String zipcode;

    public AssetOwner() {
    }

    public AssetOwner(String owner_name, String id_no, String addr, String rt, String rw, String kelurahan, String kecamatan, String city, String zipcode) {
        this.owner_name = owner_name;
        this.id_no = id_no;
        this.addr = addr;
        this.rt = rt;
        this.rw = rw;
        this.kelurahan = kelurahan;
        this.kecamatan = kecamatan;
        this.city = city;
        this.zipcode = zipcode;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getId_no() {
        return id_no;
    }

    public void setId_no(String id_no) {
        this.id_no = id_no;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getRt() {
        return rt;
    }

    public void setRt(String rt) {
        this.rt = rt;
    }

    public String getRw() {
        return rw;
    }

    public void setRw(String rw) {
        this.rw = rw;
    }

    public String getKelurahan() {
        return kelurahan;
    }

    public void setKelurahan(String kelurahan) {
        this.kelurahan = kelurahan;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
