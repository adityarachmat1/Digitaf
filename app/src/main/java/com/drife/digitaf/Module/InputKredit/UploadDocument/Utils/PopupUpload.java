package com.drife.digitaf.Module.InputKredit.UploadDocument.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
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
import com.drife.digitaf.GeneralUtility.OCR.Utility.OCRUtility;
import com.drife.digitaf.GeneralUtility.Spinner.SpinnerUtility;
import com.drife.digitaf.GeneralUtility.TextUtility.TextUtility;
import com.drife.digitaf.KalkulatorKredit.SelectedData;
import com.drife.digitaf.Module.Camera.CameraActivity;
import com.drife.digitaf.Module.Camera.CameraCardNameActivity;
import com.drife.digitaf.Module.FileChooser.Activity.FileChooser;
import com.drife.digitaf.Module.InputKredit.UploadDocument.Model.UploadDocumentItem;
import com.drife.digitaf.Module.InputKredit.UploadDocument_v2.Activity.UploadDocumentActivity;
import com.drife.digitaf.Module.Profile.Activity.ProfileActivity;
import com.drife.digitaf.ORM.Controller.MaritalStatusController;
import com.drife.digitaf.ORM.Controller.ReligionController;
import com.drife.digitaf.ORM.Database.MaritalStatus;
import com.drife.digitaf.ORM.Database.Religion;
import com.drife.digitaf.R;
import com.drife.digitaf.Service.OCR.OCRCallback;
import com.drife.digitaf.Service.OCR.OCRPresenter;
import com.drife.digitaf.Service.Profile.UbahProfilePresenter;
import com.drife.digitaf.UIModel.ListOCRData;
import com.drife.digitaf.UIModel.OCRData;
import com.drife.digitaf.retrofitmodule.Model.OCRResult;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.nekoloop.base64image.Base64Image;
import com.nekoloop.base64image.RequestEncode;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;

import retrofit2.Call;

/**
 * Created by ferdinandprasetyo on 10/3/18.
 */

public class PopupUpload {
    private static String TAG = PopupUpload.class.getSimpleName();
    private static String imgPath = "";
    private static String pdfPath = "";
    private static int isUpdate = 0;
    public static int maxPromisedDate = 3;

    public static Call call;
    public static Timer timer = new Timer();

    public interface SaveDataListener {
        void isSaved(boolean isSaved, HashMap<String, String> datas);
        void onTakePicture(String imagePath, UploadDocumentItem.DocumentType documentType, HashMap<String, String> data);
    }

    private static void checkIsUpdate(Activity activity) {
        if (activity instanceof UploadDocumentActivity) {
            if (((UploadDocumentActivity)activity).isUpdate == 1) {
                isUpdate = 1;
            } else {
                isUpdate = 0;
            }
        }

        Log.d("singo", "IS UPDATE "+isUpdate);
    }

    /*popup dibuat satu2 agar fleksible dan mudah ketika kedepannya ada perubahan di salah satu popup*/

    public static PopupWindow inputKTP(final Activity activity, View view, final SaveDataListener saveDataListener, final String imagePath,
                                       final String customerId, final String fileName, final List<OCRData> data, final HashMap<String,String> dataKTP) {
        final PopupWindow popupWindow;
        final HashMap<String, String> datas = new HashMap<>();

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout inflatedView = (LinearLayout) inflater.inflate(R.layout.z_popup_input, null, false);

        ImageView imgClose = inflatedView.findViewById(R.id.imgClose);
        Button btnSaveUpdate = inflatedView.findViewById(R.id.btnSaveUpdate);
        final ImageView ivPicture = inflatedView.findViewById(R.id.ivPicture);
        final ImageView imgInputUpload = inflatedView.findViewById(R.id.imgInputUpload);
        final ImageView imgBackground = inflatedView.findViewById(R.id.imgBackgroud);
        final Spinner spinStatusPerkawinan = inflatedView.findViewById(R.id.spinStatusPerkawinan);
        final EditText edNik = inflatedView.findViewById(R.id.edNik);
        final EditText edNama = inflatedView.findViewById(R.id.edNama);
        final EditText edTempatLahir = inflatedView.findViewById(R.id.edTempatLahir);
        final EditText edTanggaLahir = inflatedView.findViewById(R.id.edTanggaLahir);
        final Spinner spinJenisKelamin = inflatedView.findViewById(R.id.spinJenisKelamin);
        final EditText edAlamat = inflatedView.findViewById(R.id.edAlamat);
        final EditText edRT = inflatedView.findViewById(R.id.edRT);
        final EditText edKelurahan = inflatedView.findViewById(R.id.edKelurahan);
        final EditText edKecamatan = inflatedView.findViewById(R.id.edKecamatan);
        final Spinner spinAgama = inflatedView.findViewById(R.id.spinAgama);
        final EditText edPekerjaan = inflatedView.findViewById(R.id.edPekerjaan);
        EditText edKewarganegaraan = inflatedView.findViewById(R.id.edKewarganegaraan);
        final EditText edBerlakuHingga = inflatedView.findViewById(R.id.edBerlakuHingga);
        final EditText edRW = inflatedView.findViewById(R.id.edRW);
        final EditText edKota = inflatedView.findViewById(R.id.edKota);
        final EditText edZipcode = inflatedView.findViewById(R.id.edZipCode);
        final TextView tvBirthDate = inflatedView.findViewById(R.id.txtBirthDate);
        final TextView txtBerlaku = inflatedView.findViewById(R.id.txtBerlaku);
        final CheckBox cbSeumurHidup = inflatedView.findViewById(R.id.cbSeumurHidup);
        final RelativeLayout lyDeleteImage = inflatedView.findViewById(R.id.lyDeleteImage);

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        popupWindow = new PopupWindow(inflatedView, point.x, point.y, true);

        try {
            setSpinnerJekel(activity, spinJenisKelamin);
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

                /*String birth = dataKTP.get("tanggalLahir");
                String[] parse = birth.split(" ");
                String[] dateparse = parse[0].split("-");
                tvBirthDate.setText(dateparse[2]+"/"+dateparse[1]+"/"+dateparse[0]);*/

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
                Log.d("singo", "jenis kelamin : "+dataKTP.get("jenisKelamin"));
                if(dataKTP.get("jenisKelamin").toUpperCase().contains("LAKI")){
                    spinJenisKelamin.setSelection(1);
                }else{
                    spinJenisKelamin.setSelection(2);
                }

                /*agama*/
                String agama = dataKTP.get("agama").toUpperCase();
//                if (agama.contains("ISLAM")){
//                    spinAgama.setSelection(1);
//                }else if(agama.contains("KRISTEN")){
//                    spinAgama.setSelection(2);
//                }else if(agama.contains("KATHOLIK")){
//                    spinAgama.setSelection(3);
//                }else if(agama.contains("HINDU")){
//                    spinAgama.setSelection(4);
//                }else if(agama.contains("BUDHA")){
//                    spinAgama.setSelection(5);
//                }else if(agama.contains("KONFUCHU")){
//                    spinAgama.setSelection(6);
//                }else{
//                    spinAgama.setSelection(0);
//                }

                setSelectionReligion(agama, spinAgama);

                /*status perkawinan*/
                String statusPerkawinan = dataKTP.get("statusPerkawinan");
//                if(statusPerkawinan.contains("KAWIN")){
//                    spinStatusPerkawinan.setSelection(2);
//                }else if(statusPerkawinan.contains("BELUM KAWIN")){
//                    spinStatusPerkawinan.setSelection(1);
//                }else{
//                    spinStatusPerkawinan.setSelection(0);
//                }
                setSelectionMaritalStatus(statusPerkawinan, spinStatusPerkawinan);

                /*pekerjaan*/
                edPekerjaan.setText(dataKTP.get("pekerjaan"));

                /*berlaku hingga*/
                String berlaku = dataKTP.get("berlakuHingga");
                Log.d("singo", "berlaku : "+dataKTP.get("berlaku"));
                if(berlaku != null && !berlaku.equals("null") && !berlaku.equals("")){
                    if(berlaku.equals("31/12/2099")){
                        cbSeumurHidup.setChecked(true);
                        txtBerlaku.setText("31/12/2099");
                    }else{
                        String[] berlakuHingga = berlaku.split(" ");
                        if(berlakuHingga != null){
                            String[] brlku = berlakuHingga[0].split("-");
                            txtBerlaku.setText(brlku[2]+"/"+brlku[1]+"/"+brlku[0]);
                        }
                    }
                }
            }
        }catch (Exception e){

        }

        if(data != null){
            LogUtility.logging(TAG,LogUtility.infoLog,"inputKTP","data", JSONProcessor.toJSON(data));
            for (int i=0; i<data.size(); i++){
                OCRData ocrData = data.get(i);
                List<HashMap<String,String>> listData = ocrData.getData();
                for (int j=0; j<listData.size(); j++){
                    HashMap<String,String> hashMap = listData.get(j);
                    String value = hashMap.get("value");
                    String type = hashMap.get("key");
                    if(type.equals("nik")){
                        edNik.setText(value);
                    }else if(type.equals("nama")){
                        edNama.setText(value);
                    }else if(type.equals("tempatlahir")){
                        edTempatLahir.setText(value);
                    }else if(type.equals("tanggallahir")){
                        edTanggaLahir.setText(value);
                    }else if(type.equals("alamat")){
                        edAlamat.setText(value);
                    }else if(type.equals("rtrw")){
                        edRT.setText(value);
                    }else if(type.equals("kelurahan")){
                        edKelurahan.setText(value);
                    }else if(type.equals("kecamatan")){
                        edKecamatan.setText("kecamatan");
                    }else if(type.equals("kewarganegaraan")){
                        edKewarganegaraan.setText(value);
                    }else if(type.equals("berlakuhingga")){
                        edBerlakuHingga.setText(value);
                    }else if(type.equals("agama")){
                        if (value.contains("ISLAM")){
                            spinAgama.setSelection(1);
                        }else if(value.contains("KRISTEN")){
                            spinAgama.setSelection(2);
                        }else if(value.contains("KATHOLIK")){
                            spinAgama.setSelection(3);
                        }else if(value.contains("HINDU")){
                            spinAgama.setSelection(4);
                        }else if(value.contains("BUDHA")){
                            spinAgama.setSelection(5);
                        }else if(value.contains("KONFUCHU")){
                            spinAgama.setSelection(6);
                        }else{
                            spinAgama.setSelection(0);
                        }
                    }else if(type.equals("jeniskelamin")){
                        if(value.contains("LAKI")){
                            spinJenisKelamin.setSelection(1);
                        }else if(value.contains("PEREMPUAN")){
                            spinJenisKelamin.setSelection(2);
                        }else{
                            spinJenisKelamin.setSelection(0);
                        }
                    }
                }
            }
        }

        if(!imagePath.equals("")){
            Bitmap bitmap = ImageUtility.loadBitmapFromPath(imagePath);
            imgInputUpload.setImageBitmap(bitmap);
            ivPicture.setVisibility(View.GONE);
            imgBackground.setVisibility(View.GONE);
            lyDeleteImage.setVisibility(View.VISIBLE);
        }

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

        btnSaveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean stat = true;
                if(!imagePath.equals("")){
                    if (edNik.getText().toString().equals("")){
                        stat = false;
                        //edNik.setError("Field can not be empty");
                    }else{
                        datas.put("nik",edNik.getText().toString());
                    }

                    if (edNama.getText().toString().equals("")){
                        stat = false;
                        //edNama.setError("Field can not be empty");
                    }else{
                        datas.put("nama", edNama.getText().toString());
                    }

                    if (edTempatLahir.getText().toString().equals("")){
                        stat = false;
                        //edTempatLahir.setError("Field can not be empty");
                    }else{
                        datas.put("tempatLahir",edTempatLahir.getText().toString());
                    }

                    if (spinJenisKelamin.getSelectedItemPosition()==0){
                        stat = false;
                        //Toast.makeText(activity,"Gender is not selected",Toast.LENGTH_SHORT).show();
                    }else{
                        datas.put("jenisKelamin", spinJenisKelamin.getSelectedItem().toString());
                    }

                    if (edAlamat.getText().toString().equals("")){
                        stat = false;
                        //edAlamat.setError("Field can not be empty");
                    }else{
                        datas.put("alamat", edAlamat.getText().toString());
                    }

                    if (edRT.getText().toString().equals("")){
                        stat = false;
                        //edRT.setError("Field can not be empty");
                    }else{
                        datas.put("rt",edRT.getText().toString());
                    }

                    if (edRW.getText().toString().equals("")){
                        stat = false;
                        //edRW.setError("Field can not be empty");
                    }else{
                        datas.put("rw",edRW.getText().toString());
                    }

                    if (edKelurahan.getText().toString().equals("")){
                        stat = false;
                        //edKelurahan.setError("Field can not be empty");
                    }else{
                        datas.put("kelurahan",edKelurahan.getText().toString());
                    }

                    if (edKecamatan.getText().toString().equals("")){
                        stat = false;
                        //edKecamatan.setError("Field can not be empty");
                    }else{
                        datas.put("kecamatan",edKecamatan.getText().toString());
                    }

                    if (spinAgama.getSelectedItemPosition()==0){
                        stat = false;
                        //Toast.makeText(activity,"Religion is not selected",Toast.LENGTH_SHORT).show();
                    }else{
                        datas.put("agama",spinAgama.getSelectedItem().toString());
                    }

                    if (spinStatusPerkawinan.getSelectedItemPosition()==0){
                        stat = false;
                        //Toast.makeText(activity,"MaritalStatus status is not selected",Toast.LENGTH_SHORT).show();
                    }else{
                        if(spinStatusPerkawinan.getSelectedItemPosition()==1){
                            SelectedData.IsMarried = false;
                        }else if(spinStatusPerkawinan.getSelectedItemPosition()==2){
                            SelectedData.IsMarried = true;
                        }
                        datas.put("statusPerkawinan", spinStatusPerkawinan.getSelectedItem().toString());
                    }

                    if (edPekerjaan.getText().toString().equals("")){
                        stat = false;
                        //edPekerjaan.setError("Field can not be empty");
                    }else{
                        datas.put("pekerjaan",edPekerjaan.getText().toString());
                    }

                    if (edKota.getText().toString().equals("")){
                        stat = false;
                        //edKota.setError("Field can not be empty");
                    }else{
                        datas.put("kota",edKota.getText().toString());
                    }

                    if (edZipcode.getText().toString().equals("")){
                        stat = false;
                        //edZipcode.setError("Field can not be empty");
                    }else{
                        datas.put("zipCode", edZipcode.getText().toString());
                    }

                    if(dataKTP.get("tanggalLahir")!= null && !dataKTP.get("tanggalLahir").equals("")){
                        datas.put("tanggalLahir",dataKTP.get("tanggalLahir"));
                    }

                    if(datas.get("berlakuHingga") == null && !datas.get("berlakuHingga").equals("")){
                        if(dataKTP.get("berlakuHingga")!= null && !dataKTP.get("berlakuHingga").equals("")){
                            datas.put("berlakuHingga",dataKTP.get("berlakuHingga"));
                        }
                    }

                    if(stat == true){
                        popupWindow.dismiss();
                        saveDataListener.isSaved(true, datas);
                    }else{
                        Toast.makeText(activity,"Data is not complete",Toast.LENGTH_SHORT).show();
                    }
                    //datas.put("berlakuHingga",edBerlakuHingga.getText().toString());
                }else{
                    Toast.makeText(activity,"Foto KTP tidak boleh kosong",Toast.LENGTH_SHORT).show();
                }

            }
        });

        ivPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                Intent intent = new Intent(activity, CameraActivity.class);
                intent.putExtra("customerId",customerId);
                intent.putExtra("fileName",fileName);
                activity.startActivityForResult(intent,CameraUtility.REQUEST_IMAGE_CAPTURE_1);

                openCameraOrGallery(activity, popupWindow, datas, customerId, fileName, UploadDocumentItem.DocumentType.KK, CameraUtility.REQUEST_IMAGE_CAPTURE_4, saveDataListener);
            }
        });

        imgInputUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                Intent intent = new Intent(activity, CameraActivity.class);
                intent.putExtra("customerId",customerId);
                intent.putExtra("fileName",fileName);
                activity.startActivityForResult(intent,CameraUtility.REQUEST_IMAGE_CAPTURE_1);
            }
        });

        tvBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((UploadDocumentActivity)activity).showDatePicker();
                Calendar calendar = Calendar.getInstance();
                MonthAdapter.CalendarDay currDate = new MonthAdapter.CalendarDay();
                currDate.setDay(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
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
                        .setPreselectedDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                        .setDateRange(null, currDate)
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
                } else {
                    //txtBerlaku.setText(dataKTP.get());
                    String berlaku = dataKTP.get("berlakuHingga");
                    if(berlaku != null){
                        String[] berlakuHingga = berlaku.split(" ");
                        if(berlakuHingga.length == 2){
                            if(berlakuHingga != null){
                                String[] brlku = berlakuHingga[0].split("-");
                                txtBerlaku.setText(brlku[2]+"/"+brlku[1]+"/"+brlku[0]);
                            }
                        }else{
                            txtBerlaku.setText(berlaku);
                        }
                    }
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
            }
        });

        popupWindow.showAtLocation(view, Gravity.CENTER, point.x, point.y);
        return popupWindow;
    }

    public static PopupWindow inputSPK(final Activity activity, View view, final SaveDataListener saveDataListener,
                                       final String imagePath, final String customerId, final String fileName, final HashMap<String,String> dataSPK) {
        final PopupWindow popupWindow;
        final HashMap<String, String> datas = new HashMap<>();

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout inflatedView = (LinearLayout) inflater.inflate(R.layout.z_popup_spk, null, false);

        ImageView imgClose = inflatedView.findViewById(R.id.imgClose);
        Button btnSaveUpdate = inflatedView.findViewById(R.id.btnSaveUpdate);
        final ImageView ivPicture = inflatedView.findViewById(R.id.ivPicture);
        final ImageView imgInputUpload = inflatedView.findViewById(R.id.imgInputUpload);
        final ImageView imgBackground = inflatedView.findViewById(R.id.imgBackgroud);
        final EditText edSpk = inflatedView.findViewById(R.id.edSpk);
        final RelativeLayout lyDeleteImage = inflatedView.findViewById(R.id.lyDeleteImage);

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        popupWindow = new PopupWindow(inflatedView, point.x, point.y, true);

        if(dataSPK !=null){
            edSpk.setText(dataSPK.get("spk"));
        }

        if(!imagePath.equals("")){
            Bitmap bitmap = ImageUtility.loadBitmapFromPath(imagePath);
            imgInputUpload.setImageBitmap(bitmap);
            ivPicture.setVisibility(View.GONE);
            imgBackground.setVisibility(View.GONE);
            lyDeleteImage.setVisibility(View.VISIBLE);
        }

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDataListener.isSaved(false, dataSPK);
                popupWindow.dismiss();
            }
        });

        btnSaveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edSpk.getText().toString().equals("") && !imgPath.equals("")){
                    imgPath = "";
                    datas.put("spk",edSpk.getText().toString());
                    popupWindow.dismiss();
                    saveDataListener.isSaved(true, datas);
                }else{
                    Toast.makeText(activity,"Data is not complete",Toast.LENGTH_SHORT).show();
                }
            }
        });

        ivPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = ImageUtility.createImageFile(customerId,fileName);
                String path = file.getAbsolutePath();
                CameraUtility.openCamera(activity, CameraUtility.REQUEST_IMAGE_CAPTURE_2,file);
                popupWindow.dismiss();
                saveDataListener.onTakePicture(path, UploadDocumentItem.DocumentType.SPK, datas);

//                openCameraOrGallery(activity, popupWindow, datas, customerId, fileName, UploadDocumentItem.DocumentType.SPK, CameraUtility.REQUEST_IMAGE_CAPTURE_2, saveDataListener);
            }
        });

        imgInputUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = ImageUtility.createImageFile(customerId,fileName);
                String path = file.getAbsolutePath();
                CameraUtility.openCamera(activity, CameraUtility.REQUEST_IMAGE_CAPTURE_2,file);
                popupWindow.dismiss();
                saveDataListener.onTakePicture(path, UploadDocumentItem.DocumentType.SPK, datas);

