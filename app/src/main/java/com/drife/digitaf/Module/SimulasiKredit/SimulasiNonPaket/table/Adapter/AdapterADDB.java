package com.drife.digitaf.Module.SimulasiKredit.SimulasiNonPaket.table.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.drife.digitaf.GeneralUtility.Currency.CurrencyFormat;
import com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.Tenor;
import com.drife.digitaf.KalkulatorKredit.Model.Master.RateBungaMaster;
import com.drife.digitaf.KalkulatorKredit.SelectedData;
import com.drife.digitaf.Module.InputKredit.InputItemKredit.Activity.InputItemKreditActivity;
import com.drife.digitaf.Module.SimulasiKredit.ResultSimulasi.Activity.ResultSimulasiActivity;
import com.drife.digitaf.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PANDU on 10/5/2018.
 */

public class AdapterADDB extends RecyclerView.Adapter<AdapterADDB.ViewHolder> {
    private List<Long> tdp;
    private List<Long> cicilan;
    private List<Tenor> tenors;
    private List<Double> bunga;
    private Context context;
    private String selectedPackage;
    private String selectedPackageCode;
    private double dpPercentage;

    public AdapterADDB(Context context, List<Long> tdp, List<Long> cicilan,
                       List<Tenor> tenors, List<Double> bunga, String selectedPackage,
                       String selectedPackageCode, double dpPercentage) {
        this.tdp = tdp;
        this.cicilan = cicilan;
        this.tenors = tenors;
        this.context = context;
        this.bunga = bunga;
        this.selectedPackage = selectedPackage;
        this.selectedPackageCode = selectedPackageCode;
        this.dpPercentage = dpPercentage;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_tdp)
        TextView tv_tdp;
        @BindView(R.id.tv_cicilan)
        TextView tv_cicilan;
        @BindView(R.id.tv_tenor)
        TextView tv_tenor;

        public ViewHolder(View itemVew) {
            super(itemVew);
            context = itemVew.getContext();
            ButterKnife.bind(this, itemView);
        }

        public void bindHolder(long tdp, long cicilan, String tenor, final int position) {
            String formattedTdp = CurrencyFormat.formatRupiah().format((double) tdp);
            String formattedCicilan = CurrencyFormat.formatRupiah().format((double) cicilan);
            tv_tdp.setText(formattedTdp);
            tv_cicilan.setText(formattedCicilan);
            tv_tenor.setText(tenor+"");

            tv_tenor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SelectedData.Tenor = position;
                    SelectedData.JenisAngsuran = "ADDB";
                    SelectedData.Rate = bunga.get(position)+"";
                    SelectedData.SelectedPackage = selectedPackage;
                    SelectedData.SelectedPackageCode = selectedPackageCode;
                    SelectedData.DpPercentage = dpPercentage+"";
                    /*Intent intent = new Intent(context, InputItemKreditActivity.class);
                    context.startActivity(intent);*/
                    ((ResultSimulasiActivity)context).showInputData();
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_table, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindHolder(tdp.get(position),cicilan.get(position), tenors.get(position).getName(), position);
    }

    public int getItemCount() {
        return tdp.size();
    }
}

