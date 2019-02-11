package com.drife.digitaf.Module.Camera;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.drife.digitaf.GeneralUtility.DialogUtility.DialogUtility;
import com.drife.digitaf.GeneralUtility.ImageUtility.ImageUtility;
import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.GeneralUtility.OCR.Utility.OCRUtility;
import com.drife.digitaf.R;
import com.drife.digitaf.UIModel.ListOCRData;
import com.drife.digitaf.UIModel.OCRData;
import com.flurgle.camerakit.CameraListener;
import com.flurgle.camerakit.CameraView;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CameraSPKActivity extends AppCompatActivity{
    private String TAG = CameraSPKActivity.class.getSimpleName();

    @BindView(R.id.camera)
    CameraView cameraView;
    @BindView(R.id.lyCapture)
    LinearLayout lyCapture;
    @BindView(R.id.ivPreview)
    ImageView ivPreview;
    @BindView(R.id.lyDone)
    LinearLayout lyDone;

    private Bitmap bitmap;
    private ProgressDialog progressDialog;
    private String customerId = "";
    private String fileName = "";
    private HashMap<String,String> datas = new HashMap<>();
    private HashMap<String,String> dataSPK = new HashMap<>();
    List<OCRData> ocrResult = new ArrayList<>();
    String nomorSPK = "";
    boolean isDonScan = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_spk);
        ButterKnife.bind(this);
        initVariables();
        initListeners();
        callFunctions();
    }

    private void initVariables(){
        getIntentData();
    }

    private void initListeners(){
        //cameraKitView.requestPermissions(CameraActivity.this);
        cameraView.setCameraListener(new CameraListener() {
            @Override
            public void onPictureTaken(byte[] jpeg) {
                super.onPictureTaken(jpeg);

                bitmap = ImageUtility.getBitmapFromArray(jpeg);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lyDone.setVisibility(View.VISIBLE);
                        lyCapture.setVisibility(View.GONE);
                        ivPreview.setVisibility(View.VISIBLE);
                        ivPreview.setImageBitmap(bitmap);
                    }
                });

                FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmap);
                OCRUtility.executeOCRSpk(firebaseVisionImage, new OCRUtility.CallbackSPK() {
                    @Override
                    public void onSuccessCallback(String data) {
                        nomorSPK = data;
                        isDonScan = true;
                        Log.d(TAG, "Data OCR SPK "+JSONProcessor.toJSON(nomorSPK));
                    }

                    @Override
                    public void onFailed(String message) {
                        isDonScan = true;
                        Log.d(TAG, "Error OCR "+message);
                    }
                });
            }
        });

        lyCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraView.captureImage();
            }
        });

        lyDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isDonScan) {
                    List<OCRData> ocrDataList = new ArrayList<>();
                    ListOCRData listOCRData = new ListOCRData(ocrDataList);

                    Log.d(TAG, "Data OCR SPK1 " + nomorSPK);
                    //OCRData ocrData = new OCRData(data);
                    Intent intent = new Intent();
                    intent.putExtra("data", listOCRData);
                    intent.putExtra("datas", datas);
                    intent.putExtra("dataSPK", dataSPK);
                    intent.putExtra("nomor_spk", nomorSPK);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(CameraSPKActivity.this, "Proses scan masih berjalan. Mohon tunggu.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void callFunctions(){

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    protected void onPause() {
        cameraView.stop();
        super.onPause();
    }

    @Override
    protected void onStop() {
        cameraView.stop();
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void getIntentData(){
        customerId = getIntent().getStringExtra("customerId");
        fileName = getIntent().getStringExtra("fileName");
        datas = (HashMap<String, String>) getIntent().getSerializableExtra("datas");
        dataSPK = (HashMap<String, String>) getIntent().getSerializableExtra("dataSPK");
    }

    private String savePicture(Bitmap bitmap, String customerId,String fileName){
        File file = ImageUtility.createImageFile(customerId,fileName);
        String path = file.getAbsolutePath();
        Bitmap compressedBipmap = ImageUtility.resizeImage(bitmap);
        boolean save = ImageUtility.saveBitmapToFileStorage(file,compressedBipmap);
        if(save){
            return path;
        }else {
            return null;
        }
    }

    class savePicture extends AsyncTask<Void,Void,Void>{
        String imagePath = "";

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                imagePath = savePicture(bitmap,customerId,fileName);
            }catch (Exception e){
                LogUtility.logging(TAG,LogUtility.errorLog,"savePicture","Exception",e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                progressDialog.dismiss();
                List<OCRData> ocrDataList = ocrResult;
                ListOCRData listOCRData = new ListOCRData(ocrDataList);

                Log.d(TAG, "Data OCR SPK1 "+JSONProcessor.toJSON(ocrResult));
                //OCRData ocrData = new OCRData(data);
                Intent intent = new Intent();
                intent.putExtra("data", listOCRData);
                intent.putExtra("path", imagePath);
                intent.putExtra("datas",datas);
                intent.putExtra("dataSPK",dataSPK);
                setResult(RESULT_OK,intent);
                finish();
            }catch (Exception e){
                LogUtility.logging(TAG,LogUtility.errorLog,"savePicture","Exception",e.getMessage());
            }
        }
    }
}