//                openCameraOrGallery(activity, popupWindow, datas, customerId, fileName, UploadDocumentItem.DocumentType.SPK, CameraUtility.REQUEST_IMAGE_CAPTURE_2, saveDataListener);
            }
        });

        lyDeleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lyDeleteImage.setVisibility(View.GONE);
                ivPicture.setVisibility(View.VISIBLE);
                imgBackground.setVisibility(View.VISIBLE);
                imgInputUpload.setImageBitmap(null);
            }
        });

        popupWindow.showAtLocation(view, Gravity.CENTER, point.x, point.y);
        return popupWindow;
    }

    public static PopupWindow inputKartuNama(final Activity activity, View view, final SaveDataListener saveDataListener,
                                             String imagePath, final String customerId, final String fileName, final HashMap<String,String> dataOCRKartuNama, final HashMap<String,String> dataKartuNama) {
        final PopupWindow popupWindow;
        final HashMap<String, String> datas = new HashMap<>();

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout inflatedView = (LinearLayout) inflater.inflate(R.layout.z_popup_kartunama, null, false);

        ImageView imgClose = inflatedView.findViewById(R.id.imgClose);
        Button btnSaveUpdate = inflatedView.findViewById(R.id.btnSaveUpdate);
        final ImageView ivPicture = inflatedView.findViewById(R.id.ivPicture);
        final ImageView imgInputUpload = inflatedView.findViewById(R.id.imgInputUpload);
        final ImageView imgBackground = inflatedView.findViewById(R.id.imgBackgroud);
        final EditText edAlamat = inflatedView.findViewById(R.id.edAlamat);
        final EditText edRT = inflatedView.findViewById(R.id.edRT);
        final EditText edKelurahan = inflatedView.findViewById(R.id.edKelurahan);
        final EditText edKecamatan = inflatedView.findViewById(R.id.edKecamatan);
        final EditText edRW = inflatedView.findViewById(R.id.edRW);
        final EditText edKota = inflatedView.findViewById(R.id.edKota);
        final EditText edZipcode = inflatedView.findViewById(R.id.edZipCode);
        final EditText edNamaPerusahaan = inflatedView.findViewById(R.id.edNamaPerusahaan);
        final EditText edJabatan = inflatedView.findViewById(R.id.edJabatan);
        final EditText edEmail = inflatedView.findViewById(R.id.edEmail);
        final RelativeLayout lyDeleteImage = inflatedView.findViewById(R.id.lyDeleteImage);
        final CardView cardView = inflatedView.findViewById(R.id.cardView);

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        popupWindow = new PopupWindow(inflatedView, point.x, point.y, true);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        if(dataKartuNama != null && dataOCRKartuNama.isEmpty()){
            edAlamat.setText(dataKartuNama.get("alamat"));
            edNamaPerusahaan.setText(dataKartuNama.get("perusahaan"));
            edJabatan.setText(dataKartuNama.get("jabatan"));
            edEmail.setText(dataKartuNama.get("email"));
        } else if (!dataOCRKartuNama.isEmpty()) {
            edAlamat.setText(dataOCRKartuNama.get("alamat"));
            edNamaPerusahaan.setText(dataOCRKartuNama.get("perusahaan"));
            edJabatan.setText(dataOCRKartuNama.get("jabatan"));
            edEmail.setText(dataOCRKartuNama.get("email"));
        }

        if(!imagePath.equals("")){
            Bitmap bitmap = ImageUtility.loadBitmapFromPath(imagePath);
            imgInputUpload.setImageBitmap(bitmap);
            ivPicture.setVisibility(View.GONE);
            imgBackground.setVisibility(View.GONE);
            lyDeleteImage.setVisibility(View.VISIBLE);
            imgPath = imagePath;

            if (dataKartuNama.get("is_camera") != null) {
                if (dataKartuNama.get("is_camera").equalsIgnoreCase("1")) {
                    final ProgressDialog progressDialog = new ProgressDialog(activity);
                    progressDialog.setMessage("Processing OCR...");
                    progressDialog.show();

                    FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmap);
                    OCRUtility.executeOCRCardName(firebaseVisionImage, new OCRUtility.CallbackCardName() {
                        @Override
                        public void onSuccessCallback(HashMap<String, String> data) {
                            progressDialog.dismiss();

                            edAlamat.setText(data.get("alamat"));
                            edNamaPerusahaan.setText(data.get("perusahaan"));
                            edJabatan.setText(data.get("jabatan"));
                            edEmail.setText(data.get("email"));
                        }

                        @Override
                        public void onFailed(String message) {
                            progressDialog.dismiss();
                            Log.d(TAG, "Error OCR " + message);
                        }
                    });
                }
            }
        }

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDataListener.isSaved(false, dataKartuNama);
                imgPath = "";
                popupWindow.dismiss();
            }
        });

        btnSaveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if (!imgPath.isEmpty()) {
                    if (!isEmpty(edNamaPerusahaan.getText().toString()) && !isEmpty(edAlamat.getText().toString())
                            && !isEmpty(edJabatan.getText().toString())) {
                        datas.put("alamat", edAlamat.getText().toString());
                        datas.put("rt", edRT.getText().toString());
                        datas.put("rw", edRW.getText().toString());
                        datas.put("kelurahan", edKelurahan.getText().toString());
                        datas.put("kecamatan", edKecamatan.getText().toString());
                        datas.put("kota", edKota.getText().toString());
                        datas.put("zipcode", edZipcode.getText().toString());
                        datas.put("perusahaan", edNamaPerusahaan.getText().toString());
                        datas.put("jabatan", edJabatan.getText().toString());
                        datas.put("email", edEmail.getText().toString());
                        datas.put("is_saved", "1");
                        datas.put("imagePath", imgPath);
                        imgPath = "";
                        popupWindow.dismiss();
                        saveDataListener.isSaved(true, datas);
                    } else if (isEmpty(edNamaPerusahaan.getText().toString())) {
                        Toast.makeText(activity, "Nama perusahaan tidak boleh kosong.", Toast.LENGTH_SHORT).show();
                    } else if (isEmpty(edAlamat.getText().toString())) {
                        Toast.makeText(activity, "Alamat tidak boleh kosong.", Toast.LENGTH_SHORT).show();
                    } else if (isEmpty(edEmail.getText().toString())) {
                        Toast.makeText(activity, "Email tidak boleh kosong.", Toast.LENGTH_SHORT).show();
                    } else if (isEmpty(edJabatan.getText().toString())) {
                        Toast.makeText(activity, "Jabatan tidak boleh kosong.", Toast.LENGTH_SHORT).show();
                    }
                /*} else {
                    Toast.makeText(activity, "Foto tidak boleh kosong.", Toast.LENGTH_SHORT).show();
                }*/
            }
        });

        ivPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas.put("alamat", edAlamat.getText().toString());
                datas.put("perusahaan", edNamaPerusahaan.getText().toString());
                datas.put("jabatan", edJabatan.getText().toString());
                datas.put("email", edEmail.getText().toString());

                Intent intent = new Intent(activity, CameraCardNameActivity.class);
                intent.putExtra("customerId",customerId);
                intent.putExtra("fileName",fileName);
                intent.putExtra("datas",datas);
                intent.putExtra("dataKartuNama",dataKartuNama);

                saveDataListener.onTakePicture(imgPath, UploadDocumentItem.DocumentType.KartuNama, datas);
                openCameraOrGalleryOCR(activity, popupWindow, intent, CameraUtility.REQUEST_IMAGE_CAPTURE_3);
            }
        });

        imgInputUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas.put("alamat", edAlamat.getText().toString());
                datas.put("perusahaan", edNamaPerusahaan.getText().toString());
                datas.put("jabatan", edJabatan.getText().toString());
                datas.put("email", edEmail.getText().toString());

                Intent intent = new Intent(activity, CameraCardNameActivity.class);
                intent.putExtra("customerId",customerId);
                intent.putExtra("fileName",fileName);
                intent.putExtra("datas",datas);
                intent.putExtra("dataKartuNama",dataKartuNama);

                saveDataListener.onTakePicture(imgPath, UploadDocumentItem.DocumentType.KartuNama, datas);
                openCameraOrGalleryOCR(activity, popupWindow, intent, CameraUtility.REQUEST_IMAGE_CAPTURE_3);
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
            }
        });

        DialogUtility.relayoutPopupWindow(activity, cardView);

        popupWindow.showAtLocation(view, Gravity.CENTER, point.x, point.y);
        return popupWindow;
    }

    public static PopupWindow inputKartuKeluarga(final Activity activity, View view, final SaveDataListener saveDataListener, final String imagePath,final String filePdfPath,
                                                 final String customerId, final String fileName, final String promiseDate, final HashMap<String, String> data) {
        final PopupWindow popupWindow;
        final HashMap<String, String> datas = new HashMap<>();
        checkIsUpdate(activity);

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout inflatedView = (LinearLayout) inflater.inflate(R.layout.z_popup_kk, null, false);

        ImageView imgClose = inflatedView.findViewById(R.id.imgClose);
        Button btnSaveUpdate = inflatedView.findViewById(R.id.btnSaveUpdate);
        final ImageView ivPicture = inflatedView.findViewById(R.id.ivPicture);
        final ImageView imgInputUpload = inflatedView.findViewById(R.id.imgInputUpload);
        final ImageView imgBackground = inflatedView.findViewById(R.id.imgBackgroud);

        final RadioButton rbTersedia = inflatedView.findViewById(R.id.rbTersedia);
        final RadioButton rbMenunggu = inflatedView.findViewById(R.id.rbMenyusul);
        ImageView ivDate = inflatedView.findViewById(R.id.ivDate);
        RelativeLayout lyPromiseDate = inflatedView.findViewById(R.id.lyPromiseDate);
        final RelativeLayout lyParentPromiseDate = inflatedView.findViewById(R.id.lyParentPromiseDate);
        final RelativeLayout lyParentImage = inflatedView.findViewById(R.id.lyParentImage);
        final TextView txtPromiseDate = inflatedView.findViewById(R.id.txtPromiseDate);
        final RelativeLayout lyDeleteImage = inflatedView.findViewById(R.id.lyDeleteImage);
        final RelativeLayout imgPdfAttach = inflatedView.findViewById(R.id.imgPdfAttach);

        final EditText edNoKK = inflatedView.findViewById(R.id.edNoKK);

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        popupWindow = new PopupWindow(inflatedView, point.x, point.y, true);

        if(!imagePath.equals("")){
            Bitmap bitmap = ImageUtility.loadBitmapFromPath(imagePath);
            imgInputUpload.setImageBitmap(bitmap);
            ivPicture.setVisibility(View.GONE);
            imgBackground.setVisibility(View.GONE);
            lyDeleteImage.setVisibility(View.VISIBLE);
            imgPdfAttach.setVisibility(View.GONE);
            imgPath = imagePath;
        } else if (!filePdfPath.equals("")) {
            ivPicture.setVisibility(View.GONE);
            imgBackground.setVisibility(View.GONE);
            lyDeleteImage.setVisibility(View.VISIBLE);
            imgPdfAttach.setVisibility(View.VISIBLE);
            pdfPath = filePdfPath;
        }

        if (isUpdate == 1) {
            inflatedView.findViewById(R.id.lyRadioGrup).setVisibility(View.GONE);
        }

        if(promiseDate != null && !promiseDate.equals("")){
            lyParentPromiseDate.setVisibility(View.VISIBLE);
            lyParentImage.setVisibility(View.GONE);
            txtPromiseDate.setText(promiseDate);
            rbMenunggu.setChecked(true);
        }

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgPath = "";
                pdfPath = "";
                saveDataListener.isSaved(false, data);
                popupWindow.dismiss();
            }
        });

        btnSaveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rbTersedia.isChecked() && (!imgPath.equals("") || !pdfPath.equals(""))){
                    imgPath = "";
                    pdfPath = "";
                    datas.put("promiseDate","");
                    datas.put("is_saved", "1");
                    datas.put("isPDF", "0");

                    popupWindow.dismiss();
                    saveDataListener.isSaved(true, datas);
                } else if (rbMenunggu.isChecked() && !txtPromiseDate.getText().toString().equals("")) {
                    popupWindow.dismiss();
                    imgPath = "";
                    pdfPath = "";
                    datas.put("is_saved", "1");
                    datas.put("isPDF", "0");
                    datas.clear();
                    saveDataListener.isSaved(false, datas);
                    datas.put("promiseDate",txtPromiseDate.getText().toString());
                    saveDataListener.isSaved(true, datas);
                } else {
                    String message = isUpdate != 1 ? "Foto atau File PDF atau promised date tidak boleh kosong." : "Foto atau File PDF tidak boleh kosong.";
                    Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                }
            }
        });

        ivPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas.put("isPDF", "0");
                openCameraOrGallery(activity, popupWindow, datas, customerId, fileName, UploadDocumentItem.DocumentType.KK, CameraUtility.REQUEST_IMAGE_CAPTURE_4, saveDataListener);
            }
        });

        imgInputUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas.put("isPDF", "0");
                openCameraOrGallery(activity, popupWindow, datas, customerId, fileName, UploadDocumentItem.DocumentType.KK, CameraUtility.REQUEST_IMAGE_CAPTURE_4, saveDataListener);
            }
        });

        rbTersedia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    lyParentPromiseDate.setVisibility(View.GONE);
                    lyParentImage.setVisibility(View.VISIBLE);
                }
            }
        });

        rbMenunggu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    lyParentPromiseDate.setVisibility(View.VISIBLE);
                    lyParentImage.setVisibility(View.GONE);
                }
            }
        });

        ivDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((UploadDocumentActivity)activity).showDatePicker();
                Calendar calendar = Calendar.getInstance();
                MonthAdapter.CalendarDay currDate = new MonthAdapter.CalendarDay();
                currDate.setDay(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                MonthAdapter.CalendarDay maxDate = new MonthAdapter.CalendarDay();
                maxDate.setDay(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)+maxPromisedDate);
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                            @Override
                            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                                int month = monthOfYear+1;
                                String date = dayOfMonth+"/"+month+"/"+year;
                                txtPromiseDate.setText(date);
                                datas.put("promiseDate",date);
                            }
                        })
                        .setFirstDayOfWeek(Calendar.SUNDAY)
                        .setPreselectedDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                        .setDateRange(currDate, maxDate)
                        .setDoneText("Yes")
                        .setCancelText("No");
                cdp.show(((UploadDocumentActivity) activity).getSupportFragmentManager(),"Promise Date");
            }
        });

        lyDeleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lyDeleteImage.setVisibility(View.GONE);
                ivPicture.setVisibility(View.VISIBLE);
                imgBackground.setVisibility(View.VISIBLE);
                imgPdfAttach.setVisibility(View.GONE);
                imgInputUpload.setImageBitmap(null);
                imgPath = "";
                pdfPath = "";
            }
        });

        popupWindow.showAtLocation(view, Gravity.CENTER, point.x, point.y);
        return popupWindow;
    }

    public static PopupWindow inputNPWP(final Activity activity, View view, final SaveDataListener saveDataListener, final String imagePath,final String filePdfPath,
                                        final String customerId, final String fileName, final HashMap<String,String> data) {
        final PopupWindow popupWindow;
        final HashMap<String, String> datas = new HashMap<>();
        checkIsUpdate(activity);

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout inflatedView = (LinearLayout) inflater.inflate(R.layout.z_popup_npwp, null, false);

        ImageView imgClose = inflatedView.findViewById(R.id.imgClose);
        Button btnSaveUpdate = inflatedView.findViewById(R.id.btnSaveUpdate);
        final ImageView ivPicture = inflatedView.findViewById(R.id.ivPicture);
        final ImageView imgInputUpload = inflatedView.findViewById(R.id.imgInputUpload);
        final ImageView imgBackground = inflatedView.findViewById(R.id.imgBackgroud);

        final RadioButton rbTersedia = inflatedView.findViewById(R.id.rbTersedia);
        final RadioButton rbMenunggu = inflatedView.findViewById(R.id.rbMenyusul);
        ImageView ivDate = inflatedView.findViewById(R.id.ivDate);
        RelativeLayout lyPromiseDate = inflatedView.findViewById(R.id.lyPromiseDate);
        final RelativeLayout lyParentPromiseDate = inflatedView.findViewById(R.id.lyParentPromiseDate);
        final RelativeLayout lyParentImage = inflatedView.findViewById(R.id.lyParentImage);
        final TextView txtPromiseDate = inflatedView.findViewById(R.id.txtPromiseDate);
        final RelativeLayout lyNoNPWP = inflatedView.findViewById(R.id.lyNoNPWP);
        final EditText edNoNPWP = inflatedView.findViewById(R.id.edNoNPWP);
        final RelativeLayout lyDeleteImage = inflatedView.findViewById(R.id.lyDeleteImage);
        final CardView cardView = inflatedView.findViewById(R.id.cardView);
        final RelativeLayout imgPdfAttach = inflatedView.findViewById(R.id.imgPdfAttach);

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        popupWindow = new PopupWindow(inflatedView, point.x, point.y, true);

        if(!imagePath.equals("")){
            Bitmap bitmap = ImageUtility.loadBitmapFromPath(imagePath);
            imgInputUpload.setImageBitmap(bitmap);
            ivPicture.setVisibility(View.GONE);
            imgBackground.setVisibility(View.GONE);
            lyDeleteImage.setVisibility(View.VISIBLE);
            imgPdfAttach.setVisibility(View.GONE);
            imgPath = imagePath;
        } else if (!filePdfPath.equals("")) {
            ivPicture.setVisibility(View.GONE);
            imgBackground.setVisibility(View.GONE);
            lyDeleteImage.setVisibility(View.VISIBLE);
            imgPdfAttach.setVisibility(View.VISIBLE);
            pdfPath = filePdfPath;
        }

        if (isUpdate == 1) {
            inflatedView.findViewById(R.id.lyRadioGrup).setVisibility(View.GONE);
            inflatedView.findViewById(R.id.lyNoNPWP).setVisibility(View.GONE);
        }

        if(data != null){
            if(data.get("promiseDate") != null && !data.get("promiseDate").equals("")){
                lyParentPromiseDate.setVisibility(View.VISIBLE);
                lyParentImage.setVisibility(View.GONE);
                lyNoNPWP.setVisibility(View.GONE);
                txtPromiseDate.setText(data.get("promiseDate"));
                rbMenunggu.setChecked(true);
            } else if (data.get("npwp") != null) {
                edNoNPWP.setText(data.get("npwp"));
            }
        }

        btnSaveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rbTersedia.isChecked() && (!imgPath.equals("") || !pdfPath.equals("")) && (!edNoNPWP.getText().toString().equals("") || isUpdate == 1)){
                    imgPath = "";
                    pdfPath = "";
                    datas.put("npwp",edNoNPWP.getText().toString());
                    datas.put("promiseDate","");
                    datas.put("is_saved", "1");
                    datas.put("isPDF", "0");

                    popupWindow.dismiss();
                    saveDataListener.isSaved(true, datas);
                } else if (rbMenunggu.isChecked() && !txtPromiseDate.getText().toString().equals("")) {
                    popupWindow.dismiss();
                    imgPath = "";
                    pdfPath = "";
                    datas.put("is_saved", "1");
                    datas.put("isPDF", "0");
                    datas.clear();
                    saveDataListener.isSaved(false, datas);
                    datas.put("promiseDate",txtPromiseDate.getText().toString());
                    saveDataListener.isSaved(true, datas);
                } else {
                    if (edNoNPWP.getText().toString().equals("") && isUpdate != 1) {
                        Toast.makeText(activity, "Nomor NPWP tidak boleh kosong.", Toast.LENGTH_LONG).show();
                    } else {
                        String message = isUpdate != 1 ? "Foto atau File PDF atau promised date tidak boleh kosong." : "Foto atau File PDF tidak boleh kosong.";
                        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgPath = "";
                pdfPath = "";
                saveDataListener.isSaved(false, data);
                popupWindow.dismiss();
            }
        });

        ivPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas.put("npwp",edNoNPWP.getText().toString());
                datas.put("isPDF", "0");
                openCameraOrGallery(activity, popupWindow, datas, customerId, fileName, UploadDocumentItem.DocumentType.NPWP, CameraUtility.REQUEST_IMAGE_CAPTURE_5, saveDataListener);
            }
        });

        imgInputUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas.put("npwp",edNoNPWP.getText().toString());
                datas.put("isPDF", "0");
                openCameraOrGallery(activity, popupWindow, datas, customerId, fileName, UploadDocumentItem.DocumentType.NPWP, CameraUtility.REQUEST_IMAGE_CAPTURE_5, saveDataListener);
            }
        });

        rbTersedia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    lyParentPromiseDate.setVisibility(View.GONE);
                    lyParentImage.setVisibility(View.VISIBLE);
                    lyNoNPWP.setVisibility(View.VISIBLE);
                }
            }
        });

        rbMenunggu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    lyParentPromiseDate.setVisibility(View.VISIBLE);
                    lyParentImage.setVisibility(View.GONE);
                    lyNoNPWP.setVisibility(View.GONE);
                }
            }
        });

        ivDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((UploadDocumentActivity)activity).showDatePicker();
                Calendar calendar = Calendar.getInstance();
                MonthAdapter.CalendarDay currDate = new MonthAdapter.CalendarDay();
                currDate.setDay(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                MonthAdapter.CalendarDay maxDate = new MonthAdapter.CalendarDay();
                maxDate.setDay(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)+maxPromisedDate);
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                            @Override
                            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                                int month = monthOfYear+1;
                                String date = dayOfMonth+"/"+month+"/"+year;
                                txtPromiseDate.setText(date);
                                datas.put("promiseDate",date);
                            }
                        })
                        .setFirstDayOfWeek(Calendar.SUNDAY)
                        .setPreselectedDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                        .setDateRange(currDate, maxDate)
                        .setDoneText("Yes")
                        .setCancelText("No");
                cdp.show(((UploadDocumentActivity) activity).getSupportFragmentManager(),"Promise Date");
            }
        });

        lyDeleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lyDeleteImage.setVisibility(View.GONE);
                ivPicture.setVisibility(View.VISIBLE);
                imgBackground.setVisibility(View.VISIBLE);
                imgPdfAttach.setVisibility(View.GONE);
                imgInputUpload.setImageBitmap(null);
                imgPath = "";
                pdfPath = "";
            }
        });

        DialogUtility.relayoutPopupWindow(activity, cardView);

        popupWindow.showAtLocation(view, Gravity.CENTER, point.x, point.y);
        return popupWindow;
    }

    public static PopupWindow inputBuktiKeuangan(final Activity activity, View view, final SaveDataListener saveDataListener, final String imagePath,final String filePdfPath,
                                                 final String customerId, final String fileName, final String promiseDate, final HashMap<String, String> data) {
        final PopupWindow popupWindow;
        final HashMap<String, String> datas = new HashMap<>();
        checkIsUpdate(activity);

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout inflatedView = (LinearLayout) inflater.inflate(R.layout.z_popup_buktikeu, null, false);

        ImageView imgClose = inflatedView.findViewById(R.id.imgClose);
        Button btnSaveUpdate = inflatedView.findViewById(R.id.btnSaveUpdate);
        final ImageView ivPicture = inflatedView.findViewById(R.id.ivPicture);
        final ImageView imgInputUpload = inflatedView.findViewById(R.id.imgInputUpload);
        final ImageView imgBackground = inflatedView.findViewById(R.id.imgBackgroud);

        final RadioButton rbTersedia = inflatedView.findViewById(R.id.rbTersedia);
        final RadioButton rbMenunggu = inflatedView.findViewById(R.id.rbMenyusul);
        ImageView ivDate = inflatedView.findViewById(R.id.ivDate);
        RelativeLayout lyPromiseDate = inflatedView.findViewById(R.id.lyPromiseDate);
        final RelativeLayout lyParentPromiseDate = inflatedView.findViewById(R.id.lyParentPromiseDate);
        final RelativeLayout lyParentImage = inflatedView.findViewById(R.id.lyParentImage);
        final TextView txtPromiseDate = inflatedView.findViewById(R.id.txtPromiseDate);
        final RelativeLayout lyDeleteImage = inflatedView.findViewById(R.id.lyDeleteImage);
        final RelativeLayout imgPdfAttach = inflatedView.findViewById(R.id.imgPdfAttach);

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        popupWindow = new PopupWindow(inflatedView, point.x, point.y, true);

        if(!imagePath.equals("")){
            Bitmap bitmap = ImageUtility.loadBitmapFromPath(imagePath);
            imgInputUpload.setImageBitmap(bitmap);
            ivPicture.setVisibility(View.GONE);
            imgBackground.setVisibility(View.GONE);
            lyDeleteImage.setVisibility(View.VISIBLE);
            imgPdfAttach.setVisibility(View.GONE);
            imgPath = imagePath;
        } else if (!filePdfPath.equals("")) {
            ivPicture.setVisibility(View.GONE);
            imgBackground.setVisibility(View.GONE);
            lyDeleteImage.setVisibility(View.VISIBLE);
            imgPdfAttach.setVisibility(View.VISIBLE);
            pdfPath = filePdfPath;
        }

        if (isUpdate == 1) {
            inflatedView.findViewById(R.id.lyRadioGrup).setVisibility(View.GONE);
        }

        if(promiseDate != null && !promiseDate.equals("")){
            lyParentPromiseDate.setVisibility(View.VISIBLE);
            lyParentImage.setVisibility(View.GONE);
            txtPromiseDate.setText(promiseDate);
            rbMenunggu.setChecked(true);
            datas.put("promiseDate",promiseDate);
        }

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgPath = "";
                pdfPath = "";
                saveDataListener.isSaved(false, data);
                popupWindow.dismiss();
            }
        });

        btnSaveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rbTersedia.isChecked() && (!imgPath.equals("") || !pdfPath.equals(""))){
                    imgPath = "";
                    pdfPath = "";
                    datas.put("promiseDate","");
                    datas.put("is_saved", "1");
                    datas.put("isPDF", "0");

                    popupWindow.dismiss();
                    saveDataListener.isSaved(true, datas);
                } else if (rbMenunggu.isChecked() && !txtPromiseDate.getText().toString().equals("")) {
                    popupWindow.dismiss();
                    imgPath = "";
                    pdfPath = "";
                    datas.put("is_saved", "1");
                    datas.put("isPDF", "0");
                    datas.clear();
                    saveDataListener.isSaved(false, datas);
                    datas.put("promiseDate",txtPromiseDate.getText().toString());
                    saveDataListener.isSaved(true, datas);
                } else {
                    String message = isUpdate != 1 ? "Foto atau File PDF atau promised date tidak boleh kosong." : "Foto atau File PDF tidak boleh kosong.";
                    Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                }
            }
        });

        ivPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas.put("isPDF", "0");
                openCameraOrGallery(activity, popupWindow, datas, customerId, fileName, UploadDocumentItem.DocumentType.BuktiKeuangan, CameraUtility.REQUEST_IMAGE_CAPTURE_6, saveDataListener);
            }
        });

        imgInputUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas.put("isPDF", "0");
                openCameraOrGallery(activity, popupWindow, datas, customerId, fileName, UploadDocumentItem.DocumentType.BuktiKeuangan, CameraUtility.REQUEST_IMAGE_CAPTURE_6, saveDataListener);
            }
        });

        rbTersedia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    lyParentPromiseDate.setVisibility(View.GONE);
                    lyParentImage.setVisibility(View.VISIBLE);
                }
            }
        });

        rbMenunggu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    lyParentPromiseDate.setVisibility(View.VISIBLE);
                    lyParentImage.setVisibility(View.GONE);
                }
            }
        });

        ivDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((UploadDocumentActivity)activity).showDatePicker();
                Calendar calendar = Calendar.getInstance();
                MonthAdapter.CalendarDay currDate = new MonthAdapter.CalendarDay();
                currDate.setDay(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                MonthAdapter.CalendarDay maxDate = new MonthAdapter.CalendarDay();
                maxDate.setDay(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)+maxPromisedDate);
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                            @Override
                            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                                int month = monthOfYear+1;
                                String date = dayOfMonth+"/"+month+"/"+year;
                                txtPromiseDate.setText(date);
                                datas.put("promiseDate",date);
                            }
                        })
                        .setFirstDayOfWeek(Calendar.SUNDAY)
                        .setPreselectedDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                        .setDateRange(currDate, maxDate)
                        .setDoneText("Yes")
                        .setCancelText("No");
                cdp.show(((UploadDocumentActivity) activity).getSupportFragmentManager(),"Promise Date");
            }
        });


        lyDeleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lyDeleteImage.setVisibility(View.GONE);
                ivPicture.setVisibility(View.VISIBLE);
                imgBackground.setVisibility(View.VISIBLE);
                imgPdfAttach.setVisibility(View.GONE);
                imgInputUpload.setImageBitmap(null);
                imgPath = "";
                pdfPath = "";
            }
        });

        popupWindow.showAtLocation(view, Gravity.CENTER, point.x, point.y);
        return popupWindow;
    }

    public static PopupWindow inputRekeningKoran(final Activity activity, View view, final SaveDataListener saveDataListener, final String imagePath,final String filePdfPath,
                                                 final String customerId, final String fileName, final String promiseDate, final HashMap<String, String> data) {
        final PopupWindow popupWindow;
        final HashMap<String, String> datas = new HashMap<>();
        checkIsUpdate(activity);

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout inflatedView = (LinearLayout) inflater.inflate(R.layout.z_popup_rekeningkoran, null, false);

        ImageView imgClose = inflatedView.findViewById(R.id.imgClose);
        Button btnSaveUpdate = inflatedView.findViewById(R.id.btnSaveUpdate);
        final ImageView ivPicture = inflatedView.findViewById(R.id.ivPicture);
        final ImageView imgInputUpload = inflatedView.findViewById(R.id.imgInputUpload);
        final ImageView imgBackground = inflatedView.findViewById(R.id.imgBackgroud);

        final RadioButton rbTersedia = inflatedView.findViewById(R.id.rbTersedia);
        final RadioButton rbMenunggu = inflatedView.findViewById(R.id.rbMenyusul);
        ImageView ivDate = inflatedView.findViewById(R.id.ivDate);
        RelativeLayout lyPromiseDate = inflatedView.findViewById(R.id.lyPromiseDate);
        final RelativeLayout lyParentPromiseDate = inflatedView.findViewById(R.id.lyParentPromiseDate);
        final RelativeLayout lyParentImage = inflatedView.findViewById(R.id.lyParentImage);
        final TextView txtPromiseDate = inflatedView.findViewById(R.id.txtPromiseDate);
        final RelativeLayout lyDeleteImage = inflatedView.findViewById(R.id.lyDeleteImage);
        final RelativeLayout imgPdfAttach = inflatedView.findViewById(R.id.imgPdfAttach);

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        popupWindow = new PopupWindow(inflatedView, point.x, point.y, true);

        if(!imagePath.equals("")){
            Bitmap bitmap = ImageUtility.loadBitmapFromPath(imagePath);
            imgInputUpload.setImageBitmap(bitmap);
            ivPicture.setVisibility(View.GONE);
            imgBackground.setVisibility(View.GONE);
            lyDeleteImage.setVisibility(View.VISIBLE);
            imgPdfAttach.setVisibility(View.GONE);
            imgPath = imagePath;
        } else if (!filePdfPath.equals("")) {
            ivPicture.setVisibility(View.GONE);
            imgBackground.setVisibility(View.GONE);
            lyDeleteImage.setVisibility(View.VISIBLE);
            imgPdfAttach.setVisibility(View.VISIBLE);
            pdfPath = filePdfPath;
        }

        if (isUpdate == 1) {
            inflatedView.findViewById(R.id.lyRadioGrup).setVisibility(View.GONE);
        }

        if(promiseDate != null && !promiseDate.equals("")){
            lyParentPromiseDate.setVisibility(View.VISIBLE);
            lyParentImage.setVisibility(View.GONE);
            txtPromiseDate.setText(promiseDate);
            rbMenunggu.setChecked(true);
            datas.put("promiseDate",promiseDate);
        }

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pdfPath = "";
                imgPath = "";
                saveDataListener.isSaved(false, data);
                popupWindow.dismiss();
            }
        });

        btnSaveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rbTersedia.isChecked() && (!imgPath.equals("") || !pdfPath.equals(""))){
                    imgPath = "";
                    pdfPath = "";
                    datas.put("promiseDate","");
                    datas.put("is_saved", "1");
                    datas.put("isPDF", "0");

                    popupWindow.dismiss();
                    saveDataListener.isSaved(true, datas);
                } else if (rbMenunggu.isChecked() && !txtPromiseDate.getText().toString().equals("")) {
                    popupWindow.dismiss();
                    imgPath = "";
                    pdfPath = "";
                    datas.put("is_saved", "1");
                    datas.put("isPDF", "0");
                    datas.clear();
                    saveDataListener.isSaved(false, datas);
                    datas.put("promiseDate",txtPromiseDate.getText().toString());
                    saveDataListener.isSaved(true, datas);
                } else {
                    String message = isUpdate != 1 ? "Foto atau File PDF atau promised date tidak boleh kosong." : "Foto atau File PDF tidak boleh kosong.";
                    Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                }
            }
        });

        ivPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                File file = ImageUtility.createImageFile(customerId,fileName);
