package com.drife.digitaf.Module.myapplication.childfragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.calendardatepicker.MonthAdapter;
import com.drife.digitaf.GeneralUtility.DialogUtility.DialogUtility;
import com.drife.digitaf.GeneralUtility.FileUtility.FileUtils;
import com.drife.digitaf.GeneralUtility.ImageUtility.ImageUtility;
import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.Module.InputKredit.UploadDocument_v2.Activity.UploadDocumentActivity;
import com.drife.digitaf.Module.myapplication.adapter.OutboxListAdapter;
import com.drife.digitaf.Module.myapplication.model.OutboxItem;
import com.drife.digitaf.ORM.Controller.Outbox_submitController;
import com.drife.digitaf.ORM.Database.Outbox_submit;
import com.drife.digitaf.R;
import com.drife.digitaf.Service.Submit.SubmitCallback;
import com.drife.digitaf.Service.Submit.SubmitPresenter;
import com.drife.digitaf.UIModel.UserSession;
import com.drife.digitaf.retrofitmodule.SubmitParam.DocTcData;
import com.drife.digitaf.retrofitmodule.SubmitParam.ImageAttachment;
import com.drife.digitaf.retrofitmodule.SubmitParam.ImageAttachmentOffline;
import com.drife.digitaf.retrofitmodule.SubmitParam.PdfAttachment;
import com.google.gson.Gson;
import com.nekoloop.base64image.Base64Image;
import com.nekoloop.base64image.RequestEncode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ferdinandprasetyo on 10/1/18.
 */

public class OutboxFragment extends Fragment {
    private static final String TAG = OutboxFragment.class.getSimpleName();

