package com.drife.digitaf.retrofitmodule;

public class ConnectionUrl {
    //public static String BASE_URL = "http://nikisaetravel.com/digitaf/api/v1/";
    /*prod*/
//    public static String BASE_URL = "http://103.18.133.218/digitaf/api/v1/";
    /*dev*/
    public static String BASE_URL = "http://103.18.133.228/digitaf/api/v1/";
    //public static String BASE_URL = "https://digitaf-qa.taf.co.id/digitaf/api/v1/";

    public static String AUTH = BASE_URL+"authenticate";
    public static String RESEND_OTP = BASE_URL+"authenticate/otp/resend";
    public static String REGISTER = BASE_URL+"register";
    public static String DEALER_LIST = BASE_URL+"dealers";
    public static String VALIDATE_OTP = BASE_URL+"authenticate/otp";
    public static String AUTH_FINGERPRINT = BASE_URL+"authenticate/fingerprint";
    public static String REFRESH_TOKEN = BASE_URL+"authenticate/refresh_token";
    public static String CAR_MODEL = BASE_URL+"car/models";
    public static String TENOR = BASE_URL+"insurance/tenors";
    public static String DEPRESIASI = BASE_URL+"insurance/depresiasi";
    public static String PACKAGE_RULE = BASE_URL+"package/rules?rate_area_id=";
    public static String INQUIRY = BASE_URL+"submissions?status=inquiry";
    public static String LIST_NOTIFICATION = BASE_URL+"notification/list";
    public static String UPDATE_PHONE_NUMBER = BASE_URL+"submission/update_phone_no";
    public static String SUBMISSONS = BASE_URL+"submissions";
    public static String LUPA_PASSWORD = BASE_URL+"forgot_password";
    public static String GANTI_PASSWORD = BASE_URL+"change_password";
    public static String GANTI_FOTOPROFIL = BASE_URL+"user/profile/update";
    public static String SUBMIT = BASE_URL+"submission/create";
    public static String WAY_OF_PAYMENT = BASE_URL+"master/way_of_payments";
    public static String UPLOAD = BASE_URL+"submission/upload";
    public static String DRAFT = BASE_URL+"submissions?status=draft";
    public static String NEEDACTION = BASE_URL+"submissions?status=inquiry&promise_date=1";
    public static String UPDATE_DOCUMENT = BASE_URL+"submission/upload_alt";
    public static String PACKAGE = BASE_URL+"packages";
    public static String Home_summary = BASE_URL+"submission/summary";
    public static String RELIGIONS = BASE_URL+"master/religions";
    public static String MARITALS = BASE_URL+"master/marital_status";
    public static String OCR_REQUEST = "http://178.128.55.215:8080/v2/ktp_npwp";
    public static String REPORT_SALES_BYPACKAGE = BASE_URL+"tafqlue/GetReportSalesByPackage";
    public static String INCENTIVE_LETTER = BASE_URL+"tafqlue/GetAllIncentiveLetter";
    public static String SEND_EMAIL = BASE_URL+"submission/simulasi_pdf";
    public static String CONFIG = BASE_URL+"app/config?platform=android";
}