//                String path = file.getAbsolutePath();
//                CameraUtility.openCamera(activity, CameraUtility.REQUEST_IMAGE_CAPTURE_7,file);
//                popupWindow.dismiss();
//                saveDataListener.onTakePicture(path, UploadDocumentItem.DocumentType.RekeningKoran, datas);
                datas.put("isPDF", "0");
                openCameraOrGallery(activity, popupWindow, datas, customerId, fileName, UploadDocumentItem.DocumentType.RekeningKoran, CameraUtility.REQUEST_IMAGE_CAPTURE_7, saveDataListener);
            }
        });

        imgInputUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                File file = ImageUtility.createImageFile(customerId,fileName);
//                String path = file.getAbsolutePath();
//                CameraUtility.openCamera(activity, CameraUtility.REQUEST_IMAGE_CAPTURE_7,file);
//                popupWindow.dismiss();
//                saveDataListener.onTakePicture(path, UploadDocumentItem.DocumentType.RekeningKoran, datas);

                datas.put("isPDF", "0");
                openCameraOrGallery(activity, popupWindow, datas, customerId, fileName, UploadDocumentItem.DocumentType.RekeningKoran, CameraUtility.REQUEST_IMAGE_CAPTURE_7, saveDataListener);
            }
        });

        rbTersedia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    lyParentPromiseDate.setVisibility(View.GONE);
                    lyParentImage.setVisibility(View.VISIBLE);
                }
            }
        });

        rbMenunggu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    lyParentPromiseDate.setVisibility(View.VISIBLE);
                    lyParentImage.setVisibility(View.GONE);
                }
            }
        });

        ivDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((UploadDocumentActivity)activity).showDatePicker();
                Calendar calendar = Calendar.getInstance();
                MonthAdapter.CalendarDay currDate = new MonthAdapter.CalendarDay();
                currDate.setDay(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                MonthAdapter.CalendarDay maxDate = new MonthAdapter.CalendarDay();
                maxDate.setDay(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)+maxPromisedDate);
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                            @Override
                            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                                int month = monthOfYear+1;
                                String date = dayOfMonth+"/"+month+"/"+year;
                                txtPromiseDate.setText(date);
                                datas.put("promiseDate",date);
                            }
                        })
                        .setFirstDayOfWeek(Calendar.SUNDAY)
                        .setPreselectedDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                        .setDateRange(currDate, maxDate)
                        .setDoneText("Yes")
                        .setCancelText("No");
                cdp.show(((UploadDocumentActivity) activity).getSupportFragmentManager(),"Promise Date");
            }
        });

        lyDeleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lyDeleteImage.setVisibility(View.GONE);
                ivPicture.setVisibility(View.VISIBLE);
                imgBackground.setVisibility(View.VISIBLE);
                imgPdfAttach.setVisibility(View.GONE);
                imgInputUpload.setImageBitmap(null);
                imgPath = "";
                pdfPath = "";
            }
        });

        popupWindow.showAtLocation(view, Gravity.CENTER, point.x, point.y);
        return popupWindow;
    }

    public static PopupWindow inputCoverTabungan(final Activity activity, View view, final SaveDataListener saveDataListener, final String imagePath,final String filePdfPath,
                                                 final String customerId, final String fileName, final String promiseDate, final HashMap<String, String> data) {
        final PopupWindow popupWindow;
        final HashMap<String, String> datas = new HashMap<>();
        checkIsUpdate(activity);

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout inflatedView = (LinearLayout) inflater.inflate(R.layout.z_popup_covertabungan, null, false);

        ImageView imgClose = inflatedView.findViewById(R.id.imgClose);
        Button btnSaveUpdate = inflatedView.findViewById(R.id.btnSaveUpdate);
        final ImageView ivPicture = inflatedView.findViewById(R.id.ivPicture);
        final ImageView imgInputUpload = inflatedView.findViewById(R.id.imgInputUpload);
        final ImageView imgBackground = inflatedView.findViewById(R.id.imgBackgroud);

        final RadioButton rbTersedia = inflatedView.findViewById(R.id.rbTersedia);
        final RadioButton rbMenunggu = inflatedView.findViewById(R.id.rbMenyusul);
        ImageView ivDate = inflatedView.findViewById(R.id.ivDate);
        RelativeLayout lyPromiseDate = inflatedView.findViewById(R.id.lyPromiseDate);
        final RelativeLayout lyParentPromiseDate = inflatedView.findViewById(R.id.lyParentPromiseDate);
        final RelativeLayout lyParentImage = inflatedView.findViewById(R.id.lyParentImage);
        final TextView txtPromiseDate = inflatedView.findViewById(R.id.txtPromiseDate);
        final RelativeLayout lyDeleteImage = inflatedView.findViewById(R.id.lyDeleteImage);
        final RelativeLayout imgPdfAttach = inflatedView.findViewById(R.id.imgPdfAttach);

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        popupWindow = new PopupWindow(inflatedView, point.x, point.y, true);

        if(!imagePath.equals("")){
            Bitmap bitmap = ImageUtility.loadBitmapFromPath(imagePath);
            imgInputUpload.setImageBitmap(bitmap);
            ivPicture.setVisibility(View.GONE);
            imgBackground.setVisibility(View.GONE);
            lyDeleteImage.setVisibility(View.VISIBLE);
            imgPdfAttach.setVisibility(View.GONE);
            imgPath = imagePath;
        } else if (!filePdfPath.equals("")) {
            ivPicture.setVisibility(View.GONE);
            imgBackground.setVisibility(View.GONE);
            lyDeleteImage.setVisibility(View.VISIBLE);
            imgPdfAttach.setVisibility(View.VISIBLE);
            pdfPath = filePdfPath;
        }

        if (isUpdate == 1) {
            inflatedView.findViewById(R.id.lyRadioGrup).setVisibility(View.GONE);
        }

        if(promiseDate != null && !promiseDate.equals("")){
            lyParentPromiseDate.setVisibility(View.VISIBLE);
            lyParentImage.setVisibility(View.GONE);
            txtPromiseDate.setText(promiseDate);
            rbMenunggu.setChecked(true);
            datas.put("promiseDate",promiseDate);
        }

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pdfPath = "";
                imgPath = "";
                saveDataListener.isSaved(false, data);
                popupWindow.dismiss();
            }
        });

        btnSaveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rbTersedia.isChecked() && (!imgPath.equals("") || !pdfPath.equals(""))){
                    imgPath = "";
                    pdfPath = "";
                    datas.put("promiseDate","");
                    datas.put("is_saved", "1");
                    datas.put("isPDF", "0");

                    popupWindow.dismiss();
                    saveDataListener.isSaved(true, datas);
                } else if (rbMenunggu.isChecked() && !txtPromiseDate.getText().toString().equals("")) {
                    popupWindow.dismiss();
                    imgPath = "";
                    pdfPath = "";
                    datas.put("is_saved", "1");
                    datas.put("isPDF", "0");
                    datas.clear();
                    saveDataListener.isSaved(false, datas);
                    datas.put("promiseDate",txtPromiseDate.getText().toString());
                    saveDataListener.isSaved(true, datas);
                } else {
                    String message = isUpdate != 1 ? "Foto atau File PDF atau promised date tidak boleh kosong." : "Foto atau File PDF tidak boleh kosong.";
                    Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                }
            }
        });

        ivPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas.put("isPDF", "0");
                openCameraOrGallery(activity, popupWindow, datas, customerId, fileName, UploadDocumentItem.DocumentType.CoverBukuTabungan, CameraUtility.REQUEST_IMAGE_CAPTURE_8, saveDataListener);
            }
        });

        imgInputUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas.put("isPDF", "0");
                openCameraOrGallery(activity, popupWindow, datas, customerId, fileName, UploadDocumentItem.DocumentType.CoverBukuTabungan, CameraUtility.REQUEST_IMAGE_CAPTURE_8, saveDataListener);
            }
        });

        rbTersedia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    lyParentPromiseDate.setVisibility(View.GONE);
                    lyParentImage.setVisibility(View.VISIBLE);
                }
            }
        });

        rbMenunggu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    lyParentPromiseDate.setVisibility(View.VISIBLE);
                    lyParentImage.setVisibility(View.GONE);
                }
            }
        });

        ivDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((UploadDocumentActivity)activity).showDatePicker();
                Calendar calendar = Calendar.getInstance();
                MonthAdapter.CalendarDay currDate = new MonthAdapter.CalendarDay();
                currDate.setDay(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                MonthAdapter.CalendarDay maxDate = new MonthAdapter.CalendarDay();
                maxDate.setDay(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)+maxPromisedDate);
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                            @Override
                            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                                int month = monthOfYear+1;
                                String date = dayOfMonth+"/"+month+"/"+year;
                                txtPromiseDate.setText(date);
                                datas.put("promiseDate",date);
                            }
                        })
                        .setFirstDayOfWeek(Calendar.SUNDAY)
                        .setPreselectedDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                        .setDateRange(currDate, maxDate)
                        .setDoneText("Yes")
                        .setCancelText("No");
                cdp.show(((UploadDocumentActivity) activity).getSupportFragmentManager(),"Promise Date");
            }
        });

        lyDeleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lyDeleteImage.setVisibility(View.GONE);
                ivPicture.setVisibility(View.VISIBLE);
                imgBackground.setVisibility(View.VISIBLE);
                imgPdfAttach.setVisibility(View.GONE);
                imgInputUpload.setImageBitmap(null);
                imgPath = "";
                pdfPath = "";
            }
        });

        popupWindow.showAtLocation(view, Gravity.CENTER, point.x, point.y);
        return popupWindow;
    }

    public static PopupWindow inputBuktiKepemilikanRumah(final Activity activity, View view, final SaveDataListener saveDataListener, final String imagePath,final String filePdfPath,
                                                         final String customerId, final String fileName, final String promiseDate, final HashMap<String, String> data) {
        final PopupWindow popupWindow;
        final HashMap<String, String> datas = new HashMap<>();
        checkIsUpdate(activity);

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout inflatedView = (LinearLayout) inflater.inflate(R.layout.z_popup_buktikepemilikanrumah, null, false);

        ImageView imgClose = inflatedView.findViewById(R.id.imgClose);
        Button btnSaveUpdate = inflatedView.findViewById(R.id.btnSaveUpdate);
        final ImageView ivPicture = inflatedView.findViewById(R.id.ivPicture);
        final ImageView imgInputUpload = inflatedView.findViewById(R.id.imgInputUpload);
        final ImageView imgBackground = inflatedView.findViewById(R.id.imgBackgroud);

        final RadioButton rbTersedia = inflatedView.findViewById(R.id.rbTersedia);
        final RadioButton rbMenunggu = inflatedView.findViewById(R.id.rbMenyusul);
        ImageView ivDate = inflatedView.findViewById(R.id.ivDate);
        RelativeLayout lyPromiseDate = inflatedView.findViewById(R.id.lyPromiseDate);
        final RelativeLayout lyParentPromiseDate = inflatedView.findViewById(R.id.lyParentPromiseDate);
        final RelativeLayout lyParentImage = inflatedView.findViewById(R.id.lyParentImage);
        final TextView txtPromiseDate = inflatedView.findViewById(R.id.txtPromiseDate);
        final RelativeLayout lyDeleteImage = inflatedView.findViewById(R.id.lyDeleteImage);
        final RelativeLayout imgPdfAttach = inflatedView.findViewById(R.id.imgPdfAttach);

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        popupWindow = new PopupWindow(inflatedView, point.x, point.y, true);

        if(!imagePath.equals("")){
            Bitmap bitmap = ImageUtility.loadBitmapFromPath(imagePath);
            imgInputUpload.setImageBitmap(bitmap);
            ivPicture.setVisibility(View.GONE);
            imgBackground.setVisibility(View.GONE);
            lyDeleteImage.setVisibility(View.VISIBLE);
            imgPdfAttach.setVisibility(View.GONE);
            imgPath = imagePath;
        } else if (!filePdfPath.equals("")) {
            ivPicture.setVisibility(View.GONE);
            imgBackground.setVisibility(View.GONE);
            lyDeleteImage.setVisibility(View.VISIBLE);
            imgPdfAttach.setVisibility(View.VISIBLE);
            pdfPath = filePdfPath;
        }

        if (isUpdate == 1) {
            inflatedView.findViewById(R.id.lyRadioGrup).setVisibility(View.GONE);
        }

        if(promiseDate != null && !promiseDate.equals("")){
            lyParentPromiseDate.setVisibility(View.VISIBLE);
            lyParentImage.setVisibility(View.GONE);
            txtPromiseDate.setText(promiseDate);
            rbMenunggu.setChecked(true);
            datas.put("promiseDate",promiseDate);
        }

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pdfPath = "";
                imgPath = "";
                saveDataListener.isSaved(false, data);
                popupWindow.dismiss();
            }
        });

        btnSaveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rbTersedia.isChecked() && (!imgPath.equals("") || !pdfPath.equals(""))){
                    imgPath = "";
                    pdfPath = "";
                    datas.put("promiseDate","");
                    datas.put("is_saved", "1");
                    datas.put("isPDF", "0");

                    popupWindow.dismiss();
                    saveDataListener.isSaved(true, datas);
                } else if (rbMenunggu.isChecked() && !txtPromiseDate.getText().toString().equals("")) {
                    popupWindow.dismiss();
                    imgPath = "";
                    pdfPath = "";
                    datas.put("is_saved", "1");
                    datas.put("isPDF", "0");
                    datas.clear();
                    saveDataListener.isSaved(false, datas);
                    datas.put("promiseDate",txtPromiseDate.getText().toString());
                    saveDataListener.isSaved(true, datas);
                } else {
                    String message = isUpdate != 1 ? "Foto atau File PDF atau promised date tidak boleh kosong." : "Foto atau File PDF tidak boleh kosong.";
                    Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                }
            }
        });

        ivPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas.put("isPDF", "0");
                openCameraOrGallery(activity, popupWindow, datas, customerId, fileName, UploadDocumentItem.DocumentType.BuktiKepemilikanRumah, CameraUtility.REQUEST_IMAGE_CAPTURE_9, saveDataListener);
            }
        });

        imgInputUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas.put("isPDF", "0");
                openCameraOrGallery(activity, popupWindow, datas, customerId, fileName, UploadDocumentItem.DocumentType.BuktiKepemilikanRumah, CameraUtility.REQUEST_IMAGE_CAPTURE_9, saveDataListener);
            }
        });

        rbTersedia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    lyParentPromiseDate.setVisibility(View.GONE);
                    lyParentImage.setVisibility(View.VISIBLE);
                }
            }
        });

        rbMenunggu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    lyParentPromiseDate.setVisibility(View.VISIBLE);
                    lyParentImage.setVisibility(View.GONE);
                }
            }
        });

        ivDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((UploadDocumentActivity)activity).showDatePicker();
                Calendar calendar = Calendar.getInstance();
                MonthAdapter.CalendarDay currDate = new MonthAdapter.CalendarDay();
                currDate.setDay(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                MonthAdapter.CalendarDay maxDate = new MonthAdapter.CalendarDay();
                maxDate.setDay(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)+maxPromisedDate);
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                            @Override
                            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                                int month = monthOfYear+1;
                                String date = dayOfMonth+"/"+month+"/"+year;
                                txtPromiseDate.setText(date);
                                datas.put("promiseDate",date);
                            }
                        })
                        .setFirstDayOfWeek(Calendar.SUNDAY)
                        .setPreselectedDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                        .setDateRange(currDate, maxDate)
                        .setDoneText("Yes")
                        .setCancelText("No");
                cdp.show(((UploadDocumentActivity) activity).getSupportFragmentManager(),"Promise Date");
            }
        });

        lyDeleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lyDeleteImage.setVisibility(View.GONE);
                ivPicture.setVisibility(View.VISIBLE);
                imgBackground.setVisibility(View.VISIBLE);
                imgPdfAttach.setVisibility(View.GONE);
                imgInputUpload.setImageBitmap(null);
                imgPath = "";
                pdfPath = "";
            }
        });

        popupWindow.showAtLocation(view, Gravity.CENTER, point.x, point.y);
        return popupWindow;
    }

    public static PopupWindow inputKontrakKerja(final Activity activity, View view, final SaveDataListener saveDataListener, final String imagePath,final String filePdfPath,
                                                final String customerId, final String fileName, final String promiseDate, final HashMap<String, String> data) {
        final PopupWindow popupWindow;
        final HashMap<String, String> datas = new HashMap<>();
        checkIsUpdate(activity);

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout inflatedView = (LinearLayout) inflater.inflate(R.layout.z_popup_kontrakkerja, null, false);

        ImageView imgClose = inflatedView.findViewById(R.id.imgClose);
        Button btnSaveUpdate = inflatedView.findViewById(R.id.btnSaveUpdate);
        final ImageView ivPicture = inflatedView.findViewById(R.id.ivPicture);
        final ImageView imgInputUpload = inflatedView.findViewById(R.id.imgInputUpload);
        final ImageView imgBackground = inflatedView.findViewById(R.id.imgBackgroud);

        final RadioButton rbTersedia = inflatedView.findViewById(R.id.rbTersedia);
        final RadioButton rbMenunggu = inflatedView.findViewById(R.id.rbMenyusul);
        ImageView ivDate = inflatedView.findViewById(R.id.ivDate);
        RelativeLayout lyPromiseDate = inflatedView.findViewById(R.id.lyPromiseDate);
        final RelativeLayout lyParentPromiseDate = inflatedView.findViewById(R.id.lyParentPromiseDate);
        final RelativeLayout lyParentImage = inflatedView.findViewById(R.id.lyParentImage);
        final TextView txtPromiseDate = inflatedView.findViewById(R.id.txtPromiseDate);
        final RelativeLayout lyDeleteImage = inflatedView.findViewById(R.id.lyDeleteImage);
        final RelativeLayout imgPdfAttach = inflatedView.findViewById(R.id.imgPdfAttach);

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        popupWindow = new PopupWindow(inflatedView, point.x, point.y, true);

        if(!imagePath.equals("")){
            Bitmap bitmap = ImageUtility.loadBitmapFromPath(imagePath);
            imgInputUpload.setImageBitmap(bitmap);
            ivPicture.setVisibility(View.GONE);
            imgBackground.setVisibility(View.GONE);
            lyDeleteImage.setVisibility(View.VISIBLE);
            imgPdfAttach.setVisibility(View.GONE);
            imgPath = imagePath;
        } else if (!filePdfPath.equals("")) {
            ivPicture.setVisibility(View.GONE);
            imgBackground.setVisibility(View.GONE);
            lyDeleteImage.setVisibility(View.VISIBLE);
            imgPdfAttach.setVisibility(View.VISIBLE);
            pdfPath = filePdfPath;
        }

        if (isUpdate == 1) {
            inflatedView.findViewById(R.id.lyRadioGrup).setVisibility(View.GONE);
        }

        if(promiseDate != null && !promiseDate.equals("")){
            lyParentPromiseDate.setVisibility(View.VISIBLE);
            lyParentImage.setVisibility(View.GONE);
            txtPromiseDate.setText(promiseDate);
            rbMenunggu.setChecked(true);
            datas.put("promiseDate",promiseDate);
        }

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pdfPath = "";
                imgPath = "";
                saveDataListener.isSaved(false, data);
                popupWindow.dismiss();
            }
        });

        btnSaveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rbTersedia.isChecked() && (!imgPath.equals("") || !pdfPath.equals(""))){
                    imgPath = "";
                    pdfPath = "";
                    datas.put("promiseDate","");
                    datas.put("is_saved", "1");
                    datas.put("isPDF", "0");

                    popupWindow.dismiss();
                    saveDataListener.isSaved(true, datas);
                } else if (rbMenunggu.isChecked() && !txtPromiseDate.getText().toString().equals("")) {
                    popupWindow.dismiss();
                    imgPath = "";
                    pdfPath = "";
                    datas.put("is_saved", "1");
                    datas.put("isPDF", "0");
                    datas.clear();
                    saveDataListener.isSaved(false, datas);
                    datas.put("promiseDate",txtPromiseDate.getText().toString());
                    saveDataListener.isSaved(true, datas);
                } else {
                    String message = isUpdate != 1 ? "Foto atau File PDF atau promised date tidak boleh kosong." : "Foto atau File PDF tidak boleh kosong.";
                    Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                }
            }
        });

        ivPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas.put("isPDF", "0");
                openCameraOrGallery(activity, popupWindow, datas, customerId, fileName, UploadDocumentItem.DocumentType.KontrakKerja, CameraUtility.REQUEST_IMAGE_CAPTURE_10, saveDataListener);
            }
        });

        imgInputUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas.put("isPDF", "0");
                openCameraOrGallery(activity, popupWindow, datas, customerId, fileName, UploadDocumentItem.DocumentType.KontrakKerja, CameraUtility.REQUEST_IMAGE_CAPTURE_10, saveDataListener);
            }
        });

        rbTersedia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    lyParentPromiseDate.setVisibility(View.GONE);
                    lyParentImage.setVisibility(View.VISIBLE);
                }
            }
        });

        rbMenunggu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    lyParentPromiseDate.setVisibility(View.VISIBLE);
                    lyParentImage.setVisibility(View.GONE);
                }
            }
        });

        ivDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((UploadDocumentActivity)activity).showDatePicker();
                Calendar calendar = Calendar.getInstance();
                MonthAdapter.CalendarDay currDate = new MonthAdapter.CalendarDay();
                currDate.setDay(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                MonthAdapter.CalendarDay maxDate = new MonthAdapter.CalendarDay();
                maxDate.setDay(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)+maxPromisedDate);
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                            @Override
                            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                                int month = monthOfYear+1;
                                String date = dayOfMonth+"/"+month+"/"+year;
                                txtPromiseDate.setText(date);
                                datas.put("promiseDate",date);
                            }
                        })
                        .setFirstDayOfWeek(Calendar.SUNDAY)
                        .setPreselectedDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                        .setDateRange(currDate, maxDate)
                        .setDoneText("Yes")
                        .setCancelText("No");
                cdp.show(((UploadDocumentActivity) activity).getSupportFragmentManager(),"Promise Date");
            }
        });

        lyDeleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lyDeleteImage.setVisibility(View.GONE);
                ivPicture.setVisibility(View.VISIBLE);
                imgBackground.setVisibility(View.VISIBLE);
                imgPdfAttach.setVisibility(View.GONE);
                imgInputUpload.setImageBitmap(null);
                imgPath = "";
                pdfPath = "";
            }
        });

        popupWindow.showAtLocation(view, Gravity.CENTER, point.x, point.y);
        return popupWindow;
    }

    public static PopupWindow inputKeteranganDomisili(final Activity activity, View view, final SaveDataListener saveDataListener, final String imagePath,final String filePdfPath,
                                                      final String customerId, final String fileName, final String promiseDate, final HashMap<String, String> data) {
        final PopupWindow popupWindow;
        final HashMap<String, String> datas = new HashMap<>();
        checkIsUpdate(activity);

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout inflatedView = (LinearLayout) inflater.inflate(R.layout.z_popup_keterangandomisili, null, false);

        ImageView imgClose = inflatedView.findViewById(R.id.imgClose);
        Button btnSaveUpdate = inflatedView.findViewById(R.id.btnSaveUpdate);
        final ImageView ivPicture = inflatedView.findViewById(R.id.ivPicture);
        final ImageView imgInputUpload = inflatedView.findViewById(R.id.imgInputUpload);
        final ImageView imgBackground = inflatedView.findViewById(R.id.imgBackgroud);

        final RadioButton rbTersedia = inflatedView.findViewById(R.id.rbTersedia);
        final RadioButton rbMenunggu = inflatedView.findViewById(R.id.rbMenyusul);
        ImageView ivDate = inflatedView.findViewById(R.id.ivDate);
        RelativeLayout lyPromiseDate = inflatedView.findViewById(R.id.lyPromiseDate);
        final RelativeLayout lyParentPromiseDate = inflatedView.findViewById(R.id.lyParentPromiseDate);
        final RelativeLayout lyParentImage = inflatedView.findViewById(R.id.lyParentImage);
        final TextView txtPromiseDate = inflatedView.findViewById(R.id.txtPromiseDate);
        final RelativeLayout lyDeleteImage = inflatedView.findViewById(R.id.lyDeleteImage);
        final RelativeLayout imgPdfAttach = inflatedView.findViewById(R.id.imgPdfAttach);

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        popupWindow = new PopupWindow(inflatedView, point.x, point.y, true);

        if(!imagePath.equals("")){
            Bitmap bitmap = ImageUtility.loadBitmapFromPath(imagePath);
            imgInputUpload.setImageBitmap(bitmap);
            ivPicture.setVisibility(View.GONE);
            imgBackground.setVisibility(View.GONE);
            lyDeleteImage.setVisibility(View.VISIBLE);
            imgPdfAttach.setVisibility(View.GONE);
            imgPath = imagePath;
        } else if (!filePdfPath.equals("")) {
            ivPicture.setVisibility(View.GONE);
            imgBackground.setVisibility(View.GONE);
            lyDeleteImage.setVisibility(View.VISIBLE);
            imgPdfAttach.setVisibility(View.VISIBLE);
            pdfPath = filePdfPath;
        }

        if (isUpdate == 1) {
            inflatedView.findViewById(R.id.lyRadioGrup).setVisibility(View.GONE);
        }

        if(promiseDate != null && !promiseDate.equals("")){
            lyParentPromiseDate.setVisibility(View.VISIBLE);
            lyParentImage.setVisibility(View.GONE);
            txtPromiseDate.setText(promiseDate);
            rbMenunggu.setChecked(true);
            datas.put("promiseDate",promiseDate);
        }

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pdfPath = "";
                imgPath = "";
                saveDataListener.isSaved(false, data);
                popupWindow.dismiss();
            }
        });

        btnSaveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rbTersedia.isChecked() && (!imgPath.equals("") || !pdfPath.equals(""))){
                    imgPath = "";
                    pdfPath = "";
                    datas.put("promiseDate","");
                    datas.put("is_saved", "1");
                    datas.put("isPDF", "0");

                    popupWindow.dismiss();
                    saveDataListener.isSaved(true, datas);
                } else if (rbMenunggu.isChecked() && !txtPromiseDate.getText().toString().equals("")) {
                    popupWindow.dismiss();
                    imgPath = "";
                    pdfPath = "";
                    datas.put("is_saved", "1");
                    datas.put("isPDF", "0");
                    datas.clear();
                    saveDataListener.isSaved(false, datas);
                    datas.put("promiseDate",txtPromiseDate.getText().toString());
                    saveDataListener.isSaved(true, datas);
                } else {
                    String message = isUpdate != 1 ? "Foto atau File PDF atau promised date tidak boleh kosong." : "Foto atau File PDF tidak boleh kosong.";
                    Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                }
            }
        });

        ivPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas.put("isPDF", "0");
                openCameraOrGallery(activity, popupWindow, datas, customerId, fileName, UploadDocumentItem.DocumentType.KeteranganDomisili, CameraUtility.REQUEST_IMAGE_CAPTURE_11, saveDataListener);
            }
        });

        imgInputUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas.put("isPDF", "0");
                openCameraOrGallery(activity, popupWindow, datas, customerId, fileName, UploadDocumentItem.DocumentType.KeteranganDomisili, CameraUtility.REQUEST_IMAGE_CAPTURE_11, saveDataListener);
            }
        });

        rbTersedia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    lyParentPromiseDate.setVisibility(View.GONE);
                    lyParentImage.setVisibility(View.VISIBLE);
                }
            }
        });

        rbMenunggu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    lyParentPromiseDate.setVisibility(View.VISIBLE);
                    lyParentImage.setVisibility(View.GONE);
                }
            }
        });

        ivDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((UploadDocumentActivity)activity).showDatePicker();
                Calendar calendar = Calendar.getInstance();
                MonthAdapter.CalendarDay currDate = new MonthAdapter.CalendarDay();
                currDate.setDay(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                MonthAdapter.CalendarDay maxDate = new MonthAdapter.CalendarDay();
                maxDate.setDay(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)+maxPromisedDate);
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                            @Override
                            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                                int month = monthOfYear+1;
                                String date = dayOfMonth+"/"+month+"/"+year;
                                txtPromiseDate.setText(date);
                                datas.put("promiseDate",date);
                            }
                        })
                        .setFirstDayOfWeek(Calendar.SUNDAY)
                        .setPreselectedDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                        .setDateRange(currDate, maxDate)
                        .setDoneText("Yes")
                        .setCancelText("No");
                cdp.show(((UploadDocumentActivity) activity).getSupportFragmentManager(),"Promise Date");
            }
        });

        lyDeleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lyDeleteImage.setVisibility(View.GONE);
                ivPicture.setVisibility(View.VISIBLE);
                imgBackground.setVisibility(View.VISIBLE);
                imgPdfAttach.setVisibility(View.GONE);
                imgInputUpload.setImageBitmap(null);
                imgPath = "";
                pdfPath = "";
            }
        });

        popupWindow.showAtLocation(view, Gravity.CENTER, point.x, point.y);
        return popupWindow;
    }

    public static PopupWindow inputBukuNikah(final Activity activity, View view, final SaveDataListener saveDataListener, final String imagePath,final String filePdfPath,
                                             final String customerId, final String fileName, final String promiseDate, final HashMap<String, String> data) {
        final PopupWindow popupWindow;
        final HashMap<String, String> datas = new HashMap<>();
        checkIsUpdate(activity);

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout inflatedView = (LinearLayout) inflater.inflate(R.layout.z_popup_bukunikah, null, false);

        ImageView imgClose = inflatedView.findViewById(R.id.imgClose);
        Button btnSaveUpdate = inflatedView.findViewById(R.id.btnSaveUpdate);
        final ImageView ivPicture = inflatedView.findViewById(R.id.ivPicture);
        final ImageView imgInputUpload = inflatedView.findViewById(R.id.imgInputUpload);
        final ImageView imgBackground = inflatedView.findViewById(R.id.imgBackgroud);

        final RadioButton rbTersedia = inflatedView.findViewById(R.id.rbTersedia);
        final RadioButton rbMenunggu = inflatedView.findViewById(R.id.rbMenyusul);
        ImageView ivDate = inflatedView.findViewById(R.id.ivDate);
        RelativeLayout lyPromiseDate = inflatedView.findViewById(R.id.lyPromiseDate);
        final RelativeLayout lyParentPromiseDate = inflatedView.findViewById(R.id.lyParentPromiseDate);
        final RelativeLayout lyParentImage = inflatedView.findViewById(R.id.lyParentImage);
        final TextView txtPromiseDate = inflatedView.findViewById(R.id.txtPromiseDate);
        final RelativeLayout lyDeleteImage = inflatedView.findViewById(R.id.lyDeleteImage);
        final RelativeLayout imgPdfAttach = inflatedView.findViewById(R.id.imgPdfAttach);

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        popupWindow = new PopupWindow(inflatedView, point.x, point.y, true);

        if(!imagePath.equals("")){
            Bitmap bitmap = ImageUtility.loadBitmapFromPath(imagePath);
            imgInputUpload.setImageBitmap(bitmap);
            ivPicture.setVisibility(View.GONE);
            imgBackground.setVisibility(View.GONE);
            lyDeleteImage.setVisibility(View.VISIBLE);
            imgPdfAttach.setVisibility(View.GONE);
            imgPath = imagePath;
        } else if (!filePdfPath.equals("")) {
            ivPicture.setVisibility(View.GONE);
            imgBackground.setVisibility(View.GONE);
            lyDeleteImage.setVisibility(View.VISIBLE);
            imgPdfAttach.setVisibility(View.VISIBLE);
            pdfPath = filePdfPath;
        }

        if (isUpdate == 1) {
            inflatedView.findViewById(R.id.lyRadioGrup).setVisibility(View.GONE);
        }

        if(promiseDate != null && !promiseDate.equals("")){
            lyParentPromiseDate.setVisibility(View.VISIBLE);
            lyParentImage.setVisibility(View.GONE);
            txtPromiseDate.setText(promiseDate);
            rbMenunggu.setChecked(true);
            datas.put("promiseDate",promiseDate);
        }

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pdfPath = "";
                imgPath = "";
                saveDataListener.isSaved(false, data);
                popupWindow.dismiss();
            }
        });

        btnSaveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rbTersedia.isChecked() && (!imgPath.equals("") || !pdfPath.equals(""))){
                    imgPath = "";
                    pdfPath = "";
                    datas.put("promiseDate","");
                    datas.put("is_saved", "1");
                    datas.put("isPDF", "0");

                    popupWindow.dismiss();
                    saveDataListener.isSaved(true, datas);
                } else if (rbMenunggu.isChecked() && !txtPromiseDate.getText().toString().equals("")) {
                    popupWindow.dismiss();
                    imgPath = "";
                    pdfPath = "";
                    datas.put("is_saved", "1");
                    datas.put("isPDF", "0");
                    datas.clear();
                    saveDataListener.isSaved(false, datas);
                    datas.put("promiseDate",txtPromiseDate.getText().toString());
                    saveDataListener.isSaved(true, datas);
                } else {
                    String message = isUpdate != 1 ? "Foto atau File PDF atau promised date tidak boleh kosong." : "Foto atau File PDF tidak boleh kosong.";
                    Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                }
            }
        });

        ivPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas.put("isPDF", "0");
                openCameraOrGallery(activity, popupWindow, datas, customerId, fileName, UploadDocumentItem.DocumentType.BukuNikah, CameraUtility.REQUEST_IMAGE_CAPTURE_12, saveDataListener);
            }
        });

        imgInputUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas.put("isPDF", "0");
                openCameraOrGallery(activity, popupWindow, datas, customerId, fileName, UploadDocumentItem.DocumentType.BukuNikah, CameraUtility.REQUEST_IMAGE_CAPTURE_12, saveDataListener);
            }
        });

        rbTersedia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    lyParentPromiseDate.setVisibility(View.GONE);
                    lyParentImage.setVisibility(View.VISIBLE);
                }
            }
        });

        rbMenunggu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    lyParentPromiseDate.setVisibility(View.VISIBLE);
                    lyParentImage.setVisibility(View.GONE);
                }
            }
        });

        ivDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((UploadDocumentActivity)activity).showDatePicker();
                Calendar calendar = Calendar.getInstance();
                MonthAdapter.CalendarDay currDate = new MonthAdapter.CalendarDay();
                currDate.setDay(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                MonthAdapter.CalendarDay maxDate = new MonthAdapter.CalendarDay();
                maxDate.setDay(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)+maxPromisedDate);
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                            @Override
                            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                                int month = monthOfYear+1;
                                String date = dayOfMonth+"/"+month+"/"+year;
                                txtPromiseDate.setText(date);
                                datas.put("promiseDate",date);
                            }
                        })
                        .setFirstDayOfWeek(Calendar.SUNDAY)
                        .setPreselectedDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                        .setDateRange(currDate, maxDate)
                        .setDoneText("Yes")
                        .setCancelText("No");
                cdp.show(((UploadDocumentActivity) activity).getSupportFragmentManager(),"Promise Date");
            }
        });

        lyDeleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lyDeleteImage.setVisibility(View.GONE);
                ivPicture.setVisibility(View.VISIBLE);
                imgBackground.setVisibility(View.VISIBLE);
                imgPdfAttach.setVisibility(View.GONE);
                imgInputUpload.setImageBitmap(null);
                imgPath = "";
                pdfPath = "";
            }
        });

        popupWindow.showAtLocation(view, Gravity.CENTER, point.x, point.y);
        return popupWindow;
    }

    public static PopupWindow inputKtpPasangan(final Activity activity, View view, final SaveDataListener saveDataListener, final String imagePath,final String filePdfPath,
                                               final String customerId, final String fileName, OCRResult ocrResult, final HashMap<String,String> data) {
        final PopupWindow popupWindow;
        final HashMap<String, String> datas = new HashMap<>();
        checkIsUpdate(activity);

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout inflatedView = (LinearLayout) inflater.inflate(R.layout.z_popup_ktppasangan, null, false);

        ImageView imgClose = inflatedView.findViewById(R.id.imgClose);
        Button btnSaveUpdate = inflatedView.findViewById(R.id.btnSaveUpdate);
        final ImageView ivPicture = inflatedView.findViewById(R.id.ivPicture);
        final ImageView imgInputUpload = inflatedView.findViewById(R.id.imgInputUpload);
        final ImageView imgBackground = inflatedView.findViewById(R.id.imgBackgroud);
        final Spinner spinStatusPerkawinan = inflatedView.findViewById(R.id.spinStatusPerkawinan);
        final EditText edNik = inflatedView.findViewById(R.id.edNik);
        final EditText edNama = inflatedView.findViewById(R.id.edNama);
        final EditText edTempatLahir = inflatedView.findViewById(R.id.edTempatLahir);
        final EditText edTanggaLahir = inflatedView.findViewById(R.id.edTanggaLahir);
        final Spinner spinJenisKelamin = inflatedView.findViewById(R.id.spinJenisKelamin);
        final EditText edAlamat = inflatedView.findViewById(R.id.edAlamat);
        final EditText edRT = inflatedView.findViewById(R.id.edRT);
        final EditText edKelurahan = inflatedView.findViewById(R.id.edKelurahan);
        final EditText edKecamatan = inflatedView.findViewById(R.id.edKecamatan);
        final Spinner spinAgama = inflatedView.findViewById(R.id.spinAgama);
        final EditText edPekerjaan = inflatedView.findViewById(R.id.edPekerjaan);
        final EditText edKewarganegaraan = inflatedView.findViewById(R.id.edKewarganegaraan);
        final EditText edBerlakuHingga = inflatedView.findViewById(R.id.edBerlakuHingga);
        final EditText edRW = inflatedView.findViewById(R.id.edRW);
        final EditText edKota = inflatedView.findViewById(R.id.edKota);
        final EditText edZipcode = inflatedView.findViewById(R.id.edZipCode);
        final CardView cardView = inflatedView.findViewById(R.id.cardView);

        final RadioButton rbTersedia = inflatedView.findViewById(R.id.rbTersedia);
        final RadioButton rbMenunggu = inflatedView.findViewById(R.id.rbMenyusul);
        ImageView ivDate = inflatedView.findViewById(R.id.ivDate);
        RelativeLayout lyPromiseDate = inflatedView.findViewById(R.id.lyPromiseDate);
        final RelativeLayout lyParentPromiseDate = inflatedView.findViewById(R.id.lyParentPromiseDate);
        final RelativeLayout lyParentImage = inflatedView.findViewById(R.id.lyParentImage);
        final TextView txtPromiseDate = inflatedView.findViewById(R.id.txtPromiseDate);
        final LinearLayout lyInput = inflatedView.findViewById(R.id.lyInput);
        final TextView tvBirthDate = inflatedView.findViewById(R.id.txtBirthDate);
        final TextView txtBerlaku = inflatedView.findViewById(R.id.txtBerlaku);
        final CheckBox cbSeumurHidup = inflatedView.findViewById(R.id.cbSeumurHidup);
        final RelativeLayout lyDeleteImage = inflatedView.findViewById(R.id.lyDeleteImage);
        final RelativeLayout imgPdfAttach = inflatedView.findViewById(R.id.imgPdfAttach);

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        popupWindow = new PopupWindow(inflatedView, point.x, point.y, true);

        if(data != null){
            if(data.get("promiseDate") != null && !data.get("promiseDate").equals("")){
                lyParentPromiseDate.setVisibility(View.VISIBLE);
                lyParentImage.setVisibility(View.GONE);
                lyInput.setVisibility(View.GONE);
                txtPromiseDate.setText(data.get("promiseDate"));
                //edNoNPWP.setText(data.get("npwp"));
                rbMenunggu.setChecked(true);
            }
        }

        if (isUpdate == 1) {
            inflatedView.findViewById(R.id.lyRadioGrup).setVisibility(View.GONE);
            inflatedView.findViewById(R.id.lyInput).setVisibility(View.GONE);
        }

        setSpinnerJekel(activity, spinJenisKelamin);
        setSpinnerData(activity, spinAgama, 1);
        setSpinnerData(activity, spinStatusPerkawinan, 3);

        try {
            if(data != null){
                edNik.setText(data.get("nik"));
                edNama.setText(data.get("nama"));
                edTempatLahir.setText(data.get("tempatLahir"));
                edAlamat.setText(data.get("alamat"));
                edRT.setText(data.get("rt"));
                edRW.setText(data.get("rw"));
                edKelurahan.setText(data.get("kelurahan"));
                edKecamatan.setText(data.get("kecamatan"));
                edKota.setText(data.get("kota"));
                edZipcode.setText(data.get("zipCode"));
                edPekerjaan.setText(data.get("pekerjaan"));

                /*set tanggal lahir*/
                String birthDate = data.get("tanggalLahir");
                if(birthDate != null){
                    String[] brthdate = birthDate.split(" ");
                    if(brthdate != null){
                        String[] birth = brthdate[0].split("-");
                        tvBirthDate.setText(birth[2]+"/"+birth[1]+"/"+birth[0]);
                    }
                }

                /*set jenis kelamin*/
                if(data.get("jenisKelamin").toUpperCase().contains("LAKI")){
                    spinJenisKelamin.setSelection(1);
                }else if (data.get("jenisKelamin").toUpperCase().contains("PEREMPUAN")) {
                    spinJenisKelamin.setSelection(2);
                }else{
                    spinJenisKelamin.setSelection(0);
                }

                /*agama*/
                String agamaStr = data.get("agama").toUpperCase();

                /*status perkawinan*/
//                String statusPerkawinanStr = data.get("statusPerkawinan");

                setSelectionReligion(agamaStr, spinAgama);
//                setSelectionMaritalStatus(statusPerkawinanStr, spinStatusPerkawinan);

                /*pekerjaan*/
                edPekerjaan.setText(data.get("pekerjaan"));

                /*berlaku hingga*/
                String berlaku = TextUtility.changeDateFormat("yyyy-MM-dd HH:mm:ss.SSS", "dd/MM/yyyy", data.get("berlakuHingga"));
                if(berlaku != null && !berlaku.equals("null") && !berlaku.equals("")){
                    if(berlaku.equals("31/12/2099")){
                        cbSeumurHidup.setChecked(true);
                        txtBerlaku.setText("31/12/2099");
                    }else{
                        cbSeumurHidup.setChecked(false);
                        txtBerlaku.setText(berlaku);
                    }
                }
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

                    tvBirthDate.setText(TextUtility.changeDateFormat("dd-MM-yyyy", "dd/MM/yyyy", (arrTtl[1]).replace(" ", "")));
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

            String agama = ocrResult.getAgama();
            if(agama != null && !agama.equals("")){
                agama = agama.toUpperCase();
            }
            //String agama = ocrResult.getAgama().toUpperCase();
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
//            String statusKawin = ocrResult.getStatusPerkawinan();
//            if (!statusKawin.contains("BELUM") && statusKawin.contains("KAWIN")) {
//                setSelectionMaritalStatus("MAR", spinStatusPerkawinan);
//            } else {
//                setSelectionMaritalStatus("MAR", spinStatusPerkawinan);
//            }

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

            if (data.get("is_camera") != null) {
                if (data.get("is_camera").equalsIgnoreCase("1")) {
                    final ProgressDialog progressDialog = new ProgressDialog(activity);
                    progressDialog.setMessage("Processing OCR...");
                    progressDialog.show();

                    Base64Image.with(activity)
                            .encode(bitmap)
                            .into(new RequestEncode.Encode() {
                                @Override
                                public void onSuccess(String s) {
                                    call = OCRPresenter.requestOCR(activity, s, new OCRCallback() {
                                        @Override
                                        public void onSuccessOCR(OCRResult result) {
                                            progressDialog.dismiss();
                                            edNik.setText(result.getNIK());
                                            edNama.setText(result.getNama());
                                            String ttl = result.getTempatTanggalLahir();
                                            if (ttl != null) {
                                                String[] arrTtl = ttl.split(",");
                                                if (arrTtl.length > 1) {
                                                    edTempatLahir.setText(arrTtl[0]);
                                                    tvBirthDate.setText(TextUtility.changeDateFormat("dd-MM-yyyy", "dd/MM/yyyy", (arrTtl[1]).replace(" ", "")));
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
                                            txtBerlaku.setText(result.getBerlakuHingga());

                                            String agama = result.getAgama().toUpperCase();
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
//                                            if (!statusKawin.contains("BELUM") && statusKawin.contains("KAWIN")) {
//                                                setSelectionMaritalStatus("MAR", spinStatusPerkawinan);
//                                            } else {
//                                                setSelectionMaritalStatus("MAR", spinStatusPerkawinan);
//                                            }

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
                                            progressDialog.dismiss();
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
                                                                call.cancel();
                                                                progressDialog.dismiss();
                                                                //Toast.makeText(CameraActivity.this, "OCR Processing Failed. Please check your connection", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                    }
                                                },
                                                30000
                                        );
                                    }catch (Exception e){
                                        LogUtility.logging(TAG,LogUtility.warningLog,"inputKtpPasangan","timer.schedule",e.getMessage());
                                    }
                                }

                                @Override
                                public void onFailure() {
                                    progressDialog.dismiss();
                                }
                            });
                }
            }
        }

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgPath = "";
                pdfPath = "";
                saveDataListener.isSaved(false, data);
                popupWindow.dismiss();
            }
        });

        final String img = imagePath;

        btnSaveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rbTersedia.isChecked() && !img.equals("")){
                    datas.put("promiseDate","");
                    if (!imgPath.isEmpty()) {
                        if (isUpdate != 1) {
                            if (!isEmpty(edNik.getText().toString()) && !isEmpty(edNama.getText().toString()) && !isEmpty(edTempatLahir.getText().toString())
                                    && !spinJenisKelamin.getSelectedItem().toString().contains("- Select") && !isEmpty(edAlamat.getText().toString()) && !isEmpty(edRT.getText().toString())
                                    && !isEmpty(edRW.getText().toString()) && !isEmpty(edKelurahan.getText().toString()) && !isEmpty(edKecamatan.getText().toString())
                                    && !spinAgama.getSelectedItem().toString().contains("- Select") && !spinStatusPerkawinan.getSelectedItem().toString().contains("- Select") && !isEmpty(edPekerjaan.getText().toString())
                                    && !isEmpty(txtBerlaku.getText().toString()) /*&& !isEmpty(edZipcode.getText().toString()) && edZipcode.getText().toString().length() == 5*/ && !isEmpty(edKota.getText().toString())
                                    && !isEmpty(tvBirthDate.getText().toString())) {
                                datas.put("nik", edNik.getText().toString());
                                datas.put("nama", edNama.getText().toString());
                                datas.put("tempatLahir", edTempatLahir.getText().toString());
                                datas.put("jenisKelamin", spinJenisKelamin.getSelectedItem().toString());
                                datas.put("alamat", edAlamat.getText().toString());
                                datas.put("rt", edRT.getText().toString());
                                datas.put("rw", edRW.getText().toString());
                                datas.put("kelurahan", edKelurahan.getText().toString());
                                datas.put("kecamatan", edKecamatan.getText().toString());
                                datas.put("agama", ((Religion)spinAgama.getSelectedItem()).getCode());
                                datas.put("statusPerkawinan", ((MaritalStatus)spinStatusPerkawinan.getSelectedItem()).getCode());
                                datas.put("pekerjaan", edPekerjaan.getText().toString());
                                datas.put("berlakuHingga",TextUtility.changeDateFormat("dd/MM/yyyy", "yyyy-MM-dd", txtBerlaku.getText().toString())+" 00:00:00.000");
                                datas.put("tanggalLahir", TextUtility.changeDateFormat("dd/MM/yyyy", "yyyy-MM-dd", tvBirthDate.getText().toString())+" 00:00:00.000");
                                datas.put("zipCode", ""/*edZipcode.getText().toString()*/);
                                datas.put("kota", edKota.getText().toString());
                                datas.put("is_saved", "1");

                                imgPath = "";
                                popupWindow.dismiss();
                                saveDataListener.isSaved(true, datas);
                            } else if (isEmpty(edNik.getText().toString())) {
                                Toast.makeText(activity, "NIK tidak boleh kosong", Toast.LENGTH_SHORT).show();
                            } else if (isEmpty(edNama.getText().toString())) {
                                Toast.makeText(activity, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show();
                            } else if (isEmpty(edTempatLahir.getText().toString())) {
                                Toast.makeText(activity, "Tempat Lahir tidak boleh kosong", Toast.LENGTH_SHORT).show();
                            } else if (spinJenisKelamin.getSelectedItem().toString().contains("- Select")) {
                                Toast.makeText(activity, "Jenis Kelamin tidak boleh kosong", Toast.LENGTH_SHORT).show();
                            } else if (isEmpty(edAlamat.getText().toString())) {
                                Toast.makeText(activity, "Alamat tidak boleh kosong", Toast.LENGTH_SHORT).show();
                            } else if (isEmpty(edRT.getText().toString())) {
                                Toast.makeText(activity, "RT tidak boleh kosong", Toast.LENGTH_SHORT).show();
                            } else if (isEmpty(edRW.getText().toString())) {
                                Toast.makeText(activity, "RW tidak boleh kosong", Toast.LENGTH_SHORT).show();
                            } else if (isEmpty(edKelurahan.getText().toString())) {
                                Toast.makeText(activity, "Kelurahan tidak boleh kosong", Toast.LENGTH_SHORT).show();
                            } else if (isEmpty(edKecamatan.getText().toString())) {
                                Toast.makeText(activity, "Kecamatan tidak boleh kosong", Toast.LENGTH_SHORT).show();
                            } else if (spinAgama.getSelectedItem().toString().contains("- Select")) {
                                Toast.makeText(activity, "Agama tidak boleh kosong", Toast.LENGTH_SHORT).show();
                            } else if (spinStatusPerkawinan.getSelectedItem().toString().contains("- Select")) {
                                Toast.makeText(activity, "Status Perkawinan tidak boleh kosong", Toast.LENGTH_SHORT).show();
                            } else if (isEmpty(edPekerjaan.getText().toString())) {
                                Toast.makeText(activity, "Pekerjaan tidak boleh kosong", Toast.LENGTH_SHORT).show();
                            } else if (isEmpty(txtBerlaku.getText().toString())) {
                                Toast.makeText(activity, "Berlaku Hingga tidak boleh kosong", Toast.LENGTH_SHORT).show();
                            } /*else if (isEmpty(edZipcode.getText().toString())) {
                                Toast.makeText(activity, "Kode Pos tidak boleh kosong", Toast.LENGTH_SHORT).show();
                            } else if (edZipcode.getText().toString().length() != 5) {
                                Toast.makeText(activity, "Kode Pos harus 5 digit", Toast.LENGTH_SHORT).show();
                            }*/ else if (isEmpty(edKota.getText().toString())) {
                                Toast.makeText(activity, "Kota tidak boleh kosong", Toast.LENGTH_SHORT).show();
                            } else if (isEmpty(tvBirthDate.getText().toString())) {
                                Toast.makeText(activity, "Tanggal Lahir tidak boleh kosong", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            imgPath = "";
                            popupWindow.dismiss();
                            saveDataListener.isSaved(true, datas);
                        }
                    } else {
                        Toast.makeText(activity, "Foto tidak boleh kosong.", Toast.LENGTH_SHORT).show();
                    }
                } else if (rbMenunggu.isChecked() && !txtPromiseDate.getText().toString().equals("")) {
                    popupWindow.dismiss();
                    imgPath = "";
                    datas.put("is_saved", "1");
                    datas.clear();
                    saveDataListener.isSaved(false, datas);
                    datas.put("promiseDate",txtPromiseDate.getText().toString());
                    saveDataListener.isSaved(true, datas);
                } else {
                    String message = isUpdate != 1 ? "Foto atau promised date tidak boleh kosong." : "Foto tidak boleh kosong.";
                    Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                }
            }
        });

        ivPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas.put("nik",edNik.getText().toString());
                datas.put("nama", edNama.getText().toString());
                datas.put("tempatLahir",edTempatLahir.getText().toString());
                datas.put("jenisKelamin", spinJenisKelamin.getSelectedItem().toString());
                datas.put("alamat", edAlamat.getText().toString());
                datas.put("rt",edRT.getText().toString());
                datas.put("rw",edRW.getText().toString());
                datas.put("kelurahan",edKelurahan.getText().toString());
                datas.put("kecamatan",edKecamatan.getText().toString());
                datas.put("agama",spinAgama.getSelectedItem().toString());
                datas.put("statusPerkawinan", spinStatusPerkawinan.getSelectedItem().toString());
                datas.put("pekerjaan",edPekerjaan.getText().toString());
                datas.put("berlakuHingga",TextUtility.changeDateFormat("dd/MM/yyyy", "yyyy-MM-dd", txtBerlaku.getText().toString())+" 00:00:00.000");
                datas.put("tanggalLahir", TextUtility.changeDateFormat("dd/MM/yyyy", "yyyy-MM-dd", tvBirthDate.getText().toString())+" 00:00:00.000");
                datas.put("zipCode",""/*edZipcode.getText().toString()*/);
                datas.put("kota",edKota.getText().toString());
