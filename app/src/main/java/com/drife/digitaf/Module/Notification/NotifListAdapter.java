package com.drife.digitaf.Module.Notification;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.drife.digitaf.Module.inquiry.fragment.model.InquiryItem;
import com.drife.digitaf.R;
import com.drife.digitaf.Service.Notification.NotificationItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by ferdinandprasetyo on 10/1/18.
 */

public class NotifListAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<NotificationItem> listItems;
    private NotificationActivity notificationActivity;

    public NotifListAdapter(Context context, List<NotificationItem> listItems, NotificationActivity notificationActivity) {
        this.context = context;
        this.listItems = listItems;
        this.notificationActivity = notificationActivity;
    }

    //View holder for item layout
    public class DraftViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName, txtDue, txtStatusText, txtDocumentInfo;
        public LinearLayout lyNotification;

        public DraftViewHolder(View itemView) {
            super(itemView);

            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtStatusText = (TextView) itemView.findViewById(R.id.txtStatusText);
            txtDue = (TextView) itemView.findViewById(R.id.txtDue);
            txtDocumentInfo = (TextView) itemView.findViewById(R.id.txtDocumentInfo);
            lyNotification = itemView.findViewById(R.id.lyNotification);
        }
    }

    //Function to define which layout to create/inflate
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.r_layout_item_notification, parent, false);
        return new DraftViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        NotificationItem notificationItem = getItemAt(position);

        DraftViewHolder draftViewHolder = (DraftViewHolder) holder;

        draftViewHolder.txtName.setText(notificationItem.getInquiryItem().getFormValue().getCustomerName());
        draftViewHolder.txtStatusText.setText(notificationItem.getNotificationParam().getTitle_message());
        draftViewHolder.txtDocumentInfo.setText(notificationItem.getInquiryItem().getStatus_txt());
        draftViewHolder.txtDue.setVisibility(View.VISIBLE);
        draftViewHolder.txtDue.setText(chageDateFormat(notificationItem.getCreated_at(),""));

