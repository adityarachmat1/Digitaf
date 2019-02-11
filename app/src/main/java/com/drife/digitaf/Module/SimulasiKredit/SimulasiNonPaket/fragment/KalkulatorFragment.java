package com.drife.digitaf.Module.SimulasiKredit.SimulasiNonPaket.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.GeneralUtility.SharedPreferenceUtil.SharedPreferenceUtils;
import com.drife.digitaf.GeneralUtility.Spinner.SpinnerUtility;
import com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.AsuransiPremi;
import com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.Depresiasi;
import com.drife.digitaf.KalkulatorKredit.Model.Master.AsuransiPremiMaster;
import com.drife.digitaf.KalkulatorKredit.Model.Master.BiayaTambahanMaster;
import com.drife.digitaf.KalkulatorKredit.Model.Master.CarModelMaster;
import com.drife.digitaf.KalkulatorKredit.Model.Master.DepresiasiMaster;
import com.drife.digitaf.KalkulatorKredit.Model.Master.PCLMaster;
import com.drife.digitaf.KalkulatorKredit.Model.Master.RateBungaMaster;
import com.drife.digitaf.KalkulatorKredit.Model.Master.TenorMaster;
import com.drife.digitaf.KalkulatorKredit.Model.OTRDepresiasi;
import com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.Tenor;
import com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.CarModel;
import com.drife.digitaf.KalkulatorKredit.utility.KalkulatorUtility;
import com.drife.digitaf.Module.SimulasiKredit.SimulasiNonPaket.table.TableActivity;
import com.drife.digitaf.Module.SimulasiKredit.SimulasiNonPaket.table.TableData;
import com.drife.digitaf.R;
import com.drife.digitaf.UIModel.UserSession;