//                File file = ImageUtility.createImageFile(customerId,fileName);
//                String path = file.getAbsolutePath();
//                CameraUtility.openCamera(activity, CameraUtility.REQUEST_IMAGE_CAPTURE_13,file);
//                popupWindow.dismiss();

                Intent intent = new Intent(activity, CameraActivity.class);
                intent.putExtra("customerId",customerId);
                intent.putExtra("fileName",fileName);
                intent.putExtra("datas",datas);
                intent.putExtra("dataKTPPasangan",data);

                saveDataListener.onTakePicture(imgPath, UploadDocumentItem.DocumentType.KTPIstriSuami, datas);
                openCameraOrGalleryOCR(activity, popupWindow, intent, CameraUtility.REQUEST_IMAGE_CAPTURE_13);
            }
        });

        imgInputUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas.put("nik",edNik.getText().toString());
                datas.put("nama", edNama.getText().toString());
                datas.put("tempatLahir",edTempatLahir.getText().toString());
                datas.put("jenisKelamin", spinJenisKelamin.getSelectedItem().toString());
                datas.put("alamat", edAlamat.getText().toString());
                datas.put("rt",edRT.getText().toString());
                datas.put("rw",edRW.getText().toString());
                datas.put("kelurahan",edKelurahan.getText().toString());
                datas.put("kecamatan",edKecamatan.getText().toString());
                datas.put("agama",spinAgama.getSelectedItem().toString());
                datas.put("statusPerkawinan", spinStatusPerkawinan.getSelectedItem().toString());
                datas.put("pekerjaan",edPekerjaan.getText().toString());
                datas.put("berlakuHingga",TextUtility.changeDateFormat("dd/MM/yyyy", "yyyy-MM-dd", txtBerlaku.getText().toString())+" 00:00:00.000");
                datas.put("tanggalLahir", TextUtility.changeDateFormat("dd/MM/yyyy", "yyyy-MM-dd", tvBirthDate.getText().toString())+" 00:00:00.000");
                datas.put("zipCode",""/*edZipcode.getText().toString()*/);
                datas.put("kota",edKota.getText().toString());
