package com.drife.digitaf.Module.inquiry.fragment.model;

import com.drife.digitaf.retrofitmodule.SubmitParam.Customer;
import com.drife.digitaf.retrofitmodule.SubmitParam.CustomerJob;
import com.drife.digitaf.retrofitmodule.SubmitParam.Document;
import com.drife.digitaf.retrofitmodule.SubmitParam.Insurance;
import com.drife.digitaf.retrofitmodule.SubmitParam.JobAddress;
import com.drife.digitaf.retrofitmodule.SubmitParam.LegalAddress;
import com.drife.digitaf.retrofitmodule.SubmitParam.LifeInsurance;
import com.drife.digitaf.retrofitmodule.SubmitParam.ResidenceAddress;
import com.drife.digitaf.retrofitmodule.SubmitParam.Spouse;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class FormValue implements Serializable{
    @SerializedName("auth_key")
    String auth_key = "";
    @SerializedName("lead_id")
    String lead_id = "";
    @SerializedName("flag")
    String flag = "";
    @SerializedName("customer_name")
    String customer_name = "";
    @SerializedName("reference_name")
    String reference_name = "";
    @SerializedName("reference_phone")
    String reference_phone = "";
    @SerializedName("mobile_phone")
    String mobile_phone = "";
    @SerializedName("email")
    String email = "";
    @SerializedName("customer_model")
    String customer_model = "";
    @SerializedName("mother_maiden_name")
    String mother_maiden_name = "";
    @SerializedName("document")
    Document document = new Document();

    //Update 06 November 2018
    @SerializedName("no_kk")
    String no_kk = "";
    @SerializedName("no_npwp")
    String no_npwp = "";
    @SerializedName("no_spk")
    String no_spk = "";
    @SerializedName("npk")
    String npk = "";
    @SerializedName("ktp_salesman")
    String ktp_salesman = "";
    @SerializedName("merk")
    String merk = "";
    @SerializedName("model")
    String model = "";
    @SerializedName("type")
    String type = "";
    @SerializedName("suppl_code")
    String suppl_code = "";
    @SerializedName("suppl_branch")
    String suppl_branch = "";
    @SerializedName("otr")
    String otr = "";
    @SerializedName("dp_nett")
    String dp_nett = "";
    @SerializedName("tdp")
    String tdp = "";
    @SerializedName("asset_usage")
    String asset_usage = "";
    @SerializedName("provision_fee")
    String provision_fee = "";
    @SerializedName("fiducia_fee")
    String fiducia_fee = "";
    @SerializedName("tenor")
    String tenor = "";
    @SerializedName("first_installment_type")
    String first_installment_type = "";
    @SerializedName("flat_rate")
//    @SerializedName("effective_rate")
    String effective_rate = "";
    @SerializedName("installment_amount")
    String installment_amount = "";
    @SerializedName("total_ar")
    String total_ar = "";
    @SerializedName("subsidy_amt")
    String subsidy_amt = "";
    @SerializedName("product_offering_code")
    String product_offering_code = "";
    @SerializedName("provision_payment_type")
    String provision_payment_type = "";
    @SerializedName("wop_code")
    String wop_code = "";

    @SerializedName("other_fee")
    private List<HashMap<String,String>> other_fee;

    @SerializedName("customer")
    private Customer customer;

    @SerializedName("insurance")
    private Insurance insurance;

    @SerializedName("life_insurance")
    private LifeInsurance lifeInsurance;

    @SerializedName("spouse")
    private Spouse spouse;

    @SerializedName("legal_address") // alamat KTP
    private LegalAddress legal_address;

    @SerializedName("residence_address") // alamat customer sesuai ktp. apabila sesuai ktp, di kirim value sesuai ktp
    private ResidenceAddress residence_address;

    @SerializedName("job_address")
    private JobAddress job_address;

    @SerializedName("customer_job")
    private CustomerJob customer_job;

    public FormValue() {
    }

    public LifeInsurance getLifeInsurance() {
        return lifeInsurance;
    }

    public void setLifeInsurance(LifeInsurance lifeInsurance) {
        this.lifeInsurance = lifeInsurance;
    }

    public List<HashMap<String, String>> getOther_fee() {
        return other_fee;
    }

    public void setOther_fee(List<HashMap<String, String>> other_fee) {
        this.other_fee = other_fee;
    }

    public String getWop_code() {
        return wop_code;
    }

    public void setWop_code(String wop_code) {
        this.wop_code = wop_code;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }



    public String getAuthKey() {
        return auth_key;
    }

    public void setAuthKey(String auth_key) {
        this.auth_key = auth_key;
    }

    public String getLeadId() {
        return lead_id;
    }

    public void setLeadId(String lead_id) {
        this.lead_id = lead_id;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCustomerName() {
        return customer_name;
    }

    public void setCustomerName(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getReferenceName() {
        return reference_name;
    }

    public void setReferenceName(String reference_name) {
        this.reference_name = reference_name;
    }

    public String getReferencePhone() {
        return reference_phone;
    }

    public void setReferencePhone(String reference_phone) {
        this.reference_phone = reference_phone;
    }

    public String getMobilePhone() {
        return mobile_phone;
    }

    public void setMobilePhone(String mobile_phone) {
        this.mobile_phone = mobile_phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCustomerModel() {
        return customer_model;
    }

    public void setCustomerModel(String customer_model) {
        this.customer_model = customer_model;
    }

    public String getMotherMaidenName() {
        return mother_maiden_name;
    }

    public void setMotherMaidenName(String mother_maiden_name) {
        this.mother_maiden_name = mother_maiden_name;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public String getNpk() {
        return npk;
    }

    public void setNpk(String npk) {
        this.npk = npk;
    }

    public String getKtpSalesman() {
        return ktp_salesman;
    }

    public void setKtpSalesman(String ktp_salesman) {
        this.ktp_salesman = ktp_salesman;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSupplCode() {
        return suppl_code;
    }

    public void setSupplCode(String suppl_code) {
        this.suppl_code = suppl_code;
    }

    public String getSupplBranch() {
        return suppl_branch;
    }

    public void setSupplBranch(String suppl_branch) {
        this.suppl_branch = suppl_branch;
    }

    public String getOtr() {
        return otr;
    }

    public void setOtr(String otr) {
        this.otr = otr;
    }

    public String getDpNett() {
        return dp_nett;
    }

    public void setDpNett(String dp_nett) {
        this.dp_nett = dp_nett;
    }

    public String getTdp() {
        return tdp;
    }

    public void setTdp(String tdp) {
        this.tdp = tdp;
    }

    public String getAssetUsage() {
        return asset_usage;
    }

    public void setAssetUsage(String asset_usage) {
        this.asset_usage = asset_usage;
    }

    public String getProvisionFee() {
        return provision_fee;
    }

    public void setProvisionFee(String provision_fee) {
        this.provision_fee = provision_fee;
    }

    public String getFiduciaFee() {
        return fiducia_fee;
    }

    public void setFiduciaFee(String fiducia_fee) {
        this.fiducia_fee = fiducia_fee;
    }

    public String getTenor() {
        return tenor;
    }

    public void setTenor(String tenor) {
        this.tenor = tenor;
    }

    public String getFirstInstallmentType() {
        return first_installment_type;
    }

    public void setFirstInstallmentType(String first_installment_type) {
        this.first_installment_type = first_installment_type;
    }

    public String getEffectiveRate() {
        return effective_rate;
    }

    public void setEffectiveRate(String effective_rate) {
        this.effective_rate = effective_rate;
    }

    public String getInstallmentAmount() {
        return installment_amount;
    }

    public void setInstallmentAmount(String installment_amount) {
        this.installment_amount = installment_amount;
    }

    public String getTotalAr() {
        return total_ar;
    }

    public void setTotalAr(String total_ar) {
        this.total_ar = total_ar;
    }

    public String getSubsidyAmt() {
        return subsidy_amt;
    }

    public void setSubsidyAmt(String subsidy_amt) {
        this.subsidy_amt = subsidy_amt;
    }

    public String getProductOfferingCode() {
        return product_offering_code;
    }

    public void setProductOfferingCode(String product_offering_code) {
        this.product_offering_code = product_offering_code;
    }

    public String getProvisionPaymentType() {
        return provision_payment_type;
    }

    public void setProvisionPaymentType(String provision_payment_type) {
        this.provision_payment_type = provision_payment_type;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Spouse getSpouse() {
        return spouse;
    }

    public void setSpouse(Spouse spouse) {
        this.spouse = spouse;
    }

    public LegalAddress getLegal_address() {
        return legal_address;
    }

    public void setLegal_address(LegalAddress legal_address) {
        this.legal_address = legal_address;
    }

    public ResidenceAddress getResidence_address() {
        return residence_address;
    }

    public void setResidence_address(ResidenceAddress residence_address) {
        this.residence_address = residence_address;
    }

    public JobAddress getJob_address() {
        return job_address;
    }

    public void setJob_address(JobAddress job_address) {
        this.job_address = job_address;
    }

    public CustomerJob getCustomer_job() {
        return customer_job;
    }

    public void setCustomer_job(CustomerJob customer_job) {
        this.customer_job = customer_job;
    }
}
