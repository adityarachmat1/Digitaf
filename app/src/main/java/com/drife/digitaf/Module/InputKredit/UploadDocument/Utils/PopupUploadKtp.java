package com.drife.digitaf.Module.InputKredit.UploadDocument.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.provider.MediaStore;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.calendardatepicker.MonthAdapter;
import com.drife.digitaf.GeneralUtility.CameraUtility.CameraUtility;
import com.drife.digitaf.GeneralUtility.DialogUtility.DialogUtility;
import com.drife.digitaf.GeneralUtility.ImageUtility.ImageUtility;
import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.GeneralUtility.Spinner.SpinnerUtility;
import com.drife.digitaf.GeneralUtility.TextUtility.TextUtility;
import com.drife.digitaf.KalkulatorKredit.SelectedData;
import com.drife.digitaf.Module.Camera.CameraActivity;
import com.drife.digitaf.Module.InputKredit.UploadDocument.Model.UploadDocumentItem;
import com.drife.digitaf.Module.InputKredit.UploadDocument_v2.Activity.UploadDocumentActivity;
import com.drife.digitaf.ORM.Controller.MaritalStatusController;
import com.drife.digitaf.ORM.Controller.ReligionController;
import com.drife.digitaf.ORM.Database.MaritalStatus;
import com.drife.digitaf.ORM.Database.Religion;
import com.drife.digitaf.R;
import com.drife.digitaf.Service.OCR.OCRCallback;
import com.drife.digitaf.Service.OCR.OCRPresenter;
import com.drife.digitaf.UIModel.OCRData;
import com.drife.digitaf.retrofitmodule.Model.OCRResult;
import com.nekoloop.base64image.Base64Image;
import com.nekoloop.base64image.RequestEncode;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;

import retrofit2.Call;

public class PopupUploadKtp {
    private static String TAG = PopupUploadKtp.class.getSimpleName();

    public interface SaveDataListener {
        void isSaved(boolean isSaved, HashMap<String, String> datas);
        void onTakePicture(String imagePath, UploadDocumentItem.DocumentType documentType, HashMap<String, String> datas);
    }

    static ImageView imgClose;
    static Button btnSaveUpdate;
    static ImageView ivPicture;
    static ImageView imgInputUpload;
    static ImageView imgBackground;
    static Spinner spinStatusPerkawinan;
    static EditText edNik;
    static EditText edNama;
    static EditText edTempatLahir;
    static EditText edTanggaLahir;
    static Spinner spinJenisKelamin;
    static EditText edAlamat;
    static EditText edRT;
    static EditText edKelurahan;
    static EditText edKecamatan;
    static Spinner spinAgama;
    static EditText edPekerjaan;
    static EditText edKewarganegaraan;
    static EditText edBerlakuHingga;
    static EditText edRW;
    static EditText edKota;
    static EditText edZipcode;
    static TextView tvBirthDate;
    static TextView txtBerlaku;
    static CheckBox cbSeumurHidup;
    static RelativeLayout lyDeleteImage;
    static ScrollView scrollView;
    static CardView cardView;

    static String imgPath = "";
    static HashMap<String,String> datas = new HashMap<>();
    //static HashMap<String,String> dataKTP = new HashMap<>();
    private static List<Religion> religions = new ArrayList<>();
    private static List<Religion> maritalStatus = new ArrayList<>();

    public static Call call;
    public static Timer timer = new Timer();

