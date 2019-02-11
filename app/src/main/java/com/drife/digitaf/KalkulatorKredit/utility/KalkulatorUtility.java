package com.drife.digitaf.KalkulatorKredit.utility;

import android.util.Log;

import com.drife.digitaf.GeneralUtility.JSONProcessor.JSONProcessor;
import com.drife.digitaf.GeneralUtility.LogUtility.LogUtility;
import com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.Depresiasi;
import com.drife.digitaf.KalkulatorKredit.Model.OTRDepresiasi;
import com.drife.digitaf.KalkulatorKredit.Model.DatabaseModel.Tenor;
import com.drife.digitaf.KalkulatorKredit.SelectedData;
import com.drife.digitaf.ORM.Database.PackageRule;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class KalkulatorUtility {
    private static String TAG = KalkulatorUtility.class.getSimpleName();

    public static double countPercentage(double nominal1, double nominal2){
        //double total = (double) (nominal2/nominal1)*100;
        double total = (float)nominal2/nominal1*100;
        return total;
    }

    public static double countPercentageLong(long nominal1, long nominal2){
        double total = (double) ((float) nominal2/nominal1*100);
        return total;
    }

    public static float countAmount(long otr, double percentage){
        float total = (float) (percentage*otr)/100f;
        LogUtility.logging(TAG,LogUtility.debugLog,"countAmount",total+"");
        return total;
    }

    public static int countAmountInt(int nominal, double percentage){
        int total = new Double ((percentage*nominal)/100).intValue();
        LogUtility.logging(TAG,LogUtility.debugLog,"countAmountInt",total+"");
        return total;
    }

    public static float countAmountFloat(float otr, double percentage){
        float total = (float) (percentage*otr)/100f;
        LogUtility.logging(TAG,LogUtility.debugLog,"countAmount",total+"");
        return total;
    }
    public static double countAmountDouble(double otr, double percentage){
        double total = (percentage*otr)/100;
        LogUtility.logging(TAG,LogUtility.debugLog,"countAmount",total+"");
        return total;
    }

    public static long countAmountLong(long otr, double percentage){
        long total = new Double((float)otr*percentage/100).longValue();
        LogUtility.logging(TAG,LogUtility.debugLog,"countAmountLong",total+"");
        return total;
    }

    public static List<OTRDepresiasi> getOTRAfterDepresiasi(long otr, List<Depresiasi> depresiasis){
        List<OTRDepresiasi> otrDepresiasis = new ArrayList<>();
        for (int i=0; i<depresiasis.size(); i++){
            Depresiasi depresiasi = depresiasis.get(i);
            double percentage = depresiasi.getPercentage();
            /*double newOtr = (float)otr*percentage/100;
            OTRDepresiasi otrDepresiasi = new OTRDepresiasi(depresiasi,new Double(newOtr).longValue());*/
            long newOtr = new Double((float)otr*percentage/100).longValue();
            OTRDepresiasi otrDepresiasi = new OTRDepresiasi(depresiasi,newOtr);
            otrDepresiasis.add(otrDepresiasi);
        }
        LogUtility.logging(TAG,LogUtility.debugLog,"getOTRAfterDepresiasi","getOTRAfterDepresiasi", JSONProcessor.toJSON(otrDepresiasis));
        LogUtility.logging(TAG,LogUtility.debugLog,"getOTRAfterDepresiasi","depresiasis", JSONProcessor.toJSON(depresiasis));
        return otrDepresiasis;
    }

    public static List<OTRDepresiasi> getOTRAfterDepresiasi(Long otr, List<Depresiasi> depresiasis){
        List<OTRDepresiasi> otrDepresiasis = new ArrayList<>();
        for (int i=0; i<depresiasis.size(); i++){
            Depresiasi depresiasi = depresiasis.get(i);
            double percentage = depresiasi.getPercentage();
            double newOtr = otr*percentage/100;
            OTRDepresiasi otrDepresiasi = new OTRDepresiasi(depresiasi,(int) newOtr);
            otrDepresiasis.add(otrDepresiasi);
        }
        LogUtility.logging(TAG,LogUtility.debugLog,"getOTRAfterDepresiasi","getOTRAfterDepresiasi", JSONProcessor.toJSON(otrDepresiasis));
        LogUtility.logging(TAG,LogUtility.debugLog,"getOTRAfterDepresiasi","depresiasis", JSONProcessor.toJSON(depresiasis));
        return otrDepresiasis;
    }

    /*public static List<OTRDepresiasi> getOTRAfterDepresiasi2(int otr, List<com.drife.digitaf.ORM.Database.Depresiasi> depresiasis){
        LogUtility.logging(TAG,LogUtility.debugLog,"otr",otr+"");
        List<OTRDepresiasi> otrDepresiasis = new ArrayList<>();
        for (int i=0; i<depresiasis.size(); i++){
            Depresiasi depresiasi = depresiasis.get(i);
            double percentage = depresiasi.getPercentage();
            double newOtr = otr*percentage/100;
            OTRDepresiasi otrDepresiasi = new OTRDepresiasi(depresiasi,(int) newOtr);
            otrDepresiasis.add(otrDepresiasi);
        }
        return otrDepresiasis;
    }*/

    public static int floadToInt(float data){
        int a = (int)Math.round(data);
        return new Double(a).intValue();
        //return roundUpCurrency(a);
        //return a;
    }

    public static int roundUp(int num){
        int rounded = ((num + 999) / 1000 ) * 1000;
        return rounded;
    }

    public static Long roundUp(Long num){
        Long rounded = ((num + 999) / 1000 ) * 1000;
        return rounded;
    }

    public static int roundUpCurrency(int num){
        int rounded = ((num + 9) / 1000 ) * 1000;
        return rounded;
    }

    public static double roundUpDecimal(double num){
        //double data = Math.round((num+0.99) / 100.00) * 100.00;
        double a = num;
        a = a*100;
        a = Math.ceil(a);
        a = a/100;

        return a;
    }

    public static List<Long> getPremiAmountPCLPrepaid(List<Long> totalHutangRoundingPrepaid, List<Double> PCLs){
        List<Long> premiAmountPCL = new ArrayList<>();
        for (int i=0; i<totalHutangRoundingPrepaid.size(); i++){
            long totalHutangRounding = totalHutangRoundingPrepaid.get(i);
            double pcl = PCLs.get(i);
            long premiAmount = (int) (totalHutangRounding*pcl)/100;
            premiAmountPCL.add(premiAmount);
        }
        return premiAmountPCL;
    }

    public static List<Long> getPremiAmountPCLPrepaid(List<Long> totalHutangRoundingPrepaid, double PCL){
        List<Long> premiAmountPCL = new ArrayList<>();
        for (int i=0; i<totalHutangRoundingPrepaid.size(); i++){
            long totalHutangRounding = totalHutangRoundingPrepaid.get(i);
            double pcl = PCL;
            long premiAmount = (int) (totalHutangRounding*pcl)/100;
            premiAmountPCL.add(premiAmount);
        }
        return premiAmountPCL;
    }

    public static List<Integer> getPokokHutangPCLPrepaid(List<Integer> pokokHutangNonPCLPrepaid, List<Integer> premiAmountPCLPrepaid){
        List<Integer> pokokHutangPCLPrepaid = new ArrayList<>();
        for(int i=0; i<pokokHutangNonPCLPrepaid.size(); i++){
            int pokokHutang = pokokHutangNonPCLPrepaid.get(i);
            int premiAmount = premiAmountPCLPrepaid.get(i);
            int pkkHtng = pokokHutang+premiAmount;
            pokokHutangPCLPrepaid.add(pkkHtng);
        }
        return pokokHutangPCLPrepaid;
    }

    public static List<Integer> getBungaAmountPCLPrepaid(List<Integer> pokokHutangPCLPrepaid, List<Double> bungaPrepaid, List<Tenor> tenors){
        List<Integer> bungaAmountPCLPrepaid = new ArrayList<>();
        for (int i=0; i<pokokHutangPCLPrepaid.size(); i++){
            Tenor tenor = tenors.get(i);
            int pokokHutang = pokokHutangPCLPrepaid.get(i);
            double bunga = bungaPrepaid.get(i);
            int ten = Integer.parseInt(tenor.getName());

            int t = ten/12;
            int a = (int)(pokokHutang*bunga)/100;
            int bungaAmount = a*t;

            bungaAmountPCLPrepaid.add(bungaAmount);
        }
        return bungaAmountPCLPrepaid;
    }

    public static List<Integer> getTotalHutangPCLPrepaid(List<Integer> pokokHutangPCLPrepaid, List<Integer> bungaAmountPCLPrepaid){
        List<Integer> totalHutangPCLPrepaid = new ArrayList<>();
        for(int i=0; i<pokokHutangPCLPrepaid.size(); i++){
            int pkkHtng = pokokHutangPCLPrepaid.get(i);
            int bungaAmount = bungaAmountPCLPrepaid.get(i);
            int totHutPCL = pkkHtng+bungaAmount;
            totalHutangPCLPrepaid.add(totHutPCL);
        }
        return totalHutangPCLPrepaid;
    }

    public static List<Integer> getInstallmentFinal(List<Integer> totalHutangPCLPrepaid,List<Tenor> tenors){
        List<Integer> installmentFinal = new ArrayList<>();
        for(int i=0; i<tenors.size(); i++){
            Tenor tenor = tenors.get(i);
            int ten = Integer.parseInt(tenor.getName());
            int totalHutang = totalHutangPCLPrepaid.get(i);
            int round = KalkulatorUtility.roundUp(totalHutang/ten);
            installmentFinal.add(round);
        }
        return installmentFinal;
    }

    public static List<Long> getTDP_ADDB(long DP, List<Integer> biayaAdmin, List<Integer> polis){
        LogUtility.logging(TAG,LogUtility.infoLog,"getTDP_ADDB","biayaAdmin",JSONProcessor.toJSON(biayaAdmin));
        List<Long> TDP = new ArrayList<>();
        for (int i=0; i<biayaAdmin.size(); i++){
            int admin = biayaAdmin.get(i);
            //int pol = polis.get(i);
            //int tot = DP+admin+pol;
            long tot = DP+admin;
            TDP.add(tot);
        }
        return TDP;
    }

    public static List<Long> getTDP_ADDB(long DP, List<Integer> biayaAdmin, List<Integer> polis,List<Tenor> tenors, int sizeBunga){
        LogUtility.logging(TAG,LogUtility.infoLog,"getTDP_ADDB","biayaAdmin",JSONProcessor.toJSON(biayaAdmin));
        List<Long> TDP = new ArrayList<>();
        for (int i=0; i<sizeBunga; i++){
            int admin = biayaAdmin.get(i);
            //int pol = polis.get(i);
            //int tot = DP+admin+pol;
            long tot = DP+admin;
            TDP.add(tot);
        }
        return TDP;
    }

    public static List<Long> getTDP_ADDB(List<Long> DP, List<Long> biayaAdmin, List<Integer> polis,List<Tenor> tenors){
        LogUtility.logging(TAG,LogUtility.infoLog,"getTDP_ADDB","biayaAdmin",JSONProcessor.toJSON(biayaAdmin));
        List<Long> TDP = new ArrayList<>();
        for (int i=0; i<DP.size(); i++){
            long admin = biayaAdmin.get(i);
            //int pol = polis.get(i);
            //int tot = DP+admin+pol;
            Long tot = DP.get(i)+admin;
            TDP.add(tot);
        }
        return TDP;
    }

    public static List<Integer> getTDP_ADDM(int DP, List<Integer> biayaAdmin, List<Integer> polis, List<Integer> installment2Prepaid, List<Integer> premiAmountSumUp){
        List<Integer> TDPPrepaid = new ArrayList<>();
        LogUtility.logging(TAG,LogUtility.infoLog,"getTDP_ADDM","biayaAdmin",JSONProcessor.toJSON(biayaAdmin));
        for (int i=0; i<biayaAdmin.size(); i++){
            int admin = biayaAdmin.get(i);
            //int pol = polis.get(i);
            int inst = installment2Prepaid.get(i);
            int premi = premiAmountSumUp.get(i);
            //int tot = DP+inst+admin+pol+premi;
            int tot = DP+inst+admin+premi;
            TDPPrepaid.add(tot);
        }
        return TDPPrepaid;
    }

    public static List<Integer> getTDP_ADDM(int DP, List<Integer> biayaAdmin, List<Integer> polis, List<Integer> installment2Prepaid, List<Integer> premiAmountSumUp, List<Tenor> tenors){
        List<Integer> TDPPrepaid = new ArrayList<>();
        LogUtility.logging(TAG,LogUtility.infoLog,"getTDP_ADDM","biayaAdmin",JSONProcessor.toJSON(biayaAdmin));
        for (int i=0; i<tenors.size(); i++){
            int admin = biayaAdmin.get(i);
            //int pol = polis.get(i);
            int inst = installment2Prepaid.get(i);
            int premi = premiAmountSumUp.get(i);
            //int tot = DP+inst+admin+pol+premi;
            int tot = DP+inst+admin+premi;
            TDPPrepaid.add(tot);
        }
        return TDPPrepaid;
    }

    public static List<Long> getTDP_ADDM(long DP, List<Integer> biayaAdmin, List<Integer> polis, List<Long> installment2Prepaid, List<Long> premiAmountSumUp, String paymentType){
        List<Long> TDPPrepaid = new ArrayList<>();
        LogUtility.logging(TAG,LogUtility.infoLog,"getTDP_ADDM","biayaAdmin",JSONProcessor.toJSON(biayaAdmin));
        for (int i=0; i<biayaAdmin.size(); i++){
            int admin = biayaAdmin.get(i);
            //int pol = polis.get(i);
            long inst = installment2Prepaid.get(i);
            long premi = premiAmountSumUp.get(i);
            //int tot = DP+inst+admin+pol+premi;
            long tot = 0;
            if(paymentType.equals("ONLOAN")){
                LogUtility.logging(TAG,LogUtility.infoLog,"getTDP_ADDM","DP",DP+"");
                LogUtility.logging(TAG,LogUtility.infoLog,"getTDP_ADDM","Installment",inst+"");
                LogUtility.logging(TAG,LogUtility.infoLog,"getTDP_ADDM","admin",admin+"");
                tot = DP+inst+admin;
            }else{
                tot = DP+inst+admin+premi;
            }
            TDPPrepaid.add(tot);
        }
        return TDPPrepaid;
    }

    public static List<Long> getTDP_ADDM(long DP, List<Integer> biayaAdmin,
                                         List<Integer> polis, List<Long> installment2Prepaid,
                                         List<Long> premiAmountSumUp, String paymentType,
                                         List<Tenor> tenors, int sizeBunga){
        List<Long> TDPPrepaid = new ArrayList<>();
        LogUtility.logging(TAG,LogUtility.infoLog,"getTDP_ADDM","biayaAdmin",JSONProcessor.toJSON(biayaAdmin));
        for (int i=0; i<sizeBunga; i++){
            int admin = biayaAdmin.get(i);
            //int pol = polis.get(i);
            long inst = installment2Prepaid.get(i);
            long premi = premiAmountSumUp.get(i);
            //int tot = DP+inst+admin+pol+premi;
            long tot = 0;
            if(paymentType.equals("ONLOAN")){
                LogUtility.logging(TAG,LogUtility.infoLog,"getTDP_ADDM","DP",DP+"");
                LogUtility.logging(TAG,LogUtility.infoLog,"getTDP_ADDM","Installment",inst+"");
                LogUtility.logging(TAG,LogUtility.infoLog,"getTDP_ADDM","admin",admin+"");
                tot = DP+inst+admin;
            }else{
                tot = DP+inst+admin+premi;
            }
            TDPPrepaid.add(tot);
        }
        return TDPPrepaid;
    }

    public static List<Long> getTDP_ADDM2(List<Long> DP, List<Long> biayaAdmin, List<Integer> polis, List<Long> installment2Prepaid, List<Long> premiAmountSumUp, String paymentType, List<Tenor> tenors){
        List<Long> TDPPrepaid = new ArrayList<>();
        LogUtility.logging(TAG,LogUtility.infoLog,"getTDP_ADDM","biayaAdmin",JSONProcessor.toJSON(biayaAdmin));
        for (int i=0; i<DP.size(); i++){
            Long admin = biayaAdmin.get(i);
            //int pol = polis.get(i);
            Long inst = installment2Prepaid.get(i);
            long premi = premiAmountSumUp.get(i);
            //int tot = DP+inst+admin+pol+premi;
            Long tot = 0l;
            if(paymentType.equals("ONLOAN")){
                LogUtility.logging(TAG,LogUtility.infoLog,"getTDP_ADDM","DP",DP+"");
                LogUtility.logging(TAG,LogUtility.infoLog,"getTDP_ADDM","Installment",inst+"");
                LogUtility.logging(TAG,LogUtility.infoLog,"getTDP_ADDM","admin",admin+"");
                tot = DP.get(i)+inst+admin;
            }else{
                tot = DP.get(i)+inst+admin+premi;
            }
            if(DP.get(i)>0){
                TDPPrepaid.add(tot);
            }else{
                TDPPrepaid.add(0l);
            }
        }
        return TDPPrepaid;
    }

    public static String setRoundDecimalPlace(double val){
        DecimalFormat f = new DecimalFormat("##.00");
        String text = f.format(val);

        return text.replaceAll(",",".");
    }

    public static void resetGlobalData(){
        SelectedData.Otr = "";
        SelectedData.Dp = "";
        SelectedData.DpPercentage = "";
        SelectedData.Tenor = -1;
        SelectedData.Rate = "";
        SelectedData.pclPremiAmount = "";
        SelectedData.AdminFee = "";
        SelectedData.BaseDP = "";
        SelectedData.PokokHutangAkhir = "";
        SelectedData.pvPremiAmount = "";
    }

    public static List<Tenor> getAvailableTenor(List<PackageRule> packageRules){
        LogUtility.logging(TAG,LogUtility.infoLog,"getAvailableTenor","packageRules",JSONProcessor.toJSON(packageRules));
        List<String> tenors = new ArrayList<>();
        for(int i=0; i<packageRules.size(); i++){
            PackageRule packageRule = packageRules.get(i);
            String tenor = packageRule.getTenor();
            if(!tenors.contains(tenor)){
                tenors.add(tenor);
            }
        }

        List<Tenor> list = new ArrayList<>();
        for (int i=0; i<tenors.size(); i++){
            Tenor tenor = new Tenor();
            tenor.setName(tenors.get(i));
            list.add(tenor);
        }
        LogUtility.logging(TAG,LogUtility.infoLog,"getAvailableTenor","list",JSONProcessor.toJSON(list));
        return list;
    }
}
