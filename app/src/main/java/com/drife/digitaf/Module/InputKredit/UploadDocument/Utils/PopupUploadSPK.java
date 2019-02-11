package com.drife.digitaf.Module.InputKredit.UploadDocument.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.drife.digitaf.GeneralUtility.CameraUtility.CameraUtility;
import com.drife.digitaf.GeneralUtility.DialogUtility.DialogUtility;
import com.drife.digitaf.GeneralUtility.ImageUtility.ImageUtility;
import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.OCR.Utility.OCRUtility;
import com.drife.digitaf.Module.Camera.CameraActivity;
import com.drife.digitaf.Module.Camera.CameraSPKActivity;
import com.drife.digitaf.Module.InputKredit.UploadDocument.Model.UploadDocumentItem;
import com.drife.digitaf.R;
import com.drife.digitaf.UIModel.OCRData;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class PopupUploadSPK {
    private static String TAG = PopupUploadSPK.class.getSimpleName();

    public interface SaveDataListener {
        void isSaved(boolean isSaved, HashMap<String, String> datas);
        void onTakePicture(String imagePath, UploadDocumentItem.DocumentType documentType, HashMap<String,String> datas);
    }

    static ImageView imgClose;
    static Button btnSaveUpdate;
    static ImageView ivPicture;
    static ImageView imgInputUpload;
    static ImageView imgBackground;
    static EditText edSpk;
    static RelativeLayout lyDeleteImage;
    static Button btnScan;
    static CardView cardView;

    static String imgPath = "";

//    public static PopupWindow inputSPK(final Activity activity, View view, final PopupUploadSPK.SaveDataListener saveDataListener,
//                                       final String imagePath, final String customerId, final String fileName, final HashMap<String,String> dataSPK) {
//public static PopupWindow inputSPK(final Activity activity, View view, final PopupUploadSPK.SaveDataListener saveDataListener,
//                                   final String imagePath, final String customerId, final String fileName, final List<OCRData> dataOCR, final HashMap<String,String> dataSPK) {
public static PopupWindow inputSPK(final Activity activity, View view, final PopupUploadSPK.SaveDataListener saveDataListener,
                                   final String imagePath, final String customerId, final String fileName, final String nomorSPK, final HashMap<String,String> dataSPK) {
        final PopupWindow popupWindow;

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout inflatedView = (LinearLayout) inflater.inflate(R.layout.z_popup_spk, null, false);

        imgClose = inflatedView.findViewById(R.id.imgClose);
        btnSaveUpdate = inflatedView.findViewById(R.id.btnSaveUpdate);
        ivPicture = inflatedView.findViewById(R.id.ivPicture);
        imgInputUpload = inflatedView.findViewById(R.id.imgInputUpload);
        imgBackground = inflatedView.findViewById(R.id.imgBackgroud);
        edSpk = inflatedView.findViewById(R.id.edSpk);
        lyDeleteImage = inflatedView.findViewById(R.id.lyDeleteImage);
        btnScan = inflatedView.findViewById(R.id.btnScan);
        cardView = inflatedView.findViewById(R.id.cardView);

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        popupWindow = new PopupWindow(inflatedView, point.x, point.y, true);

        if(dataSPK !=null && nomorSPK.equalsIgnoreCase("")){
            edSpk.setText(dataSPK.get("spk"));
        } else {
            edSpk.setText(nomorSPK);
        }

        if(!imagePath.equals("")){
            Bitmap bitmap = ImageUtility.loadBitmapFromPath(imagePath);
            imgInputUpload.setImageBitmap(bitmap);
            ivPicture.setVisibility(View.GONE);
            imgBackground.setVisibility(View.GONE);
            lyDeleteImage.setVisibility(View.VISIBLE);
            imgPath = imagePath;
        }

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgPath = "";
                saveDataListener.isSaved(false, dataSPK);
                popupWindow.dismiss();
            }
        });

        btnSaveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "img "+imgPath);

                if(validasi(activity,imgPath)){
                    HashMap<String,String> datas = getData();
                    datas.put("is_saved", "1");
                    popupWindow.dismiss();
                    saveDataListener.isSaved(true, datas);
                }
            }
        });

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,String> datas = getData();
                popupWindow.dismiss();

                Intent intent = new Intent(activity, CameraSPKActivity.class);
                intent.putExtra("customerId",customerId);
                intent.putExtra("fileName",fileName);
                intent.putExtra("datas",datas);
                intent.putExtra("dataSPK",dataSPK);
                activity.startActivityForResult(intent,CameraUtility.REQUEST_IMAGE_CAPTURE_17);
            }
        });

        ivPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                File file = ImageUtility.createImageFile(customerId,fileName);
