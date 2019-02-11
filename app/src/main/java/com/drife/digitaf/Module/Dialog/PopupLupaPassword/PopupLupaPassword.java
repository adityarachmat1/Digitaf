package com.drife.digitaf.Module.Dialog.PopupLupaPassword;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.drife.digitaf.GeneralUtility.DialogUtility.DialogUtility;
import com.drife.digitaf.GeneralUtility.TextUtility.TextUtility;
import com.drife.digitaf.R;
import com.drife.digitaf.Service.Login.LoginPresenter;
import com.drife.digitaf.Service.Login.LupaPasswordCallback;

import java.util.HashMap;

public class PopupLupaPassword {

    public static PopupWindow showPopup(final Activity activity, View view, final LupaPasswordSavedListener callback) {
        final PopupWindow popupWindow;
        final HashMap<String, String> datas = new HashMap<>();

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout inflatedView = (LinearLayout) inflater.inflate(R.layout.popup_lupa_password, null, false);

        ImageView imgClose = inflatedView.findViewById(R.id.imgClose);
        final EditText edEmail = inflatedView.findViewById(R.id.edEmail);
        Button btnSend = inflatedView.findViewById(R.id.btnSend);
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        popupWindow = new PopupWindow(inflatedView, point.x, point.y, true);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edEmail.getText().toString();
                if(!email.equals("")){
                    if(TextUtility.isEmailFormat(email)){
                        final ProgressDialog progressDialog = DialogUtility.createProgressDialog(activity,"","Please wait...");
                        progressDialog.show();
                        LoginPresenter.lupaPassword(activity, email, new LupaPasswordCallback() {
                            @Override
                            public void onSuccessLupaPassword() {
                                progressDialog.dismiss();
                                popupWindow.dismiss();
                                callback.isSent();
                            }

                            @Override
                            public void onFailedLupaPassword() {
                                progressDialog.dismiss();
                                popupWindow.dismiss();
                                callback.isFailed();
                            }

                            @Override
                            public void onLoopEnd() {
                                progressDialog.dismiss();
                                popupWindow.dismiss();
                                callback.isFailed();
                            }
                        });
                    }else {
                        Toast.makeText(activity,"Email address is not valid",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(activity,"Email address can not be empty",Toast.LENGTH_SHORT).show();
                }
            }
        });

        popupWindow.showAtLocation(view, Gravity.CENTER, point.x, point.y);
        return popupWindow;
    }
}
