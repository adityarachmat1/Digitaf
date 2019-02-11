package com.drife.digitaf.GeneralUtility.PermissionUtility;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class PermissionUtility {
    public interface PermissionsCallback {
        void onRequestGranted();
    }

    public static void permissionHandling(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23)
        {
            if (checkPermission(activity) && checkPermissionCamera(activity) && checkPermissionLocation(activity))
            {
                // Code for above or equal 23 API Oriented Device
                // Your Permission granted already .Do next code
            } else {
                requestPermission(activity); // Code for permission
            }
        }
        else
        {

            // Code for Below 23 API Oriented Device
            // Do next code
        }
    }

    public static void permissionHandling(Activity activity, PermissionsCallback permissionsCallback) {
        if (Build.VERSION.SDK_INT >= 23)
        {
            if (checkPermission(activity) && checkPermissionCamera(activity) && checkPermissionLocation(activity))
            {
                // Code for above or equal 23 API Oriented Device
                // Your Permission granted already .Do next code
                permissionsCallback.onRequestGranted();
            } else {
                requestPermission(activity); // Code for permission
            }
        }
        else
        {

            // Code for Below 23 API Oriented Device
            // Do next code
        }
    }

    public static boolean checkPermission(Activity activity) {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkPermissionCamera(Activity activity){
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkPermissionLocation(Activity activity){
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private static void requestPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        /*Manifest.permission.GET_ACCOUNTS,*/
                        Manifest.permission.ACCESS_FINE_LOCATION/*,
                        Manifest.permission.RECORD_AUDIO*/},
                1);
    }
}
