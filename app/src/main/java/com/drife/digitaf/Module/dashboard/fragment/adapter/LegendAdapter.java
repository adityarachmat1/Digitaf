package com.drife.digitaf.Module.dashboard.fragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.drife.digitaf.Module.dashboard.fragment.model.LegendItem;
import com.drife.digitaf.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LegendAdapter extends RecyclerView.Adapter<LegendAdapter.ViewHolder> {
    private List<LegendItem> data;
    private Context context;

    public LegendAdapter(Context context, List<LegendItem> data) {
        this.data = data;
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtLegendTitle)
        TextView txtLegendTitle;
        @BindView(R.id.imgLegendColor)
        ImageView imgLegendColor;

        public ViewHolder(View itemVew) {
            super(itemVew);
            context = itemVew.getContext();
            ButterKnife.bind(this, itemView);
        }

        public void bindHolder(LegendItem data, int position) {
            imgLegendColor.setBackgroundColor(data.getColor());
            txtLegendTitle.setText(data.getName());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_legend, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindHolder(data.get(position), position);
    }

    public int getItemCount() {
        return data.size();
    }
}

