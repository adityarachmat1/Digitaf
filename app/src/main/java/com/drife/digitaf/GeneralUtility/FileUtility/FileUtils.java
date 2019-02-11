package com.drife.digitaf.GeneralUtility.FileUtility;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileUtils {
    static String TAG = FileUtils.class.getSimpleName();

    public static String getPathPDF(Context context, Intent data) {
        Uri uri = data.getData();
        String selectedFilePath = FilePath.getPath(context, uri);
        if (selectedFilePath != null) {
            return selectedFilePath;
        } else {
            Toast.makeText(context, "Tidak dapat memuat dokumen.", Toast.LENGTH_LONG).show();
        }

        return "";
    }

    public static String getBase64StringPdf(String path) {
        final File file = new File(path);

        byte[] dataFilePDF = new byte[(int) file.length()];
        try {
            new FileInputStream(file).read(dataFilePDF);

            return Base64.encodeToString(dataFilePDF, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();

            return "";
        }
    }

    public static void createFile(String path) {
        byte[] newBase64 = Base64.decode(FileUtils.getBase64StringPdf(path), 0);
        try (OutputStream stream = new FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/test.pdf")) {
            stream.write(newBase64);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
