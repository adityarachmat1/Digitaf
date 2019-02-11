package com.drife.digitaf.GeneralUtility.ZipConverter;

import com.drife.digitaf.GeneralUtility.ImageUtility.ImageUtility;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipConverter {
    private static int BUFFER = 1024;

    /*zipping file*/
    public static void zip(ArrayList<String> _files, String zipFilePath) {
        try {
            BufferedInputStream origin;
            FileOutputStream dest = new FileOutputStream(zipFilePath);
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));

            byte data[] = new byte[BUFFER];

            for (int i = 0; i < _files.size(); i++) {
                FileInputStream fi = new FileInputStream(_files.get(i));
                origin = new BufferedInputStream(fi, BUFFER);
                ZipEntry entry = new ZipEntry(_files.get(i).substring(_files.get(i).lastIndexOf("/") + 1));
                out.putNextEntry(entry);
                int count;
                while ((count = origin.read(data, 0, BUFFER)) != -1) {
                    out.write(data, 0, count);
                }
                origin.close();
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*create file direction*/
    public static File getOutputZipFile(String fileName) {
        File mediaStorageDir = new File(ImageUtility.getCompressPath());
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        return new File(mediaStorageDir.getPath() + File.separator + fileName);
    }

    /*compress file*/
    public static String compressFile(ArrayList<String> paths, String zipName){
        ArrayList<String> mFilePathList = paths;
        File file = getOutputZipFile(zipName);
        String zipFilePath = "";
        if (file != null) {
            zipFilePath = file.getAbsolutePath();
            if (mFilePathList.size() > 0) {
                zip(mFilePathList, zipFilePath);
            }
        }
        return zipFilePath;
    }
}
