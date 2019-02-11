package com.drife.digitaf.GeneralUtility.OCR.Utility;

import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;

import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.GeneralUtility.OCRHelper.ListOfInfo;
import com.drife.digitaf.UIModel.OCRData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.google.firebase.ml.vision.text.RecognizedLanguage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OCRUtility {
    private static String TAG = OCRUtility.class.getSimpleName();

    public interface Callback{
        void onSuccessCallback(List<OCRData> data);
        void onFailed(String message);
    }

    public interface CallbackSPK{
        void onSuccessCallback(String spkNomor);
        void onFailed(String message);
    }

    public interface CallbackCardName{
        void onSuccessCallback(HashMap<String, String> ocrData);
        void onFailed(String message);
    }

    private static int xTolerance = 80;
    private static int yTolerance = 30;
    private static Point pointNIK = new Point(582,345);
    private static Point pointNama = new Point(680,463);
    private static Point pointTTL = new Point(1082,471);
    private static Point pointJenisKelamin = new Point(670,592);
    private static Point pointTanggalLahir = new Point(1080,514);
    private static Point pointTempatLahir = new Point(670,514);
    private static Point pointGolonganDarah = new Point(1455,580);
    private static Point pointAlamat = new Point(682,630);
    private static Point pointRTRW = new Point(695,699);
    private static Point pointKecamatan = new Point(647,805);
    private static Point pointAgama = new Point(695,874);
    private static Point pointStatusPerkawinan = new Point(694,931);
    private static Point pointKewarganegaraan = new Point(693,1046);
    private static Point pointBerlakuHingga = new Point(677,1100);
    private static Point pointDesa = new Point(670,770);

    public static void executeOCR(FirebaseVisionImage firebaseVisionImage, final Callback callback){
        String text = "";
        final String changeLine = "\n";
        /*FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance()
                .getOnDeviceTextRecognizer();*/

        FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance()
                .getCloudTextRecognizer();

        final List<OCRData> ocrDatas = new ArrayList<>();

        Task<FirebaseVisionText> result =
                detector.processImage(firebaseVisionImage)
                        .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                            @Override
                            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                                // Task completed successfully
                                String res = firebaseVisionText.getText();
                                LogUtility.logging(TAG,LogUtility.infoLog,"executeOCR","res",res);

                                String txt = "";
                                for (FirebaseVisionText.TextBlock block: firebaseVisionText.getTextBlocks()) {
                                    String blockText = block.getText();
                                    Float blockConfidence = block.getConfidence();
                                    List<RecognizedLanguage> blockLanguages = block.getRecognizedLanguages();
                                    Point[] blockCornerPoints = block.getCornerPoints();
                                    Rect blockFrame = block.getBoundingBox();
                                    //LogUtility.logging(TAG,LogUtility.infoLog,"executeOCR","blockText", JSONProcessor.toJSON(block.getLines()));
                                    String jsonData = JSONProcessor.toJSON(block.getLines());

                                    List<HashMap<String,String>> data = parseValueLev1(jsonData);
                                    OCRData ocrData = new OCRData(data);
                                    ocrDatas.add(ocrData);

                                    if(data !=null && data.size() != 0){
                                        //LogUtility.logging(TAG,LogUtility.infoLog,"executeOCR","data", JSONProcessor.toJSON(data));
                                        //callback.onSuccessCallback(data);
                                    }
                                }

                                LogUtility.logging(TAG,LogUtility.infoLog,"executeOCR1","ocr",JSONProcessor.toJSON(ocrDatas));

                                callback.onSuccessCallback(ocrDatas);
                            }
                        })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        LogUtility.logging(TAG,LogUtility.errorLog,"executeOCR",e.getMessage());
                                        callback.onFailed(e.getMessage());
                                    }
                                });
    }

    public static void executeOCRSpk(FirebaseVisionImage firebaseVisionImage, final CallbackSPK callback){
        String text = "";
        FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance()
                .getCloudTextRecognizer();

        Task<FirebaseVisionText> result =
                detector.processImage(firebaseVisionImage)
                        .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                            @Override
                            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                                // Task completed successfully
                                String res = firebaseVisionText.getText();
                                Pattern numberPat = Pattern.compile("\\d+");
                                Matcher matcher1 = numberPat.matcher(res);

                                LogUtility.logging(TAG,LogUtility.infoLog,"executeOCR","res",res);
                                LogUtility.logging(TAG,LogUtility.infoLog,"executeOCR","res",JSONProcessor.toJSON(res.split("\n")));

                                if (res.length() > 0) {
                                    if (res.split("\n").length > 0) {
                                        boolean find = false;
                                        for (int i = 0;i < res.split("\n").length;i++) {
                                            String data = res.split("\n")[i];
                                            LogUtility.logging(TAG,LogUtility.infoLog,"executeOCR","loop",data+" "+stringContainsNumber(data));
                                            if (stringContainsNumber(data) && !find) {
                                                find = true;
                                                callback.onSuccessCallback(cleanNomorSPK(data));
                                            }
                                        }
                                    }
                                }
                            }
                        })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        LogUtility.logging(TAG,LogUtility.errorLog,"executeOCR",e.getMessage());
                                        callback.onFailed(e.getMessage());
                                    }
                                });
    }

    public static void executeOCRCardName(FirebaseVisionImage firebaseVisionImage, final CallbackCardName callback){
        FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance()
                .getCloudTextRecognizer();

        Task<FirebaseVisionText> result =
                detector.processImage(firebaseVisionImage)
                        .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                            @Override
                            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                                String res = firebaseVisionText.getText();
                                LogUtility.logging(TAG,LogUtility.infoLog,"executeOCR","res",res);

                                ListOfInfo.startScan(firebaseVisionText, new ListOfInfo.ScanKartuNamaListener() {
                                    @Override
                                    public void onSuccessScan(HashMap<String, String> datas) {
                                        callback.onSuccessCallback(datas);
                                    }
                                });
                            }
                        })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        LogUtility.logging(TAG,LogUtility.errorLog,"executeOCR",e.getMessage());
                                        callback.onFailed(e.getMessage());
                                    }
                                });
    }

    public static boolean stringContainsNumber( String s ) {
        Pattern p = Pattern.compile( "[0-9]" );
        Matcher m = p.matcher( s );

        return m.find();
    }

    public static String cleanNomorSPK(String spk) {
        return spk.replace("No", "").replace("No ", "").replace("No. ", "").replace("Nomor ", "").replace("no. ", "").replace("nomor ", "").replace("No.", "").replace("Nomor", "").replace("no.", "").replace("nomor", "").replace("mor", "");
    }

    public static boolean inRange(Point point1, Point point2){
        boolean status = true;
        int xDiff = 0;
        int yDiff = 0;
        /*count xPoint difference*/
        if(point1.x > point2.x){
            xDiff = point1.x-point2.x;
        }else if(point1.x < point2.x){
            xDiff = point2.x-point1.x;
        }
        if(xDiff>xTolerance){
            status = false;
        }

        /*count yPoint difference*/
        if(point1.y > point2.y){
            yDiff = point1.y-point2.y;
        }else if(point1.y < point2.y){
            yDiff = point2.y-point1.y;
        }
        if(yDiff>yTolerance){
            status = false;
        }

        //LogUtility.logging(TAG,LogUtility.infoLog,"executeOCR","inRange",status+"");
        return status;
    }

    public static List<HashMap<String,String>> parseValueLev1(String json){
        List<HashMap<String,String>> values = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            if(jsonArray != null){
                for (int i=0; i<jsonArray.length(); i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    JSONArray zzabx = object.getJSONArray("zzabx");
                    String textLevel1 = object.getString("text");

                    JSONObject zzzxLev1 = object.getJSONObject("zzzx");
                    int topLev1 = zzzxLev1.getInt("top");
                    int leftLev1 = zzzxLev1.getInt("left");
                    if(checkBlock(topLev1,leftLev1,pointNIK.y,pointNIK.x,150,yTolerance)){
                        HashMap<String,String> datas = new HashMap<>();
                        datas.put("value",textLevel1);
                        datas.put("key","nik");
                        values.add(datas);
                    }else if(checkBlock(topLev1,leftLev1,pointNama.y,pointNama.x,xTolerance,yTolerance)){
                        HashMap<String,String> datas = new HashMap<>();
                        datas.put("value",textLevel1);
                        datas.put("key","nama");
                        values.add(datas);
                    }else if(checkBlock(topLev1,leftLev1,pointTanggalLahir.y,pointTanggalLahir.x,xTolerance,yTolerance)){
                        HashMap<String,String> datas = new HashMap<>();
                        datas.put("value",textLevel1);
                        datas.put("key","tanggallahir");
                        values.add(datas);
                    }else if(checkBlock(topLev1,leftLev1,pointTempatLahir.y,pointTempatLahir.x,xTolerance,yTolerance)){
                        HashMap<String,String> datas = new HashMap<>();
                        datas.put("value",textLevel1);
                        datas.put("key","tempatlahir");
                        values.add(datas);
                    }else if(checkBlock(topLev1,leftLev1,pointGolonganDarah.y,pointGolonganDarah.x,xTolerance,yTolerance)){
                        HashMap<String,String> datas = new HashMap<>();
                        datas.put("value",textLevel1);
                        datas.put("key","golongandarah");
                        values.add(datas);
                    }else if(checkBlock(topLev1,leftLev1,pointAlamat.y,pointAlamat.x,xTolerance,yTolerance)){
                        HashMap<String,String> datas = new HashMap<>();
                        datas.put("value",textLevel1);
                        datas.put("key","alamat");
                        values.add(datas);
                    }else if(checkBlock(topLev1,leftLev1,pointRTRW.y,pointRTRW.x,xTolerance,yTolerance)){
                        HashMap<String,String> datas = new HashMap<>();
                        datas.put("value",textLevel1);
                        datas.put("key","rtrw");
                        values.add(datas);
                    }else if(checkBlock(topLev1,leftLev1,pointKecamatan.y,pointKecamatan.x,xTolerance,yTolerance)){
                        HashMap<String,String> datas = new HashMap<>();
                        datas.put("value",textLevel1);
                        datas.put("key","kecamatan");
                        values.add(datas);
                    }else if(checkBlock(topLev1,leftLev1,pointAgama.y,pointAgama.x,xTolerance,yTolerance)){
                        HashMap<String,String> datas = new HashMap<>();
                        datas.put("value",textLevel1);
                        datas.put("key","agama");
                        values.add(datas);
                    }else if(checkBlock(topLev1,leftLev1,pointStatusPerkawinan.y,pointStatusPerkawinan.x,xTolerance,yTolerance)){
                        HashMap<String,String> datas = new HashMap<>();
                        datas.put("value",textLevel1);
                        datas.put("key","statusperkawinan");
                        values.add(datas);
                    }else if(checkBlock(topLev1,leftLev1,pointKewarganegaraan.y,pointKewarganegaraan.x,xTolerance,yTolerance)){
                        HashMap<String,String> datas = new HashMap<>();
                        datas.put("value",textLevel1);
                        datas.put("key","kewarganegaraan");
                        values.add(datas);
                    }else if(checkBlock(topLev1,leftLev1,pointBerlakuHingga.y,pointBerlakuHingga.x,xTolerance,yTolerance)){
                        HashMap<String,String> datas = new HashMap<>();
                        datas.put("value",textLevel1);
                        datas.put("key","berlakuhingga");
                        values.add(datas);
                    }else if(checkBlock(topLev1,leftLev1,pointDesa.y,pointDesa.x,xTolerance,yTolerance)){
                        HashMap<String,String> datas = new HashMap<>();
                        datas.put("value",textLevel1);
                        datas.put("key","desa");
                        values.add(datas);
                    }else if(checkBlock(topLev1,leftLev1,pointJenisKelamin.y,pointJenisKelamin.x,xTolerance,yTolerance)){
                        HashMap<String,String> datas = new HashMap<>();
                        datas.put("value",textLevel1);
                        datas.put("key","jeniskelamin");
                        values.add(datas);
                    }else{
                        List<paramLev2> lev2 = parseValueLev2(zzabx);
                        if(lev2 != null && lev2.size() != 0){
                            for (int k=0; k<lev2.size(); k++){
                                //datas.put(lev2.get(k).type,lev2.get(k).value);
                                HashMap<String,String> datas = new HashMap<>();
                                datas.put("value",lev2.get(k).value);
                                datas.put("key",lev2.get(k).type);
                                values.add(datas);
                            }
                        }
                    }
                }
            }
        }catch (JSONException e){
            LogUtility.logging(TAG,LogUtility.errorLog,"parseValue","JSONException",e.getMessage());
        }
        return values;
    }

    public static List<paramLev2> parseValueLev2(JSONArray zzabx){
        List<paramLev2> datas = new ArrayList<>();
        try {
            if(zzabx != null && zzabx.length() != 0){
                for (int j=0; j<zzabx.length(); j++){
                    JSONObject obj = zzabx.getJSONObject(j);
                    String text = obj.getString("text");
                    JSONObject zzzx = obj.getJSONObject("zzzx");
                    int bottom = zzzx.getInt("bottom");
                    int left = zzzx.getInt("left");
                    int right = zzzx.getInt("right");
                    int top = zzzx.getInt("top");

                    if(checkBlock(top,left,pointNIK.y,pointNIK.x,150,yTolerance)){
                        paramLev2 lev2 = new paramLev2("nik",text);
                        datas.add(lev2);
                    }else if(checkBlock(top,left,pointNIK.y,pointNIK.x,xTolerance,yTolerance)){
                        paramLev2 lev2 = new paramLev2("nama",text);
                        datas.add(lev2);
                    }else if(checkBlock(top,left,pointTanggalLahir.y,pointTanggalLahir.x,xTolerance,yTolerance)){
                        paramLev2 lev2 = new paramLev2("tanggallahir",text);
                        datas.add(lev2);
                    }else if(checkBlock(top,left,pointTempatLahir.y,pointTempatLahir.x,xTolerance,yTolerance)){
                        paramLev2 lev2 = new paramLev2("tempatlahir",text);
                        datas.add(lev2);
                    }else if(checkBlock(top,left,pointGolonganDarah.y,pointGolonganDarah.x,xTolerance,yTolerance)){
                        paramLev2 lev2 = new paramLev2("golongandarah",text);
                        datas.add(lev2);
                    }else if(checkBlock(top,left,pointAlamat.y,pointAlamat.x,xTolerance,yTolerance)){
                        paramLev2 lev2 = new paramLev2("alamat",text);
                        datas.add(lev2);
                    }else if(checkBlock(top,left,pointRTRW.y,pointRTRW.x,xTolerance,yTolerance)){
                        paramLev2 lev2 = new paramLev2("rtrw",text);
                        datas.add(lev2);
                    }else if(checkBlock(top,left,pointKecamatan.y,pointKecamatan.x,xTolerance,yTolerance)){
                        paramLev2 lev2 = new paramLev2("kecamatan",text);
                        datas.add(lev2);
                    }else if(checkBlock(top,left,pointAgama.y,pointAgama.x,xTolerance,yTolerance)){
                        paramLev2 lev2 = new paramLev2("agama",text);
                        datas.add(lev2);
                    }else if(checkBlock(top,left,pointStatusPerkawinan.y,pointStatusPerkawinan.x,xTolerance,yTolerance)){
                        paramLev2 lev2 = new paramLev2("statusPerkawinan",text);
                        datas.add(lev2);
                    }else if(checkBlock(top,left,pointKewarganegaraan.y,pointKewarganegaraan.x,xTolerance,yTolerance)){
                        paramLev2 lev2 = new paramLev2("kewarganegaraan",text);
                        datas.add(lev2);
                    }else if(checkBlock(top,left,pointBerlakuHingga.y,pointBerlakuHingga.x,xTolerance,yTolerance)){
                        paramLev2 lev2 = new paramLev2("berlakuHingga",text);
                        datas.add(lev2);
                    }else if(checkBlock(top,left,pointDesa.y,pointDesa.x,xTolerance,yTolerance)){
                        paramLev2 lev2 = new paramLev2("desa",text);
                        datas.add(lev2);
                    }else if(checkBlock(top,left,pointJenisKelamin.y,pointJenisKelamin.x,xTolerance,yTolerance)){
                        paramLev2 lev2 = new paramLev2("jeniskelamin",text);
                        datas.add(lev2);
                    }
                }
            }
        }catch (JSONException e){
            LogUtility.logging(TAG,LogUtility.errorLog,"parseValueLev2","JSONException",e.getMessage());
        }
        return datas;
    }

    public static boolean checkBlock(int top, int left, int topPoint, int leftPoint, int toleranceX,int toleranceY){
        boolean stat = true;
        int topDiff = 0;
        int leftDiff = 0;

        if(topPoint>top){
            topDiff = topPoint-top;
        }else if(topPoint<top){
            topDiff = top-topPoint;
        }
        if(topDiff>toleranceY){
            stat = false;
        }

        if(leftPoint>left){
            leftDiff = leftPoint-left;
        }else if(leftPoint<left){
            leftDiff = left-leftPoint;
        }
        if(leftDiff>toleranceX){
            stat = false;
        }

        return stat;
    }

    static class paramLev2{
        private String type;
        private String value;

        public paramLev2() {
        }

        public paramLev2(String type, String value) {
            this.type = type;
            this.value = value;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
