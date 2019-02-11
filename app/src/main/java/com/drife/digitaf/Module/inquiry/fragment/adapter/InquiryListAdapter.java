package com.drife.digitaf.Module.inquiry.fragment.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.Module.InputKredit.UploadDocument_v2.Activity.UploadDocumentActivity;
import com.drife.digitaf.Module.inquiry.fragment.InquiryFragment;
import com.drife.digitaf.Module.inquiry.fragment.model.InquiryItem;
import com.drife.digitaf.R;
import com.drife.digitaf.UIModel.UserSession;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ferdinandprasetyo on 10/1/18.
 */

public class InquiryListAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<InquiryItem> listItems;
    private InquiryFragment inquiryFragment;

    public InquiryListAdapter(Context context, List<InquiryItem> listItems, InquiryFragment inquiryFragment) {
        this.context = context;
        this.listItems = listItems;
        this.inquiryFragment = inquiryFragment;
    }

    //View holder for item layout
    public class DraftViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName, txtStatus, txtDocumentInfo, txtSent, txtDue;
        public CardView lyInquiry;

        public DraftViewHolder(View itemView) {
            super(itemView);

            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtDocumentInfo = (TextView) itemView.findViewById(R.id.txtDocumentInfo);
            txtSent = (TextView) itemView.findViewById(R.id.txtSent);
            txtStatus = (TextView) itemView.findViewById(R.id.txtStatus);
            txtDue = (TextView) itemView.findViewById(R.id.txtDue);
            lyInquiry = itemView.findViewById(R.id.lyInquiry);
        }
    }

    //Function to define which layout to create/inflate
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.r_list_inquiry_item, parent, false);
        return new DraftViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final InquiryItem draftItem = getItemAt(position);

        DraftViewHolder draftViewHolder = (DraftViewHolder) holder;
        draftViewHolder.setIsRecyclable(false);

        draftViewHolder.txtName.setText(draftItem.getFormValue().getCustomerName());
        //draftViewHolder.txtStatus.setText(draftItem.getStatus_txt().toUpperCase());
        draftViewHolder.txtSent.setText("sent " + chageDateFormat(draftItem.getSent(), "sent"));

        if(draftItem.getSubmit_status().equalsIgnoreCase("success")){
            draftViewHolder.txtStatus.setText(draftItem.getStatus_txt().toUpperCase());

            if (draftItem.getFormValue().getDocument().getOptional() != null) {
                int pendingDoc = 0;
                int newest = 0;
                if (draftItem.getFormValue().getDocument().getOptional().size() > 0) {
                    for (int i = 0;i < draftItem.getFormValue().getDocument().getOptional().size();i++) {
                        if (!draftItem.getFormValue().getDocument().getOptional().get(i).getPromise_date().equals("")) {
                            pendingDoc = pendingDoc + 1;
                        }

                        if (!draftItem.getFormValue().getDocument().getOptional().get(i).getPromise_date().equals("")) {
                            String dateOne = draftItem.getFormValue().getDocument().getOptional().get(i).getPromise_date().split(" ")[0];
                            int date = Integer.valueOf(dateOne.split("-")[2]);
                            if (newest == 0) {
                                newest = date;
                            }

                            if (date > 0 && date < newest) {
                                newest = date;

                                draftViewHolder.txtDue.setVisibility(View.VISIBLE);
                                draftViewHolder.txtDue.setText("due: "+chageDateFormat(draftItem.getFormValue().getDocument().getOptional().get(i).getPromise_date(), "due"));
                            } else {
                                draftViewHolder.txtDue.setVisibility(View.VISIBLE);
                                draftViewHolder.txtDue.setText("due: "+chageDateFormat(draftItem.getFormValue().getDocument().getOptional().get(i).getPromise_date(), "due"));
                            }
                        }
                    }
                }

                if (pendingDoc > 0) {
                    draftViewHolder.txtDocumentInfo.setVisibility(View.VISIBLE);
                    draftViewHolder.txtDocumentInfo.setText(""+pendingDoc+" pending doc");
                }
            }
        }else{
            draftViewHolder.txtStatus.setText("FAILED");
        }

        draftViewHolder.lyInquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(draftItem.getSubmit_status().equalsIgnoreCase("success")){
                    if(listItems != null && listItems.size()>0){
                        InquiryItem inquiryItem = listItems.get(position);

                    /*Intent intent = new Intent(context, UploadDocumentActivity.class);
                    intent.putExtra("data",inquiryItem);
                    intent.putExtra("isUpdate",1);
                    context.startActivity(intent);*/

                        if (getPendingDoc(inquiryItem) != 0) {
                            UserSession userSession = SharedPreferenceUtils.getUserSession(context);
                            if(!userSession.getUser().getType().equals("spv") && !userSession.getUser().getType().equals("bm")){
                                inquiryFragment.showUploadPage(inquiryItem);
                            }
                        }
                    }
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(context)
                            .setTitle("Failed submit to confinse")
                            //.setMessage("Reason : "+draftItem.getConfins_response()+". Apakah anda ingin merubah data?")
                            .setMessage("Reason : "+draftItem.getConfins_response())
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            /*.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })*/;
                    builder.show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    //Funtion to get adapter data from selected position
    public InquiryItem getItemAt(int position) {
        return this.listItems.get(position);
    }

    public void clear() {
        listItems.clear();
        notifyDataSetChanged();
    }

    public String chageDateFormat(String date, String formatDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.SSS");
        SimpleDateFormat newFormat = new SimpleDateFormat(formatDate.equals("sent") ? "dd MMM yyyy, kk:mm" : "dd MMM yyyy");

        try {
            return newFormat.format(format.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    public int getPendingDoc(InquiryItem inquiryItem) {
        int pendingDoc = 0;
        if (inquiryItem.getFormValue().getDocument().getOptional() != null) {
            if (inquiryItem.getFormValue().getDocument().getOptional().size() > 0) {
                for (int i = 0;i < inquiryItem.getFormValue().getDocument().getOptional().size();i++) {
                    if (!inquiryItem.getFormValue().getDocument().getOptional().get(i).getPromise_date().equals("")) {
                        pendingDoc = pendingDoc + 1;
                    }
                }
            }
        }

        return pendingDoc;
    }
}
