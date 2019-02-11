package com.drife.digitaf.Module.SimulasiKredit.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.Module.Camera.CameraActivity;
import com.drife.digitaf.Module.Main.Activity.Main;
import com.drife.digitaf.Module.SimulasiKredit.SimulasiBudget.Activity.SimulasiBudgetActivity;
import com.drife.digitaf.Module.SimulasiKredit.SimulasiNonPaket.Activity.NonPaketActivity;
import com.drife.digitaf.Module.SimulasiKredit.SimulasiPaket.Activity.SimulasiPaketActivity;
import com.drife.digitaf.R;
import com.drife.digitaf.UIModel.UserSession;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SimulasiKreditFragment extends Fragment{
    private String TAG = SimulasiKreditFragment.class.getSimpleName();
    private View rootView;

    @BindView(R.id.card_simulasi_paket)
    CardView card_simulasi_paket;

    @BindView(R.id.card_simulasi_non_paket)
    CardView card_simulasi_non_paket;

    @BindView(R.id.card_simulasi_budget)
    CardView card_simulasi_budget;

    @BindView(R.id.iv_back)
    ImageView imgBack;

    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.txtDealerName)
    TextView txtDealerName;

    int lastPosition = -1;
    boolean isVisibleFragment = false;

    private UserSession userSession;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_simulasi_kredit,container,false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isAdded()) {
            if (isVisibleFragment) {
                ((Main)getActivity()).adapter.forward(1);
            }
        }

        initVariables();
        initListeners();
        callFunctions();
    }

    private void initVariables(){
        userSession = SharedPreferenceUtils.getUserSession(getActivity());

        if (userSession.getUser().getProfile().getFullname() != null) {
            txtName.setText(userSession.getUser().getProfile().getFullname());
        }

        if (userSession.getUser().getType().equals("so")) {
            if (userSession.getUser().getResponseConfins() != null) {
                if (userSession.getUser().getResponseConfins().getBranchName() != null) {
                    txtDealerName.setText(userSession.getUser().getResponseConfins().getBranchName());
                }
            }
        } else {
            if (userSession.getUser().getResponseConfins() != null) {
                if (userSession.getUser().getResponseConfins().getDealerName() != null) {
                    txtDealerName.setText(userSession.getUser().getResponseConfins().getDealerName());
                }
            }
        }
    }

    private void initListeners(){
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Main)getActivity()).view_pager.setCurrentItem(((Main)getActivity()).adapter.back());
            }
        });

        card_simulasi_non_paket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NonPaketActivity.class);
//                startActivity(intent);
                getActivity().startActivityForResult(intent, 1);
            }
        });

        card_simulasi_budget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SimulasiBudgetActivity.class);
//                startActivity(intent);
                getActivity().startActivityForResult(intent, 1);
            }
        });

        card_simulasi_paket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SimulasiPaketActivity.class);
//                startActivity(intent);
                getActivity().startActivityForResult(intent, 1);
            }
        });
    }

    private void callFunctions(){

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        isVisibleFragment = isVisibleToUser;

        if (isAdded()) {
            if (isVisibleFragment) {
                ((Main)getActivity()).adapter.forward(1);
            }
        }
    }
}