//        if (draftViewHolder.txtStatusText.getText().toString().equals("Application Wait Status")) {
//            if (notificationItem.getInquiryItem().getFormValue().getDocument().getOptional() != null) {
//                int pendingDoc = 0;
//                int newest = 0;
//                if (notificationItem.getInquiryItem().getFormValue().getDocument().getOptional().size() > 0) {
//                    for (int i = 0; i < notificationItem.getInquiryItem().getFormValue().getDocument().getOptional().size(); i++) {
//                        if (!notificationItem.getInquiryItem().getFormValue().getDocument().getOptional().get(i).getPromise_date().equals("")) {
//                            String dateOne = notificationItem.getInquiryItem().getFormValue().getDocument().getOptional().get(i).getPromise_date().split(" ")[0];
//                            int date = Integer.valueOf(dateOne.split("-")[2]);
//                            if (newest == 0) {
//                                newest = date;
//                            }
//
//                            if (date > 0 && date < newest) {
//                                newest = date;
//                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.SSS");
//
//                                try {
//                                    Date date1 = sdf.parse(notificationItem.getInquiryItem().getFormValue().getDocument().getOptional().get(i).getPromise_date());
//                                    Date date2 = sdf.parse(sdf.format(new Date()));
//
//                                    long different = date1.getTime() - date2.getTime();
//
//                                    long secondsInMilli = 1000;
//                                    long minutesInMilli = secondsInMilli * 60;
//                                    long hoursInMilli = minutesInMilli * 60;
//                                    long daysInMilli = hoursInMilli * 24;
//
//                                    long elapsedDays = different / daysInMilli;
//                                    different = different % daysInMilli;
//
//                                    long elapsedHours = different / hoursInMilli;
//                                    different = different % hoursInMilli;
//
//                                    long elapsedMinutes = different / minutesInMilli;
//                                    different = different % minutesInMilli;
//
//                                    long elapsedSeconds = different / secondsInMilli;
//
//                                    draftViewHolder.txtDue.setVisibility(View.VISIBLE);
//                                    if (elapsedDays < 0) {
//                                        draftViewHolder.txtDue.setText("Expired");
//                                    }else if (elapsedDays < 1){
//                                        draftViewHolder.txtDue.setText("Hari Terakhir");
//                                    }else {
//                                        Log.d("day promise", elapsedDays + " days, "+ elapsedHours + " hours," + elapsedMinutes + "Minutes");
//                                        draftViewHolder.txtDue.setText(elapsedDays+" Hari Lagi");
//                                    }
//                                } catch (ParseException e) {
//                                    e.printStackTrace();
//                                }
//                            } else {
//                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.SSS");
//
//                                try {
//                                    Date date1 = sdf.parse(notificationItem.getInquiryItem().getFormValue().getDocument().getOptional().get(i).getPromise_date());
//                                    Date date2 = sdf.parse(sdf.format(new Date()));
//
//                                    long different = date1.getTime() - date2.getTime();
//
//                                    long secondsInMilli = 1000;
//                                    long minutesInMilli = secondsInMilli * 60;
//                                    long hoursInMilli = minutesInMilli * 60;
//                                    long daysInMilli = hoursInMilli * 24;
//
//                                    long elapsedDays = different / daysInMilli;
//                                    different = different % daysInMilli;
//
//                                    long elapsedHours = different / hoursInMilli;
//                                    different = different % hoursInMilli;
//
//                                    long elapsedMinutes = different / minutesInMilli;
//                                    different = different % minutesInMilli;
//
//                                    long elapsedSeconds = different / secondsInMilli;
//
//                                    draftViewHolder.txtDue.setVisibility(View.VISIBLE);
//                                    if (elapsedDays < 0) {
//                                        draftViewHolder.txtDue.setText("Expired");
//                                    }else if (elapsedDays < 1){
//                                        draftViewHolder.txtDue.setText("Hari Terakhir");
//                                    }else {
//                                        Log.d("day promise", elapsedDays + " days, "+ elapsedHours + " hours," + elapsedMinutes + "Minutes");
//                                        draftViewHolder.txtDue.setText(elapsedDays+" Hari Lagi");
//                                    }
//                                } catch (ParseException e) {
//                                    e.printStackTrace();
//                                }
//                                }
//                        }
//                    }
//                }
//            }
//        }else if (draftViewHolder.txtStatusText.getText().toString().equals("Request Additional Document")) {
//            if (notificationItem.getInquiryItem().getFormValue().getDocument().getOptional() != null) {
//                int pendingDoc = 0;
//                int newest = 0;
//                if (notificationItem.getInquiryItem().getFormValue().getDocument().getOptional().size() > 0) {
//                    for (int i = 0; i < notificationItem.getInquiryItem().getFormValue().getDocument().getOptional().size(); i++) {
////                        if (!notificationItem.getInquiryItem().getFormValue().getDocument().getOptional().get(i).getPromise_date().equals("")) {
////                            pendingDoc = pendingDoc + 1;
////                        }
//
//                        if (!notificationItem.getInquiryItem().getFormValue().getDocument().getOptional().get(i).getPromise_date().equals("")) {
//                            String dateOne = notificationItem.getInquiryItem().getFormValue().getDocument().getOptional().get(i).getPromise_date().split(" ")[0];
//                            int date = Integer.valueOf(dateOne.split("-")[2]);
//                            if (newest == 0) {
//                                newest = date;
//                            }
//
//                            if (date > 0 && date < newest) {
//                                newest = date;
//                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.SSS");
//
//                                try {
//                                    Date date1 = sdf.parse(notificationItem.getInquiryItem().getFormValue().getDocument().getOptional().get(i).getPromise_date());
//                                    Date date2 = sdf.parse(sdf.format(new Date()));
//
//                                    long different = date1.getTime() - date2.getTime();
//
//                                    long secondsInMilli = 1000;
//                                    long minutesInMilli = secondsInMilli * 60;
//                                    long hoursInMilli = minutesInMilli * 60;
//                                    long daysInMilli = hoursInMilli * 24;
//
//                                    long elapsedDays = different / daysInMilli;
//                                    different = different % daysInMilli;
//
//                                    long elapsedHours = different / hoursInMilli;
//                                    different = different % hoursInMilli;
//
//                                    long elapsedMinutes = different / minutesInMilli;
//
//                                    draftViewHolder.txtDue.setVisibility(View.VISIBLE);
//                                    if (elapsedDays < 0) {
//                                        draftViewHolder.txtDue.setText("Expired");
//                                    }else if (elapsedDays < 1){
//                                        draftViewHolder.txtDue.setText("Hari Terakhir");
//                                    }else {
//                                        Log.d("day promise", elapsedDays + " days, "+ elapsedHours + " hours," + elapsedMinutes + "Minutes");
//                                        draftViewHolder.txtDue.setText(elapsedDays+" Hari Lagi");
//                                    }
//                                } catch (ParseException e) {
//                                    e.printStackTrace();
//                                }
//                            } else {
//                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.SSS");
//
//                                try {
//                                    Date date1 = sdf.parse(notificationItem.getInquiryItem().getFormValue().getDocument().getOptional().get(i).getPromise_date());
//                                    Date date2 = sdf.parse(sdf.format(new Date()));
//
//                                    long different = date1.getTime() - date2.getTime();
//
//                                    long secondsInMilli = 1000;
//                                    long minutesInMilli = secondsInMilli * 60;
//                                    long hoursInMilli = minutesInMilli * 60;
//                                    long daysInMilli = hoursInMilli * 24;
//
//                                    long elapsedDays = different / daysInMilli;
//                                    different = different % daysInMilli;
//
//                                    long elapsedHours = different / hoursInMilli;
//                                    different = different % hoursInMilli;
//
//                                    long elapsedMinutes = different / minutesInMilli;
//
//                                    draftViewHolder.txtDue.setVisibility(View.VISIBLE);
//                                    if (elapsedDays < 0) {
//                                        draftViewHolder.txtDue.setText("Expired");
//                                    }else if (elapsedDays < 1){
//                                        draftViewHolder.txtDue.setText("Hari Terakhir");
//                                    }else {
//                                        Log.d("day promise", elapsedDays + " days, "+ elapsedHours + " hours," + elapsedMinutes + "Minutes");
//
//                                        draftViewHolder.txtDue.setText(elapsedDays+" Hari Lagi");
//                                    }
//                                } catch (ParseException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }else {
//            draftViewHolder.txtDue.setVisibility(View.VISIBLE);
//            draftViewHolder.txtDue.setText(chageDateFormat(notificationItem.getCreated_at(),""));
//            draftViewHolder.txtDocumentInfo.setText(notificationItem.getInquiryItem().getStatus_txt());
//        }

        draftViewHolder.lyNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationItem notificationItem = listItems.get(position);
                notificationActivity.showUploadPage(notificationItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    //Funtion to get adapter data from selected position
    public NotificationItem getItemAt(int position) {
        return this.listItems.get(position);
    }

    public void clear() {
        listItems.clear();
        notifyDataSetChanged();
    }

    public String chageDateFormat(String date, String formatDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.SSS");
        SimpleDateFormat newFormat = new SimpleDateFormat(formatDate.equals("sent") ? "dd MMMM yyyy, kk:mm" : "dd MMMM yyyy");

        try {
            return newFormat.format(format.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }
}
