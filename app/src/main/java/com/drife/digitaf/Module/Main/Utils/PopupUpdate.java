package com.drife.digitaf.Module.Main.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.drife.digitaf.Module.InputKredit.UploadDocument.Model.UploadDocumentItem;
import com.drife.digitaf.R;

import java.io.File;
import java.util.HashMap;

public class PopupUpdate {
    private static String TAG = PopupUpdate.class.getSimpleName();

    public interface SaveDataListener {
        void isSaved(boolean isSaved, HashMap<String, String> datas);
        void onTakePicture(String imagePath, UploadDocumentItem.DocumentType documentType, HashMap<String,String> datas);
    }

    static Button btnUpdate;
    static TextView txtInfo;

    public static PopupWindow showForceUpdate(final Activity activity, final View view, String info, Boolean isForce) {
        final PopupWindow popupWindow;

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout inflatedView = (RelativeLayout) inflater.inflate(R.layout.z_popup_forceupdate, null, false);

        final String appPackageName = activity.getApplication().getPackageName();

        btnUpdate = inflatedView.findViewById(R.id.btnUpdate);
        txtInfo = inflatedView.findViewById(R.id.txtInfo);

        Display display = activity.getWindowManager().getDefaultDisplay();
        final Point point = new Point();
        display.getSize(point);

        popupWindow = new PopupWindow(inflatedView, point.x, point.y, true);

        txtInfo.setText(info);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+appPackageName)));
                }

                popupWindow.dismiss();
                activity.finish();
            }
        });

        popupWindow.showAtLocation(view, Gravity.CENTER, point.x, point.y);
        return popupWindow;
    }
}
