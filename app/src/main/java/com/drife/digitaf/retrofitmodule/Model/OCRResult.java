package com.drife.digitaf.retrofitmodule.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OCRResult implements Serializable {
    @SerializedName("Agama")
    private String Agama;
    @SerializedName("Alamat")
    private String Alamat;
    @SerializedName("BerlakuHingga")
    private String BerlakuHingga;
    @SerializedName("GolDarah")
    private String GolDarah;
    @SerializedName("JenisKelamin")
    private String JenisKelamin;
    @SerializedName("Kecamatan")
    private String Kecamatan;
    @SerializedName("Kel/Desa")
    private String KelDesa;
    @SerializedName("Kewarganegaraan")
    private String Kewarganegaraan;
    @SerializedName("NIK")
    private String NIK;
    @SerializedName("Nama")
    private String Nama;
    @SerializedName("Pekerjaan")
    private String Pekerjaan;
    @SerializedName("RT/RW")
    private String RtRw;
    @SerializedName("StatusPerkawinan")
    private String StatusPerkawinan;
    @SerializedName("Tempat/Tgl Lahir")
    private String TempatTanggalLahir;

    public OCRResult() {
    }

    public String getAgama() {
        return Agama;
    }

    public void setAgama(String agama) {
        Agama = agama;
    }

    public String getAlamat() {
        return Alamat;
    }

    public void setAlamat(String alamat) {
        Alamat = alamat;
    }

    public String getBerlakuHingga() {
        return BerlakuHingga;
    }

    public void setBerlakuHingga(String berlakuHingga) {
        BerlakuHingga = berlakuHingga;
    }

    public String getGolDarah() {
        return GolDarah;
    }

    public void setGolDarah(String golDarah) {
        GolDarah = golDarah;
    }

    public String getJenisKelamin() {
        return JenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        JenisKelamin = jenisKelamin;
    }

    public String getKecamatan() {
        return Kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        Kecamatan = kecamatan;
    }

    public String getKelDesa() {
        return KelDesa;
    }

    public void setKelDesa(String kelDesa) {
        KelDesa = kelDesa;
    }

    public String getKewarganegaraan() {
        return Kewarganegaraan;
    }

    public void setKewarganegaraan(String kewarganegaraan) {
        Kewarganegaraan = kewarganegaraan;
    }

    public String getNIK() {
        return NIK;
    }

    public void setNIK(String NIK) {
        this.NIK = NIK;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getPekerjaan() {
        return Pekerjaan;
    }

    public void setPekerjaan(String pekerjaan) {
        Pekerjaan = pekerjaan;
    }

    public String getRtRw() {
        return RtRw;
    }

    public void setRtRw(String rtRw) {
        RtRw = rtRw;
    }

    public String getStatusPerkawinan() {
        return StatusPerkawinan;
    }

    public void setStatusPerkawinan(String statusPerkawinan) {
        StatusPerkawinan = statusPerkawinan;
    }

    public String getTempatTanggalLahir() {
        return TempatTanggalLahir;
    }

    public void setTempatTanggalLahir(String tempatTanggalLahir) {
        TempatTanggalLahir = tempatTanggalLahir;
    }
}