//                String path = file.getAbsolutePath();
//                HashMap<String,String> datas = getData();
//                CameraUtility.openCamera(activity, CameraUtility.REQUEST_IMAGE_CAPTURE_2,file);
//                popupWindow.dismiss();
//                saveDataListener.onTakePicture(path, UploadDocumentItem.DocumentType.SPK,datas);

                openCameraOrGallery(activity, popupWindow, getData(), customerId, fileName, UploadDocumentItem.DocumentType.SPK, CameraUtility.REQUEST_IMAGE_CAPTURE_2, saveDataListener);
            }
        });

        imgInputUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                File file = ImageUtility.createImageFile(customerId,fileName);
//                String path = file.getAbsolutePath();
//                HashMap<String,String> datas = getData();
//                CameraUtility.openCamera(activity, CameraUtility.REQUEST_IMAGE_CAPTURE_2,file);
//                popupWindow.dismiss();
//                saveDataListener.onTakePicture(path, UploadDocumentItem.DocumentType.SPK,datas);

                openCameraOrGallery(activity, popupWindow, getData(), customerId, fileName, UploadDocumentItem.DocumentType.SPK, CameraUtility.REQUEST_IMAGE_CAPTURE_2, saveDataListener);
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
                saveDataListener.isSaved(false, dataSPK);
            }
        });

        DialogUtility.relayoutPopupWindow(activity, cardView);

        popupWindow.showAtLocation(view, Gravity.CENTER, point.x, point.y);
        return popupWindow;
    }

    private static boolean validasi(Activity activity, String imagePath){
        boolean status = true;
        if(imagePath.equals("")){
            status = false;
            Toast.makeText(activity,"Foto SPK tidak boleh kosong",Toast.LENGTH_SHORT).show();
        } else if(edSpk.getText().toString().equals("")){
            status = false;
            Toast.makeText(activity,"No SPK tidak boleh kosong",Toast.LENGTH_SHORT).show();
        }
        return status;
    }

    private static HashMap<String,String> getData(){
        HashMap<String, String> datas = new HashMap<>();
        datas.put("spk",edSpk.getText().toString());
        return datas;
    }

    public static void openCameraOrGallery(final Activity activity, final PopupWindow popupWindow, final HashMap<String, String> datas, final String customerId, final String fileName, final UploadDocumentItem.DocumentType documentType, final int requestID, final PopupUploadSPK.SaveDataListener saveDataListener) {
        android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(activity);
        alert.setTitle("Upload Gambar")
                .setItems(new String[]{"Camera", "Gallery (Maksimal File Upload 500Kb)"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                File file = ImageUtility.createImageFile(customerId,fileName);
                                String path = file.getAbsolutePath();
                                CameraUtility.openCamera(activity, requestID, file);
                                popupWindow.dismiss();
                                saveDataListener.onTakePicture(path, documentType, datas);
                                break;
                            case 1:
                                popupWindow.dismiss();
                                saveDataListener.onTakePicture("", documentType, datas);
                                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                activity.startActivityForResult(galleryIntent, requestID);
                                break;
                        }
                    }
                });
        alert.create().show();
    }
}
