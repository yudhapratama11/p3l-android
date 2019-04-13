package com.b_laundry.p3l.p3l.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SparepartResponse extends Sparepart{
    @SerializedName("data")
    @Expose

    private List<Sparepart> data = new ArrayList<>();

    public List<Sparepart> getData() {
        return data;
    }

    public void setData(List<Sparepart> data) {
        this.data = data;
    }
}
