package com.drife.digitaf.retrofitmodule.Model;

import com.google.gson.annotations.SerializedName;

public class PackageRule {
    @SerializedName("id")
    private String id;
    @SerializedName("package_id")
    private String package_id;
    @SerializedName("car_category_id")
    private String car_category_id;
    @SerializedName("car_model_id")
    private String car_model_id;
    @SerializedName("rate_area_id")
    private String rate_area_id;
    @SerializedName("tenor_code")
    private String tenor_code;
    @SerializedName("min_dp")
    private String min_dp;
    @SerializedName("base_dp")
    private String base_dp;
    @SerializedName("max_dp")
    private String max_dp;
    @SerializedName("min_rate")
    private String min_rate;
    @SerializedName("base_rate")
    private String base_rate;
    @SerializedName("max_rate")
    private String max_rate;
    @SerializedName("admin_fee")
    private String admin_fee;
    @SerializedName("installment_type_id")
    private String installment_type_id;
    @SerializedName("provision")
    private String provision;
    @SerializedName("provision_min")
    private String provision_min;
    @SerializedName("provision_max")
    private String provision_max;
    @SerializedName("provision_base")
    private String provision_base;
    @SerializedName("tavp_coverage_id")
    private String tavp_coverage_id;
    @SerializedName("tavp_payment_id")
    private String tavp_payment_id;
    @SerializedName("pcl_type_id")
    private String pcl_type_id;
    @SerializedName("pcl_coverage_id")
    private String pcl_coverage_id;
    @SerializedName("created_by")
    private String created_by;
    @SerializedName("modified_by")
    private String modified_by;
    @SerializedName("is_active")
    private String is_active;
    @SerializedName("is_active_confins")
    private String is_active_confins;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("updated_at")
    private String updated_at;
    @SerializedName("deleted_at")
    private String deleted_at;
    @SerializedName("package")
    private Package aPackage;
    @SerializedName("car_category")
    private Category car_category;
    @SerializedName("car_model")
    private CarModel car_model;
    @SerializedName("rate_area")
    private RateArea rate_area;
    @SerializedName("installment_type")
    private InstallmentType installment_type;
    @SerializedName("tavp_coverage")
    private TAVPCoverage tavp_coverage;
    @SerializedName("tavp_payment")
    private TAVPPayment tavp_payment;
    @SerializedName("pcl_type")
    private PCLType pcl_type;
    @SerializedName("pcl_payment")
    private PCLPayment pcl_payment;
    @SerializedName("car_category_group")
    private CategoryGroup category_group;
    @SerializedName("provision_payment")
    private ProvisionPayment provision_payment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPackage_id() {
        return package_id;
    }

    public void setPackage_id(String package_id) {
        this.package_id = package_id;
    }

    public String getCar_category_id() {
        return car_category_id;
    }

    public void setCar_category_id(String car_category_id) {
        this.car_category_id = car_category_id;
    }

    public String getCar_model_id() {
        return car_model_id;
    }

    public void setCar_model_id(String car_model_id) {
        this.car_model_id = car_model_id;
    }

    public String getRate_area_id() {
        return rate_area_id;
    }

    public void setRate_area_id(String rate_area_id) {
        this.rate_area_id = rate_area_id;
    }

    public String getTenor_code() {
        return tenor_code;
    }

    public void setTenor_code(String tenor_code) {
        this.tenor_code = tenor_code;
    }

    public String getMin_dp() {
        return min_dp;
    }

    public void setMin_dp(String min_dp) {
        this.min_dp = min_dp;
    }

    public String getBase_dp() {
        return base_dp;
    }

    public void setBase_dp(String base_dp) {
        this.base_dp = base_dp;
    }

    public String getMax_dp() {
        return max_dp;
    }

    public void setMax_dp(String max_dp) {
        this.max_dp = max_dp;
    }

    public String getMin_rate() {
        return min_rate;
    }

    public void setMin_rate(String min_rate) {
        this.min_rate = min_rate;
    }

    public String getBase_rate() {
        return base_rate;
    }

    public void setBase_rate(String base_rate) {
        this.base_rate = base_rate;
    }

