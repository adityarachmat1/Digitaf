package com.drife.digitaf.GeneralUtility.Spinner;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.drife.digitaf.ORM.Database.MaritalStatus;
import com.drife.digitaf.ORM.Database.Religion;
import com.drife.digitaf.R;

import java.util.List;

public class SpinnerUtility {
    public static void setSpinnerItem(Context context, Spinner spinner, List<String> data){
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
                R.layout.item_spinner, data);
        dataAdapter.setDropDownViewResource(R.layout.item_spinner_popup);
        spinner.setAdapter(dataAdapter);
    }

    public static void setSpinnerItemDraft(Context context, Spinner spinner, List<String> data, String code){
        String compareValue = code;
        Log.d("code", compareValue);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
                R.layout.item_spinner, data);
        dataAdapter.setDropDownViewResource(R.layout.item_spinner_popup);
        spinner.setAdapter(dataAdapter);
        if (compareValue != null) {
            int spinnerPosition = dataAdapter.getPosition(compareValue);
            spinner.setSelection(spinnerPosition);
        }

    }

    public static void setSpinnerItem(Activity activity, Spinner spinner, List<String> data){
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(activity,
                R.layout.item_spinner, data);
        dataAdapter.setDropDownViewResource(R.layout.item_spinner_popup);
        spinner.setAdapter(dataAdapter);
    }

    public static void setSpinnerItemDraft(Activity activity, Spinner spinner, List<String> data, String name){
        String compareValue = name;
        Log.d("tenor", compareValue);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(activity,
                R.layout.item_spinner, data);
        dataAdapter.setDropDownViewResource(R.layout.item_spinner_popup);
        spinner.setAdapter(dataAdapter);
        if (compareValue != null) {
            int spinnerPosition = dataAdapter.getPosition(compareValue);
            spinner.setSelection(spinnerPosition);
        }
    }

    public static void setSpinnerItemReligion(Activity activity, Spinner spinner, List<Religion> data){
        ArrayAdapter<Religion> dataAdapter = new ArrayAdapter<Religion>(activity,
                R.layout.item_spinner, data);
        dataAdapter.setDropDownViewResource(R.layout.item_spinner_popup);
        spinner.setAdapter(dataAdapter);
    }

    public static void setSpinnerItemMaritalStatus(Activity activity, Spinner spinner, List<MaritalStatus> data){
        ArrayAdapter<MaritalStatus> dataAdapter = new ArrayAdapter<MaritalStatus>(activity,
                R.layout.item_spinner, data);
        dataAdapter.setDropDownViewResource(R.layout.item_spinner_popup);
        spinner.setAdapter(dataAdapter);
    }
}
