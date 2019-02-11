package com.drife.digitaf.retrofitmodule.SubmitParam;

import com.drife.digitaf.retrofitmodule.Model.DocTC;

import java.io.Serializable;

public class DocTcData implements Serializable{

    public static DocTC ktp = new DocTC("1","C01","KTP");
    public static DocTC ktpSpouse = new DocTC("2","C02","KTP Spouse");
    public static DocTC kk = new DocTC("7","C07","KK");
    public static DocTC npwp = new DocTC("10","C10","NPWP");
    public static DocTC ijinPraktek  = new DocTC("9","C09","Ijin Praktek");
    public static DocTC siup = new DocTC("11","C11","SIUP");
    public static DocTC rekeningKoran = new DocTC("13","C13","Rekening Koran");
    public static DocTC laporanKeuangan = new DocTC("14","C14","Laporan Keuangan");
    public static DocTC suratMenikah = new DocTC("19","C19","Surat Menikah");
    public static DocTC pbb = new DocTC("22","C22","PBB");
    public static DocTC dokumenKematian = new DocTC("20","C20","Dokumen Kematian");
    public static DocTC kartuNama = new DocTC("40","C24","Kartu Nama Customer");
    public static DocTC kartuNamaSpouse = new DocTC("41","C25","Kartu Nama Spouse");
    public static DocTC KTPSTNK = new DocTC("42","C26","KTP Atas Nama STNK");
    public static DocTC KKSTNK = new DocTC("43","C27","KK Atas Nama STNK");
    public static DocTC spk = new DocTC("39","C23","SPK");
    public static DocTC coverTabungan = new DocTC("39","C28","Cover Tabungan");
    public static DocTC keteranganDomisili = new DocTC("39","C29","Keterangan Domisili");
    public static DocTC slipGaji = new DocTC("39","C12","Slip Gaji");
    /*public static DocTC rekeningKoran = new DocTC("13","C12","Rekening Koran");
    public static DocTC rekeningKoran = new DocTC("13","C12","Rekening Koran");
    public static DocTC rekeningKoran = new DocTC("13","C12","Rekening Koran");
    public static DocTC rekeningKoran = new DocTC("13","C12","Rekening Koran");
    public static DocTC rekeningKoran = new DocTC("13","C12","Rekening Koran");
    public static DocTC rekeningKoran = new DocTC("13","C12","Rekening Koran");*/
}