    public String getMax_rate() {
        return max_rate;
    }

    public void setMax_rate(String max_rate) {
        this.max_rate = max_rate;
    }

    public String getAdmin_fee() {
        return admin_fee;
    }

    public void setAdmin_fee(String admin_fee) {
        this.admin_fee = admin_fee;
    }

    public String getInstallment_type_id() {
        return installment_type_id;
    }

    public void setInstallment_type_id(String installment_type_id) {
        this.installment_type_id = installment_type_id;
    }

    public String getProvision() {
        return provision;
    }

    public void setProvision(String provision) {
        this.provision = provision;
    }

    public String getProvision_min() {
        return provision_min;
    }

    public void setProvision_min(String provision_min) {
        this.provision_min = provision_min;
    }

    public String getProvision_max() {
        return provision_max;
    }

    public void setProvision_max(String provision_max) {
        this.provision_max = provision_max;
    }

    public String getProvision_base() {
        return provision_base;
    }

    public void setProvision_base(String provision_base) {
        this.provision_base = provision_base;
    }

    public String getTavp_coverage_id() {
        return tavp_coverage_id;
    }

    public void setTavp_coverage_id(String tavp_coverage_id) {
        this.tavp_coverage_id = tavp_coverage_id;
    }

    public String getTavp_payment_id() {
        return tavp_payment_id;
    }

    public void setTavp_payment_id(String tavp_payment_id) {
        this.tavp_payment_id = tavp_payment_id;
    }

    public String getPcl_type_id() {
        return pcl_type_id;
    }

    public void setPcl_type_id(String pcl_type_id) {
        this.pcl_type_id = pcl_type_id;
    }

    public String getPcl_coverage_id() {
        return pcl_coverage_id;
    }

    public void setPcl_coverage_id(String pcl_coverage_id) {
        this.pcl_coverage_id = pcl_coverage_id;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getModified_by() {
        return modified_by;
    }

    public void setModified_by(String modified_by) {
        this.modified_by = modified_by;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getIs_active_confins() {
        return is_active_confins;
    }

    public void setIs_active_confins(String is_active_confins) {
        this.is_active_confins = is_active_confins;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public Package getaPackage() {
        return aPackage;
    }

    public void setaPackage(Package aPackage) {
        this.aPackage = aPackage;
    }

    public Category getCar_category() {
        return car_category;
    }

    public void setCar_category(Category car_category) {
        this.car_category = car_category;
    }

    public CarModel getCar_model() {
        return car_model;
    }

    public void setCar_model(CarModel car_model) {
        this.car_model = car_model;
    }

    public RateArea getRate_area() {
        return rate_area;
    }

    public void setRate_area(RateArea rate_area) {
        this.rate_area = rate_area;
    }

    public InstallmentType getInstallment_type() {
        return installment_type;
    }

    public void setInstallment_type(InstallmentType installment_type) {
        this.installment_type = installment_type;
    }

    public TAVPCoverage getTavp_coverage() {
        return tavp_coverage;
    }

    public void setTavp_coverage(TAVPCoverage tavp_coverage) {
        this.tavp_coverage = tavp_coverage;
    }

    public TAVPPayment getTavp_payment() {
        return tavp_payment;
    }

    public void setTavp_payment(TAVPPayment tavp_payment) {
        this.tavp_payment = tavp_payment;
    }

    public PCLType getPcl_type() {
        return pcl_type;
    }

    public void setPcl_type(PCLType pcl_type) {
        this.pcl_type = pcl_type;
    }

    public PCLPayment getPcl_payment() {
        return pcl_payment;
    }

    public void setPcl_payment(PCLPayment pcl_payment) {
        this.pcl_payment = pcl_payment;
    }

    public CategoryGroup getCategory_group() {
        return category_group;
    }

    public void setCategory_group(CategoryGroup category_group) {
        this.category_group = category_group;
    }

    public ProvisionPayment getProvision_payment() {
        return provision_payment;
    }

    public void setProvision_payment(ProvisionPayment provision_payment) {
        this.provision_payment = provision_payment;
    }
}
