package com.drife.digitaf.GeneralUtility.TextUtility;

import android.widget.EditText;

import com.drife.digitaf.ORM.Database.CarModel;
import com.drife.digitaf.ORM.Database.WayOfPayment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtility {
    public static String randomString(int len){
        String characters = "abcdefghijklmnopqrtuvwxyz1234567890";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(len);

        for (int i = 0; i < len; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }

    public static boolean isEmailFormat(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static String changeDateFormat(String format, String newFormat, String stringDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        SimpleDateFormat newDateFormat = new SimpleDateFormat(newFormat);

        try {
            Date date = dateFormat.parse(stringDate);
            return newDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String checkPassword(String password, EditText editText, String title) {
        String msg = "";

        boolean hasUppercase = !password.equals(password.toLowerCase());
        boolean hasLowercase = !password.equals(password.toUpperCase());
        boolean isAtLeast8   = password.length() >= 8;
        boolean hasSpecial   = !password.matches("[A-Za-z0-9]*");
        boolean isNotEmpty   = !password.equals("");

        if (!isNotEmpty) {
            msg = title + " tidak boleh kosong.";
        } else if (!isAtLeast8) {
            msg = "Panjang "+ title +" kurang dari 8 karakter.";
        } else if (!hasLowercase) {
            msg = title + " harus mengandung 1 huruf kecil.";
        } else if (!hasUppercase) {
            msg = title + " harus mengandung 1 huruf besar.";
        } else if (!hasSpecial) {
            msg = title + " harus mengandung kombinasi huruf, angka & special karakter.";
        } else {
            msg = "";
        }

        return msg;
    }

    public static void sortCar(List<CarModel> carModelList) {
        Collections.sort(carModelList, new Comparator<CarModel>()
        {
            @Override
            public int compare(CarModel text1, CarModel text2)
            {
                return text1.getCarName().compareToIgnoreCase(text2.getCarName());
            }
        });
    }

    public static void sortWOP(List<WayOfPayment> wopList) {
        if(wopList != null){
            Collections.sort(wopList, new Comparator<WayOfPayment>()
            {
                @Override
                public int compare(WayOfPayment text1, WayOfPayment text2)
                {
                    return text1.getName().compareToIgnoreCase(text2.getName());
                }
            });
        }
    }
}
