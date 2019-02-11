package com.drife.digitaf.Module.home.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.Module.Main.Activity.Main;
import com.drife.digitaf.Module.Notification.NotificationActivity;
import com.drife.digitaf.Module.home.fragment.HomeFragment;
import com.drife.digitaf.Module.inquiry.fragment.InquiryFragment;
import com.drife.digitaf.Module.inquiry.fragment.model.FilterStatus;
import com.drife.digitaf.UIModel.ToDo;
import com.drife.digitaf.R;
import com.drife.digitaf.UIModel.UserSession;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private List<ToDo> data;
    private Context context;
    UserSession userSession;

    public HomeAdapter(Context context, List<ToDo> data) {
        this.data = data;
        this.context = context;
        userSession = SharedPreferenceUtils.getUserSession(context);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_quantity)
        TextView tv_quantity;
        @BindView(R.id.iv_arrow)
        ImageView iv_arrow;
        @BindView(R.id.rlToDo)
        RelativeLayout rlToDo;
        @BindView(R.id.iv_tooltip)
        ImageView iv_tooltip;
        @BindView(R.id.rlTooltip)
        RelativeLayout rlTooltip;
        @BindView(R.id.rlQuantity)
        RelativeLayout rlQuantity;

        public ViewHolder(View itemVew) {
            super(itemVew);
            context = itemVew.getContext();
            ButterKnife.bind(this, itemView);
        }

        public void bindHolder(final ToDo data, int position) {
            String id = data.getId();
            final String title = data.getTitle();
            String quantity = data.getQuantity();
            final String toolTip = data.getTooltipInfo() != null ? data.getTooltipInfo() : "";

            tv_title.setText(title);
            tv_quantity.setText(quantity);

            if (!toolTip.equals("")) {
                iv_tooltip.setVisibility(View.VISIBLE);
            } else {
                iv_tooltip.setVisibility(View.GONE);
            }

            rlQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String status = "inquiry";
                    int pos = 3;

                    if (title.equalsIgnoreCase("Valid")) {
                        status = FilterStatus.valid;
                    } else if (title.equalsIgnoreCase("Approve")) {
                        status = FilterStatus.approve;
                    } else if (title.equalsIgnoreCase("Reject")) {
                        status = FilterStatus.reject;
                    } else if (title.equalsIgnoreCase("Credit Review")) {
                        status = FilterStatus.credit_review;
                    } else if (title.equalsIgnoreCase("Data Entry")) {
                        status = FilterStatus.data_entry;
                    } else if (title.equalsIgnoreCase("Cancel")) {
                        status = FilterStatus.cancel;
                    } else if (title.equalsIgnoreCase("Wait")) {
                        status = FilterStatus.wait;
                    } else if (title.equalsIgnoreCase("Re-Input")) {
                        status = FilterStatus.re_input;
                    }

                    if (title.equalsIgnoreCase("Follow Up Document")) {
                        pos = 2;
                    }

                    if (title.equalsIgnoreCase("Update Phone Number")){
                        Intent intent = new Intent(context, NotificationActivity.class);
                        intent.putExtra("update_phone", "update_phone");
                        ((Activity)context).startActivityForResult(intent, 2);
                    }else {
                        if (((Activity)context) instanceof Main) {
                            if (userSession.getUser().getType().equals("spv") || userSession.getUser().getType().equals("bm")) {
                                pos = pos - 1;
                            }
                            ((Main)context).view_pager.setCurrentItem(pos);

                            HomeFragment home = (HomeFragment)((Main)context).adapter.getItem(0);
                            if (home.onFragmentInteractionListener != null) {
                                home.onFragmentInteractionListener.onFragmentInteraction(status, pos);
                            }
                        }
                    }
                }
            });

            rlTooltip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showToolTip(toolTip);
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_to_do, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindHolder(data.get(position), position);
    }

    public int getItemCount() {
        return data.size();
    }

    public void showToolTip(String message) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context)
                .setMessage(message)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       dialog.dismiss();
                    }
                });
        alert.create().show();
    }
}

