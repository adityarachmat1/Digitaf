package com.drife.digitaf.Module.Camera;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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

import com.drife.digitaf.GeneralUtility.ConnectionUtility.ConnectionUtility;
import com.drife.digitaf.GeneralUtility.DialogUtility.DialogUtility;
import com.drife.digitaf.GeneralUtility.ImageUtility.ImageUtility;
import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.GeneralUtility.OCR.Utility.OCRUtility;
import com.drife.digitaf.R;
import com.drife.digitaf.Service.Car.CarCallback;
import com.drife.digitaf.Service.OCR.OCRCallback;
import com.drife.digitaf.Service.OCR.OCRPresenter;
import com.drife.digitaf.UIModel.ListOCRData;
import com.drife.digitaf.UIModel.OCRData;
import com.drife.digitaf.retrofitmodule.ConnectionUrl;
import com.drife.digitaf.retrofitmodule.Model.CarModel;
import com.drife.digitaf.retrofitmodule.Model.OCRResult;
import com.drife.digitaf.retrofitmodule.SubmitParam.OCRParam;
import com.flurgle.camerakit.CameraListener;
import com.flurgle.camerakit.CameraView;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.nekoloop.base64image.Base64Image;
import com.nekoloop.base64image.RequestEncode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

public class CameraActivity extends AppCompatActivity{
    private String TAG = CameraActivity.class.getSimpleName();

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
    private HashMap<String,String> dataKTP = new HashMap<>();
    private OCRResult ocrResult;

    public Call call;
    Timer timer = new Timer();
    AlertDialog alertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
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

                        /*progressDialog = new ProgressDialog(CameraActivity.this);
                        progressDialog.setTitle("OCR Processing");
                        progressDialog.setMessage("Please wait...");
                        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
                        progressDialog.setCancelable(false);
                        progressDialog.show();*/
                    }
                });

                /*FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmap);
                OCRUtility.executeOCR(firebaseVisionImage, new OCRUtility.Callback() {
                    @Override
                    public void onSuccessCallback(List<OCRData> data) {
                        Log.d(TAG, "Data OCR "+JSONProcessor.toJSON(data));
                    }

                    @Override
                    public void onFailed(String message) {
                        Log.d(TAG, "Error OCR "+message);
                    }
                });*/

                Base64Image.with(CameraActivity.this)
                        .encode(bitmap)
                        .into(new RequestEncode.Encode() {
                                  @Override
                                  public void onSuccess(String s) {
                                      call = OCRPresenter.requestOCR(CameraActivity.this, s, new OCRCallback() {
                                          @Override
                                          public void onSuccessOCR(OCRResult result) {
                                              //progressDialog.dismiss();
                                              runOnUiThread(new Runnable() {
                                                  @Override
                                                  public void run() {
                                                      alertDialog.dismiss();
                                                  }
                                              });
                                              ocrResult = result;
                                              timer.cancel();
                                          }

                                          @Override
                                          public void onFailedOCR(String message) {
                                              //progressDialog.dismiss();
                                              runOnUiThread(new Runnable() {
                                                  @Override
                                                  public void run() {
                                                      alertDialog.dismiss();
                                                  }
                                              });

                                              Toast.makeText(CameraActivity.this, "OCR Processing Failed.", Toast.LENGTH_SHORT).show();
                                              timer.cancel();
                                          }
                                      });

                                      try {
                                          timer.schedule(
                                                  new java.util.TimerTask() {
                                                      @Override
                                                      public void run() {
                                                          // your code here
                                                          runOnUiThread(new Runnable() {
                                                              @Override
                                                              public void run() {
                                                                  if(call != null){
                                                                      call.cancel();
                                                                      //progressDialog.dismiss();
                                                                      runOnUiThread(new Runnable() {
                                                                          @Override
                                                                          public void run() {
                                                                              alertDialog.dismiss();
                                                                          }
                                                                      });
                                                                  }
                                                              }
                                                          });
                                                      }
                                                  },
                                                  30000
                                          );
                                      }catch (Exception e){

                                      }
                                  }

                                  @Override
                                  public void onFailure() {
                                      //progressDialog.dismiss();
                                      runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              alertDialog.dismiss();
                                          }
                                      });

                                      Toast.makeText(CameraActivity.this, "OCR Processing Failed.", Toast.LENGTH_SHORT).show();
                                  }
                              });

                final AlertDialog.Builder builder = new AlertDialog.Builder(CameraActivity.this)
                        .setTitle("Processing OCR")
                        .setMessage("Please wait...")
                        .setNegativeButton("CANCEL PROCESS", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                timer.cancel();
                                if(call != null){
                                    call.cancel();
                                }
                            }
                        });
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        alertDialog = builder.show();
                    }
                });
            }
        });

        lyCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    cameraView.captureImage();
                }catch (Exception e){
                    LogUtility.logging(TAG,LogUtility.errorLog,"lyCapture.setOnClickListener",e.getMessage());
                }
            }
        });

        lyDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = DialogUtility.createProgressDialog(CameraActivity.this,"","Please wait...");
                progressDialog.show();
                //imagePath = savePicture(bitmap,customerId,fileName);
                new savePicture().execute();

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
        dataKTP = (HashMap<String, String>) getIntent().getSerializableExtra("dataKTP");
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
                List<OCRData> ocrDataList = new ArrayList<>();
                ListOCRData listOCRData = new ListOCRData(ocrDataList);
                //OCRData ocrData = new OCRData(data);
                Intent intent = new Intent();
                intent.putExtra("data", listOCRData);
                intent.putExtra("path", imagePath);
                intent.putExtra("datas",datas);
                intent.putExtra("dataKTP",dataKTP);
                intent.putExtra("dataOCR", ocrResult);
                //intent.putExtra("test","singo");
                setResult(RESULT_OK,intent);
                finish();
                /*if(ConnectionUtility.isConnected()){
                    FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmap);
                    OCRUtility.executeOCR(firebaseVisionImage, new OCRUtility.Callback() {

                        @Override
                        public void onSuccessCallback(List<OCRData> data) {
                            progressDialog.dismiss();
                            List<OCRData> ocrDataList = data;
                            ListOCRData listOCRData = new ListOCRData(ocrDataList);
                            //OCRData ocrData = new OCRData(data);
                            Intent intent = new Intent();
                            intent.putExtra("data", listOCRData);
                            intent.putExtra("path", imagePath);
                            //intent.putExtra("test","singo");
                            setResult(RESULT_OK,intent);
                            finish();
                        }

                        @Override
                        public void onFailed(String message) {
                            progressDialog.dismiss();
                            LogUtility.logging(TAG,LogUtility.errorLog,"savePicture","executeOCR",message);
                            finish();
                        }
                    });
                }else{
                    progressDialog.dismiss();
                    LogUtility.logging(TAG,LogUtility.errorLog,"savePicture","is not connected");
                    Intent intent = new Intent();
                    intent.putExtra("path", imagePath);
                    setResult(RESULT_CANCELED,intent);
                    finish();
                }*/
            }catch (Exception e){
                LogUtility.logging(TAG,LogUtility.errorLog,"savePicture","Exception",e.getMessage());
            }
        }
    }
}
