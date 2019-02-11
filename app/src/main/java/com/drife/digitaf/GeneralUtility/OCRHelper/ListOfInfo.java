package com.drife.digitaf.GeneralUtility.OCRHelper;

import android.util.Log;
import android.util.Patterns;

import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.google.firebase.ml.vision.text.FirebaseVisionText;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListOfInfo {
    public interface ScanKartuNamaListener {
        void onSuccessScan(HashMap<String, String> datas);
    }

    private static final String URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";
    private static final String SYMBOL_REGEX = "[\\[\\]^/,'*:!><~#$%+=?|\"\\\\()]+";
    private static final String ALPHABET_REGEX = "[a-zA-Z]";
    private static final String NUMERIC_REGEX = "[0-9]";

    private static String[] listCities = new String[] {"Aceh", "Bali", "Bangka", "Banten", "Bengkulu", "Gorontalo", "Jakarta", "Jambi", "Jawa",
            "Kalimantan", "Riau", "Lampung", "Maluku", "Nusa Tenggara", "Papua", "Sulawesi", "Sumatera", "Yogyakarta", "Jogjakarta",
            "Langsa", "Lhokseumawe", "Meulaboh", "Sabang", "Subulussalam", "Denpasar", "Pangkalpinang", "Cilegon", "Serang", "Tangerang",
            "Sungai Penuh", "Bandung", "Bekasi", "Bogor", "Cimahi", "Cirebon", "Depok", "Sukabumi", "Tasikmalaya", "Banjar", "Magelang",
            "Pekalongan", "Purwokerto", "Salatiga", "Semarang", "Surakarta", "Tegal", "Batu", "Blitar", "Kediri", "Madiun", "Malang", "Mojokerto"
            , "Pasuruan", "Probolinggo", "Surabaya", "Pontianak", "Singkawang", "Banjarbaru", "Banjarmasin", "Palangkaraya", "Balikpapan", "Bontang",
            "Samarinda", "Tarakan", "Batam", "Tanjungpinang", "Metro", "Ternate", "Tidore", "Ambon", "Tual", "Bima", "Mataram", "Kupang", "Sorong", "Jayapura", "Dumai", "Pekanbaru", "Makassar",
            "Palopo", "Parepare", "Palu", "Bau-Bau", "Kendari", "Bitung", "Kotamobagu", "Manado", "Tomohon", "Bukittinggi",
            "Pag", "Pagpanjang", "Pariaman", "Payakumbuh", "Sawahlunto", "Solok", "Lubuklinggau", "Pagaralam", "Palembang",
            "Prabumulih", "Binjai", "Medan", "Padang Sidempuan", "Pematangsiantar", "Sibolga","Tanjungbalai","Tebingtinggi",};

    private static String[] listCountries = new String[] {"Indonesia"};

    private static String[] listJobs = new String[] {"Able Seamen", "Account", "Actor", "Actuary", "Adjustment", "Auditor", "Clerk", "Admin", "Advert", "Aerospace",
            "Engineer", "Agricultural", "Crop", "Farm", "Manager", "Inspector", "Agricultural", "Product", "Grader", "Sorter", "Sales", "Representative",
            "Science Techni", "Air Crew", "Officer", "Aircraft", "Assembler", "Repair", "Body", "Recovery", "Specialist", "Operation", "Driver", "Pilot",
            "Alteration", "Tailor", "Trainer", "Care", "Worker", "Medical", "Doctor", "President", "Marketing", "Credit", "Mechanical", "Operator", "Designer",
            "Graphic", "Secretary", "Assistant", "Representative", "Network", "Developer", "Restauran", "Senior", "Relationship", "Law", "Notaris", "Advocate",
            "Legal", "Consultant", "Chief", "Ceo", "Chairman", "Programmer", "Recruiter", "Musician", "Business", "Process", "Analyst", "Web", "Creative", "Director"};

    private static String[] listCompanyInput = new String[] {"PT", "CV", "Tbk", "Co.", "Co", "Ltd.", "Ltd", "Inc.", "Inc", "Ptd"};

    private static String[] listAddressPrefix = new String[] {"Jl", "Jl.", "Jalan", "Jln", "Jln.", "JI.", "JI", "Ji.", "Ji", "J1", "J1."};
    private static String[] listAddressBuilding = new String[] {"Plaza", "Tower", "Menara", "Wisma", "Mall", "Building", "Residence", "Graha",
            "City", "Place", "Gedung", "Ruko", "Rukan", "Komp.", "Komplek", "Perum", "Perumahan", "Pasar", "Kawasan", "Square", "Park", "Taman", "Office", "Pusat",
            "Dinas", "Rumah", "Badan", "Center", "Kantor"};

    private static String[] countryCode = new String[]{"+93", "+355", "+213", "+1-684", "+376", "+244", "+1-264", "+672", "+1-268", "+54", "+374", "+297",
            "+61", "+43", "+994", "+1-242", "+973", "+880", "+1-246", "+375", "+32", "+501", "+229", "+1-441", "+975", "+591", "+387", "+267", "+55", "+673",
            "+359", "+226", "+257", "+855", "+237", "+1", "+238", "+1-345", "+236", "+235", "+56", "+86", "+53", "+61", "+57", "+269", "+243", "+242", "+682",
            "+506", "+225", "+385", "+53", "+357", "+420", "+45", "+253", "+1-767", "+1-809", "+1-829", "+670", "+593", "+20", "+503", "+240", "+291", "+372",
            "+251", "+500", "+298", "+679", "+358", "+33", "+594", "+689", "+241", "+220", "+995", "+49", "+233", "+350", "+30", "+299", "+1-473", "+590", "+1-671",
            "+502", "+224", "+245", "+592", "+509", "+504", "+852", "+36", "+354", "+91", "+62", "+98", "+964", "+353", "+972", "+39", "+1-876", "+81", "+962",
            "+7", "+254", "+686", "+850", "+82", "+965", "+996", "+856", "+371", "+961", "+266", "+231", "+218", "+423", "+370", "+352", "+853", "+389", "+261",
            "+265", "+60", "+960", "+223", "+356", "+692", "+596", "+222", "+230", "+269", "+52", "+691", "+373", "+377", "+976", "+1-664", "+212", "+258", "+95", "+264",
            "+674", "+977", "+31", "+599", "+687", "+64", "+505", "+227", "+234", "+683", "+672", "+1-670", "+47", "+968", "+92", "+680", "+970", "+507", "+675",
            "+595", "+51", "+63", "+48", "+351", "+1-787", "+1-939", "+974", "+262", "+40", "+7", "+250", "+290", "+1-869", "+1-758", "+508", "+1-784", "+685",
            "+378", "+239", "+966", "+221", "+248", "+232", "+65", "+421", "+386", "+677", "+252", "+27", "+34", "+94", "+249", "+597", "+268", "+46", "+41", "+963",
            "+886", "+992", "+255", "+66", "+690", "+676", "+1-868", "+216", "+90", "+993", "+1-649", "+688", "+256", "+380", "+971", "+44", "+1", "+598", "+998",
            "+678", "+418", "+58", "+84", "+1-284", "+1-340", "+681", "+967", "+260", "+263"};

    private static String[] listPrefixEmail = new String[]{"E", "e", "Email", "email", "EMAIL", "mail", "Mail", "MAIL"};

    private static String countryCodePhonePrefix = "62";

    public static void startScan(FirebaseVisionText firebaseVisionText, ScanKartuNamaListener scanKartuNamaListener) {
        HashMap<String,String> listData = new HashMap<>();

        if (firebaseVisionText.getText().length() > 0) {
            if (firebaseVisionText.getText().split("\n").length > 0) {
                String[] results = firebaseVisionText.getText().split("\n");

                String companyName = "";
                String address = "";
                String jobTitles = "";
                String email = "";
                String mobile = "";
                String phone = "";

                for (int i = 0;i < results.length;i++) {
                    String data = results[i];
                    String key = "others"+i;

                    if (ListOfInfo.matchesOfCompany(data)) {
                        companyName = data;
                    }

                    if (ListOfInfo.matchesOfAddressBuilding(data)) {
                        address = !address.equals("") ? address+"\n"+data : data;
                    }

                    if (ListOfInfo.matchesOfAddress(data)) {
                        address = !address.equals("") ? address+"\n"+data : data;
                        if (i+1 < results.length) {
                            if (ListOfInfo.matchesOfCities(results[i+1])) {
                                address = !address.equals("") ? address +"\n"+results[i+1] : results[i+1];
                                if (i+2 < results.length) {
                                    if (ListOfInfo.matchesOfCountries(results[i+2])) {
                                        address = !address.equals("") ? address +"\n"+results[i+2] : results[i+2];
                                    }
                                }
                            }
                        }
                    }

                    if (isMatchesMobile(data)) {
                        mobile = data;
                    } else if (data.contains("|") || data.contains("/") || data.contains("]")) {
                        String[] listMobile = new String[]{};
                        if (data.contains("|")) {
                            listMobile = data.split("\\|");
                        } else if (data.contains("/")) {
                            listMobile = data.split("/");
                        } else if (data.contains("]")) {
                            listMobile = data.split("]");
                        }

                        for (String strMobile: listMobile) {
                            String strClearMobile = strMobile.replaceAll(" ", "").replaceAll(ALPHABET_REGEX, "");
                            strClearMobile = strClearMobile.replaceAll("[\\+\\.]", "").replaceAll("(-)", "");

                            if (strClearMobile.replaceAll(NUMERIC_REGEX, "").isEmpty() && strClearMobile.length() >= 6) {
                                mobile = data;
                            }
                        }
                    }

                    if (isMatchesPhone(data)) {
                        phone = data;
                    } else if ((data.contains("|") || data.contains("/") || data.contains("]")) && mobile.equals("")) {
                        String[] listPhone = new String[]{};
                        if (data.contains("|")) {
                            listPhone = data.split("\\|");
                        } else if (data.contains("/")) {
                            listPhone = data.split("/");
                        } else if (data.contains("]")) {
                            listPhone = data.split("]");
                        }

                        for (String strPhone: listPhone) {
                            String strClearPhone = strPhone.replaceAll(" ", "").replaceAll(ALPHABET_REGEX, "");
                            strClearPhone = strClearPhone.replaceAll("[\\+\\.]", "").replaceAll("(-)", "");

                            if (strClearPhone.replaceAll(NUMERIC_REGEX, "").isEmpty() && strClearPhone.length() >= 6) {
                                mobile = data;
                            }
                        }
                    }

                    if (data.contains("|") || data.contains("\\\\") || data.contains("]") || data.contains(" ")) {
                        String[] listEmail = data.replaceAll(SYMBOL_REGEX, " ").split(" ");
                        for (String mail : listEmail) {
                            if (ListOfInfo.isMatchesEmail(mail) && !ListOfInfo.isMatchesURL(mail)) {
                                email = clearEmailFormat(mail);
                            }
                        }
                    } else if (isMatchesEmail(data) && !isMatchesURL(data)) {
                        email = clearEmailFormat(data);
                    }

                    if (ListOfInfo.matchesOfJobTitles(data)) {
                        jobTitles = data;
                    }

//                    listData.put(key, data);
                }

                address = !mobile.isEmpty() ? address+"\n"+mobile : address;
                address = !phone.isEmpty() ? address+"\n"+phone : address;

                listData.put("alamat", address);
                listData.put("perusahaan", companyName);
                listData.put("jabatan", jobTitles);
                listData.put("email", email);

                scanKartuNamaListener.onSuccessScan(listData);
            }
        }
    }

    public static boolean matchesOfCities(String data) {
        for (String city : listCities) {
            if (data.toLowerCase().contains(city.toLowerCase())) {
                return true;
            }
        }

        return false;
    }

    public static boolean matchesOfCountries(String data) {
        for (String country : listCountries) {
            if (data.toLowerCase().contains(country.toLowerCase())) {
                return true;
            }
        }

        return false;
    }

    public static boolean matchesOfJobTitles(String data) {
        for (String jobTitle : listJobs) {
            if (data.toLowerCase().contains(jobTitle.toLowerCase())) {
                return true;
            }
        }

        return false;
    }

    public static boolean matchesOfCompany(String data) {
        for (String company : listCompanyInput) {
            if (data.toLowerCase().contains(company.toLowerCase()) && !isMatchesURL(data) && (!isMatchesEmail(data) && !data.contains("@"))) {
                return true;
            }
        }

        return false;
    }

    public static boolean matchesOfAddress(String data) {
        for (String addressPrefix : listAddressPrefix) {
            if (data.toLowerCase().contains(addressPrefix.toLowerCase())) {
                return true;
            }
        }

        return false;
    }

    public static boolean matchesOfAddressBuilding(String data) {
        for (String addressBuilding : listAddressBuilding) {
            if (data.toLowerCase().contains(addressBuilding.toLowerCase()) && !data.toLowerCase().contains("Officer".toLowerCase())) {
                return true;
            }
        }

        return false;
    }

    public static boolean isMatchesMobile(String data) {
        String mobile = data.replaceAll("[-\\[\\]^/,'*:.!><~@#$%+=?|\"\\\\()]+", "").replaceAll(" ", "");
        mobile = mobile.startsWith(countryCodePhonePrefix) ? mobile.replaceAll(countryCodePhonePrefix, "0") : mobile;
        mobile = mobile.contains("(0)") ? mobile.replaceAll("(0)", "") : mobile;

        if (mobile.indexOf("8") == 1) {
            return true;
        }

        return false;
    }

    public static boolean isMatchesPhone(String data) {
        String mobile = data.replaceAll("[-\\[\\]^/,'*:.!><~@#$%+=?|\"\\\\()]+", "").replaceAll(" ", "");
        mobile = mobile.startsWith(countryCodePhonePrefix) ? mobile.replaceAll(countryCodePhonePrefix, "0") : mobile;
        mobile = mobile.contains("(0)") ? mobile.replaceAll("(0)", "") : mobile;

        if (mobile.indexOf("2") == 1) {
            return true;
        }

        return false;
    }

    public static boolean isMatchesURL(String url) {
        Pattern p = Pattern.compile(URL_REGEX);
        Matcher m = p.matcher(url.replaceAll(" ", ""));

        return m.find();
    }

    public static boolean isMatchesEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email.replaceAll(" ", "").replaceAll(SYMBOL_REGEX, "")).matches();
    }

    public static String clearEmailFormat(String email) {
        String newEmail = email;

        if (email.contains(" ") && email.toLowerCase().startsWith("e")) {
            newEmail = email.split(" ").length > 0 ? email.split(" ")[1] : email;
        }

        Log.d("Email" , "Email "+newEmail);

        return newEmail;
    }

    public static String clearMobilePhoneFormat(String mobile) {
        String newMobile = mobile;

        if (mobile.contains(" ") && (mobile.toLowerCase().startsWith("m") || mobile.toLowerCase().startsWith("h"))) {
            newMobile = mobile.split(" ").length > 0 ? mobile.substring((mobile.split(" ")[0]).length()) : mobile;
        }

        return newMobile;
    }

    public static String clearPhoneFormat(String phone) {
        String newPhone = phone;

        if (phone.contains(" ") && phone.toLowerCase().startsWith("t")) {
            newPhone = phone.split(" ").length > 0 ? phone.substring((phone.split(" ")[0]).length()) : phone;
        }

        return newPhone;
    }

    public static String replaceMobile(String data) {
        return data.replaceAll("mobile", "").replaceAll("Mobile", "").replaceAll("MOBILE", "").replaceAll("m", "").replaceAll("M", "")
                .replaceAll("HP", "").replaceAll("hp", "").replaceAll("Hp", "");
    }
}
