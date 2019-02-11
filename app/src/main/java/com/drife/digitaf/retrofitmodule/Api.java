package com.drife.digitaf.retrofitmodule;

import com.drife.digitaf.Module.dashboard.fragment.model.ReportSalesByPackage;
import com.drife.digitaf.Module.home.model.Home_data;
import com.drife.digitaf.Module.inquiry.fragment.model.InquiryItem;
import com.drife.digitaf.Module.myapplication.model.DraftItem;
import com.drife.digitaf.Module.myapplication.model.NeedActionItem;
import com.drife.digitaf.Service.Notification.NotificationItem;
import com.drife.digitaf.retrofitmodule.Model.BaseObjectResponse;
import com.drife.digitaf.retrofitmodule.Model.CarModel;
import com.drife.digitaf.retrofitmodule.Model.Dealer;
import com.drife.digitaf.retrofitmodule.Model.DealerSimple;
import com.drife.digitaf.retrofitmodule.Model.Depresiasi;
import com.drife.digitaf.retrofitmodule.Model.Inquiry;
import com.drife.digitaf.retrofitmodule.Model.LoginResult;
import com.drife.digitaf.retrofitmodule.Model.MaritalStatus;
import com.drife.digitaf.retrofitmodule.Model.OCRData;
import com.drife.digitaf.retrofitmodule.Model.OCRResult;
import com.drife.digitaf.retrofitmodule.Model.PackageRule;
import com.drife.digitaf.retrofitmodule.Model.Religion;
import com.drife.digitaf.retrofitmodule.Model.Tenor;
import com.drife.digitaf.retrofitmodule.Model.User;
import com.drife.digitaf.retrofitmodule.Model.WayOfPayment;

