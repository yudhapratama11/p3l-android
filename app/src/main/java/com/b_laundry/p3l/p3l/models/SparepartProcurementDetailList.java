package com.b_laundry.p3l.p3l.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SparepartProcurementDetailList {
    @SerializedName("data")
    @Expose
    private List<SparepartProcurementDetail> data = new ArrayList<>();

    public List<SparepartProcurementDetail> getData() {
        return data;
    }

    public void setData(List<SparepartProcurementDetail> data) {
        this.data = data;
    }

}