//                File file = ImageUtility.createImageFile(customerId,fileName);
//                String path = file.getAbsolutePath();
//                CameraUtility.openCamera(activity, CameraUtility.REQUEST_IMAGE_CAPTURE_13,file);
//                popupWindow.dismiss();
//                saveDataListener.onTakePicture(path, UploadDocumentItem.DocumentType.KTPIstriSuami, datas);

                Intent intent = new Intent(activity, CameraActivity.class);
                intent.putExtra("customerId",customerId);
                intent.putExtra("fileName",fileName);
                intent.putExtra("datas",datas);
                intent.putExtra("dataKTPPasangan",data);

                saveDataListener.onTakePicture(imgPath, UploadDocumentItem.DocumentType.KTPIstriSuami, datas);
                openCameraOrGalleryOCR(activity, popupWindow, intent, CameraUtility.REQUEST_IMAGE_CAPTURE_13);
            }
        });

        rbTersedia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    lyParentPromiseDate.setVisibility(View.GONE);
                    lyParentImage.setVisibility(View.VISIBLE);
                    lyInput.setVisibility(View.VISIBLE);
                }
            }
        });

        rbMenunggu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    lyParentPromiseDate.setVisibility(View.VISIBLE);
                    lyParentImage.setVisibility(View.GONE);
                    lyInput.setVisibility(View.GONE);
                }
            }
        });

        ivDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((UploadDocumentActivity)activity).showDatePicker();
                Calendar calendar = Calendar.getInstance();
                MonthAdapter.CalendarDay currDate = new MonthAdapter.CalendarDay();
                currDate.setDay(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                MonthAdapter.CalendarDay maxDate = new MonthAdapter.CalendarDay();
                maxDate.setDay(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)+maxPromisedDate);
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                            @Override
                            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                                int month = monthOfYear+1;
                                String date = dayOfMonth+"/"+month+"/"+year;
                                txtPromiseDate.setText(date);
                                datas.put("promiseDate",date);
                            }
                        })
                        .setFirstDayOfWeek(Calendar.SUNDAY)
                        .setPreselectedDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                        .setDateRange(currDate, maxDate)
                        .setDoneText("Yes")
                        .setCancelText("No");
                cdp.show(((UploadDocumentActivity) activity).getSupportFragmentManager(),"Promise Date");
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
                currDate1.setDay(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)-1);
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                            @Override
                            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                                int month = monthOfYear+1;
                                String date = dayOfMonth+"/"+month+"/"+year;
                                tvBirthDate.setText(date);

                                String dateToSave = year+"-"+month+"-"+dayOfMonth+" 00:00:00.000";
                                datas.put("tanggalLahir",dateToSave);
                            }
                        })
                        .setFirstDayOfWeek(Calendar.SUNDAY)
                        .setPreselectedDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)-1)
                        .setDateRange(null, currDate)
                        .setDoneText("Yes")
                        .setCancelText("No");
                cdp.show(((UploadDocumentActivity) activity).getSupportFragmentManager(),"Birth Date");
            }
        });

        txtBerlaku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                MonthAdapter.CalendarDay currDate = new MonthAdapter.CalendarDay();
                currDate.setDay(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                            @Override
                            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                                int month = monthOfYear+1;
                                String date = dayOfMonth+"/"+month+"/"+year;
                                txtBerlaku.setText(date);

                                String dateToSave = year+"-"+month+"-"+dayOfMonth+" 00:00:00.000";
                                datas.put("berlakuHingga",dateToSave);
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

        cbSeumurHidup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    txtBerlaku.setText("31/12/2099");
                    txtBerlaku.setClickable(false);
                } else {
                    txtBerlaku.setText("");
                    txtBerlaku.setClickable(true);
                }
            }
        });

        lyDeleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lyDeleteImage.setVisibility(View.GONE);
                ivPicture.setVisibility(View.VISIBLE);
                imgBackground.setVisibility(View.VISIBLE);
                imgPdfAttach.setVisibility(View.GONE);
                imgInputUpload.setImageBitmap(null);
                imgPath = "";
                pdfPath = "";
            }
        });

        DialogUtility.relayoutPopupWindow(activity, cardView);

        popupWindow.showAtLocation(view, Gravity.CENTER, point.x, point.y);
        return popupWindow;
    }

    public static boolean isEmpty(String data) {
        if (!data.isEmpty()) {
            return false;
        }

        return true;
    }

    public static PopupWindow inputKartuNamaPasangan(final Activity activity, View view, final SaveDataListener saveDataListener, String imagePath,final String filePdfPath,
                                               final String customerId, final String fileName, final HashMap<String,String> dataOCRKartuNama, final HashMap<String,String> data) {
        final PopupWindow popupWindow;
        final HashMap<String, String> datas = new HashMap<>();
        checkIsUpdate(activity);

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout inflatedView = (LinearLayout) inflater.inflate(R.layout.z_popup_kartunamapasangan, null, false);

        ImageView imgClose = inflatedView.findViewById(R.id.imgClose);
        Button btnSaveUpdate = inflatedView.findViewById(R.id.btnSaveUpdate);
        final ImageView ivPicture = inflatedView.findViewById(R.id.ivPicture);
        final ImageView imgInputUpload = inflatedView.findViewById(R.id.imgInputUpload);
        final ImageView imgBackground = inflatedView.findViewById(R.id.imgBackgroud);

        final RadioButton rbTersedia = inflatedView.findViewById(R.id.rbTersedia);
        final RadioButton rbMenunggu = inflatedView.findViewById(R.id.rbMenyusul);
        ImageView ivDate = inflatedView.findViewById(R.id.ivDate);
        RelativeLayout lyPromiseDate = inflatedView.findViewById(R.id.lyPromiseDate);
        final RelativeLayout lyParentPromiseDate = inflatedView.findViewById(R.id.lyParentPromiseDate);
        final RelativeLayout lyParentImage = inflatedView.findViewById(R.id.lyParentImage);
        final TextView txtPromiseDate = inflatedView.findViewById(R.id.txtPromiseDate);
        final LinearLayout lyInput = inflatedView.findViewById(R.id.lyInput);

        final EditText edAlamat = inflatedView.findViewById(R.id.edAlamat);
        final EditText edRT = inflatedView.findViewById(R.id.edRT);
        final EditText edKelurahan = inflatedView.findViewById(R.id.edKelurahan);
        final EditText edKecamatan = inflatedView.findViewById(R.id.edKecamatan);
        final EditText edRW = inflatedView.findViewById(R.id.edRW);
        final EditText edKota = inflatedView.findViewById(R.id.edKota);
        final EditText edZipcode = inflatedView.findViewById(R.id.edZipCode);
        final EditText edNamaPerusahaan = inflatedView.findViewById(R.id.edNamaPerusahaan);
        final EditText edJabatan = inflatedView.findViewById(R.id.edJabatan);
        final EditText edEmail = inflatedView.findViewById(R.id.edEmail);
        final RelativeLayout lyDeleteImage = inflatedView.findViewById(R.id.lyDeleteImage);
        final CardView cardView = inflatedView.findViewById(R.id.cardView);
        final RelativeLayout imgPdfAttach = inflatedView.findViewById(R.id.imgPdfAttach);

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        popupWindow = new PopupWindow(inflatedView, point.x, point.y, true);

        if(!imagePath.equals("")){
            Bitmap bitmap = ImageUtility.loadBitmapFromPath(imagePath);
            imgInputUpload.setImageBitmap(bitmap);
            ivPicture.setVisibility(View.GONE);
            imgBackground.setVisibility(View.GONE);
            lyDeleteImage.setVisibility(View.VISIBLE);
            imgPath = imagePath;

            if(data.get("is_camera") != null){
                if (data.get("is_camera").equalsIgnoreCase("1")) {
                    final ProgressDialog progressDialog = new ProgressDialog(activity);
                    progressDialog.setMessage("Processing OCR...");
                    progressDialog.show();

                    FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmap);
                    OCRUtility.executeOCRCardName(firebaseVisionImage, new OCRUtility.CallbackCardName() {
                        @Override
                        public void onSuccessCallback(HashMap<String, String> data) {
                            progressDialog.dismiss();

                            edAlamat.setText(data.get("alamat"));
                            edNamaPerusahaan.setText(data.get("perusahaan"));
                            edJabatan.setText(data.get("jabatan"));
                            edEmail.setText(data.get("email"));
                        }

                        @Override
                        public void onFailed(String message) {
                            progressDialog.dismiss();

                            Log.d(TAG, "Error OCR " + message);
                        }
                    });
                }
            }
        }

        if (isUpdate == 1) {
            inflatedView.findViewById(R.id.lyRadioGrup).setVisibility(View.GONE);
            inflatedView.findViewById(R.id.lyInput).setVisibility(View.GONE);
        }

        if(data != null){
            if(data.get("promiseDate") != null && !data.get("promiseDate").equals("")){
                lyParentPromiseDate.setVisibility(View.VISIBLE);
                lyParentImage.setVisibility(View.GONE);
                lyInput.setVisibility(View.GONE);
                txtPromiseDate.setText(data.get("promiseDate"));
                rbMenunggu.setChecked(true);
            } else {
                if (dataOCRKartuNama.isEmpty()) {
                    edAlamat.setText(data.get("alamat"));
                    edNamaPerusahaan.setText(data.get("perusahaan"));
                    edJabatan.setText(data.get("jabatan"));
                    edEmail.setText(data.get("email"));
                } else {
                    edAlamat.setText(dataOCRKartuNama.get("alamat"));
                    edNamaPerusahaan.setText(dataOCRKartuNama.get("perusahaan"));
                    edJabatan.setText(dataOCRKartuNama.get("jabatan"));
                    edEmail.setText(dataOCRKartuNama.get("email"));
                }
            }
        }

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgPath = "";
                pdfPath = "";
                saveDataListener.isSaved(false, data);
                popupWindow.dismiss();
            }
        });

        final String img = imagePath;
        btnSaveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if(rbTersedia.isChecked() && !img.equals("")){
                if(rbTersedia.isChecked()){
                    datas.put("promiseDate","");
                    //if (!imgPath.isEmpty()) {
                        if (isUpdate != 1) {
                            if (!isEmpty(edNamaPerusahaan.getText().toString()) && !isEmpty(edAlamat.getText().toString())
                                    && !isEmpty(edJabatan.getText().toString())) {
                                datas.put("alamat", edAlamat.getText().toString());
                                datas.put("rt", edRT.getText().toString());
                                datas.put("rw", edRW.getText().toString());
                                datas.put("kelurahan", edKelurahan.getText().toString());
                                datas.put("kecamatan", edKecamatan.getText().toString());
                                datas.put("kota", edKota.getText().toString());
                                datas.put("zipcode", ""/*edZipcode.getText().toString()*/);
                                datas.put("perusahaan", edNamaPerusahaan.getText().toString());
                                datas.put("jabatan", edJabatan.getText().toString());
                                datas.put("email", edEmail.getText().toString());
                                datas.put("is_saved", "1");
                                datas.put("imagePath", imgPath);

                                imgPath = "";
                                popupWindow.dismiss();
                                saveDataListener.isSaved(true, datas);
                            } else if (isEmpty(edNamaPerusahaan.getText().toString())) {
                                Toast.makeText(activity, "Nama perusahaan tidak boleh kosong.", Toast.LENGTH_SHORT).show();
                            } else if (isEmpty(edAlamat.getText().toString())) {
                                Toast.makeText(activity, "Alamat tidak boleh kosong.", Toast.LENGTH_SHORT).show();
                            } else if (isEmpty(edEmail.getText().toString())) {
                                Toast.makeText(activity, "Email tidak boleh kosong.", Toast.LENGTH_SHORT).show();
                            } else if (isEmpty(edJabatan.getText().toString())) {
                                Toast.makeText(activity, "Jabatan tidak boleh kosong.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            imgPath = "";
                            popupWindow.dismiss();
                            saveDataListener.isSaved(true, datas);
                        }
                    /*} else {
                        Toast.makeText(activity, "Foto tidak boleh kosong.", Toast.LENGTH_SHORT).show();
                    }*/
                } else if (rbMenunggu.isChecked() && !txtPromiseDate.getText().toString().equals("")) {
                    popupWindow.dismiss();
                    imgPath = "";
                    datas.put("is_saved", "1");
                    datas.clear();
                    saveDataListener.isSaved(false, datas);
                    datas.put("promiseDate",txtPromiseDate.getText().toString());
                    saveDataListener.isSaved(true, datas);
                } else {
                    String message = isUpdate != 1 ? "Foto atau promised date tidak boleh kosong." : "Foto tidak boleh kosong.";
                    Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                }
            }
        });

        ivPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas.put("alamat", edAlamat.getText().toString());
                datas.put("perusahaan", edNamaPerusahaan.getText().toString());
                datas.put("jabatan", edJabatan.getText().toString());
                datas.put("email", edEmail.getText().toString());

                Intent intent = new Intent(activity, CameraCardNameActivity.class);
                intent.putExtra("customerId",customerId);
                intent.putExtra("fileName",fileName);
                intent.putExtra("datas",datas);
                intent.putExtra("dataKartuNama",data);

                saveDataListener.onTakePicture(imgPath, UploadDocumentItem.DocumentType.KartuNamaIstriSuami, datas);
                openCameraOrGalleryOCR(activity, popupWindow, intent, CameraUtility.REQUEST_IMAGE_CAPTURE_14);
            }
        });

        imgInputUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas.put("alamat", edAlamat.getText().toString());
                datas.put("perusahaan", edNamaPerusahaan.getText().toString());
                datas.put("jabatan", edJabatan.getText().toString());
                datas.put("email", edEmail.getText().toString());

                Intent intent = new Intent(activity, CameraCardNameActivity.class);
                intent.putExtra("customerId",customerId);
                intent.putExtra("fileName",fileName);
                intent.putExtra("datas",datas);
                intent.putExtra("dataKartuNama",data);

                saveDataListener.onTakePicture(imgPath, UploadDocumentItem.DocumentType.KartuNamaIstriSuami, datas);
                openCameraOrGalleryOCR(activity, popupWindow, intent, CameraUtility.REQUEST_IMAGE_CAPTURE_14);
            }
        });

        rbTersedia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    lyParentPromiseDate.setVisibility(View.GONE);
                    lyParentImage.setVisibility(View.VISIBLE);
                    lyInput.setVisibility(View.VISIBLE);
                }
            }
        });

        rbMenunggu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    lyParentPromiseDate.setVisibility(View.VISIBLE);
                    lyParentImage.setVisibility(View.GONE);
                    lyInput.setVisibility(View.GONE);
                }
            }
        });

        ivDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((UploadDocumentActivity)activity).showDatePicker();
                Calendar calendar = Calendar.getInstance();
                MonthAdapter.CalendarDay currDate = new MonthAdapter.CalendarDay();
                currDate.setDay(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                MonthAdapter.CalendarDay maxDate = new MonthAdapter.CalendarDay();
                maxDate.setDay(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)+maxPromisedDate);
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                            @Override
                            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                                int month = monthOfYear+1;
                                String date = dayOfMonth+"/"+month+"/"+year;
                                txtPromiseDate.setText(date);
                                datas.put("promiseDate",date);
                            }
                        })
                        .setFirstDayOfWeek(Calendar.SUNDAY)
                        .setPreselectedDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                        .setDateRange(currDate, maxDate)
                        .setDoneText("Yes")
                        .setCancelText("No");
                cdp.show(((UploadDocumentActivity) activity).getSupportFragmentManager(),"Promise Date");
            }
        });

        lyDeleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lyDeleteImage.setVisibility(View.GONE);
                ivPicture.setVisibility(View.VISIBLE);
                imgBackground.setVisibility(View.VISIBLE);
                imgPdfAttach.setVisibility(View.GONE);
                imgInputUpload.setImageBitmap(null);
                imgPath = "";
                pdfPath = "";
            }
        });

        DialogUtility.relayoutPopupWindow(activity, cardView);

        popupWindow.showAtLocation(view, Gravity.CENTER, point.x, point.y);
        return popupWindow;
    }

    public static PopupWindow inputKKSTNK(final Activity activity, View view, final SaveDataListener saveDataListener, final String imagePath,final String filePdfPath,
                                          final String customerId, final String fileName, final String promiseDate, final HashMap<String, String> data) {
        final PopupWindow popupWindow;
        final HashMap<String, String> datas = new HashMap<>();
        checkIsUpdate(activity);

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout inflatedView = (LinearLayout) inflater.inflate(R.layout.z_popup_kkstnk, null, false);

        ImageView imgClose = inflatedView.findViewById(R.id.imgClose);
        Button btnSaveUpdate = inflatedView.findViewById(R.id.btnSaveUpdate);
        final ImageView ivPicture = inflatedView.findViewById(R.id.ivPicture);
        final ImageView imgInputUpload = inflatedView.findViewById(R.id.imgInputUpload);
        final ImageView imgBackground = inflatedView.findViewById(R.id.imgBackgroud);

        final RadioButton rbTersedia = inflatedView.findViewById(R.id.rbTersedia);
        final RadioButton rbMenunggu = inflatedView.findViewById(R.id.rbMenyusul);
        ImageView ivDate = inflatedView.findViewById(R.id.ivDate);
        RelativeLayout lyPromiseDate = inflatedView.findViewById(R.id.lyPromiseDate);
        final RelativeLayout lyParentPromiseDate = inflatedView.findViewById(R.id.lyParentPromiseDate);
        final RelativeLayout lyParentImage = inflatedView.findViewById(R.id.lyParentImage);
        final TextView txtPromiseDate = inflatedView.findViewById(R.id.txtPromiseDate);
        final RelativeLayout lyDeleteImage = inflatedView.findViewById(R.id.lyDeleteImage);
        final RelativeLayout imgPdfAttach = inflatedView.findViewById(R.id.imgPdfAttach);

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        popupWindow = new PopupWindow(inflatedView, point.x, point.y, true);

        if(!imagePath.equals("")){
            Bitmap bitmap = ImageUtility.loadBitmapFromPath(imagePath);
            imgInputUpload.setImageBitmap(bitmap);
            ivPicture.setVisibility(View.GONE);
            imgBackground.setVisibility(View.GONE);
            lyDeleteImage.setVisibility(View.VISIBLE);
            imgPdfAttach.setVisibility(View.GONE);
            imgPath = imagePath;
        } else if (!filePdfPath.equals("")) {
            ivPicture.setVisibility(View.GONE);
            imgBackground.setVisibility(View.GONE);
            lyDeleteImage.setVisibility(View.VISIBLE);
            imgPdfAttach.setVisibility(View.VISIBLE);
            pdfPath = filePdfPath;
        }

        if (isUpdate == 1) {
            inflatedView.findViewById(R.id.lyRadioGrup).setVisibility(View.GONE);
        }

        if(promiseDate != null && !promiseDate.equals("")){
            lyParentPromiseDate.setVisibility(View.VISIBLE);
            lyParentImage.setVisibility(View.GONE);
            txtPromiseDate.setText(promiseDate);
            rbMenunggu.setChecked(true);
        }

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgPath = "";
                pdfPath = "";
                saveDataListener.isSaved(false, data);
                popupWindow.dismiss();
            }
        });

        btnSaveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rbTersedia.isChecked() && (!imgPath.equals("") || !pdfPath.equals(""))){
                    imgPath = "";
                    pdfPath = "";
                    datas.put("promiseDate","");
                    datas.put("is_saved", "1");
                    datas.put("isPDF", "0");

                    popupWindow.dismiss();
                    saveDataListener.isSaved(true, datas);
                } else if (rbMenunggu.isChecked() && !txtPromiseDate.getText().toString().equals("")) {
                    popupWindow.dismiss();
                    imgPath = "";
                    pdfPath = "";
                    datas.put("is_saved", "1");
                    datas.put("isPDF", "0");
                    datas.clear();
                    saveDataListener.isSaved(false, datas);
                    datas.put("promiseDate",txtPromiseDate.getText().toString());
                    saveDataListener.isSaved(true, datas);
                } else {
                    String message = isUpdate != 1 ? "Foto atau File PDF atau promised date tidak boleh kosong." : "Foto atau File PDF tidak boleh kosong.";
                    Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                }
            }
        });

        ivPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas.put("isPDF", "0");
                openCameraOrGallery(activity, popupWindow, datas, customerId, fileName, UploadDocumentItem.DocumentType.KKSTNK, CameraUtility.REQUEST_IMAGE_CAPTURE_15, saveDataListener);
            }
        });

        imgInputUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas.put("isPDF", "0");
                openCameraOrGallery(activity, popupWindow, datas, customerId, fileName, UploadDocumentItem.DocumentType.KKSTNK, CameraUtility.REQUEST_IMAGE_CAPTURE_15, saveDataListener);
            }
        });

        rbTersedia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    lyParentPromiseDate.setVisibility(View.GONE);
                    lyParentImage.setVisibility(View.VISIBLE);
                }
            }
        });

        rbMenunggu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    lyParentPromiseDate.setVisibility(View.VISIBLE);
                    lyParentImage.setVisibility(View.GONE);
                }
            }
        });

        ivDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((UploadDocumentActivity)activity).showDatePicker();
                Calendar calendar = Calendar.getInstance();
                MonthAdapter.CalendarDay currDate = new MonthAdapter.CalendarDay();
                currDate.setDay(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                MonthAdapter.CalendarDay maxDate = new MonthAdapter.CalendarDay();
                maxDate.setDay(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)+maxPromisedDate);
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                            @Override
                            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                                int month = monthOfYear+1;
                                String date = dayOfMonth+"/"+month+"/"+year;
                                txtPromiseDate.setText(date);
                                datas.put("promiseDate",date);
                            }
                        })
                        .setFirstDayOfWeek(Calendar.SUNDAY)
                        .setPreselectedDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                        .setDateRange(currDate, maxDate)
                        .setDoneText("Yes")
                        .setCancelText("No");
                cdp.show(((UploadDocumentActivity) activity).getSupportFragmentManager(),"Promise Date");
            }
        });

        lyDeleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lyDeleteImage.setVisibility(View.GONE);
                ivPicture.setVisibility(View.VISIBLE);
                imgBackground.setVisibility(View.VISIBLE);
                imgPdfAttach.setVisibility(View.GONE);
                imgInputUpload.setImageBitmap(null);
                imgPath = "";
                pdfPath = "";
            }
        });

        popupWindow.showAtLocation(view, Gravity.CENTER, point.x, point.y);
        return popupWindow;
    }

    public static PopupWindow inputKTPSTNK(final Activity activity, View view, final SaveDataListener saveDataListener, String imagePath,final String filePdfPath,
                                           final String customerId, final String fileName, OCRResult ocrResult, final HashMap<String,String> data) {
        final PopupWindow popupWindow;
        final HashMap<String, String> datas = new HashMap<>();
        checkIsUpdate(activity);

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout inflatedView = (LinearLayout) inflater.inflate(R.layout.z_popup_ktpstnk, null, false);

        ImageView imgClose = inflatedView.findViewById(R.id.imgClose);
        Button btnSaveUpdate = inflatedView.findViewById(R.id.btnSaveUpdate);
        final ImageView ivPicture = inflatedView.findViewById(R.id.ivPicture);
        final ImageView imgInputUpload = inflatedView.findViewById(R.id.imgInputUpload);
        final ImageView imgBackground = inflatedView.findViewById(R.id.imgBackgroud);

        final RadioButton rbTersedia = inflatedView.findViewById(R.id.rbTersedia);
        final RadioButton rbMenunggu = inflatedView.findViewById(R.id.rbMenyusul);
        ImageView ivDate = inflatedView.findViewById(R.id.ivDate);
        RelativeLayout lyPromiseDate = inflatedView.findViewById(R.id.lyPromiseDate);
        final RelativeLayout lyParentPromiseDate = inflatedView.findViewById(R.id.lyParentPromiseDate);
        final RelativeLayout lyParentImage = inflatedView.findViewById(R.id.lyParentImage);
        final TextView txtPromiseDate = inflatedView.findViewById(R.id.txtPromiseDate);
        final LinearLayout lyInput = inflatedView.findViewById(R.id.lyInput);

        final Spinner spinJenisKelamin = inflatedView.findViewById(R.id.spinJenisKelamin);
        final Spinner spinAgama = inflatedView.findViewById(R.id.spinAgama);
        final Spinner spinStatusPerkawinan = inflatedView.findViewById(R.id.spinStatusPerkawinan);
        final EditText edNik = inflatedView.findViewById(R.id.edNik);
        final EditText edNama = inflatedView.findViewById(R.id.edNama);
        final EditText edTempatLahir = inflatedView.findViewById(R.id.edTempatLahir);
        final EditText edAlamat = inflatedView.findViewById(R.id.edAlamat);
        final EditText edRT = inflatedView.findViewById(R.id.edRT);
        final EditText edRW = inflatedView.findViewById(R.id.edRW);
        final EditText edKelurahan = inflatedView.findViewById(R.id.edKelurahan);
        final EditText edKecamatan = inflatedView.findViewById(R.id.edKecamatan);
        final EditText edPekerjaan = inflatedView.findViewById(R.id.edPekerjaan);
//        final EditText edKewarganegaraan = inflatedView.findViewById(R.id.edKewarganegaraan);
        final EditText edKota = inflatedView.findViewById(R.id.edKota);
        final EditText edZipcode = inflatedView.findViewById(R.id.edZipCode);

        final TextView tvBirthDate = inflatedView.findViewById(R.id.txtBirthDate);
        final TextView txtBerlaku = inflatedView.findViewById(R.id.txtBerlaku);
        final CheckBox cbSeumurHidup = inflatedView.findViewById(R.id.cbSeumurHidup);
        final RelativeLayout lyDeleteImage = inflatedView.findViewById(R.id.lyDeleteImage);
        final CardView cardView = inflatedView.findViewById(R.id.cardView);
        final RelativeLayout imgPdfAttach = inflatedView.findViewById(R.id.imgPdfAttach);

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        popupWindow = new PopupWindow(inflatedView, point.x, point.y, true);

        if(data != null){
            if(data.get("promiseDate") != null && !data.get("promiseDate").equals("")){
                lyParentPromiseDate.setVisibility(View.VISIBLE);
                lyParentImage.setVisibility(View.GONE);
                lyInput.setVisibility(View.GONE);
                txtPromiseDate.setText(data.get("promiseDate"));
                //edNoNPWP.setText(data.get("npwp"));
                rbMenunggu.setChecked(true);
            }
        }

        if (isUpdate == 1) {
            inflatedView.findViewById(R.id.lyRadioGrup).setVisibility(View.GONE);
            inflatedView.findViewById(R.id.lyInput).setVisibility(View.GONE);
        }

        setSpinnerJekel(activity, spinJenisKelamin);
        setSpinnerData(activity, spinAgama, 1);
        setSpinnerData(activity, spinStatusPerkawinan, 2);

        try {
            if(data != null){
                edNik.setText(data.get("nik"));
                edNama.setText(data.get("nama"));
                edTempatLahir.setText(data.get("tempatLahir"));
                edAlamat.setText(data.get("alamat"));
                edRT.setText(data.get("rt"));
                edRW.setText(data.get("rw"));
                edKelurahan.setText(data.get("kelurahan"));
                edKecamatan.setText(data.get("kecamatan"));
                edKota.setText(data.get("kota"));
                edZipcode.setText(data.get("zipCode"));
                edPekerjaan.setText(data.get("pekerjaan"));

                /*set tanggal lahir*/
                String birthDate = data.get("tanggalLahir");
                if(birthDate != null){
                    String[] brthdate = birthDate.split(" ");
                    if(brthdate != null){
                        String[] birth = brthdate[0].split("-");
                        tvBirthDate.setText(birth[2]+"/"+birth[1]+"/"+birth[0]);
                    }
                }

                /*set jenis kelamin*/
                if(data.get("jenisKelamin").toUpperCase().contains("LAKI")){
                    spinJenisKelamin.setSelection(1);
                }else if (data.get("jenisKelamin").toUpperCase().contains("PEREMPUAN")) {
                    spinJenisKelamin.setSelection(2);
                }else{
                    spinJenisKelamin.setSelection(0);
                }

                /*agama*/
                String agamaStr = data.get("agama").toUpperCase();

                /*status perkawinan*/
                String statusPerkawinanStr = data.get("statusPerkawinan");

                setSelectionReligion(agamaStr, spinAgama);
                setSelectionMaritalStatus(statusPerkawinanStr, spinStatusPerkawinan);

                /*pekerjaan*/
                edPekerjaan.setText(data.get("pekerjaan"));

                /*berlaku hingga*/
                String berlaku = TextUtility.changeDateFormat("yyyy-MM-dd HH:mm:ss.SSS", "dd/MM/yyyy", data.get("berlakuHingga"));
                if(berlaku != null && !berlaku.equals("null") && !berlaku.equals("")){
                    if(berlaku.equals("31/12/2099")){
                        cbSeumurHidup.setChecked(true);
                        txtBerlaku.setText("31/12/2099");
                    }else{
                        cbSeumurHidup.setChecked(false);
                        txtBerlaku.setText(berlaku);
                    }
                }
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

                    tvBirthDate.setText(TextUtility.changeDateFormat("dd-MM-yyyy", "dd/MM/yyyy", (arrTtl[1]).replace(" ", "")));
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

            String agama = ocrResult.getAgama();
            if(agama != null && !agama.equals("")){
                agama = agama.toUpperCase();
            }
            //String agama = ocrResult.getAgama().toUpperCase();
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

            Log.d(TAG, "DATA KTP "+data);
            Log.d(TAG, "Bitmap size camera popup "+bitmap.getHeight());

            if (data.get("is_camera") != null) {
                if (data.get("is_camera").equalsIgnoreCase("1")) {
                    final ProgressDialog progressDialog = new ProgressDialog(activity);
                    progressDialog.setMessage("Processing OCR...");
                    progressDialog.show();

                    Base64Image.with(activity)
                            .encode(bitmap)
                            .into(new RequestEncode.Encode() {
                                @Override
                                public void onSuccess(String s) {
                                    call = OCRPresenter.requestOCR(activity, s, new OCRCallback() {
                                        @Override
                                        public void onSuccessOCR(OCRResult result) {
                                            progressDialog.dismiss();
                                            edNik.setText(result.getNIK());
                                            edNama.setText(result.getNama());
                                            String ttl = result.getTempatTanggalLahir();
                                            if (ttl != null) {
                                                String[] arrTtl = ttl.split(",");
                                                if (arrTtl.length > 1) {
                                                    edTempatLahir.setText(arrTtl[0]);
                                                    tvBirthDate.setText(TextUtility.changeDateFormat("dd-MM-yyyy", "dd/MM/yyyy", (arrTtl[1]).replace(" ", "")));
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
                                            txtBerlaku.setText(result.getBerlakuHingga());

                                            String agama = result.getAgama();
                                            if(agama != null && !agama.equals("")){
                                                agama = agama.toUpperCase();
                                            }
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
                                            progressDialog.dismiss();
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
                                                                call.cancel();
                                                                progressDialog.dismiss();
                                                                //Toast.makeText(CameraActivity.this, "OCR Processing Failed. Please check your connection", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                    }
                                                },
                                                30000
                                        );
                                    }catch (Exception e){
                                        LogUtility.logging(TAG,LogUtility.warningLog, "inputKTPSTNK", "timer.schedule",e.getMessage());
                                    }
                                }

                                @Override
                                public void onFailure() {
                                    progressDialog.dismiss();
                                }
                            });
                }
            }
        }

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgPath = "";
                pdfPath = "";
                saveDataListener.isSaved(false, data);
                popupWindow.dismiss();
            }
        });

        final String img = imagePath;
        btnSaveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rbTersedia.isChecked() && !img.equals("")){
                    datas.put("promiseDate","");
                    if (!imgPath.isEmpty()) {
                        if (isUpdate != 1) {
                            if (!isEmpty(edNik.getText().toString()) && !isEmpty(edNama.getText().toString()) && !isEmpty(edTempatLahir.getText().toString())
                                    && !spinJenisKelamin.getSelectedItem().toString().contains("- Select") && !isEmpty(edAlamat.getText().toString()) && !isEmpty(edRT.getText().toString())
                                    && !isEmpty(edRW.getText().toString()) && !isEmpty(edKelurahan.getText().toString()) && !isEmpty(edKecamatan.getText().toString())
                                    && !spinAgama.getSelectedItem().toString().contains("- Select") && !spinStatusPerkawinan.getSelectedItem().toString().contains("- Select") && !isEmpty(edPekerjaan.getText().toString())
                                    && !isEmpty(txtBerlaku.getText().toString()) /*&& !isEmpty(edZipcode.getText().toString()) && edZipcode.getText().toString().length() == 5*/ && !isEmpty(edKota.getText().toString())
                                    && !isEmpty(tvBirthDate.getText().toString())) {
                                datas.put("nik", edNik.getText().toString());
                                datas.put("nama", edNama.getText().toString());
                                datas.put("tempatLahir", edTempatLahir.getText().toString());
                                datas.put("jenisKelamin", spinJenisKelamin.getSelectedItem().toString());
                                datas.put("alamat", edAlamat.getText().toString());
                                datas.put("rt", edRT.getText().toString());
                                datas.put("rw", edRW.getText().toString());
                                datas.put("kelurahan", edKelurahan.getText().toString());
                                datas.put("kecamatan", edKecamatan.getText().toString());
                                datas.put("agama", ((Religion)spinAgama.getSelectedItem()).getCode());
                                datas.put("statusPerkawinan", ((MaritalStatus)spinStatusPerkawinan.getSelectedItem()).getCode());
                                datas.put("pekerjaan", edPekerjaan.getText().toString());
                                datas.put("berlakuHingga", TextUtility.changeDateFormat("dd/MM/yyyy", "yyyy-MM-dd", txtBerlaku.getText().toString())+" 00:00:00.000");
                                datas.put("tanggalLahir", TextUtility.changeDateFormat("dd/MM/yyyy", "yyyy-MM-dd", tvBirthDate.getText().toString())+" 00:00:00.000");
                                datas.put("zipCode", ""/*edZipcode.getText().toString()*/);
                                datas.put("kota", edKota.getText().toString());
                                datas.put("is_saved", "1");

                                imgPath = "";
                                popupWindow.dismiss();
                                saveDataListener.isSaved(true, datas);
                            } else if (isEmpty(edNik.getText().toString())) {
                                Toast.makeText(activity, "NIK tidak boleh kosong", Toast.LENGTH_SHORT).show();
                            } else if (isEmpty(edNama.getText().toString())) {
                                Toast.makeText(activity, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show();
                            } else if (isEmpty(edTempatLahir.getText().toString())) {
                                Toast.makeText(activity, "Tempat Lahir tidak boleh kosong", Toast.LENGTH_SHORT).show();
                            } else if (spinJenisKelamin.getSelectedItem().toString().contains("- Select")) {
                                Toast.makeText(activity, "Jenis Kelamin tidak boleh kosong", Toast.LENGTH_SHORT).show();
                            } else if (isEmpty(edAlamat.getText().toString())) {
                                Toast.makeText(activity, "Alamat tidak boleh kosong", Toast.LENGTH_SHORT).show();
                            } else if (isEmpty(edRT.getText().toString())) {
                                Toast.makeText(activity, "RT tidak boleh kosong", Toast.LENGTH_SHORT).show();
                            } else if (isEmpty(edRW.getText().toString())) {
                                Toast.makeText(activity, "RW tidak boleh kosong", Toast.LENGTH_SHORT).show();
                            } else if (isEmpty(edKelurahan.getText().toString())) {
                                Toast.makeText(activity, "Kelurahan tidak boleh kosong", Toast.LENGTH_SHORT).show();
                            } else if (isEmpty(edKecamatan.getText().toString())) {
                                Toast.makeText(activity, "Kecamatan tidak boleh kosong", Toast.LENGTH_SHORT).show();
                            } else if (spinAgama.getSelectedItem().toString().contains("- Select")) {
                                Toast.makeText(activity, "Agama tidak boleh kosong", Toast.LENGTH_SHORT).show();
                            } else if (spinStatusPerkawinan.getSelectedItem().toString().contains("- Select")) {
                                Toast.makeText(activity, "Status Perkawinan tidak boleh kosong", Toast.LENGTH_SHORT).show();
                            } else if (isEmpty(edPekerjaan.getText().toString())) {
                                Toast.makeText(activity, "Pekerjaan tidak boleh kosong", Toast.LENGTH_SHORT).show();
                            } else if (isEmpty(txtBerlaku.getText().toString())) {
                                Toast.makeText(activity, "Berlaku Hingga tidak boleh kosong", Toast.LENGTH_SHORT).show();
                            } /*else if (isEmpty(edZipcode.getText().toString())) {
                                Toast.makeText(activity, "Kode Pos tidak boleh kosong", Toast.LENGTH_SHORT).show();
                            } else if (edZipcode.getText().toString().length() != 5) {
                                Toast.makeText(activity, "Kode Pos harus 5 digit", Toast.LENGTH_SHORT).show();
                            }*/ else if (isEmpty(edKota.getText().toString())) {
                                Toast.makeText(activity, "Kota tidak boleh kosong", Toast.LENGTH_SHORT).show();
                            } else if (isEmpty(tvBirthDate.getText().toString())) {
                                Toast.makeText(activity, "Tanggal Lahir tidak boleh kosong", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            imgPath = "";
                            popupWindow.dismiss();
                            saveDataListener.isSaved(true, datas);
                        }
                    } else {
                        Toast.makeText(activity, "Foto tidak boleh kosong.", Toast.LENGTH_SHORT).show();
                    }
                } else if (rbMenunggu.isChecked() && !txtPromiseDate.getText().toString().equals("")) {
                    popupWindow.dismiss();
                    imgPath = "";
                    datas.put("is_saved", "1");
                    datas.put("is_camera", "0");
                    datas.clear();
                    saveDataListener.isSaved(false, datas);
                    datas.put("promiseDate",txtPromiseDate.getText().toString());
                    saveDataListener.isSaved(true, datas);
                } else {
                    String message = isUpdate != 1 ? "Foto atau promised date tidak boleh kosong." : "Foto tidak boleh kosong.";
                    Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                }
            }
        });

        ivPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas.put("nik",edNik.getText().toString());
                datas.put("nama", edNama.getText().toString());
                datas.put("tempatLahir",edTempatLahir.getText().toString());
                datas.put("jenisKelamin", spinJenisKelamin.getSelectedItem().toString());
                datas.put("alamat", edAlamat.getText().toString());
                datas.put("rt",edRT.getText().toString());
                datas.put("rw",edRW.getText().toString());
                datas.put("kelurahan",edKelurahan.getText().toString());
                datas.put("kecamatan",edKecamatan.getText().toString());
                datas.put("agama",spinAgama.getSelectedItem().toString());
                datas.put("statusPerkawinan", spinStatusPerkawinan.getSelectedItem().toString());
                datas.put("pekerjaan",edPekerjaan.getText().toString());
                datas.put("berlakuHingga",TextUtility.changeDateFormat("dd/MM/yyyy", "yyyy-MM-dd", txtBerlaku.getText().toString())+" 00:00:00.000");
                datas.put("tanggalLahir", TextUtility.changeDateFormat("dd/MM/yyyy", "yyyy-MM-dd", tvBirthDate.getText().toString())+" 00:00:00.000");
                datas.put("zipCode",""/*edZipcode.getText().toString()*/);
                datas.put("kota",edKota.getText().toString());
