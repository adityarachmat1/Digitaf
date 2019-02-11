package com.drife.digitaf.Module.Dialog.PopupDialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.text.Html;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.drife.digitaf.GeneralUtility.ImageUtility.ImageUtility;
import com.drife.digitaf.Module.Dialog.PopupDraft.PopupUtils;
import com.drife.digitaf.R;

public class PopupViewImage {
    public static void showPopupImage(Activity activity, View view, String path, String url) {
        final PopupWindow popupWindow;

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout inflatedView = (LinearLayout) inflater.inflate(R.layout.z_popup_view_image, null, false);

        LinearLayout lyClose = inflatedView.findViewById(R.id.lyClose);
        ImageView imgImage = inflatedView.findViewById(R.id.imgImage);

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        popupWindow = new PopupWindow(inflatedView, point.x, point.y, true);

        if(path != null){
            Bitmap bitmap = ImageUtility.loadBitmapFromPath(path);
            imgImage.setImageBitmap(bitmap);
        }

        lyClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

        popupWindow.showAtLocation(view, Gravity.CENTER, point.x, point.y);
    }
}