    public static PopupWindow inputKTP(final Activity activity, View view, final PopupUploadKtp.SaveDataListener saveDataListener, final String imagePath,
                                       final String customerId, final String fileName, final List<OCRData> data, final HashMap<String,String> dataKTP,
                                       OCRResult ocrResult) {
        LogUtility.logging(TAG,LogUtility.infoLog,"inputKTP",JSONProcessor.toJSON(ocrResult));
        final PopupWindow popupWindow;
        final HashMap<String, String> datas = new HashMap<>();

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final LinearLayout inflatedView = (LinearLayout) inflater.inflate(R.layout.z_popup_input, null, false);

        imgClose = inflatedView.findViewById(R.id.imgClose);
        btnSaveUpdate = inflatedView.findViewById(R.id.btnSaveUpdate);
        ivPicture = inflatedView.findViewById(R.id.ivPicture);
        imgInputUpload = inflatedView.findViewById(R.id.imgInputUpload);
        imgBackground = inflatedView.findViewById(R.id.imgBackgroud);
        spinStatusPerkawinan = inflatedView.findViewById(R.id.spinStatusPerkawinan);
        edNik = inflatedView.findViewById(R.id.edNik);
        edNama = inflatedView.findViewById(R.id.edNama);
        edTempatLahir = inflatedView.findViewById(R.id.edTempatLahir);
        edTanggaLahir = inflatedView.findViewById(R.id.edTanggaLahir);
        spinJenisKelamin = inflatedView.findViewById(R.id.spinJenisKelamin);
        edAlamat = inflatedView.findViewById(R.id.edAlamat);
        edRT = inflatedView.findViewById(R.id.edRT);
        edKelurahan = inflatedView.findViewById(R.id.edKelurahan);
        edKecamatan = inflatedView.findViewById(R.id.edKecamatan);
        spinAgama = inflatedView.findViewById(R.id.spinAgama);
        edPekerjaan = inflatedView.findViewById(R.id.edPekerjaan);
        edKewarganegaraan = inflatedView.findViewById(R.id.edKewarganegaraan);
        edBerlakuHingga = inflatedView.findViewById(R.id.edBerlakuHingga);
        edRW = inflatedView.findViewById(R.id.edRW);
        edKota = inflatedView.findViewById(R.id.edKota);
        edZipcode = inflatedView.findViewById(R.id.edZipCode);
        tvBirthDate = inflatedView.findViewById(R.id.txtBirthDate);
        txtBerlaku = inflatedView.findViewById(R.id.txtBerlaku);
        cbSeumurHidup = inflatedView.findViewById(R.id.cbSeumurHidup);
        lyDeleteImage = inflatedView.findViewById(R.id.lyDeleteImage);
        scrollView = inflatedView.findViewById(R.id.scrollView);
        cardView = inflatedView.findViewById(R.id.cardView);

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        popupWindow = new PopupWindow(inflatedView, point.x, point.y, true);

        try {
            /*setSpinnerJenisKelamin*/
            List<String> jenisKelamin = new ArrayList<>();
            jenisKelamin.add("- Select -");
            jenisKelamin.add("LAKI - LAKI");
            jenisKelamin.add("PEREMPUAN");
            SpinnerUtility.setSpinnerItem(activity,spinJenisKelamin,jenisKelamin);

            setSpinnerData(activity, spinAgama, 1);
            setSpinnerData(activity, spinStatusPerkawinan, 2);
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"inputKTP","setSpinner",e.getMessage());
        }

