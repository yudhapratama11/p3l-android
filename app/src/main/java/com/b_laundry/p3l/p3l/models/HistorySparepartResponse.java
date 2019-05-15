package com.b_laundry.p3l.p3l.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class HistorySparepartResponse {
    @SerializedName("data")
    @Expose
    private List<HistorySparepart> data = new ArrayList<>();

    public List<HistorySparepart> getData() {
        return data;
    }

    public void setData(List<HistorySparepart> data) {
        this.data = data;
    }
}
