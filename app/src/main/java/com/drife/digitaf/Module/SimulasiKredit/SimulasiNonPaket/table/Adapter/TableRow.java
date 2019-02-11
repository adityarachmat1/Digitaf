package com.drife.digitaf.Module.SimulasiNonPaket.table.Adapter;

public class TableRow {
    private String tdp;
    private String cicilan;

    public TableRow() {
    }

    public TableRow(String tdp, String cicilan) {
        this.tdp = tdp;
        this.cicilan = cicilan;
    }

    public String getTdp() {
        return tdp;
    }

    public void setTdp(String tdp) {
        this.tdp = tdp;
    }

    public String getCicilan() {
        return cicilan;
    }

    public void setCicilan(String cicilan) {
        this.cicilan = cicilan;
    }
}