//                File file = ImageUtility.createImageFile(customerId,fileName);
//                String path = file.getAbsolutePath();
//                CameraUtility.openCamera(activity, CameraUtility.REQUEST_IMAGE_CAPTURE_16,file);
//                popupWindow.dismiss();
//                saveDataListener.onTakePicture(path, UploadDocumentItem.DocumentType.KTPSTNK, datas);

                Intent intent = new Intent(activity, CameraActivity.class);
                intent.putExtra("customerId",customerId);
                intent.putExtra("fileName",fileName);
                intent.putExtra("datas",datas);
                intent.putExtra("dataKTPSTNK",data);

                saveDataListener.onTakePicture(imgPath, UploadDocumentItem.DocumentType.KTPSTNK, datas);
                openCameraOrGalleryOCR(activity, popupWindow, intent, CameraUtility.REQUEST_IMAGE_CAPTURE_16);
            }
        });

        imgInputUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas.put("nik",edNik.getText().toString());
                datas.put("nama", edNama.getText().toString());
                datas.put("tempatLahir",edTempatLahir.getText().toString());
                datas.put("jenisKelamin", spinJenisKelamin.getSelectedItem().toString());
                datas.put("alamat", edAlamat.getText().toString());
                datas.put("rt",edRT.getText().toString());
                datas.put("rw",edRW.getText().toString());
                datas.put("kelurahan",edKelurahan.getText().toString());
                datas.put("kecamatan",edKecamatan.getText().toString());
                datas.put("agama",spinAgama.getSelectedItem().toString());
                datas.put("statusPerkawinan", spinStatusPerkawinan.getSelectedItem().toString());
                datas.put("pekerjaan",edPekerjaan.getText().toString());
                datas.put("berlakuHingga",TextUtility.changeDateFormat("dd/MM/yyyy", "yyyy-MM-dd", txtBerlaku.getText().toString())+" 00:00:00.000");
                datas.put("tanggalLahir", TextUtility.changeDateFormat("dd/MM/yyyy", "yyyy-MM-dd", tvBirthDate.getText().toString())+" 00:00:00.000");
                datas.put("zipCode",""/*edZipcode.getText().toString()*/);
                datas.put("kota",edKota.getText().toString());
