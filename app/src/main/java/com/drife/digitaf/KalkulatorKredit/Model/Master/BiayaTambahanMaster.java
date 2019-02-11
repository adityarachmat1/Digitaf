package com.drife.digitaf.KalkulatorKredit.Model.Master;

import java.util.ArrayList;
import java.util.List;

public class BiayaTambahanMaster {
    private static List<Integer> biayaAdmin;
    private static List<Integer> polis;

    public static List<Integer> getBiayaAdmin(){
        biayaAdmin = new ArrayList<>();
        biayaAdmin.add(2000000);
        biayaAdmin.add(2000000);
        biayaAdmin.add(2250000);
        biayaAdmin.add(2250000);
        biayaAdmin.add(2250000);
        return biayaAdmin;
    }

    public static List<Integer> getPolis(){
        polis = new ArrayList<>();
        polis.add(50000);
        polis.add(50000);
        polis.add(50000);
        polis.add(50000);
        polis.add(50000);
        return polis;
    }
}