import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface Api {

    @POST
    @FormUrlEncoded
    Call<BaseObjectResponse<LoginResult>> login(@Url String url,
                                                       @Field("fcm_reg_id") String fcm_reg_id,
                                                       @Field("email") String email,
                                                       @Field("password") String password);
    @POST
    Call<Object> loginFingerprint(@Url String url,
                                        @HeaderMap HashMap<String,String> headers);
    @POST
    Call<Object> refreshToken(@Url String url,
                              @HeaderMap HashMap<String,String> headers);
    @POST
    @FormUrlEncoded
    Call<BaseObjectResponse<User>> resendOTP(@Url String url,
                                             @Field("email") String email);

    @POST
    @FormUrlEncoded
    Call<Object> resendOTP_v2(@Url String url,
                                             @Field("email") String email);

    @POST
    @FormUrlEncoded
    Call<BaseObjectResponse<User>> validateOTP(@Url String url,
                                             @Field("otp_code") String otpCode);
    @POST
    @FormUrlEncoded
    Call<BaseObjectResponse<User>> register(@Url String url,
                                             @Field("email") String email,
                                            @Field("email_confirmation") String email_confirmation,
                                            @Field("password") String password,
                                            @Field("password_confirmation") String password_confirmation,
                                            @Field("ktp_no") String ktp_no,
                                            @Field("npk_no") String npk_no,
                                            @Field("fullname") String fullname,
                                            @Field("is_officer") String is_officer,
                                            @Field("dealer_id") String dealer_id);
    @GET
    Call<BaseObjectResponse<List<Dealer>>> getAllDealer(@Url String url);

    @GET
    Call<BaseObjectResponse<List<DealerSimple>>> getAllSimpleDealer(@Url String url);

    @GET
    Call<Object> getAllDealerString(@Url String url);

    @GET
    Call<BaseObjectResponse<List<CarModel>>> getAllCars(@Url String url,
                                                        @HeaderMap HashMap<String,String> headers);
    @GET
    Call<BaseObjectResponse<List<Tenor>>> getAllTenor(@Url String url,
                                                     @HeaderMap HashMap<String,String> headers);

    @GET
    Call<BaseObjectResponse<List<Depresiasi>>> getAllDepresiasi(@Url String url,
                                                           @HeaderMap HashMap<String,String> headers);

    @GET
    Call<BaseObjectResponse<List<PackageRule>>> getAllPackageRule(@Url String url,
                                                                 @HeaderMap HashMap<String,String> headers);

    @GET
    Call<BaseObjectResponse<List<WayOfPayment>>> getAllWOP(@Url String url,
                                                             @HeaderMap HashMap<String,String> headers);

    @POST
    @FormUrlEncoded
    Call<Object> submit(@Url String url,
                        @HeaderMap HashMap<String,String> headers,
                        @Field("params") String param,
                        @Field("status") String status,
                        @Field("image") String image,
                        @Field("form_id") String form_id,
                        @Field("pdf") String pdf);

    @POST
    @FormUrlEncoded
    Call<Object> submitDraft(@Url String url,
                        @HeaderMap HashMap<String,String> headers,
                        @Field("params") String param,
                        @Field("status") String status,
                        @Field("image") String image);

    @POST
    @FormUrlEncoded
    Call<Object> submitDraft(@Url String url,
                             @HeaderMap HashMap<String,String> headers,
                             @Field("params") String param,
                             @Field("status") String status,
                             @Field("image") String image,
                             @Field("dp_percentage") String dp_percentage,
                             @Field("is_simulasi_paket") String is_simulasi_paket,
                             @Field("is_simulasi_budget") String is_simulasi_budget,
                             @Field("is_non_paket") String is_non_paket,
                             @Field("payment_type_service_package") String payment_type_service_package,
                             @Field("coverage_type") String coverage_type);

    @POST
    @FormUrlEncoded
    Call<Object> submitDraft(@Url String url,
                             @HeaderMap HashMap<String,String> headers,
                             @Field("params") String param,
                             @Field("status") String status,
                             @Field("image") String image,
                             @Field("dp_percentage") String dp_percentage,
                             @Field("is_simulasi_paket") String is_simulasi_paket,
                             @Field("is_simulasi_budget") String is_simulasi_budget,
                             @Field("is_non_paket") String is_non_paket,
                             @Field("payment_type_service_package") String payment_type_service_package,
                             @Field("coverage_type") String coverage_type,
                             @Field("is_npwp") String is_npwp);

    /*@POST
    @FormUrlEncoded
    Call<Object> submit(@Url String url,
                        @HeaderMap HashMap<String,String> headers,
                        @Field("params") String param,
                        @Field("status") String status,
                        @Field("image") String image,
                        @Field("form_id") String form_id,
                        @Field("pdf") String pdf,
                        @Field("pokok_hutang") String pokok_hutang,
                        @Field("pv_premi") String pv_premi,
                        @Field("insurance_code") String insurance_code,
                        @Field("life_insurance_code") String life_insurance_code,
                        @Field("insurance_pay_type") String insurance_pay_type,
                        @Field("life_insurance_pay_type") String life_insurance_pay_type);*/

    @POST
    @FormUrlEncoded
    Call<Object> submit(@Url String url,
                        @HeaderMap HashMap<String,String> headers,
                        @Field("params") String param,
                        @Field("status") String status,
                        @Field("image") String image,
                        @Field("form_id") String form_id,
                        @Field("pdf") String pdf,
                        @Field("dp_percentage") String dp_percentage);

//    @POST
//    @FormUrlEncoded
//    Call<Object> draft(@Url String url,
//                        @HeaderMap HashMap<String,String> headers,
//                        @Field("params") String param,
//                        @Field("status") String status,
//                        @Field("form_id") String form_id);

    @POST
    @FormUrlEncoded
    Call<Object> submit(@Url String url,
                        @HeaderMap HashMap<String,String> headers,
                        @Field("params") String param,
                        @Field("status") String status,
                        @Field("image") String image,
                        @Field("form_id") String form_id,
                        @Field("pdf") String pdf,
                        @Field("dp_percentage") String dp_percentage,
                        @Field("is_corporate") String is_corporate,
                        @Field("is_simulasi_paket") String is_simulasi_paket,
                        @Field("is_simulasi_budget") String is_simulasi_budget,
                        @Field("is_non_paket") String is_non_paket,
                        @Field("is_npwp") String is_npwp,
                        @Field("payment_type_service_package") String payment_type_service_package,
                        @Field("coverage_type") String coverage_type);

    @POST
    @FormUrlEncoded
    Call<Object> draft(@Url String url,
                       @HeaderMap HashMap<String,String> headers,
                       @Field("params") String param,
                       @Field("status") String status,
                       @Field("form_id") String form_id,
                       @Field("pdf") String pdf,
                       @Field("dp_percentage") String dp_percentage,
                       @Field("is_corporate") String is_corporate,
                       @Field("is_simulasi_paket") String is_simulasi_paket,
                       @Field("is_simulasi_budget") String is_simulasi_budget,
                       @Field("is_non_paket") String is_non_paket,
                       @Field("is_npwp") String is_npwp,
                       @Field("payment_type_service_package") String payment_type_service_package,
                       @Field("coverage_type") String coverage_type);


    @POST
    @FormUrlEncoded
    Call<Object> upload(@Url String url,
                        @HeaderMap HashMap<String,String> headers,
                        @FieldMap HashMap<String,String> fields);

    @POST
    @FormUrlEncoded
    Call<Object> lupaPassword(@Url String url, @Field("email") String email);

    @POST
    @FormUrlEncoded
    Call<Object> change_number(@Url String url,@HeaderMap HashMap<String,String> headers, @Field("lead_id") String lead_id, @Field("phone_no") String phone_no);

    @GET
    Call<BaseObjectResponse<List<InquiryItem>>> getAllInquiry(@Url String url,
                                                                  @HeaderMap HashMap<String,String> headers);

    @GET
    Call<BaseObjectResponse<List<NotificationItem>>> getAllNotification(@Url String url,
                                                                   @HeaderMap HashMap<String,String> headers);

    @GET
    Call<BaseObjectResponse<List<DraftItem>>> getAllDraft(@Url String url,
                                                          @HeaderMap HashMap<String,String> headers);

    @GET
    Call<BaseObjectResponse<List<InquiryItem>>> getAllNeedAction(@Url String url,
                                                                    @HeaderMap HashMap<String,String> headers);

    @POST
    @FormUrlEncoded
    Call<Object> updateDocument(@Url String url,
                                @HeaderMap HashMap<String,String> headers,
                                @Field("form_id") String formId,
                                @Field("image") String image);

    @POST
    @FormUrlEncoded
    Call<Object> updateDocument(@Url String url,
                                @HeaderMap HashMap<String,String> headers,
                                @Field("form_id") String formId,
                                @Field("image") String image,
                                @Field("pdf") String pdf);

    @POST
    @FormUrlEncoded
    Call<Object> gantiPassword(@Url String url, @Field("old_password") String old_password,
                               @Field("new_password") String new_password,
                               @Field("new_password_confirmation") String new_password_confirmation,
                               @HeaderMap HashMap<String,String> headers);

    @GET
    Call<BaseObjectResponse<Home_data>> getHome_data(@Url String url,
                                                     @HeaderMap HashMap<String,String> headers);

    @POST
    @FormUrlEncoded
    Call<Object> gantiFotoProfil(@Url String url, @Field("image") String image,
                               @HeaderMap HashMap<String,String> headers);

    @GET
    Call<BaseObjectResponse<List<Religion>>> getAllReligion(@Url String url,
                                                       @HeaderMap HashMap<String,String> headers);

    @GET
    Call<BaseObjectResponse<List<MaritalStatus>>> getAllMarital(@Url String url,
                                                                 @HeaderMap HashMap<String,String> headers);
    @POST
    @Headers("Content-Type: application/json")
    Call<OCRData<OCRResult>> requestOCR(@Url String url, @Body HashMap<String,String> param);

    @GET
    Call<BaseObjectResponse<List<ReportSalesByPackage>>> getReportSalesByPackage(@Url String url,
                                                                                 @HeaderMap HashMap<String,String> headers);

    @GET
    Call<BaseObjectResponse> getAllIncentive(@Url String url,
                                                     @HeaderMap HashMap<String,String> headers);

    @POST
    @FormUrlEncoded
    Call<Object> sendEmail(@Url String url,
                        @HeaderMap HashMap<String,String> headers,
                        @Field("param") String param);

    @GET
    Call<BaseObjectResponse> getConfig(@Url String url,
                                             @HeaderMap HashMap<String,String> headers);
}
