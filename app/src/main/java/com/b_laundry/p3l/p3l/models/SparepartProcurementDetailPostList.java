package com.b_laundry.p3l.p3l.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SparepartProcurementDetailPostList {
    @SerializedName("id_sales")
    @Expose
    private Integer idSales;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("detail")
    @Expose
    private List<SparepartProcurementDetailPost> detail = new ArrayList<>();

    public Integer getIdSales() {
        return idSales;
    }

    public void setIdSales(Integer idSales) {
        this.idSales = idSales;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<SparepartProcurementDetailPost> getDetail() {
        return detail;
    }

    public void setDetail(List<SparepartProcurementDetailPost> detail) {
        this.detail = detail;
    }
}

