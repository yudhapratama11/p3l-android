package com.b_laundry.p3l.p3l.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SparepartTransactionResponse {
    @SerializedName("data")
    @Expose
    private List<SparepartTransaction> data = new ArrayList<>();

    public List<SparepartTransaction> getData() {
        return data;
    }

    public void setData(List<SparepartTransaction> data) {
        this.data = data;
    }
}
