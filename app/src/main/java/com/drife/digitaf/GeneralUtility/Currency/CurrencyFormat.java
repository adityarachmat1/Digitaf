package com.drife.digitaf.GeneralUtility.Currency;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyFormat {
    public static NumberFormat formatRupiah(){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah;
    }

    public static void changeFormat(TextWatcher context, Editable s, EditText editText) {
        editText.removeTextChangedListener(context);
        Log.d("singo", "changeFormat : "+s.toString());

        try {
            String originalString = s.toString();
            if(s.toString().equals("Rp")){
                editText.setText("");
            }else{
                Long longval;
                if (originalString.contains(",")) {
                    originalString = originalString.replaceAll(",", "");
                }

                if (originalString.contains("Rp")) {
                    originalString = originalString.replaceAll("Rp", "");
                }
                longval = Long.parseLong(originalString);

                DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                formatter.applyPattern("Rp#,###,###,###");
                String formattedString = formatter.format(longval);

                //setting text after format to EditText
                editText.setText(formattedString);
                editText.setSelection(editText.getText().length());
            }
        } catch (NumberFormatException nfe) {
            editText.setText("");
            nfe.printStackTrace();
        }

        editText.addTextChangedListener(context);
    }

    public static void changeFormat(TextWatcher context, String s, EditText editText) {
        editText.removeTextChangedListener(context);
        Log.d("singo", "changeFormat : "+s.toString());

        try {
            String originalString = s;
            if(s.toString().equals("Rp")){
                editText.setText("");
            }else{
                Long longval;
                if (originalString.contains(",")) {
                    originalString = originalString.replaceAll(",", "");
                }

                if (originalString.contains("Rp")) {
                    originalString = originalString.replaceAll("Rp", "");
                }
                longval = Long.parseLong(originalString);

                DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                formatter.applyPattern("Rp#,###,###,###");
                String formattedString = formatter.format(longval);

                //setting text after format to EditText
                editText.setText(formattedString);
                editText.setSelection(editText.getText().length());
            }
        } catch (NumberFormatException nfe) {
            editText.setText("");
            nfe.printStackTrace();
        }

        editText.addTextChangedListener(context);
    }

    public static String formatNumber(String text){
        return text.replaceAll("Rp", "").replaceAll(",", "");
    }

    public static void formatPercentage(Editable s) {
        if (s.length() > 0) {
            try {
                Double data = Double.parseDouble(s.toString());

                if(data > 100) {
                    s.replace(s.length()-1, s.length(), "");
                } else if (s.toString().startsWith("00")) {
                    s.replace(s.length()-1, s.length(), "");
                }
            }
            catch(NumberFormatException nfe){

            }
        }
    }
}