        /*set value*/
        try {
            if(dataKTP != null){
                edNik.setText(dataKTP.get("nik"));
                edNama.setText(dataKTP.get("nama"));
                edTempatLahir.setText(dataKTP.get("tempatLahir"));

                edAlamat.setText(dataKTP.get("alamat"));
                edRT.setText(dataKTP.get("rt"));
                edRW.setText(dataKTP.get("rw"));
                edKelurahan.setText(dataKTP.get("kelurahan"));
                edKecamatan.setText(dataKTP.get("kecamatan"));
                edKota.setText(dataKTP.get("kota"));
                edZipcode.setText(dataKTP.get("zipCode"));
                edPekerjaan.setText(dataKTP.get("pekerjaan"));
                edKewarganegaraan.setText(dataKTP.get("kewarganegaraan"));

                /*set tanggal lahir*/
                String birthDate = dataKTP.get("tanggalLahir");
                if(birthDate != null){
                    String[] brthdate = birthDate.split(" ");
                    if(brthdate != null){
                        String[] birth = brthdate[0].split("-");
                        tvBirthDate.setText(birth[2]+"/"+birth[1]+"/"+birth[0]);
                    }
                }

                /*set jenis kelamin*/
                //Log.d("singo", "jenis kelamin : "+dataKTP.get("jenisKelamin"));
                if(dataKTP.get("jenisKelamin").toUpperCase().contains("LAKI")){
                    spinJenisKelamin.setSelection(1);
                }else if (dataKTP.get("jenisKelamin").toUpperCase().contains("PEREMPUAN")) {
                    spinJenisKelamin.setSelection(2);
                } else {
                    spinJenisKelamin.setSelection(0);
                }

                /*agama*/
                String agama = dataKTP.get("agama").toUpperCase();
                setSelectionReligion(agama, spinAgama);

                /*status perkawinan*/
                String statusPerkawinan = dataKTP.get("statusPerkawinan");

                setSelectionMaritalStatus(statusPerkawinan, spinStatusPerkawinan);

                /*pekerjaan*/
                edPekerjaan.setText(dataKTP.get("pekerjaan"));

                /*berlaku hingga*/
                String berlaku = TextUtility.changeDateFormat("yyyy-MM-dd HH:mm:ss.SSS", "dd/MM/yyyy", dataKTP.get("berlakuHingga"));

                Log.d(TAG, "Berlaku "+berlaku);

                if(berlaku != null && !berlaku.equals("null") && !berlaku.equals("")){
                    if(berlaku.equals("31/12/2099")){
                        cbSeumurHidup.setChecked(true);
                        txtBerlaku.setText("31/12/2099");
                    }else{
                        cbSeumurHidup.setChecked(false);
                        txtBerlaku.setText(berlaku);
//                        String[] berlakuHingga = berlaku.split(" ");
//                        if(berlakuHingga != null){
//                            String[] brlku = berlakuHingga[0].split("-");
//                            txtBerlaku.setText(brlku[2]+"/"+brlku[1]+"/"+brlku[0]);
//                        }
                    }
                }
            }else{
                //selectAgama("sdfsdf");
            }
        }catch (Exception e){

        }

        if(ocrResult != null){
            edNik.setText(ocrResult.getNIK());
            edNama.setText(ocrResult.getNama());
            String ttl = ocrResult.getTempatTanggalLahir();
            if(ttl != null){
                String[] arrTtl = ttl.split(",");
                if(arrTtl.length > 1){
                    edTempatLahir.setText(arrTtl[0]);
                    if(arrTtl.length > 1){
                        try {
                            tvBirthDate.setText(TextUtility.changeDateFormat("dd-MM-yyyy", "dd/MM/yyyy", (arrTtl[1]).replace(" ", "")));
                        }catch (Exception e){

                        }
                    }
//                    if(arrTtl.length>1){
//                        String[] birth = arrTtl[1].split("-");
//                        try{
//                            if(birth.length == 3){
//                                tvBirthDate.setText(arrTtl[0]+"/"+arrTtl[1]+"/"+arrTtl[2]);
//                            }
//                        }catch (Exception e){
//
//                        }
//                    }
                }
            }
            
            edAlamat.setText(ocrResult.getAlamat());
            String rtRw = ocrResult.getRtRw();
            if(rtRw != null){
                String[] rtrw = rtRw.split("/");
                if(rtrw != null){
                    edRT.setText(rtrw[0]);
                    if(rtrw.length>1){
                        edRW.setText(rtrw[1]);
                    }
                }
            }
            edKelurahan.setText(ocrResult.getKelDesa());
            edKecamatan.setText(ocrResult.getKecamatan());
            edKewarganegaraan.setText(ocrResult.getKewarganegaraan());

            String agama = ocrResult.getAgama().toUpperCase();
            setSelectionReligion(agama, spinAgama);

            String jenKelamin = ocrResult.getJenisKelamin();
            if (jenKelamin.contains("LAKI")) {
                spinJenisKelamin.setSelection(1);
            } else if (jenKelamin.contains("PEREMPUAN")) {
                spinJenisKelamin.setSelection(2);
            } else {
                spinJenisKelamin.setSelection(0);
            }

            edPekerjaan.setText(ocrResult.getPekerjaan());
            String statusKawin = ocrResult.getStatusPerkawinan();
            if (statusKawin.contains("BELUM")) {
                setSelectionMaritalStatus("SIN", spinStatusPerkawinan);
            } else if (!statusKawin.contains("BELUM") && statusKawin.contains("KAWIN")) {
                setSelectionMaritalStatus("MAR", spinStatusPerkawinan);
            } else if (statusKawin.contains("CERAI")) {
                setSelectionMaritalStatus("DIV", spinStatusPerkawinan);
            }

            if (ocrResult.getBerlakuHingga().equalsIgnoreCase("SEUMUR HIDUP")) {
                cbSeumurHidup.setChecked(true);
                txtBerlaku.setText("31/12/2099");
                txtBerlaku.setClickable(false);
            } else {
                cbSeumurHidup.setChecked(false);
                txtBerlaku.setText(TextUtility.changeDateFormat("dd-MM-yyyy", "dd/MM/yyyy", ocrResult.getBerlakuHingga()));
            }
        }

