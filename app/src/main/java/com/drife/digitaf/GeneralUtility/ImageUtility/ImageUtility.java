package com.drife.digitaf.GeneralUtility.ImageUtility;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.GeneralUtility.TextUtility.TextUtility;
import com.drife.digitaf.R;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ImageUtility {

    static String TAG = ImageUtility.class.getSimpleName();
    /*default max size*/
    public static int maxSize = 500;
    /*default parent folder*/
    public static String parent = "Digitaf";
    /*default original size folder*/
    public static String folder = "/Documents";
    /*default compress size folder*/
    public static String compressfolder = "/compress";
    /*default file extension*/
    public static String type = ".jpg";

    static String path;

    public static int getMaxSize() {
        return maxSize;
    }

    public static void setMaxSize(int maxSize) {
        ImageUtility.maxSize = maxSize;
    }

    public static String getParent() {
        return parent;
    }

    public static void setParent(String parent) {
        ImageUtility.parent = parent;
    }

    public static String getFolder() {
        return folder;
    }

    public static void setFolder(String folder) {
        ImageUtility.folder = folder;
    }

    public static String getCompressfolder() {
        return compressfolder;
    }

    public static void setCompressfolder(String compressfolder) {
        ImageUtility.compressfolder = compressfolder;
    }

    public static String getType() {
        return type;
    }

    public static void setType(String type) {
        ImageUtility.type = type;
    }

    /*resize image size*/
    public static Bitmap resizeImage(Bitmap bitmap){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }

    /*check existing status of customer assets folder*/
    public static String getCustomerFolder(String customer_id){
        path = getPath();
        //create folder if not exist:
        File direct = new File(path);
        boolean status;
        if (!direct.exists()) {
            direct.mkdirs();
        }

        final String finalPath = path+customer_id;
        return finalPath;
    }

    /*check existing status of original image*/
    public static boolean checkImageFile(String customerId, String fileName){
        //path = getPath();

        //chek customer folder
        String dir = getCustomerFolder(customerId);
        //create folder if not exist:
        File direct = new File(dir);
        boolean status;
        if (!direct.exists()) {
            direct.mkdirs();
        }

        final String finalPath = dir;

        File file = new File(finalPath, fileName);
        if(!file.exists()){
            status = false;
        }else{
            status = true;
        }
        return status;
    }

    /*check existing status of small size image*/
    public static boolean checkSmallImageFile(String image_name){
        path = getCompressPath();
        //create folder if not exist:
        File direct = new File(path);
        boolean status;
        if (!direct.exists()) {
            direct.mkdirs();
        }

        final String finalPath = path;

        File file = new File(finalPath, image_name);
        if(!file.exists()){
            status = false;
        }else{
            status = true;
        }
        return status;
    }

    /*get path of original image folder*/
    public static String getPath(){
        File dirPath = Environment.getExternalStoragePublicDirectory(parent);
        path = dirPath.getAbsolutePath()+folder+"/";
        return path;
    }

    /*get path of small size image folder*/
    public static String getCompressPath(){
        File dirPath = Environment.getExternalStoragePublicDirectory(parent);
        path = dirPath.getAbsolutePath()+folder+ compressfolder +"/";
        return path;
    }

    /*check writable status of external storage*/
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /*create original image file*/
    public static File createImageFile(String customerId, String fileName){
        boolean imageStatus = checkImageFile(customerId,fileName);
        if(imageStatus != true){
            try {
                File dir = new File(path+customerId);
                File image = File.createTempFile(fileName,type,dir);
                return image;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }else {
            return null;
        }
    }

    /*generate image name*/
    public static String generateImageName(){
        int nameLen = 10;
        String name = TextUtility.randomString(nameLen)+"_";
        return name;
    }

    /*image loader configuration*/
    public static void configureImageLoader(Activity activity){
        ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(activity)
                .memoryCacheExtraOptions(500,500)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .memoryCacheSizePercentage(13)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .imageDownloader(new BaseImageDownloader(activity))
                .build();
        ImageLoader.getInstance().init(imageLoaderConfiguration);
    }

    public static void configureImageLoader(Context context){
        ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(500,500)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .memoryCacheSizePercentage(13)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .imageDownloader(new BaseImageDownloader(context))
                .build();
        ImageLoader.getInstance().init(imageLoaderConfiguration);
    }

    /*display image to imageview*/
    public static void displayImage(String uri, ImageView imageView){
        ImageLoader.getInstance().displayImage(uri,
                imageView, new DisplayImageOptions.Builder().cacheInMemory(true)
                        .resetViewBeforeLoading(true).build());
    }

    /*delete image from storage*/
    public static void deleteImage(String path){
        File file = new File(path);
        file.delete();
    }

    /*get bitmap from path*/
    public static Bitmap loadBitmapFromPath(String path){
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        /*try {
            return modifyOrientation(bitmap, path);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        return bitmap;
    }

    /*get name of image from path*/
    public static String getImageNameFromPath(String path){
        String[] split = path.split("/");
        String name = split[split.length-1];
        return name;
    }

    /*create small size image*/
    public static ArrayList<String> createSmallSizeImage(ArrayList<String> paths){
        ArrayList<String> compressPaths = new ArrayList<>();
        try {
            if(paths.size() != 0){
                for (int i=0; i<paths.size(); i++){
                    String path = paths.get(i);
                    Bitmap bitmap = ImageUtility.loadBitmapFromPath(path);
                    String newPath = ImageUtility.getCompressPath();
                    String name = ImageUtility.getImageNameFromPath(path);
                    boolean imageStatus = ImageUtility.checkSmallImageFile(name);
                    if(!imageStatus){
                        File file = new File(newPath,name);
                        file.createNewFile();
                        FileOutputStream ostream = new FileOutputStream(file);
                        Bitmap newBitmap = ImageUtility.resizeImage(bitmap);
                        newBitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
                        ostream.flush();
                        ostream.close();
                        compressPaths.add(file.getAbsolutePath());
                    }
                }

            }
        }catch (Exception e){
            LogUtility.logging(TAG,LogUtility.errorLog,"createSmallSizeImage",e.getMessage());
        }
        return compressPaths;
    }

    public static Bitmap getBitmapFromArray(byte[] data){
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        return bitmap;
    }

    public static boolean saveBitmapToFileStorage(File file, Bitmap bitmap){
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            return true;
        } catch (FileNotFoundException e) {
            LogUtility.logging(TAG,LogUtility.errorLog,"saveBitmapToFileStorage","FileNotFoundException",e.getMessage());
            return false;
        } catch (IOException e) {
            LogUtility.logging(TAG,LogUtility.errorLog,"saveBitmapToFileStorage","IOException",e.getMessage());
            return false;
        }
    }

    public static String encodeToBase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.PNG, 90, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public static Bitmap modifyOrientation(Bitmap bitmap, String image_absolute_path) throws IOException {
        ExifInterface ei = new ExifInterface(image_absolute_path);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotate(bitmap, 90);

            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotate(bitmap, 180);

            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotate(bitmap, 270);

            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                return flip(bitmap, true, false);

            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                return flip(bitmap, false, true);

            default:
                return bitmap;
        }
    }

    public static Bitmap rotate(Bitmap bitmap, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        try {
            Bitmap bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            return bm;
        }catch (Exception e){
            return null;
        }
        //return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap flip(Bitmap bitmap, boolean horizontal, boolean vertical) {
        Matrix matrix = new Matrix();
        matrix.preScale(horizontal ? -1 : 1, vertical ? -1 : 1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static File getImageFromGallery(Context context, Uri uri) {
        Uri selectedImage = uri;
        String[] filePathColumn = { MediaStore.Images.Media.DATA };

        Cursor cursor = context.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();

        String imgDecodableString;
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        imgDecodableString = cursor.getString(columnIndex);
        cursor.close();

        Log.d(TAG, "Data "+imgDecodableString);

        return new File(imgDecodableString);
    }
}
