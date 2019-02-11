package com.drife.digitaf.Module.Dialog.PopupDraft;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.drife.digitaf.GeneralUtility.DialogUtility.DialogUtility;
import com.drife.digitaf.Module.myapplication.childfragment.DraftFragment;
import com.drife.digitaf.R;
import com.drife.digitaf.retrofitmodule.SubmitParam.InquiryParam;
import com.drife.digitaf.retrofitmodule.SubmitParam.SubmitParameters;

/**
 * Created by ferdinandprasetyo on 10/3/18.
 */

public class PopupUtils {
    public interface SaveToDraftListener {
        void isSaved(boolean isSaved);
    }

    public interface SaveToDraftListener2 {
        void isSaved(boolean isSaved);
        void isExit(boolean isExit);
    }

    public static void simpanKeDraft(Activity activity, View view, final SaveToDraftListener saveToDraftListener) {
        final PopupWindow popupWindow;

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout inflatedView = (LinearLayout) inflater.inflate(R.layout.z_popup_simpankedraft, null, false);

        final CardView cardView = inflatedView.findViewById(R.id.cardViewDraft);
        TextView txtIsiNama = inflatedView.findViewById(R.id.txtIsiNama);
        ImageView imgClose = inflatedView.findViewById(R.id.imgClose);
        final EditText edNamaCustomer = inflatedView.findViewById(R.id.edNamaCustomer);
        Button btnSimpanKeDraft = inflatedView.findViewById(R.id.btnSimpanKeDraft);

//        txtIsiNama.setText(Html.fromHtml("Silahkan isi nama customer untuk menyimpan data penghitungan di <font color=\"#F23934\"><b>draft</b></font>."));

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        popupWindow = new PopupWindow(inflatedView, point.x, point.y, true);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();

            }
        });

        btnSimpanKeDraft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InquiryParam inquiryParam = SubmitParameters.inquiryParam;
                if (edNamaCustomer.getText().toString().equals("")){
                    edNamaCustomer.setError("Nama Customer tidak boleh kosong.");
                }else {
                    inquiryParam.setCustomer_name(edNamaCustomer.getText().toString());
                    popupWindow.dismiss();
                    saveToDraftListener.isSaved(true);
                }

            }
        });

        DialogUtility.relayoutPopupWindowDraft(activity, cardView);
        popupWindow.showAtLocation(view, Gravity.CENTER, point.x, point.y);
    }

    public static void simpanKeDraftSaved(Activity activity, View view, int type, final SaveToDraftListener saveToDraftListener) {
        final PopupWindow popupWindow;

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout inflatedView = (LinearLayout) inflater.inflate(R.layout.z_popup_simpankedraft_saved, null, false);

        TextView txtTitlePopUp = inflatedView.findViewById(R.id.txtTitlePopUp);
        TextView txtInfoPopup = inflatedView.findViewById(R.id.txtInfoPopup);
        ImageView imgIconPopup = inflatedView.findViewById(R.id.imgIconPopup);
        ImageView imgClose = inflatedView.findViewById(R.id.imgClose);
        LinearLayout btnLeft = inflatedView.findViewById(R.id.btnLeft);
        TextView txtLeft = inflatedView.findViewById(R.id.txtLeft);
        LinearLayout btnRight = inflatedView.findViewById(R.id.btnRight);
        ImageView imgRight = inflatedView.findViewById(R.id.imgRight);
        TextView txtRight = inflatedView.findViewById(R.id.txtRight);

        if (type == 1) {
            txtTitlePopUp.setText(activity.getResources().getString(R.string.title_popup_savetodraft_saved));
            txtInfoPopup.setText(activity.getResources().getString(R.string.text_perhitungan_savetodrft));
            txtLeft.setText(activity.getResources().getString(R.string.button_lihat_myapplication_savetodraft_saved));
            txtRight.setText(activity.getResources().getString(R.string.button_ok_savetodraft_saved));
        } else if (type == 2) {
            txtTitlePopUp.setText(activity.getResources().getString(R.string.title_popup_sendapplication));
            txtInfoPopup.setText(activity.getResources().getString(R.string.text_setelah_dikirim_sendapplication));
            txtLeft.setText(activity.getResources().getString(R.string.button_cekkembali_sendapplication));
            txtRight.setText(activity.getResources().getString(R.string.button_ya_sendapplication));
        } if (type == 3) {
            txtTitlePopUp.setText(activity.getResources().getString(R.string.title_popup_sentapplication));
            txtInfoPopup.setText(activity.getResources().getString(R.string.text_atas_nama_sentapplication));
            txtLeft.setText(activity.getResources().getString(R.string.button_home_sentapplication));
            txtRight.setText(activity.getResources().getString(R.string.button_lihatinquiry_sentapplication));
        } if (type == 4) {
            txtTitlePopUp.setText("EXIT");
            txtInfoPopup.setText("Simpan Ke Draft?");
            txtLeft.setText("Tidak");
            txtRight.setText("Simpan");
        }

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        popupWindow = new PopupWindow(inflatedView, point.x, point.y, true);

        imgClose.setVisibility(View.GONE);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

        btnLeft.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                saveToDraftListener.isSaved(false);
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                saveToDraftListener.isSaved(true);
            }
        });

        popupWindow.showAtLocation(view, Gravity.CENTER, point.x, point.y);
    }

    public static void simpanKeOutboxSaved(Activity activity, View view, int type, final SaveToDraftListener saveToDraftListener) {
        final PopupWindow popupWindow;

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout inflatedView = (LinearLayout) inflater.inflate(R.layout.z_popup_simpankedraft_saved, null, false);

        TextView txtTitlePopUp = inflatedView.findViewById(R.id.txtTitlePopUp);
        TextView txtInfoPopup = inflatedView.findViewById(R.id.txtInfoPopup);
        ImageView imgIconPopup = inflatedView.findViewById(R.id.imgIconPopup);
        ImageView imgClose = inflatedView.findViewById(R.id.imgClose);
        LinearLayout btnLeft = inflatedView.findViewById(R.id.btnLeft);
        TextView txtLeft = inflatedView.findViewById(R.id.txtLeft);
        LinearLayout btnRight = inflatedView.findViewById(R.id.btnRight);
        ImageView imgRight = inflatedView.findViewById(R.id.imgRight);
        TextView txtRight = inflatedView.findViewById(R.id.txtRight);

        if (type == 1) {
            txtTitlePopUp.setText(activity.getResources().getString(R.string.title_popup_savetodraft_saved));
            txtInfoPopup.setText(activity.getResources().getString(R.string.text_perhitungan_savetodrft));
            txtLeft.setText(activity.getResources().getString(R.string.button_lihat_myapplication_savetodraft_saved));
            txtRight.setText(activity.getResources().getString(R.string.button_ok_savetodraft_saved));
        } else if (type == 2) {
            txtTitlePopUp.setText(activity.getResources().getString(R.string.title_popup_sendapplication));
            txtInfoPopup.setText(activity.getResources().getString(R.string.text_setelah_dikirim_sendapplication));
            txtLeft.setText(activity.getResources().getString(R.string.button_cekkembali_sendapplication));
            txtRight.setText(activity.getResources().getString(R.string.button_ya_sendapplication));
        } if (type == 3) {
            txtTitlePopUp.setText(activity.getResources().getString(R.string.title_popup_sentapplication));
            txtInfoPopup.setText(activity.getResources().getString(R.string.text_atas_nama_sentapplication));
            txtLeft.setText(activity.getResources().getString(R.string.button_home_sentapplication));
            txtRight.setText(activity.getResources().getString(R.string.button_lihatinquiry_sentapplication));
        }

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

        btnLeft.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                saveToDraftListener.isSaved(true);
            }
        });

        popupWindow.showAtLocation(view, Gravity.CENTER, point.x, point.y);
    }

    public static void simpanKeDraftSavedExit(Activity activity, View view, int type, final SaveToDraftListener2 saveToDraftListener2) {
        final PopupWindow popupWindow;

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout inflatedView = (LinearLayout) inflater.inflate(R.layout.z_popup_simpankedraft_saved, null, false);

        TextView txtTitlePopUp = inflatedView.findViewById(R.id.txtTitlePopUp);
        TextView txtInfoPopup = inflatedView.findViewById(R.id.txtInfoPopup);
        ImageView imgIconPopup = inflatedView.findViewById(R.id.imgIconPopup);
        ImageView imgClose = inflatedView.findViewById(R.id.imgClose);
        LinearLayout btnLeft = inflatedView.findViewById(R.id.btnLeft);
        TextView txtLeft = inflatedView.findViewById(R.id.txtLeft);
        LinearLayout btnRight = inflatedView.findViewById(R.id.btnRight);
        ImageView imgRight = inflatedView.findViewById(R.id.imgRight);
        TextView txtRight = inflatedView.findViewById(R.id.txtRight);

        if (type == 1) {
            txtTitlePopUp.setText(activity.getResources().getString(R.string.title_popup_savetodraft_saved));
            txtInfoPopup.setText(activity.getResources().getString(R.string.text_perhitungan_savetodrft));
            txtLeft.setText(activity.getResources().getString(R.string.button_lihat_myapplication_savetodraft_saved));
            txtRight.setText(activity.getResources().getString(R.string.button_ok_savetodraft_saved));
        } else if (type == 2) {
            txtTitlePopUp.setText(activity.getResources().getString(R.string.title_popup_sendapplication));
            txtInfoPopup.setText(activity.getResources().getString(R.string.text_setelah_dikirim_sendapplication));
            txtLeft.setText(activity.getResources().getString(R.string.button_cekkembali_sendapplication));
            txtRight.setText(activity.getResources().getString(R.string.button_ya_sendapplication));
        } if (type == 3) {
            txtTitlePopUp.setText(activity.getResources().getString(R.string.title_popup_sentapplication));
            txtInfoPopup.setText(activity.getResources().getString(R.string.text_atas_nama_sentapplication));
            txtLeft.setText(activity.getResources().getString(R.string.button_home_sentapplication));
            txtRight.setText(activity.getResources().getString(R.string.button_lihatinquiry_sentapplication));
        } if (type == 4) {
            txtTitlePopUp.setText("EXIT");
            txtInfoPopup.setText("Simpan Ke Draft?");
            txtLeft.setText("Tidak");
            txtRight.setText("Simpan");
        }

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

        btnLeft.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                saveToDraftListener2.isExit(true);
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();

                saveToDraftListener2.isSaved(true);
            }
        });

        popupWindow.showAtLocation(view, Gravity.CENTER, point.x, point.y);
    }

    public static void backFromUploadDocument(Activity activity, View view, int type, final SaveToDraftListener2 saveToDraftListener2) {
        final PopupWindow popupWindow;

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout inflatedView = (LinearLayout) inflater.inflate(R.layout.z_popup_back, null, false);

        TextView txtTitlePopUp = inflatedView.findViewById(R.id.txtTitlePopUp);
        TextView txtInfoPopup = inflatedView.findViewById(R.id.txtInfoPopup);
        ImageView imgIconPopup = inflatedView.findViewById(R.id.imgIconPopup);
        ImageView imgClose = inflatedView.findViewById(R.id.imgClose);
        LinearLayout btnLeft = inflatedView.findViewById(R.id.btnLeft);
        TextView txtLeft = inflatedView.findViewById(R.id.txtLeft);
        LinearLayout btnRight = inflatedView.findViewById(R.id.btnRight);
        ImageView imgRight = inflatedView.findViewById(R.id.imgRight);
        TextView txtRight = inflatedView.findViewById(R.id.txtRight);

        txtTitlePopUp.setText("Kembali");
        txtInfoPopup.setText("Kembali ke halaman sebelumnya?");
        txtLeft.setText("Tidak");
        txtRight.setText("Ya");

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

        btnLeft.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                saveToDraftListener2.isExit(true);
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();

                saveToDraftListener2.isSaved(true);
            }
        });

        popupWindow.showAtLocation(view, Gravity.CENTER, point.x, point.y);
    }

    public static void backWithDraftOption(Activity activity, View view, int type, final SaveToDraftListener2 saveToDraftListener2) {
        final PopupWindow popupWindow;

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout inflatedView = (LinearLayout) inflater.inflate(R.layout.z_popup_back, null, false);

        TextView txtTitlePopUp = inflatedView.findViewById(R.id.txtTitlePopUp);
        TextView txtInfoPopup = inflatedView.findViewById(R.id.txtInfoPopup);
        ImageView imgIconPopup = inflatedView.findViewById(R.id.imgIconPopup);
        ImageView imgClose = inflatedView.findViewById(R.id.imgClose);
        LinearLayout btnLeft = inflatedView.findViewById(R.id.btnLeft);
        TextView txtLeft = inflatedView.findViewById(R.id.txtLeft);
        LinearLayout btnRight = inflatedView.findViewById(R.id.btnRight);
        ImageView imgRight = inflatedView.findViewById(R.id.imgRight);
        TextView txtRight = inflatedView.findViewById(R.id.txtRight);

        txtTitlePopUp.setText("Kembali ke halaman sebelumnya");
        txtInfoPopup.setText("Anda melakukan perubahan yang belum di simpan. Apakah anda akan menyimpan sebagai draft?");
        txtLeft.setText("HAPUS DATA");
        txtRight.setText("DRAFT");

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

        btnLeft.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                saveToDraftListener2.isExit(true);
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();

                saveToDraftListener2.isSaved(true);
            }
        });

        popupWindow.showAtLocation(view, Gravity.CENTER, point.x, point.y);
    }
}