        if(!imagePath.equals("")){
            Bitmap bitmap = ImageUtility.loadBitmapFromPath(imagePath);
            imgInputUpload.setImageBitmap(bitmap);
            ivPicture.setVisibility(View.GONE);
            imgBackground.setVisibility(View.GONE);
            lyDeleteImage.setVisibility(View.VISIBLE);
            imgPath = imagePath;

            if (dataKTP.get("is_camera") != null) {
                if (dataKTP.get("is_camera").equalsIgnoreCase("1")) {
                    /*final ProgressDialog progressDialog = new ProgressDialog(activity);
                    progressDialog.setMessage("Processing OCR...");
                    progressDialog.setCancelable(true);
                    progressDialog.show();*/
                    //Log.d("singo", "is cameraaaaaa");

                    final AlertDialog alertDialog;
                    final AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                            .setMessage("Processing ocr")
                            .setMessage("Please wait...")
                            .setNegativeButton("CANCEL PROCESS", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    if(timer != null){
                                        timer.cancel();
                                    }

                                    if(call != null){
                                        call.cancel();
                                    }
                                }
                            });
                    alertDialog = builder.show();

                    Base64Image.with(activity)
                            .encode(bitmap)
                            .into(new RequestEncode.Encode() {
                                @Override
                                public void onSuccess(String s) {
                                    OCRPresenter.requestOCR(activity, s, new OCRCallback() {
                                        @Override
                                        public void onSuccessOCR(OCRResult result) {
                                            //progressDialog.dismiss();
                                            alertDialog.dismiss();
                                            edNik.setText(result.getNIK());
                                            edNama.setText(result.getNama());
                                            String ttl = result.getTempatTanggalLahir();
                                            if (ttl != null) {
                                                String[] arrTtl = ttl.split(",");
                                                if (arrTtl.length > 1) {
                                                    edTempatLahir.setText(arrTtl[0]);
                                                    if(arrTtl.length>1){
                                                        try {
                                                            tvBirthDate.setText(TextUtility.changeDateFormat("dd-MM-yyyy", "dd/MM/yyyy", (arrTtl[1]).replace(" ", "")));
                                                        }catch (Exception e){

                                                        }
                                                    }
                                                }
                                            }

                                            edAlamat.setText(result.getAlamat());
                                            String rtRw = result.getRtRw();
                                            if (rtRw != null) {
                                                String[] rtrw = rtRw.split("/");
                                                if (rtrw != null) {
                                                    edRT.setText(rtrw[0]);
                                                    if (rtrw.length > 1) {
                                                        edRW.setText(rtrw[1]);
                                                    }
                                                }
                                            }
                                            edKelurahan.setText(result.getKelDesa());
                                            edKecamatan.setText(result.getKecamatan());
                                            edKewarganegaraan.setText(result.getKewarganegaraan());
                                            txtBerlaku.setText(result.getBerlakuHingga());

                                            String agama = result.getAgama();
                                            if(agama != null && !agama.equals("")){
                                                agama = agama.toUpperCase();
                                            }
//                                            selectAgama(agama);
                                            setSelectionReligion(agama, spinAgama);

                                            String jenKelamin = result.getJenisKelamin();
                                            if (jenKelamin.contains("LAKI")) {
                                                spinJenisKelamin.setSelection(1);
                                            } else if (jenKelamin.contains("PEREMPUAN")) {
                                                spinJenisKelamin.setSelection(2);
                                            } else {
                                                spinJenisKelamin.setSelection(0);
                                            }

                                            edPekerjaan.setText(result.getPekerjaan());
                                            String statusKawin = result.getStatusPerkawinan();
                                            if (statusKawin.contains("BELUM")) {
                                                setSelectionMaritalStatus("SIN", spinStatusPerkawinan);
                                            } else if (!statusKawin.contains("BELUM") && statusKawin.contains("KAWIN")) {
                                                setSelectionMaritalStatus("MAR", spinStatusPerkawinan);
                                            } else if (statusKawin.contains("CERAI")) {
                                                setSelectionMaritalStatus("DIV", spinStatusPerkawinan);
                                            }

                                            if (result.getBerlakuHingga().equalsIgnoreCase("SEUMUR HIDUP")) {
                                                cbSeumurHidup.setChecked(true);
                                                txtBerlaku.setText("31/12/2099");
                                                txtBerlaku.setClickable(false);
                                            } else {
                                                cbSeumurHidup.setChecked(false);
                                                txtBerlaku.setText(TextUtility.changeDateFormat("dd-MM-yyyy", "dd/MM/yyyy", result.getBerlakuHingga()));
                                            }

                                            timer.cancel();
                                        }

                                        @Override
                                        public void onFailedOCR(String message) {
                                            //progressDialog.dismiss();
                                            alertDialog.dismiss();
                                            timer.cancel();
                                        }
                                    });

                                    try {
                                        timer.schedule(
                                                new java.util.TimerTask() {
                                                    @Override
                                                    public void run() {
                                                        // your code here
                                                        activity.runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                if(call != null){
                                                                    call.cancel();
                                                                }
                                                                //progressDialog.dismiss();
                                                                alertDialog.dismiss();
                                                                //Toast.makeText(CameraActivity.this, "OCR Processing Failed. Please check your connection", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                    }
                                                },
                                                30000
                                        );
                                    }catch (Exception e){
                                        LogUtility.logging(TAG,LogUtility.warningLog,"timer.schedule",e.getMessage());
                                    }
                                }

                                @Override
                                public void onFailure() {
                                    //progressDialog.dismiss();
                                    alertDialog.dismiss();
                                }
                            });
                }
            }
        }

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgPath = "";
                saveDataListener.isSaved(false, dataKTP);
                popupWindow.dismiss();
            }
        });

        btnSaveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validasi(activity)){
                    HashMap<String,String> dataKTP = getData();
                    dataKTP.put("is_saved", "1");
                    dataKTP.put("is_camera", "0");
                    popupWindow.dismiss();
                    saveDataListener.isSaved(true, dataKTP);
                }
            }
        });

        ivPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,String> dataKTP = getData();
