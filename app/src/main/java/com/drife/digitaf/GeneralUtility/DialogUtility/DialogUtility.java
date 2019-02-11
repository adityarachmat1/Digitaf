package com.drife.digitaf.GeneralUtility.DialogUtility;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

public class DialogUtility {
    public interface AlertDialogCallback{
        void onPositive();
        void onNegative();
    }

    public static ProgressDialog createProgressDialog(Context context, String title, String describtion){
        Activity activity = new Activity();
        ProgressDialog barProgressDialog = new ProgressDialog(context);
        barProgressDialog.setTitle(title);
        barProgressDialog.setMessage(describtion);
        barProgressDialog.setProgressStyle(barProgressDialog.STYLE_SPINNER);
        barProgressDialog.setCancelable(false);
        return barProgressDialog;
    }

    public static AlertDialog.Builder createYesNoOptionAlertDialog(Context context, String title, String description, final AlertDialogCallback callback){
        return new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(description)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        callback.onPositive();
                    }})
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.onNegative();
                    }
                });
    }

    public static AlertDialog.Builder createSingleOptionAlertDialog(Context context, String title, String description, final AlertDialogCallback callback, String positiveButton){
        return new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(description)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        callback.onPositive();
                    }});
    }

    public static void relayoutPopupWindow(final Activity activity, final CardView cardView) {
        final View contentView = activity.findViewById(android.R.id.content);
        contentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int heightDiff = contentView.getRootView().getHeight() - contentView.getHeight();
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) cardView.getLayoutParams();

                if (heightDiff > 200) {
                    params.bottomMargin = (int) dpToPx(activity, 250);
                    cardView.setLayoutParams(params);
                    cardView.invalidate();
                    cardView.requestLayout();
                } else {
                    params.bottomMargin = (int) dpToPx(activity, 40);
                    cardView.setLayoutParams(params);
                    cardView.invalidate();
                    cardView.requestLayout();
                }
            }
        });
    }

    public static void relayoutPopupWindowDraft(final Activity activity, final CardView cardView) {
        final View contentView = activity.findViewById(android.R.id.content);
        contentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                contentView.getWindowVisibleDisplayFrame(r);
                int screenHeight = contentView.getRootView().getHeight();

                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) cardView.getLayoutParams();
                int keypadHeight = screenHeight - r.bottom;

                Log.d("keypadHeight", "keypadHeight = " + keypadHeight);

                if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                    // keyboard is opened
                    params.bottomMargin = (int) dpToPx(activity, 250);
                    cardView.setLayoutParams(params);
                    cardView.invalidate();
                    cardView.requestLayout();
                }
                else {
                    // keyboard is closed
                    params.bottomMargin = (int) dpToPx(activity, 40);
                    cardView.setLayoutParams(params);
                    cardView.invalidate();
                    cardView.requestLayout();
                }
            }
        });
    }

    public static float dpToPx(Context context, float valueInDp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }

    public static void showToolTip(Context context, String message) {
        android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(context)
                .setMessage(message)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alert.create().show();
    }
}
