package com.drife.digitaf.retrofitmodule.SubmitParam;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class InquiryParam implements Serializable {
    @SerializedName("auth_key")
    private String auth_key;

    @SerializedName("lead_id")
    private String lead_id;

    @SerializedName("flag") //status user ketika login (ambil dari session); SO | SD
    private String flag;

    @SerializedName("customer_name") //dari form
    private String customer_name;

    @SerializedName("reference_name") //dari form minimum customer data
    private String reference_name;

    @SerializedName("reference_phone") //dari form minimum customer data
    private String reference_phone;

    @SerializedName("mobile_phone") //dari form minumum customer data
    private String mobile_phone;

    @SerializedName("email")//dari form minimum customer data
    private String email;

    @SerializedName("customer_model") //dari form minimum customer data (tipe customer); PROF | EMP | SME | SMEBU
    private String customer_model;

    @SerializedName("mother_maiden_name") //String kosong dulu
    private String mother_maiden_name;

    @SerializedName("customer")
    private Customer customer;

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

    @SerializedName("no_kk")
    private String no_kk;

    @SerializedName("no_npwp")
    private String no_npwp;

    @SerializedName("no_spk")
    private String no_spk;

    @SerializedName("document")
    private Document document;

    @SerializedName("npk")
    private String npk;

    @SerializedName("ktp_salesman")
    private String ktp_salesman;

    @SerializedName("merk")
    private String merk;

    @SerializedName("model")
    private String model;

    @SerializedName("type")
    private String type;

    @SerializedName("suppl_code")
    private String suppl_code;

    @SerializedName("suppl_branch")
    private String suppl_branch;

    @SerializedName("otr")
    private String otr;

    @SerializedName("dp_nett")
    private String dp_nett;

    @SerializedName("tdp")
    private String tdp;

    @SerializedName("asset_usage")
    private String asset_usage;

    @SerializedName("provision_fee")
    private String provision_fee;

    @SerializedName("provision_payment_type")
    private String provision_payment_type;

    @SerializedName("fiducia_fee") //di kosongin (set 0)
    private String fiducia_fee;

    @SerializedName("tenor")
    private String tenor;

    @SerializedName("first_installment_type") //AD == ADDM | AR == ADDB
    private String first_installment_type;

    @SerializedName("flat_rate")
//    @SerializedName("effective_rate")
    private String effective_rate;

    @SerializedName("installment_amount")
    private String installment_amount;

    @SerializedName("total_ar")
    private String total_ar;

    @SerializedName("subsidy_amt")
    private String subsidy_amt;

    @SerializedName("product_offering_code")
    private String product_offering_code;

    @SerializedName("insurance")
    private Insurance insurance;

    @SerializedName("life_insurance")
    private LifeInsurance life_insurance;

    @SerializedName("other_fee")
    private List<HashMap<String,String>> other_fee;

    @SerializedName("is_vip")
    private String is_vip;

    @SerializedName("wop_code")
    private String wop_code;

    @SerializedName("spouse_job")
    private SpouseJob spouse_job;

    @SerializedName("asset_owner")
    private AssetOwner asset_owner;

    @SerializedName("office")
    private String offince;

    @SerializedName("package_service_price")
    private String package_service_price;


    public InquiryParam() {
    }

    public String getAuth_key() {
        return auth_key;
    }

    public void setAuth_key(String auth_key) {
        this.auth_key = auth_key;
    }

    public String getLead_id() {
        return lead_id;
    }

    public void setLead_id(String lead_id) {
        this.lead_id = lead_id;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getReference_name() {
        return reference_name;
    }

    public void setReference_name(String reference_name) {
        this.reference_name = reference_name;
    }

    public String getReference_phone() {
        return reference_phone;
    }

    public void setReference_phone(String reference_phone) {
        this.reference_phone = reference_phone;
    }

    public String getMobile_phone() {
        return mobile_phone;
    }

    public void setMobile_phone(String mobile_phone) {
        this.mobile_phone = mobile_phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCustomer_model() {
        return customer_model;
    }

    public void setCustomer_model(String customer_model) {
        this.customer_model = customer_model;
    }

    public String getMother_maiden_name() {
        return mother_maiden_name;
    }

    public void setMother_maiden_name(String mother_maiden_name) {
        this.mother_maiden_name = mother_maiden_name;
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

    public String getNo_kk() {
        return no_kk;
    }

    public void setNo_kk(String no_kk) {
        this.no_kk = no_kk;
    }

    public String getNo_npwp() {
        return no_npwp;
    }

    public void setNo_npwp(String no_npwp) {
        this.no_npwp = no_npwp;
    }

    public String getNo_spk() {
        return no_spk;
    }

    public void setNo_spk(String no_spk) {
        this.no_spk = no_spk;
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

    public String getKtp_salesman() {
        return ktp_salesman;
    }

    public void setKtp_salesman(String ktp_salesman) {
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

    public String getSuppl_code() {
        return suppl_code;
    }

    public void setSuppl_code(String suppl_code) {
        this.suppl_code = suppl_code;
    }

    public String getSuppl_branch() {
        return suppl_branch;
    }

    public void setSuppl_branch(String suppl_branch) {
        this.suppl_branch = suppl_branch;
    }

    public String getOtr() {
        return otr;
    }

    public void setOtr(String otr) {
        this.otr = otr;
    }

    public String getDp_nett() {
        return dp_nett;
    }

    public void setDp_nett(String dp_nett) {
        this.dp_nett = dp_nett;
    }

    public String getTdp() {
        return tdp;
    }

    public void setTdp(String tdp) {
        this.tdp = tdp;
    }

    public String getAsset_usage() {
        return asset_usage;
    }

    public void setAsset_usage(String asset_usage) {
        this.asset_usage = asset_usage;
    }

    public String getProvision_fee() {
        return provision_fee;
    }

    public void setProvision_fee(String provision_fee) {
        this.provision_fee = provision_fee;
    }

    public String getProvision_payment_type() {
        return provision_payment_type;
    }

    public void setProvision_payment_type(String provision_payment_type) {
        this.provision_payment_type = provision_payment_type;
    }

    public String getFiducia_fee() {
        return fiducia_fee;
    }

    public void setFiducia_fee(String fiducia_fee) {
        this.fiducia_fee = fiducia_fee;
    }

    public String getTenor() {
        return tenor;
    }

    public void setTenor(String tenor) {
        this.tenor = tenor;
    }

    public String getFirst_installment_type() {
        return first_installment_type;
    }

    public void setFirst_installment_type(String first_installment_type) {
        this.first_installment_type = first_installment_type;
    }

    public String getEffective_rate() {
        return effective_rate;
    }

    public void setEffective_rate(String effective_rate) {
        this.effective_rate = effective_rate;
    }

    public String getInstallment_amount() {
        return installment_amount;
    }

    public void setInstallment_amount(String installment_amount) {
        this.installment_amount = installment_amount;
    }

    public String getTotal_ar() {
        return total_ar;
    }

    public void setTotal_ar(String total_ar) {
        this.total_ar = total_ar;
    }

    public String getSubsidy_amt() {
        return subsidy_amt;
    }

    public void setSubsidy_amt(String subsidy_amt) {
        this.subsidy_amt = subsidy_amt;
    }

    public String getProduct_offering_code() {
        return product_offering_code;
    }

    public void setProduct_offering_code(String product_offering_code) {
        this.product_offering_code = product_offering_code;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }

    public LifeInsurance getLife_insurance() {
        return life_insurance;
    }

    public void setLife_insurance(LifeInsurance life_insurance) {
        this.life_insurance = life_insurance;
    }

    public List<HashMap<String, String>> getOther_fee() {
        return other_fee;
    }

    public void setOther_fee(List<HashMap<String, String>> other_fee) {
        this.other_fee = other_fee;
    }

    public String getIs_vip() {
        return is_vip;
    }

    public void setIs_vip(String is_vip) {
        this.is_vip = is_vip;
    }

    public String getWop_code() {
        return wop_code;
    }

    public void setWop_code(String wop_code) {
        this.wop_code = wop_code;
    }

    public SpouseJob getSpouse_job() {
        return spouse_job;
    }

    public void setSpouse_job(SpouseJob spouse_job) {
        this.spouse_job = spouse_job;
    }

    public AssetOwner getAsset_owner() {
        return asset_owner;
    }

    public void setAsset_owner(AssetOwner asset_owner) {
        this.asset_owner = asset_owner;
    }

    public String getOffince() {
        return offince;
    }

    public void setOffince(String offince) {
        this.offince = offince;
    }

    public String getPackage_service_price() {
        return package_service_price;
    }

    public void setPackage_service_price(String package_service_price) {
        this.package_service_price = package_service_price;
    }
}
