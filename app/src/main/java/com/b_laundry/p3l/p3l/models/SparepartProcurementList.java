package com.b_laundry.p3l.p3l.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SparepartProcurementList {
    @SerializedName("data")
    @Expose
    private List<SparepartProcurement> data = new ArrayList<>();

    public List<SparepartProcurement> getData() {
        return data;
    }

    public void setData(List<SparepartProcurement> data) {
        this.data = data;
    }
}