    private ImageView imgSearch;
    private EditText edSearch;
    private RecyclerView rvOutbox;
    private ProgressBar pbOutbox;
    private OutboxListAdapter outboxListAdapter;
    private SwipeRefreshLayout lyRefresh;
    private List<Outbox_submit> listItems;
    private RelativeLayout lyFrom, lyTo;
    private TextView tvFrom, tvTo;
    private List<Outbox_submit> outbox_submits = new ArrayList<>();
    private ProgressDialog progressDialog;
    UserSession userSession;
    int REQUEST_CODE_UPDATE = 1;
    private List<ImageAttachment> imageAttachmentList = new ArrayList<>();
    List<ImageAttachment> imageAttachments = new ArrayList<>();
    private List<PdfAttachment> AttachmentListpdf = new ArrayList<>();
    List<PdfAttachment> Attachmentspdf = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.r_fragment_outbox, container, false);
        progressDialog = DialogUtility.createProgressDialog(getActivity(),"","Please wait...");
        initLayout(view);

        return view;
    }

    private void initLayout(View view) {
        pbOutbox = view.findViewById(R.id.pbOutbox);
        pbOutbox.setVisibility(View.GONE);
        imgSearch = view.findViewById(R.id.imgSearch);
        edSearch = view.findViewById(R.id.edSearch);
        rvOutbox = view.findViewById(R.id.rvOutbox);
        lyRefresh = view.findViewById(R.id.lyRefresh);
        lyFrom = view.findViewById(R.id.lyFrom);
        lyTo = view.findViewById(R.id.lyTo);

        userSession = SharedPreferenceUtils.getUserSession(getActivity());

        listItems = new ArrayList<>();
        getOutboxList();

        lyRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getOutboxList();
            }
        });

        lyFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((UploadDocumentActivity)activity).showDatePicker();
                Calendar calendar = Calendar.getInstance();
                MonthAdapter.CalendarDay currDate = new MonthAdapter.CalendarDay();
                currDate.setDay(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                            @Override
                            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                                int month = monthOfYear + 1;
                                String date = dayOfMonth + "/" + month + "/" + year;
                                tvFrom.setText(date);
                            }
                        })
                        .setFirstDayOfWeek(Calendar.SUNDAY)
                        .setPreselectedDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                        .setDateRange(null, null)
                        .setDoneText("Yes")
                        .setCancelText("No");
                cdp.show((getActivity().getSupportFragmentManager()), "Promise Date");
            }
        });

        lyTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((UploadDocumentActivity)activity).showDatePicker();
                Calendar calendar = Calendar.getInstance();
                MonthAdapter.CalendarDay currDate = new MonthAdapter.CalendarDay();
                currDate.setDay(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                            @Override
                            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                                int month = monthOfYear + 1;
                                String date = dayOfMonth + "/" + month + "/" + year;
                                tvTo.setText(date);
                            }
                        })
                        .setFirstDayOfWeek(Calendar.SUNDAY)
                        .setPreselectedDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                        .setDateRange(null, null)
                        .setDoneText("Yes")
                        .setCancelText("No");
                cdp.show((getActivity().getSupportFragmentManager()), "Promise Date");
            }
        });
    }

    private void getOutboxList() {
        lyRefresh.setRefreshing(false);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (outbox_submits != null) {
                    if (userSession.getUser().getProfile().getFullname() != null) {
                        outbox_submits = Outbox_submitController.getOutboxId(userSession.getUser().getProfile().getFullname(), "failed");
                    }else {
                        outbox_submits = Outbox_submitController.getOutboxId( "", "failed");
                    }

                    listItems = outbox_submits;

                        outboxListAdapter = new OutboxListAdapter(getActivity(), listItems, OutboxFragment.this);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    rvOutbox.setLayoutManager(layoutManager);
                    rvOutbox.setItemAnimator(new DefaultItemAnimator());
                    rvOutbox.setNestedScrollingEnabled(false);
                    rvOutbox.setAdapter(outboxListAdapter);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_UPDATE){
            if(resultCode == Activity.RESULT_OK){
                getOutboxList();
            }
        }
    }


    public void showUploadPage(final Outbox_submit outbox_submit) {
//        Log.d("dp_percentage", outbox_submit.getDp_percentage());
        progressDialog.show();
        outbox_submits.clear();
        imageAttachmentList.clear();
        imageAttachments.clear();
        Attachmentspdf.clear();
        AttachmentListpdf.clear();
        JSONArray jsonArray;
        JSONObject jsonObject = null;
        Log.d("PDF", outbox_submit.getAttachPDF());
        if (!outbox_submit.getAttach().isEmpty()) {
            Log.d("outboxAttachmentFile", outbox_submit.getAttach());
            try {
                jsonArray = new JSONArray(outbox_submit.getAttach());
                for (int j = 0; j < jsonArray.length(); j++) {
                    jsonObject = jsonArray.getJSONObject(j);
                    ImageAttachment imageAttachment = new ImageAttachment();
                    imageAttachment.setFile(jsonObject.getString("file"));
                    imageAttachment.setType(jsonObject.getString("type"));
                    imageAttachmentList.add(imageAttachment);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < imageAttachmentList.size(); i++) {
                Bitmap bitmap = ImageUtility.loadBitmapFromPath(imageAttachmentList.get(i).getFile());
                Bitmap compressedBitmap = ImageUtility.resizeImage(bitmap);
                final int finalI = i;
                Base64Image.with(getActivity())
                        .encode(compressedBitmap)
                        .into(new RequestEncode.Encode() {
                            @Override
                            public void onSuccess(String s) {
                                ImageAttachment imageAttachment = new ImageAttachment(imageAttachmentList.get(finalI).getType(), "data:image/jpeg;base64," + s);
                                imageAttachments.add(imageAttachment);

//                            Log.d("attachmentOffline", String.valueOf(imageAttachments.get(finalI).getFile()));
                            }

                            @Override
                            public void onFailure() {
                                Log.d("attachmentOffline", "failed");
                            }
                        });
            }
        }
        if (!outbox_submit.getAttachPDF().isEmpty()) {
            Log.d("outboxAttachmentFilePDF", outbox_submit.getAttachPDF());
            try {
                jsonArray = new JSONArray(outbox_submit.getAttachPDF());
                for (int j = 0; j < jsonArray.length(); j++) {
                    jsonObject = jsonArray.getJSONObject(j);
                    PdfAttachment pdfAttachment = new PdfAttachment();
                    pdfAttachment.setFile(jsonObject.getString("file"));
                    pdfAttachment.setType(jsonObject.getString("type"));
                    AttachmentListpdf.add(pdfAttachment);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < AttachmentListpdf.size(); i++) {
                String s = FileUtils.getBase64StringPdf(AttachmentListpdf.get(i).getFile());
                                PdfAttachment pdfAttachment = new PdfAttachment(AttachmentListpdf.get(i).getType(), "data:application/pdf;base64,"+s);
                                Attachmentspdf.add(pdfAttachment);
            }
        }

            outbox_submits = Outbox_submitController.getOutboxId(userSession.getUser().getProfile().getFullname(), "failed");
            if (outbox_submits.size()>0){
                TimerTask task = new TimerTask() {
                    public void run() {
                        Log.d("size", String.valueOf(imageAttachments.size()));
                        Log.d("sizePDF", String.valueOf(Attachmentspdf.size()));
                        sendInquiry(outbox_submit);
                    }
                };
                Timer timer = new Timer("Timer");

                long delay = 15000;
                timer.schedule(task, delay);
            }else {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                        .setMessage("Data tidak ditemukan, Refresh Outbox")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                getOutboxList();
                                dialogInterface.dismiss();
                                progressDialog.dismiss();
                            }
                        });
                builder.show();
        }
    }

    private void sendInquiry(final Outbox_submit outbox_submit) {
        if (outbox_submit.getId() !=null) {
//            SubmitPresenter.submit(getActivity(), new SubmitCallback() {
//                        @Override
//                        public void onSuccessSubmit(String formId) {
//                            Long id = outbox_submit.getId();
//                            Outbox_submit outbox_submit1 = Outbox_submit.findById(Outbox_submit.class, id);
//                            outbox_submit1.delete();
//
//                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
//                                    .setMessage("Success Upload")
//                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialogInterface, int i) {
//                                            getOutboxList();
//                                            dialogInterface.dismiss();
//                                            progressDialog.dismiss();
//                                        }
//                                    });
//                            builder.show();
//                        }
//
//                        @Override
//                        public void onFailedSubmit(String message) {
//                            Long ids = outbox_submit.getId();
//                            Outbox_submit outbox_submit1 = Outbox_submit.findById(Outbox_submit.class, ids);
//                            outbox_submit1.setStatus("failed");
//                            outbox_submit1.save();
//                            Log.d("failedSumbit", message);
//                            getOutboxList();
//                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
//                                    .setMessage("Failed Upload, "+ message)
//                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialogInterface, int i) {
//                                            getOutboxList();
//                                            dialogInterface.dismiss();
//                                            progressDialog.dismiss();
//                                        }
//                                    });
//                            builder.show();
//                        }
//                    },
//                    "inquiry", outbox_submit.getForm(), outbox_submit.getAttach());


            final String attachmentOfflineImage = JSONProcessor.toJSON(imageAttachments);

            final String attachmentOfflinePDF = JSONProcessor.toJSON(Attachmentspdf);

            final String dp_percentage;

            Log.d("param", outbox_submit.getForm());
            Log.d("attachmentImage", attachmentOfflineImage);
            Log.d("attachmentPDF", attachmentOfflinePDF);
            if (outbox_submit.getDp_percentage() !=null){
                Log.d("Dp_percentage", outbox_submit.getDp_percentage());
                dp_percentage = outbox_submit.getDp_percentage();
            }else {
                dp_percentage = "";
            }

            SubmitPresenter.submit(getActivity(), new SubmitCallback() {
                        @Override
                        public void onSuccessSubmit(String formId) {
                            Long id = outbox_submit.getId();
                            Outbox_submit outbox_submit1 = Outbox_submit.findById(Outbox_submit.class, id);
                            outbox_submit1.delete();

                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                                    .setMessage("Success Upload")
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            getOutboxList();
                                            dialogInterface.dismiss();
                                            progressDialog.dismiss();
                                        }
                                    });
                            builder.show();
                        }

                        @Override
                        public void onFailedSubmit(String message) {
                            Long ids = outbox_submit.getId();
                            Outbox_submit outbox_submit1 = Outbox_submit.findById(Outbox_submit.class, ids);
                            outbox_submit1.setStatus("failed");
                            outbox_submit1.save();
                            Log.d("failedSumbit", message);
                            getOutboxList();
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                                    .setMessage("Failed Upload, "+ message)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            getOutboxList();
                                            dialogInterface.dismiss();
                                            progressDialog.dismiss();
                                        }
                                    })
                                    .setCancelable(false);
                            builder.show();
                        }
                    },"inquiry",outbox_submit.getForm(),attachmentOfflineImage, outbox_submit.getUser_id(), attachmentOfflinePDF,dp_percentage);
        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                    .setMessage("Refresh Outbox")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            getOutboxList();
                            dialogInterface.dismiss();
                            progressDialog.dismiss();
                        }
                    });
            builder.show();
        }
    }

}