//                File file = ImageUtility.createImageFile(customerId,fileName);
//                String path = file.getAbsolutePath();
//                CameraUtility.openCamera(activity, CameraUtility.REQUEST_IMAGE_CAPTURE_16,file);
//                popupWindow.dismiss();
//                saveDataListener.onTakePicture(path, UploadDocumentItem.DocumentType.KTPSTNK, datas);

                Intent intent = new Intent(activity, CameraActivity.class);
                intent.putExtra("customerId",customerId);
                intent.putExtra("fileName",fileName);
                intent.putExtra("datas",datas);
                intent.putExtra("dataKTPSTNK",data);

                saveDataListener.onTakePicture(imgPath, UploadDocumentItem.DocumentType.KTPSTNK, datas);
                openCameraOrGalleryOCR(activity, popupWindow, intent, CameraUtility.REQUEST_IMAGE_CAPTURE_16);
            }
        });

        rbTersedia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    lyParentPromiseDate.setVisibility(View.GONE);
                    lyParentImage.setVisibility(View.VISIBLE);
                    lyInput.setVisibility(View.VISIBLE);
                }
            }
        });

        rbMenunggu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    lyParentPromiseDate.setVisibility(View.VISIBLE);
                    lyParentImage.setVisibility(View.GONE);
                    lyInput.setVisibility(View.GONE);
                }
            }
        });

        ivDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((UploadDocumentActivity)activity).showDatePicker();
                Calendar calendar = Calendar.getInstance();
                MonthAdapter.CalendarDay currDate = new MonthAdapter.CalendarDay();
                currDate.setDay(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                MonthAdapter.CalendarDay maxDate = new MonthAdapter.CalendarDay();
                maxDate.setDay(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)+maxPromisedDate);
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                            @Override
                            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                                int month = monthOfYear+1;
                                String date = dayOfMonth+"/"+month+"/"+year;
                                txtPromiseDate.setText(date);
                                datas.put("promiseDate",date);
                            }
                        })
                        .setFirstDayOfWeek(Calendar.SUNDAY)
                        .setPreselectedDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                        .setDateRange(currDate, maxDate)
                        .setDoneText("Yes")
                        .setCancelText("No");
                cdp.show(((UploadDocumentActivity) activity).getSupportFragmentManager(),"Promise Date");
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
//                    datas.put("berlakuHingga","31/12/2099");
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
                currDate.setDay(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                            @Override
                            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                                int month = monthOfYear+1;
                                String date = dayOfMonth+"/"+month+"/"+year;
                                txtBerlaku.setText(date);

                                String dateToSave = year+"-"+month+"-"+dayOfMonth+" 00:00:00.000";
                                datas.put("berlakuHingga",dateToSave);
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
                imgPdfAttach.setVisibility(View.GONE);
                imgInputUpload.setImageBitmap(null);
                imgPath = "";
                pdfPath = "";
            }
        });

        DialogUtility.relayoutPopupWindow(activity, cardView);

        popupWindow.showAtLocation(view, Gravity.CENTER, point.x, point.y);
        return popupWindow;
    }

    public static void openCameraOrGallery(final Activity activity, final PopupWindow popupWindow, final HashMap<String, String> datas, final String customerId, final String fileName, final UploadDocumentItem.DocumentType documentType, final int requestID, final SaveDataListener saveDataListener) {
        String[] from = null;

        if (!datas.isEmpty()) {
            if (datas.get("isPDF") != null) {
                from = new String[]{"Camera", "Gallery (Maksimal File Upload 500Kb)", "PDF File"};
            }else {
                from = new String[]{"Camera", "Gallery (Maksimal File Upload 500Kb)"};
            }
        } else {
            from = new String[]{"Camera", "Gallery (Maksimal File Upload 500Kb)"};
        }

        android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(activity);
        alert.setTitle("Upload Gambar")
                .setItems(from, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                popupWindow.dismiss();
                                File file = ImageUtility.createImageFile(customerId,fileName);
                                String path = file.getAbsolutePath();
                                CameraUtility.openCamera(activity, requestID, file);
                                saveDataListener.onTakePicture(path, documentType, datas);
                                break;
                            case 1:
                                popupWindow.dismiss();
                                saveDataListener.onTakePicture("", documentType, datas);
                                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                activity.startActivityForResult(galleryIntent, requestID);
                                break;
                            case 2:
                                popupWindow.dismiss();
                                datas.put("isPDF", "1");
                                saveDataListener.onTakePicture("", documentType, datas);
//                                Intent intentDocument = new Intent(Intent.ACTION_GET_CONTENT);
////                                intentDocument.setType("*/*");
//                                Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath());
////                                intentDocument.setDataAndType(uri, "application/pdf");
//                                intentDocument.setType("application/pdf");
//                                intentDocument.addCategory(Intent.CATEGORY_OPENABLE);

                                Intent intentFileChooser = new Intent(activity, FileChooser.class);
                                activity.startActivityForResult(intentFileChooser, requestID);
                                break;
                        }
                    }
                });
        alert.create().show();
    }

    public static PopupWindow inputSlipGaji(final Activity activity, View view, final SaveDataListener saveDataListener, final String imagePath,final String filePdfPath,
                                          final String customerId, final String fileName, final String promiseDate, final HashMap<String, String> data) {
        final PopupWindow popupWindow;
        final HashMap<String, String> datas = new HashMap<>();
        checkIsUpdate(activity);

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout inflatedView = (LinearLayout) inflater.inflate(R.layout.z_popup_slipgaji, null, false);

        ImageView imgClose = inflatedView.findViewById(R.id.imgClose);
        Button btnSaveUpdate = inflatedView.findViewById(R.id.btnSaveUpdate);
        final ImageView ivPicture = inflatedView.findViewById(R.id.ivPicture);
        final ImageView imgInputUpload = inflatedView.findViewById(R.id.imgInputUpload);
        final ImageView imgBackground = inflatedView.findViewById(R.id.imgBackgroud);

        final RadioButton rbTersedia = inflatedView.findViewById(R.id.rbTersedia);
        final RadioButton rbMenunggu = inflatedView.findViewById(R.id.rbMenyusul);
        ImageView ivDate = inflatedView.findViewById(R.id.ivDate);
        RelativeLayout lyPromiseDate = inflatedView.findViewById(R.id.lyPromiseDate);
        final RelativeLayout lyParentPromiseDate = inflatedView.findViewById(R.id.lyParentPromiseDate);
        final RelativeLayout lyParentImage = inflatedView.findViewById(R.id.lyParentImage);
        final TextView txtPromiseDate = inflatedView.findViewById(R.id.txtPromiseDate);
        final RelativeLayout lyDeleteImage = inflatedView.findViewById(R.id.lyDeleteImage);
        final RelativeLayout imgPdfAttach = inflatedView.findViewById(R.id.imgPdfAttach);

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        popupWindow = new PopupWindow(inflatedView, point.x, point.y, true);

        if(!imagePath.equals("")){
            Bitmap bitmap = ImageUtility.loadBitmapFromPath(imagePath);
            imgInputUpload.setImageBitmap(bitmap);
            ivPicture.setVisibility(View.GONE);
            imgBackground.setVisibility(View.GONE);
            lyDeleteImage.setVisibility(View.VISIBLE);
            imgPdfAttach.setVisibility(View.GONE);
            imgPath = imagePath;
        } else if (!filePdfPath.equals("")) {
            ivPicture.setVisibility(View.GONE);
            imgBackground.setVisibility(View.GONE);
            lyDeleteImage.setVisibility(View.VISIBLE);
            imgPdfAttach.setVisibility(View.VISIBLE);
            pdfPath = filePdfPath;
        }

        if (isUpdate == 1) {
            inflatedView.findViewById(R.id.lyRadioGrup).setVisibility(View.GONE);
        }

        if(promiseDate != null && !promiseDate.equals("")){
            lyParentPromiseDate.setVisibility(View.VISIBLE);
            lyParentImage.setVisibility(View.GONE);
            txtPromiseDate.setText(promiseDate);
            rbMenunggu.setChecked(true);
        }

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgPath = "";
                pdfPath = "";
                saveDataListener.isSaved(false, data);
                popupWindow.dismiss();
            }
        });

        btnSaveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rbTersedia.isChecked() && (!imgPath.equals("") || !pdfPath.equals(""))){
                    imgPath = "";
                    pdfPath = "";
                    datas.put("promiseDate","");
                    datas.put("is_saved", "1");
                    datas.put("isPDF", "0");

                    popupWindow.dismiss();
                    saveDataListener.isSaved(true, datas);
                } else if (rbMenunggu.isChecked() && !txtPromiseDate.getText().toString().equals("")) {
                    popupWindow.dismiss();
                    imgPath = "";
                    pdfPath = "";
                    datas.put("is_saved", "1");
                    datas.put("isPDF", "0");
                    datas.clear();
                    saveDataListener.isSaved(false, datas);
                    datas.put("promiseDate",txtPromiseDate.getText().toString());
                    saveDataListener.isSaved(true, datas);
                } else {
                    String message = isUpdate != 1 ? "Foto atau File PDF atau promised date tidak boleh kosong." : "Foto atau File PDF tidak boleh kosong.";
                    Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                }
            }
        });

        ivPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas.put("isPDF", "0");
                openCameraOrGallery(activity, popupWindow, datas, customerId, fileName, UploadDocumentItem.DocumentType.SlipGaji, CameraUtility.REQUEST_IMAGE_CAPTURE_19, saveDataListener);
            }
        });

        imgInputUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas.put("isPDF", "0");
                openCameraOrGallery(activity, popupWindow, datas, customerId, fileName, UploadDocumentItem.DocumentType.SlipGaji, CameraUtility.REQUEST_IMAGE_CAPTURE_19, saveDataListener);
            }
        });

        rbTersedia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    lyParentPromiseDate.setVisibility(View.GONE);
                    lyParentImage.setVisibility(View.VISIBLE);
                }
            }
        });

        rbMenunggu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    lyParentPromiseDate.setVisibility(View.VISIBLE);
                    lyParentImage.setVisibility(View.GONE);
                }
            }
        });

        ivDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((UploadDocumentActivity)activity).showDatePicker();
                Calendar calendar = Calendar.getInstance();
                MonthAdapter.CalendarDay currDate = new MonthAdapter.CalendarDay();
                currDate.setDay(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                MonthAdapter.CalendarDay maxDate = new MonthAdapter.CalendarDay();
                maxDate.setDay(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)+maxPromisedDate);
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                            @Override
                            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                                int month = monthOfYear+1;
                                String date = dayOfMonth+"/"+month+"/"+year;
                                txtPromiseDate.setText(date);
                                datas.put("promiseDate",date);
                            }
                        })
                        .setFirstDayOfWeek(Calendar.SUNDAY)
                        .setPreselectedDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                        .setDateRange(currDate, maxDate)
                        .setDoneText("Yes")
                        .setCancelText("No");
                cdp.show(((UploadDocumentActivity) activity).getSupportFragmentManager(),"Promise Date");
            }
        });

        lyDeleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lyDeleteImage.setVisibility(View.GONE);
                ivPicture.setVisibility(View.VISIBLE);
                imgBackground.setVisibility(View.VISIBLE);
                imgPdfAttach.setVisibility(View.GONE);
                imgInputUpload.setImageBitmap(null);
                imgPath = "";
                pdfPath = "";
            }
        });

        popupWindow.showAtLocation(view, Gravity.CENTER, point.x, point.y);
        return popupWindow;
    }

    public static void openCameraOrGalleryOCR(final Activity activity, final PopupWindow popupWindow, final Intent intent, final int requestID) {
        android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(activity);
        alert.setTitle("Upload Gambar")
                .setItems(new String[]{"Camera", "Gallery (Maksimal File Upload 500Kb)"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        popupWindow.dismiss();

                        switch (i) {
                            case 0:
                                activity.startActivityForResult(intent, requestID);
                                break;
                            case 1:
                                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                activity.startActivityForResult(galleryIntent, requestID);
                                break;
                        }
                    }
                });
        alert.create().show();
    }

    private static void setSpinnerData(Activity activity, Spinner spinner, int mode) {
        if (mode == 1) {
            if (ReligionController.getAllReligion() != null) {
                List<Religion> religions = new ArrayList<>();
                religions.add(new Religion("- Select -"));
                religions.addAll(ReligionController.getAllReligion());
                SpinnerUtility.setSpinnerItemReligion(activity, spinner, religions);
            }
        } else if (mode == 2) {
            if (MaritalStatusController.getAllMaritalStatus() != null) {
                List<MaritalStatus> maritalStatuses = new ArrayList<>();
                maritalStatuses.add(new MaritalStatus("- Select -"));
                maritalStatuses.addAll(MaritalStatusController.getAllMaritalStatus());
                SpinnerUtility.setSpinnerItemMaritalStatus(activity,spinner,maritalStatuses);
            }
        } else  if (mode == 3) {
            if (MaritalStatusController.getAllMaritalStatus() != null) {
                List<MaritalStatus> maritalStatuses = new ArrayList<>();
                maritalStatuses.add(new MaritalStatus("- Select -"));
                maritalStatuses.addAll(MaritalStatusController.getAllMaritalStatus());

                for (int i = 0;i < maritalStatuses.size();i++) {
                    if ("MAR".equalsIgnoreCase(maritalStatuses.get(i).getCode())) {
                        List<MaritalStatus> statNew = new ArrayList<>();
                        statNew.add(maritalStatuses.get(i));
                        SpinnerUtility.setSpinnerItemMaritalStatus(activity,spinner,statNew);
                    }
                }
            }
        }
    }

    private static void setSelectionMaritalStatus(String status, Spinner spinner) {
        List<MaritalStatus> maritalStatuses = new ArrayList<>();
        maritalStatuses.add(new MaritalStatus("- Select -"));
        maritalStatuses.addAll(MaritalStatusController.getAllMaritalStatus());

        for (int i = 0;i < maritalStatuses.size();i++) {
            if (status.equalsIgnoreCase(maritalStatuses.get(i).getCode())) {
                spinner.setSelection(i);
            }
        }
    }

    private static void setSelectionReligion(String status, Spinner spinner) {
        List<Religion> religions = new ArrayList<>();
        religions.add(new Religion("- Select -"));
        religions.addAll(ReligionController.getAllReligion());

        if(religions != null){
            for (int i = 0;i < religions.size();i++) {
                if(status != null && religions != null && religions.size()>0){
                    if (status.equalsIgnoreCase(religions.get(i).getCode())) {
                        spinner.setSelection(i);
                    }
                }
            }
        }

    }

    private static void setSpinnerJekel(Activity activity, Spinner spinner) {
        List<String> jenisKelamin = new ArrayList<>();
        jenisKelamin.add("- Select -");
        jenisKelamin.add("LAKI - LAKI");
        jenisKelamin.add("PEREMPUAN");
        SpinnerUtility.setSpinnerItem(activity,spinner,jenisKelamin);
    }
}