//                popupWindow.dismiss();

                Intent intent = new Intent(activity, CameraActivity.class);
                intent.putExtra("customerId",customerId);
                intent.putExtra("fileName",fileName);
                intent.putExtra("datas",datas);
                intent.putExtra("dataKTP",dataKTP);
//                activity.startActivityForResult(intent, CameraUtility.REQUEST_IMAGE_CAPTURE_1);
                saveDataListener.onTakePicture(imgPath, UploadDocumentItem.DocumentType.KTP, dataKTP);

                openCameraOrGallery(activity, popupWindow, intent);
            }
        });

        imgInputUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,String> dataKTP = getData();
//                popupWindow.dismiss();

                Intent intent = new Intent(activity, CameraActivity.class);
                intent.putExtra("customerId",customerId);
                intent.putExtra("fileName",fileName);
                intent.putExtra("datas",datas);
                intent.putExtra("dataKTP",dataKTP);
//                activity.startActivityForResult(intent,CameraUtility.REQUEST_IMAGE_CAPTURE_1);
                saveDataListener.onTakePicture(imgPath, UploadDocumentItem.DocumentType.KTP, dataKTP);

                openCameraOrGallery(activity, popupWindow, intent);
            }
        });

        tvBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((UploadDocumentActivity)activity).showDatePicker();
                Calendar calendar = Calendar.getInstance();
                MonthAdapter.CalendarDay currDate = new MonthAdapter.CalendarDay();
                currDate.setDay(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)-1);
                MonthAdapter.CalendarDay currDate1 = new MonthAdapter.CalendarDay();
                currDate1.setDay(calendar.get(Calendar.YEAR)-65,calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                            @Override
                            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                                int month = monthOfYear+1;
                                String date = dayOfMonth+"/"+month+"/"+year;
                                tvBirthDate.setText(date);

                                String dateToSave = year+"-"+month+"-"+dayOfMonth+" 00:00:00.000";
                                datas.put("tanggalLahir",dateToSave);
                                //dataKTP.put("tanggalLahir",date);
                            }
                        })
                        .setFirstDayOfWeek(Calendar.SUNDAY)
                        .setPreselectedDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)-1)
                        .setDateRange(currDate1, currDate)
                        .setDoneText("Yes")
                        .setCancelText("No");
                cdp.show(((UploadDocumentActivity) activity).getSupportFragmentManager(),"Birth Date");
            }
        });

        cbSeumurHidup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    txtBerlaku.setText("31/12/2099");
                    txtBerlaku.setClickable(false);
                    datas.put("berlakuHingga","31/12/2099");
                } else {
                    txtBerlaku.setText("");
                    txtBerlaku.setClickable(true);
                }
            }
        });

        txtBerlaku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((UploadDocumentActivity)activity).showDatePicker();
                Calendar calendar = Calendar.getInstance();
                MonthAdapter.CalendarDay currDate = new MonthAdapter.CalendarDay();
                currDate.setDay(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                            @Override
                            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                                int month = monthOfYear+1;
                                String date = dayOfMonth+"/"+month+"/"+year;
                                txtBerlaku.setText(date);

                                String dateToSave = year+"-"+month+"-"+dayOfMonth+" 00:00:00.000";
                                datas.put("berlakuHingga",dateToSave);
                                //dataKTP.put("berlakuHingga",date);
                            }
                        })
                        .setFirstDayOfWeek(Calendar.SUNDAY)
                        .setPreselectedDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                        .setDateRange(currDate, null)
                        .setDoneText("Yes")
                        .setCancelText("No");
                cdp.show(((UploadDocumentActivity) activity).getSupportFragmentManager(),"Birth Date");
            }
        });

        lyDeleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lyDeleteImage.setVisibility(View.GONE);
                ivPicture.setVisibility(View.VISIBLE);
                imgBackground.setVisibility(View.VISIBLE);
                imgInputUpload.setImageBitmap(null);
                imgPath = "";
                saveDataListener.isSaved(false, dataKTP);
            }
        });

        DialogUtility.relayoutPopupWindow(activity, cardView);

        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.showAtLocation(view, Gravity.CENTER, point.x, point.y);
        return popupWindow;
    }

    private static HashMap<String,String> getData(){
        HashMap<String,String> dataKTP = new HashMap<>();
        dataKTP.put("nik",edNik.getText().toString());
        dataKTP.put("nama", edNama.getText().toString());
        dataKTP.put("tempatLahir",edTempatLahir.getText().toString());
        dataKTP.put("jenisKelamin", spinJenisKelamin.getSelectedItem().toString());
        dataKTP.put("alamat", edAlamat.getText().toString());
        dataKTP.put("rt",edRT.getText().toString());
        dataKTP.put("rw",edRW.getText().toString());
        dataKTP.put("kelurahan",edKelurahan.getText().toString());
        dataKTP.put("kecamatan",edKecamatan.getText().toString());
        if(spinAgama.getSelectedItem() != null){
            dataKTP.put("agama", (spinAgama.getSelectedItemPosition() != 0) ? ((Religion)spinAgama.getSelectedItem()).getCode() : "");
        }
        dataKTP.put("statusPerkawinan", (spinStatusPerkawinan.getSelectedItemPosition() != 0) ? ((MaritalStatus)spinStatusPerkawinan.getSelectedItem()).getCode() : "");
        dataKTP.put("pekerjaan",edPekerjaan.getText().toString());
        dataKTP.put("kota",edKota.getText().toString());
        dataKTP.put("zipCode", edZipcode.getText().toString());

        String tglLahir = tvBirthDate.getText().toString();
        if(tglLahir != null && !tglLahir.equals("null") && !tglLahir.equals("")){
            String[] tgl = tglLahir.split("/");
            if(tgl.length>1){
                String tglToSave = tgl[2]+"-"+tgl[1]+"-"+tgl[0]+" "+"00:00:00.000";
                dataKTP.put("tanggalLahir",tglToSave);
            }
        }

        String berlakuHingga = txtBerlaku.getText().toString();
        if(berlakuHingga != null && !berlakuHingga.equals("null") && !berlakuHingga.equals("")){
            String[] berlaku = berlakuHingga.split("/");
            if(berlaku.length>1){
                String tglToSave = berlaku[2]+"-"+berlaku[1]+"-"+berlaku[0]+" "+"00:00:00.000";
                dataKTP.put("berlakuHingga",tglToSave);
            }
        }

//        if (spinStatusPerkawinan.getSelectedItemPosition()!=0){
//            if(spinStatusPerkawinan.getSelectedItemPosition()==1){
//                SelectedData.IsMarried = false;
//            }else if(spinStatusPerkawinan.getSelectedItemPosition()==2){
//                SelectedData.IsMarried = true;
//            }
//        }

        if (spinStatusPerkawinan.getSelectedItemPosition() != 0) {
            if (((MaritalStatus)spinStatusPerkawinan.getSelectedItem()).getCode().equalsIgnoreCase("MAR")) {
                SelectedData.IsMarried = true;
            } else {
                SelectedData.IsMarried = false;
            }
        }

        return dataKTP;
    }

    private static boolean validasi(Activity activity){
        boolean stat = true;

        if(!imgPath.equals("")){
            if (edNik.getText().toString().equals("")){
                stat = false;
                Toast.makeText(activity,"NIK tidak boleh kosong",Toast.LENGTH_SHORT).show();
            }else if (edNama.getText().toString().equals("")){
                stat = false;
                Toast.makeText(activity,"Nama tidak boleh kosong",Toast.LENGTH_SHORT).show();
            }else if (edTempatLahir.getText().toString().equals("")){
                stat = false;
                Toast.makeText(activity,"Tempat lahir tidak boleh kosong",Toast.LENGTH_SHORT).show();
            }else if(tvBirthDate.getText().toString().equals("")){
                stat = false;
                Toast.makeText(activity,"Tanggal lahir tidak boleh kosong",Toast.LENGTH_SHORT).show();
            }else if (spinJenisKelamin.getSelectedItemPosition()==0){
                stat = false;
                Toast.makeText(activity,"Jenis kelamin tidak boleh kosong",Toast.LENGTH_SHORT).show();
            }else if (edAlamat.getText().toString().equals("")){
                stat = false;
                Toast.makeText(activity,"Alamat tidak boleh kosong",Toast.LENGTH_SHORT).show();
            }else if (edRT.getText().toString().equals("")){
                stat = false;
                Toast.makeText(activity,"RT tidak boleh kosong",Toast.LENGTH_SHORT).show();
            }else if (edRW.getText().toString().equals("")){
                stat = false;
                Toast.makeText(activity,"RW tidak boleh kosong",Toast.LENGTH_SHORT).show();
            }else if (edKelurahan.getText().toString().equals("")){
                stat = false;
                Toast.makeText(activity,"Kelurahan tidak boleh kosong",Toast.LENGTH_SHORT).show();
            }else if (edKecamatan.getText().toString().equals("")){
                stat = false;
                Toast.makeText(activity,"Kecamatan tidak boleh kosong",Toast.LENGTH_SHORT).show();
            }else if (spinAgama.getSelectedItemPosition()==0){
                stat = false;
                Toast.makeText(activity,"Agama tidak boleh kosong",Toast.LENGTH_SHORT).show();
            }else if (spinStatusPerkawinan.getSelectedItemPosition()==0){
                stat = false;
                Toast.makeText(activity,"Status perkawinan tidak boleh kosong",Toast.LENGTH_SHORT).show();
            }else if (edPekerjaan.getText().toString().equals("")){
                stat = false;
                Toast.makeText(activity,"Pekerjaan tidak boleh kosong",Toast.LENGTH_SHORT).show();
            }else if (edKota.getText().toString().equals("")){
                stat = false;
                Toast.makeText(activity,"Kota tidak boleh kosong",Toast.LENGTH_SHORT).show();
            }/*else if (edZipcode.getText().toString().equals("")){
                stat = false;
                Toast.makeText(activity,"Zipcode tidak boleh kosong",Toast.LENGTH_SHORT).show();
            }else if (edZipcode.getText().toString().length() != 5){
                stat = false;
                Toast.makeText(activity,"Zipcode harus 5 digit",Toast.LENGTH_SHORT).show();
            }*/else if(txtBerlaku.getText().toString().equals("")){
                stat = false;
                Toast.makeText(activity,"Berlaku hingga tidak boleh kosong",Toast.LENGTH_SHORT).show();
            }
        }else{
            stat = false;
            Toast.makeText(activity,"Foto KTP tidak boleh kosong",Toast.LENGTH_SHORT).show();
        }

        return stat;
    }

    private static void setSpinnerData(Activity activity, Spinner spinner, int mode) {
        if (mode == 1) {
            if(religions.size()>0){
                religions.clear();
            }

            religions = ReligionController.getAllReligion();
            if (religions != null) {
                List<Religion> religi = new ArrayList<>();
                religi.add(new Religion("- Select -"));
                religi.addAll(religions);
                SpinnerUtility.setSpinnerItemReligion(activity, spinner, religi);
            }
        } else if (mode == 2) {
            if (MaritalStatusController.getAllMaritalStatus() != null) {
                List<MaritalStatus> maritalStatuses = new ArrayList<>();
                maritalStatuses.add(new MaritalStatus("- Select -"));
                maritalStatuses.addAll(MaritalStatusController.getAllMaritalStatus());
                SpinnerUtility.setSpinnerItemMaritalStatus(activity,spinner,maritalStatuses);
            }
        }
    }

    private static void setSelectionMaritalStatus(String status, Spinner spinner) {
        List<MaritalStatus> maritalStatuses = new ArrayList<>();
        maritalStatuses.add(new MaritalStatus("- Select -"));
        maritalStatuses.addAll(MaritalStatusController.getAllMaritalStatus());

        for (int i = 0;i < maritalStatuses.size();i++) {
            if(status != null){
                if (status.equalsIgnoreCase(maritalStatuses.get(i).getCode())) {
                    spinner.setSelection(i);
                }
            }
        }
    }

    private static void setSelectionReligion(String status, Spinner spinner) {
        List<Religion> religions = new ArrayList<>();
        religions.add(new Religion("- Select -"));
        religions.addAll(ReligionController.getAllReligion());

        for (int i = 0;i < religions.size();i++) {
            if (i != 0) {
                String religi = religions.get(i).getCode();
                if(religi != null && !religi.equals("")){
                    religi = religi.toLowerCase();
                }
                /*if (status.toLowerCase().equalsIgnoreCase(religions.get(i).getCode().toLowerCase())) {
                    spinner.setSelection(i);
                }*/

                if(status != null && !status.equals("")){
                    status = status.toLowerCase();
                    if (status.equalsIgnoreCase(religi)) {
                        spinner.setSelection(i);
                    }
                }

                /*if (status.equalsIgnoreCase(religi)) {
                    spinner.setSelection(i);
                }*/
            }
        }
    }

    private static void selectAgama(String agama){
        Log.d(TAG, "Data "+agama+" "+religions.size());

        int agm = -1;
        for (int i=0; i<religions.size(); i++){
            String religi = religions.get(i).getName().toUpperCase();

            if(agama.equals(religi)){
                agm = i;
                //spinAgama.setSelection(i);
            }
        }

        if(agm>=0){
            spinAgama.setSelection(agm+1);
        }else{
            spinAgama.setSelection(religions.size());
        }
    }

    public static void openCameraOrGallery(final Activity activity, final PopupWindow popupWindow, final Intent intent) {
        android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(activity);
        alert.setTitle("Upload Gambar")
                .setItems(new String[]{"Camera", "Gallery (Maksimal File Upload 500Kb)"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        popupWindow.dismiss();

                        switch (i) {
                            case 0:
                                activity.startActivityForResult(intent,CameraUtility.REQUEST_IMAGE_CAPTURE_1);
                                break;
                            case 1:
                                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                activity.startActivityForResult(galleryIntent, CameraUtility.REQUEST_IMAGE_CAPTURE_1);
                                break;
                        }
                    }
                });
        alert.create().show();
    }
}
