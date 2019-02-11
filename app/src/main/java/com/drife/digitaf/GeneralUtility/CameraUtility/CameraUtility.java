package com.drife.digitaf.GeneralUtility.CameraUtility;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;

import java.io.File;

public class CameraUtility {

    private static String TAG = CameraUtility.class.getSimpleName();

    public static final int REQUEST_IMAGE_CAPTURE_1 = 101;
    public static final int REQUEST_IMAGE_CAPTURE_2 = 102;
    public static final int REQUEST_IMAGE_CAPTURE_3 = 103;
    public static final int REQUEST_IMAGE_CAPTURE_4 = 104;
    public static final int REQUEST_IMAGE_CAPTURE_5 = 105;
    public static final int REQUEST_IMAGE_CAPTURE_6 = 106;
    public static final int REQUEST_IMAGE_CAPTURE_7 = 107;
    public static final int REQUEST_IMAGE_CAPTURE_8 = 108;
    public static final int REQUEST_IMAGE_CAPTURE_9 = 109;
    public static final int REQUEST_IMAGE_CAPTURE_10 = 110;
    public static final int REQUEST_IMAGE_CAPTURE_11 = 111;
    public static final int REQUEST_IMAGE_CAPTURE_12 = 112;
    public static final int REQUEST_IMAGE_CAPTURE_13 = 113;
    public static final int REQUEST_IMAGE_CAPTURE_14 = 114;
    public static final int REQUEST_IMAGE_CAPTURE_15 = 115;
    public static final int REQUEST_IMAGE_CAPTURE_16 = 116;
    public static final int REQUEST_IMAGE_CAPTURE_17 = 117; //For SPK request number take camera
    public static final int REQUEST_IMAGE_CAPTURE_18 = 118; //For CardName request number take camera
    public static final int REQUEST_IMAGE_CAPTURE_19 = 119;

    public static final int REQUEST_UBAHPROFILE_CAMERA = 1001;
    public static final int REQUEST_UBAHPROFILE_GALLERY = 1002;

    /*open camera to take the photo*/
    public static void openCamera(Activity activity, int REQUEST_CODE, File file) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            if(file != null){
                try {
                    Uri photoURI = FileProvider.getUriForFile(
                            activity,
                            "com.drife.digitaf.provider", //(use your app signature + ".provider" )
                            file);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    activity.startActivityForResult(takePictureIntent, REQUEST_CODE);
                }catch (Exception e){
                    LogUtility.logging(TAG, LogUtility.errorLog, "openCamera",e.getMessage());
                }
            }
        }
    }
}
