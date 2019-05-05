package com.b_laundry.p3l.p3l.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SparepartProcurementDetail {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("id_sparepart")
    @Expose
    private String idSparepart;
    @SerializedName("nama_sparepart")
    @Expose
    private String namaSparepart;
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

    public SparepartProcurementDetail(Integer id, String idSparepart, String namaSparepart, Integer jumlah, Integer satuanHarga, Integer subtotal, Integer idSparepartProcurement) {
        this.id = id;
        this.idSparepart = idSparepart;
        this.jumlah = jumlah;
        this.namaSparepart=namaSparepart;
        this.satuanHarga = satuanHarga;
        this.subtotal = subtotal;
        this.idSparepartProcurement=idSparepartProcurement;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdSparepart() {
        return idSparepart;
    }

    public void setIdSparepart(String idSparepart) {
        this.idSparepart = idSparepart;
    }

    public String getNamaSparepart() {
        return namaSparepart;
    }

    public void setNamaSparepart(String namaSparepart) {
        this.namaSparepart = namaSparepart;
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
