package com.b_laundry.p3l.p3l.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SparepartProcurementDetailPost {

    @SerializedName("id_sparepart")
    @Expose
    private String idSparepart;
    @SerializedName("jumlah")
    @Expose
    private Integer jumlah;
    @SerializedName("satuan_harga")
    @Expose
    private Integer satuanHarga;
    @SerializedName("subtotal")
    @Expose
    private Integer subtotal;
    @SerializedName("id_sparepart_procurement")
    @Expose
    private Integer idSparepartProcurement;

    public SparepartProcurementDetailPost(String idSparepart, Integer jumlah, Integer satuanHarga, Integer subtotal, Integer idSparepartProcurement)
    {
        this.idSparepart = idSparepart;
        this.jumlah = jumlah;
        this.satuanHarga = satuanHarga;
        this.subtotal = subtotal;
        this.idSparepartProcurement = idSparepartProcurement;
    }
    public String getIdSparepart() {
        return idSparepart;
    }

    public void setIdSparepart(String idSparepart) {
        this.idSparepart = idSparepart;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

    public Integer getSatuanHarga() {
        return satuanHarga;
    }

    public void setSatuanHarga(Integer satuanHarga) {
        this.satuanHarga = satuanHarga;
    }

    public Integer getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Integer subtotal) {
        this.subtotal = subtotal;
    }

    public Integer getIdSparepartProcurement() {
        return idSparepartProcurement;
    }

    public void setIdSparepartProcurement(Integer idSparepartProcurement) {
        this.idSparepartProcurement = idSparepartProcurement;
    }
}
