package com.drife.digitaf.Module.Offline;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.Module.InputKredit.MinimumCustomerData.Activity.MinimumCUSDATADraft;
import com.drife.digitaf.Module.myapplication.childfragment.DraftFragment;
import com.drife.digitaf.ORM.Controller.Outbox_submitController;
import com.drife.digitaf.ORM.Database.Draft;
import com.drife.digitaf.ORM.Database.Outbox_submit;
import com.drife.digitaf.Service.Submit.SubmitCallback;
import com.drife.digitaf.Service.Submit.SubmitPresenter;
import com.drife.digitaf.retrofitmodule.ConnectionUrl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SubmitService extends Service {
    private final IBinder mBinder = new MyBinder();
    private String TAG = SubmitService.class.getSimpleName();
    List<Outbox_submit> outbox_submits = new ArrayList<>();
    private String status = "";
    private int index = 0;
    private int id_index = 0;
    private Long id;
    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        addResultValues();
        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        addResultValues();
        return mBinder;
    }

    public class MyBinder extends Binder {
        public SubmitService getService() {
            return SubmitService.this;
        }
    }

    private void addResultValues() {
        outbox_submits = Outbox_submitController.getOutboxsumbit();

        sendInquiry(index);
    }

    private void sendInquiry(final int indexSend) {
        if (outbox_submits.size() > 0 && indexSend < outbox_submits.size()) {
            Log.d("status", "inquiry");
            Log.d("getForm", outbox_submits.get(indexSend).getForm());
            Log.d("getAttach", outbox_submits.get(indexSend).getAttach());
            Log.d("getUser_id", outbox_submits.get(indexSend).getUser_id());
            Log.d("getAttachPDF", outbox_submits.get(indexSend).getAttachPDF());

//            new AsyncLogin().execute("inquiry",outbox_submits.get(indexSend).getForm(), outbox_submits.get(indexSend).getAttach(), outbox_submits.get(indexSend).getAttachPDF(), outbox_submits.get(indexSend).getUser_id());

            SubmitPresenter.submit(SubmitService.this, new SubmitCallback() {
                        @Override
                        public void onSuccessSubmit(String formId) {
                            if (MinimumCUSDATADraft.id_.equals(Long.valueOf(0))){
                                Log.d("_id", "bukandraft");
                            }else {
                                Draft draft = Draft.findById(Draft.class, MinimumCUSDATADraft.id_);
                                draft.delete();
                                MinimumCUSDATADraft.id_ = Long.valueOf(0);
                                MinimumCUSDATADraft.user_id_ = "";
                            }
                            if (formId.equals("null")){
                                sendInquiry(index);
                            }else {
                                id = outbox_submits.get(indexSend).getId();
                                Outbox_submit outbox_submit1 = Outbox_submit.findById(Outbox_submit.class, id);
                                outbox_submit1.delete();
                                index = index + 1;
                            }
                        }

                            @Override
                            public void onFailedSubmit(String message) {
                                if (MinimumCUSDATADraft.id_.equals(Long.valueOf(0))){
                                    Log.d("_id", "bukandraft");
                                }else {
                                    Draft draft = Draft.findById(Draft.class, MinimumCUSDATADraft.id_);
                                    draft.delete();
                                    MinimumCUSDATADraft.id_ = Long.valueOf(0);
                                    MinimumCUSDATADraft.user_id_ = "";
                                }
                                Long ids = outbox_submits.get(indexSend).getId();
                                Outbox_submit outbox_submit1 = Outbox_submit.findById(Outbox_submit.class, ids);
                                outbox_submit1.setStatus("failed");
                                outbox_submit1.save();
                                Log.d("failedSumbit", message);
                                index = index + 1;
                                sendInquiry(index);
                        }
                    },
                    "inquiry", outbox_submits.get(indexSend).getForm(),  outbox_submits.get(indexSend).getAttach(), outbox_submits.get(indexSend).getUser_id(),outbox_submits.get(indexSend).getAttachPDF());

//            SubmitPresenter.submit(SubmitService.this, new SubmitCallback() {
//                        @Override
//                        public void onSuccessSubmit(String formId) {
//
//                            id = outbox_submits.get(indexSend).getId();
//                            Outbox_submit outbox_submit1 = Outbox_submit.findById(Outbox_submit.class, id);
//                            outbox_submit1.delete();
//                            index = index + 1;
//
//                            sendInquiry(index);
//
//                            Log.d(TAG, "Sukses "+indexSend);
//                        }
//
//                        @Override
//                        public void onFailedSubmit(String message) {
//                            Long ids = outbox_submits.get(indexSend).getId();
//                            Outbox_submit outbox_submit1 = Outbox_submit.findById(Outbox_submit.class, ids);
//                            outbox_submit1.setStatus("failed");
//                            outbox_submit1.save();
//                            Log.d("failedSumbit", message);
//                            index = index + 1;
//                            sendInquiry(index);
//
//                            Log.d(TAG, "Gagal "+indexSend);
//                        }
//                    },
//                    "inquiry", outbox_submits.get(indexSend).getForm(), outbox_submits.get(indexSend).getAttach(), outbox_submits.get(indexSend).getAttachPDF(),
//                    "", "", "", "", "", "", outbox_submits.get(indexSend).getUser_id());
        } else {
            index = 0;
        }
    }


//    private class AsyncLogin extends AsyncTask<String, String, String> {
//        HttpURLConnection conn;
//        URL url = null;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            try {
//
//                // Enter URL address where your php file resides
//                url = new URL(ConnectionUrl.SUBMIT);
//
//            } catch (MalformedURLException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//                return "exception";
//            }
//            try {
//
//                String token = SharedPreferenceUtils.getToken(getApplicationContext());
//
//                conn = (HttpURLConnection) url.openConnection();
//                conn.setRequestProperty("Authorization", "Bearer "+token);
//                conn.setReadTimeout(READ_TIMEOUT);
//                conn.setConnectTimeout(CONNECTION_TIMEOUT);
//                conn.setRequestMethod("POST");
//
//                conn.setDoInput(true);
//                conn.setDoOutput(true);
//
//                Uri.Builder builder = new Uri.Builder()
//                        .appendQueryParameter("status", params[0])
//                        .appendQueryParameter("params", params[1])
//                        .appendQueryParameter("image", params[2])
//                        .appendQueryParameter("pdf", params[3])
//                        .appendQueryParameter("form_id", params[4]);
//                String query = builder.build().getEncodedQuery();
//
//                OutputStream os = conn.getOutputStream();
//                BufferedWriter writer = new BufferedWriter(
//                        new OutputStreamWriter(os, "UTF-8"));
//                writer.write(query);
//                writer.flush();
//                writer.close();
//                os.close();
//                conn.connect();
//
//            } catch (IOException e1) {
//                // TODO Auto-generated catch block
//                e1.printStackTrace();
//                return "exception";
//            }
//
//            try {
//
//                int response_code = conn.getResponseCode();
//
//                if (response_code == HttpURLConnection.HTTP_OK) {
//
//                    // Read data sent from server
//                    InputStream input = conn.getInputStream();
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
//                    StringBuilder result = new StringBuilder();
//                    String line;
//
//                    while ((line = reader.readLine()) != null) {
//                        result.append(line);
//                    }
//
//                    // Pass data to onPostExecute method
//                    return (result.toString());
//
//                } else {
//
//                    return ("unsuccessful");
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//                return "Timeout server";
//            } finally {
//                conn.disconnect();
//            }
//
//
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            Log.d("result", result);
//
//        }
//    }

}