import org.apache.commons.math3.util.Precision;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KalkulatorFragment extends Fragment {
    private String TAG = KalkulatorFragment.class.getSimpleName();
    private View rootView;

    @BindView(R.id.et_dealer)
    EditText et_dealer;
    @BindView(R.id.spin_model)
    Spinner spin_model;
    @BindView(R.id.et_otr)
    EditText et_otr;
    @BindView(R.id.etPercentage)
    EditText et_percentage;
    @BindView(R.id.et_rupiah)
    EditText et_rupiah;
    @BindView(R.id.spin_coverage)
    Spinner spin_coverage;
    @BindView(R.id.bt_simulasi)
    Button bt_simulasi;

    /*master*/
    List<Depresiasi> depresiasis;
    List<AsuransiPremi> asuransiPremis;
    List<Tenor> tenor;
    List<Double> PCL;
    List<Integer> biayaAdmin;
    List<Integer> polis;
    String coverageAsuransi = "";
    List<CarModel> carMaster;
    List<String> carModel;
    List<String> coverage;

    List<OTRDepresiasi> otrDepresiasis;
    List<Double> premiAmountPercentage;
    List<Integer> premiAmount;
    List<Integer> premiAmountSumUp;
    List<Integer> pokokHutang;
    List<Double> bunga;
    List<Integer> bungaAmount;
    List<Integer> totalHutang;
    List<Integer> installment1;
    List<Integer> totalHutangRounding;
    List<Integer> premiAmountPCL;
    List<Integer> pokokHutangPCL;
    List<Integer> bungaAmountPCL;
    List<Integer> totalHutangPCL;
    List<Integer> installment2;
    List<Integer> TDP;

    List<Integer> pokokHutangPrepaid;
    List<Integer> bungaAmountPrepaid;
    List<Integer> totalHutangPrepaid;
    List<Integer> installment1Prepaid;
    List<Integer> totalHutangRoundingPrepaid;
    List<Integer> premiAmountPCLPrepaid;
    List<Integer> pokokHutangPCLPrepaid;
    List<Integer> bungaAmountPCLPrepaid;
    List<Integer> totalHutangPCLPrepaid;
    List<Integer> installment2Prepaid;
    List<Integer> TDPPrepaid;

    private boolean percentageChange = false;
    private boolean amountChange = false;
    private int inputOtr = 0;
    private int tjh3 = 100000;
    private int premiAmountSumUpCurrentValue = 0;
    private int DP = 0;
    private int PHAwal = 0;
    private String selectedCar = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_kalkulator,container,false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        initVariables();
        initListeners();
        callFunctions();
    }

    private void initVariables(){
        carMaster = CarModelMaster.getCarModel();
    }

    private void initListeners(){
        et_rupiah.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String strotr = et_otr.getText().toString();
                    String stramount = et_rupiah.getText().toString();
                    if(strotr.equals("")){
                        strotr = "0";
                    }

                    if(stramount.equals("")){
                        stramount = "0";
                    }

                    int otr = Integer.parseInt(strotr);
                    int amount = Integer.parseInt(stramount);
                    double percentage = KalkulatorUtility.countPercentage(otr,amount);
                    if(percentage< 0.01){
                        percentage = 0;
                    }
                    et_percentage.setText(percentage+"");
                    return false;
                }

                return false;
            }
        });

        et_rupiah.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String strotr = et_otr.getText().toString();
                    String stramount = et_rupiah.getText().toString();
                    if(strotr.equals("")){
                        strotr = "0";
                    }

                    if(stramount.equals("")){
                        stramount = "0";
                    }

                    int otr = Integer.parseInt(strotr);
                    int amount = Integer.parseInt(stramount);
                    double percentage = KalkulatorUtility.countPercentage(otr,amount);
                    if(percentage< 0.01){
                        percentage = 0;
                    }
                    et_percentage.setText(percentage+"");
                }
            }
        });

        et_percentage.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String strotr = et_otr.getText().toString();
                    String strpercentage = et_percentage.getText().toString();
                    if(strotr.equals("")){
                        strotr = "0";
                    }

                    if(strpercentage.equals("")){
                        strpercentage = "0";
                    }

                    int otr = Integer.parseInt(strotr);
                    double percentage = Double.parseDouble(strpercentage);
                    float amount = 0;
                    if(percentage<=100){
                        amount = KalkulatorUtility.countAmount(otr,percentage);
                    }
                    /*if(amount< 0){
                        amount = 0;
                    }*/
                    et_rupiah.setText(KalkulatorUtility.floadToInt(amount)+"");
                    return false;
                }
                return false;
            }
        });

        et_percentage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String strotr = et_otr.getText().toString();
                String strpercentage = et_percentage.getText().toString();
                if(strotr.equals("")){
                    strotr = "0";
                }

                if(strpercentage.equals("")){
                    strpercentage = "0";
                }

                int otr = Integer.parseInt(strotr);
                double percentage = Double.parseDouble(strpercentage);
                float amount = 0;
                if(percentage<=100){
                    amount = KalkulatorUtility.countAmount(otr,percentage);
                }
                    /*if(amount< 0){
                        amount = 0;
                    }*/
                et_rupiah.setText(KalkulatorUtility.floadToInt(amount)+"");
            }
        });

        bt_simulasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                String otr = et_otr.getText().toString();
                String dp = et_rupiah.getText().toString();
                inputOtr = Integer.parseInt(otr);
                DP = Integer.parseInt(dp);
                //new asyncGetData().execute();
                PHAwal = countPHAwal(inputOtr,DP);
                countOtrDepresiasi(inputOtr);

                /*Intent intent = new Intent(getContext(), TableActivity.class);
                TableData tableData = new TableData(TDP,installment2,TDPPrepaid,installment2Prepaid,tenor);
                intent.putExtra("data", tableData);
                startActivity(intent);*/
            }
        });

        /*spin_coverage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                coverageAsuransi = spin_coverage.getSelectedItem().toString();
            }
        });*/

        spin_coverage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                coverageAsuransi = spin_coverage.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spin_model.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCar = spin_model.getSelectedItem().toString();
                getBunga();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void callFunctions(){
        setCarList();
        setCoverage();
        setMaster();
    }

    private void setCarList(){
        carModel = new ArrayList<>();
        for (int i=0; i<carMaster.size(); i++){
            CarModel carModel = carMaster.get(i);
            String model = carModel.getName();
            this.carModel.add(model);
        }
        SpinnerUtility.setSpinnerItem(getContext(), spin_model, this.carModel);
    }

    private void setCoverage(){
        coverage = new ArrayList<>();
        coverage.add("Comprehensive");
        coverage.add("TLO");
        //coverage.add("Combination");
        SpinnerUtility.setSpinnerItem(getContext(),spin_coverage,coverage);
    }

    private void setMaster(){
        depresiasis = DepresiasiMaster.getDepresiasi();
        asuransiPremis = AsuransiPremiMaster.getAsuransiPremis();
        tenor = TenorMaster.getTenor();
        PCL = PCLMaster.getPCL();
        biayaAdmin = BiayaTambahanMaster.getBiayaAdmin();
        polis = BiayaTambahanMaster.getPolis();
    }

    private int countPHAwal(int otrAwal, int dp){
        int ph = otrAwal - dp;
        LogUtility.logging(TAG,LogUtility.infoLog,"countPHAwal",ph+"");
        return ph;
    }

    private void countOtrDepresiasi(int otr){
        otrDepresiasis = KalkulatorUtility.getOTRAfterDepresiasi(otr,depresiasis);
        for (int i=0; i<otrDepresiasis.size(); i++){
            OTRDepresiasi otrDepresiasi = otrDepresiasis.get(i);
            //int otrdep = otrDepresiasi.getOTR();
            //LogUtility.logging(TAG,LogUtility.infoLog,"count",otrdep+"");
        }
        if(otrDepresiasis != null){
            getAsuransiPremiPercentage(otrDepresiasis);
            getPremiAmount(otrDepresiasis,premiAmountPercentage,tjh3);
            getBungaAmount(pokokHutang,bunga,tenor);
            getTotalHutangRounding(installment1,tenor,PCL,pokokHutang,bunga);
            getTDP();

            getBungaAmountPrepaid(pokokHutangPrepaid,bunga,tenor);
            getTotalHutangRoundingPrepaid(installment1Prepaid,tenor,PCL,pokokHutangPrepaid,bunga);
            getTDPPrepaid();
        }
    }

    private void getAsuransiPremiPercentage(List<OTRDepresiasi> otrDepresiasis){
        premiAmountPercentage = new ArrayList<>();
        for (int i=0; i<otrDepresiasis.size(); i++){
            OTRDepresiasi otrDepresiasi = otrDepresiasis.get(i);
            //int otr = otrDepresiasi.getOTR();
            for(int j=0; j<asuransiPremis.size(); j++){
                //LogUtility.logging(TAG,LogUtility.infoLog,"loop",j+"");
                AsuransiPremi asuransiPremi = asuransiPremis.get(j);
                int min = asuransiPremi.getCoverageMin();
                int max = asuransiPremi.getCoverageMax();
                double value = 0;
                //LogUtility.logging(TAG,LogUtility.infoLog,"tes","otr : "+otr+", min : "+min+", max : "+max);
                /*if(otr>min && otr<=max){

                    if(coverageAsuransi.equals("Comprehensive")){
                        value = asuransiPremi.getRateCompre();
                        premiAmountPercentage.add(value);
                        //LogUtility.logging(TAG,LogUtility.infoLog,"value",value+"");
                    }else if(coverageAsuransi.equals("TLO")){
                        value = asuransiPremi.getRateTLO();
                        premiAmountPercentage.add(value);
                        //LogUtility.logging(TAG,LogUtility.infoLog,"value",value+"");
                    }else {

                    }
                }*/
            }
        }
    }

    private void getPremiAmount(List<OTRDepresiasi> otrDepresiasis, List<Double> premiAmountPercentage, int tjh3){
        premiAmount = new ArrayList<>();
        premiAmountSumUp = new ArrayList<>();
        pokokHutang = new ArrayList<>();
        pokokHutangPrepaid = new ArrayList<>();
        for (int i=0; i<otrDepresiasis.size(); i++){
            OTRDepresiasi otrDepresiasi = otrDepresiasis.get(i);
            Double percentage = premiAmountPercentage.get(i);
            double otr = otrDepresiasi.getOTR();
            int amount = (int) ((otr*percentage)/100)+tjh3;
            premiAmount.add(amount);

            /*insert data to premiAmountSum to handle asuransi cicil or TDP*/
            premiAmountSumUpCurrentValue = premiAmountSumUpCurrentValue+amount;
            premiAmountSumUp.add(premiAmountSumUpCurrentValue);
            /*set ph */
            processOnLoanData(PHAwal,premiAmountSumUpCurrentValue);
            processPrepaidData(PHAwal);
        }
    }

    private void processOnLoanData(int phAwal, int premiAmountSumUpCurrentValue){
        //pokokHutang = new ArrayList<>();
        int count = phAwal+premiAmountSumUpCurrentValue;
        //LogUtility.logging(TAG,LogUtility.infoLog,"processOnLoanData","count",count+"");
        pokokHutang.add(count);
    }

    private void processPrepaidData(int phAwal){
        //pokokHutangPrepaid = new ArrayList<>();
        int count = phAwal;
        //LogUtility.logging(TAG,LogUtility.infoLog,"processPrepaidData","count",count+"");
        pokokHutangPrepaid.add(count);
    }

    private void getBunga(){
        if(selectedCar.equals("Avanza")){
            bunga = RateBungaMaster.rateAvanza();
        }else if(selectedCar.equals("Innova")){
            bunga = RateBungaMaster.rateInnova();
        }
    }

    private void getBungaAmount(List<Integer> pokokHutang,List<Double> bungas,List<Tenor> tenors){
        bungaAmount = new ArrayList<>();
        totalHutang = new ArrayList<>();
        installment1 = new ArrayList<>();
        for (int i=0; i<pokokHutang.size(); i++){
            int ph = pokokHutang.get(i);
            double bunga = bungas.get(i);
            Tenor tenor = tenors.get(i);
            int ten = Integer.parseInt(tenor.getName());
            int t = ten/12;
            int a = (int)(ph*bunga)/100;
            int value = a*t;

            /*total hutang*/
            int total = ph+value;
            /*installment1*/
            int ori = total/ten;
            int ins1 = (int) Precision.round((total/ten),-3);
            int round = KalkulatorUtility.roundUp(ori);

            //LogUtility.logging(TAG,LogUtility.infoLog,"getBungaAmount","value","ph : "+ph+" : bunga : "+bunga+" : "+t+" : ori : "+ori+" : ins : "+round);
            bungaAmount.add(value);
            totalHutang.add(total);
            installment1.add(round);
        }
    }

    private void getBungaAmountPrepaid(List<Integer> pokokHutang,List<Double> bungas,List<Tenor> tenors){
        bungaAmountPrepaid = new ArrayList<>();
        totalHutangPrepaid = new ArrayList<>();
        installment1Prepaid = new ArrayList<>();
        for (int i=0; i<pokokHutang.size(); i++){
            int ph = pokokHutang.get(i);
            double bunga = bungas.get(i);
            Tenor tenor = tenors.get(i);
            int ten = Integer.parseInt(tenor.getName());
            int t = ten/12;
            int a = (int)(ph*bunga)/100;
            int value = a*t;

            /*total hutang*/
            int total = ph+value;
            /*installment1*/
            int ori = total/ten;
            int ins1 = (int) Precision.round((total/ten),-3);
            int round = KalkulatorUtility.roundUp(ori);

            //LogUtility.logging(TAG,LogUtility.infoLog,"getBungaAmount","value","ph : "+ph+" : bunga : "+bunga+" : "+t+" : ori : "+ori+" : ins : "+round);
            bungaAmountPrepaid.add(value);
            totalHutangPrepaid.add(total);
            installment1Prepaid.add(round);
        }
    }

    private void getTotalHutangRounding(List<Integer> installment1,List<Tenor> tenor,List<Double> pcls,
                                        List<Integer> pokokHutang, List<Double> bungas){
        //LogUtility.logging(TAG,LogUtility.infoLog,"getTotalHutangRounding","size",installment1.size()+"");
        totalHutangRounding = new ArrayList<>();
        premiAmountPCL = new ArrayList<>();
        pokokHutangPCL = new ArrayList<>();
        bungaAmountPCL = new ArrayList<>();
        totalHutangPCL = new ArrayList<>();
        installment2 = new ArrayList<>();

        for (int i=0; i<installment1.size(); i++){
            int ins = installment1.get(i);
            Tenor tenor1 = tenor.get(i);
            double pcl = pcls.get(i);
            int pokHutang = pokokHutang.get(i);
            double bunga = bungas.get(i);

            int ten = Integer.parseInt(tenor1.getName());
            int totHRounding = ins*ten;
            int premiAmount = (int) (totHRounding*pcl)/100;
            int pkkHtng = pokHutang+premiAmount;

            int t = ten/12;
            int a = (int)(pkkHtng*bunga)/100;
            int bungaAmount = a*t;

            int totHutPCL = pkkHtng+bungaAmount;

            int round = KalkulatorUtility.roundUp(totHutPCL/ten);

            //LogUtility.logging(TAG,LogUtility.infoLog,"getTotalHutangRounding","value",round+"");
            totalHutangRounding.add(totHRounding);
            premiAmountPCL.add(premiAmount);
            pokokHutangPCL.add(pkkHtng);
            bungaAmountPCL.add(bungaAmount);
            totalHutangPCL.add(totHutPCL);
            installment2.add(round);
        }
    }

    private void getTotalHutangRoundingPrepaid(List<Integer> installment1,List<Tenor> tenor,List<Double> pcls,
                                        List<Integer> pokokHutang, List<Double> bungas){
        //LogUtility.logging(TAG,LogUtility.infoLog,"getTotalHutangRounding","size",installment1.size()+"");
        totalHutangRoundingPrepaid = new ArrayList<>();

        for (int i=0; i<installment1.size(); i++){
            int ins = installment1.get(i);
            Tenor tenor1 = tenor.get(i);
            double pcl = pcls.get(i);
            int pokHutang = pokokHutang.get(i);
            double bunga = bungas.get(i);

            int ten = Integer.parseInt(tenor1.getName());
            int totHRounding = ins*ten;

            //LogUtility.logging(TAG,LogUtility.infoLog,"getTotalHutangRounding","value",round+"");
            totalHutangRoundingPrepaid.add(totHRounding);
        }
        //premiAmountPCLPrepaid = KalkulatorUtility.getPremiAmountPCLPrepaid(this.totalHutangRoundingPrepaid,this.PCL);
        pokokHutangPCLPrepaid = KalkulatorUtility.getPokokHutangPCLPrepaid(this.pokokHutangPrepaid,this.premiAmountPCLPrepaid);
        bungaAmountPCLPrepaid = KalkulatorUtility.getBungaAmountPCLPrepaid(this.pokokHutangPCLPrepaid,this.bunga,this.tenor);
        totalHutangPCLPrepaid = KalkulatorUtility.getTotalHutangPCLPrepaid(this.pokokHutangPCLPrepaid,this.bungaAmountPCLPrepaid);
        installment2Prepaid = KalkulatorUtility.getInstallmentFinal(this.totalHutangPCLPrepaid,this.tenor);
    }

    /*final result for TDP Onloan*/
    private void getTDP(){
        //TDP = KalkulatorUtility.getTDP_ADDB(DP,biayaAdmin,polis);
    }

    /*final result for TDP Prepaid*/
    private void getTDPPrepaid(){
        TDPPrepaid = KalkulatorUtility.getTDP_ADDM(DP,biayaAdmin,polis,installment2Prepaid,premiAmountSumUp,tenor);
    }

    /*reset value*/
    private void reset(){
        try {
            otrDepresiasis.clear();
            premiAmountPercentage.clear();
            premiAmount.clear();
            premiAmountSumUp.clear();
            pokokHutang.clear();
            //bunga.clear();
            bungaAmount.clear();
            totalHutang.clear();
            installment1.clear();
            totalHutangRounding.clear();
            premiAmountPCL.clear();
            pokokHutangPCL.clear();
            bungaAmountPCL.clear();
            totalHutangPCL.clear();
            installment2.clear();
            TDP.clear();

            pokokHutangPrepaid.clear();
            //bungaAmountPrepaid.clear();
            totalHutangPrepaid.clear();
            installment1Prepaid.clear();
            totalHutangRoundingPrepaid.clear();
            premiAmountPCLPrepaid.clear();
            pokokHutangPCLPrepaid.clear();
            bungaAmountPCLPrepaid.clear();
            totalHutangPCLPrepaid.clear();
            installment2Prepaid.clear();
            TDPPrepaid.clear();

            PHAwal = 0;
            premiAmountSumUpCurrentValue = 0;
        }catch (Exception e){

        }

    }
}
