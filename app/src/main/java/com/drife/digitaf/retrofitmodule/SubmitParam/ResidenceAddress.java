package com.drife.digitaf.retrofitmodule.SubmitParam;

import java.io.Serializable;

public class ResidenceAddress implements Serializable{
    private String address;
    private String rt;
    private String rw;
    private String kelurahan;
    private String kecamatan;
    private String zipcode;
    private String city;

    public ResidenceAddress() {
    }

    public ResidenceAddress(String address, String rt, String rw, String kelurahan, String kecamatan, String zipcode, String city) {
        this.address = address;
        this.rt = rt;
        this.rw = rw;
        this.kelurahan = kelurahan;
        this.kecamatan = kecamatan;
        this.zipcode = zipcode;
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
