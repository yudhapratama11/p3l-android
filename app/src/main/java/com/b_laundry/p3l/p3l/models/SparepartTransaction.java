package com.b_laundry.p3l.p3l.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SparepartTransaction {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("id_transaction")
    @Expose
    private String idTransaction;
    @SerializedName("id_sparepart")
    @Expose
    private String idSparepart;
    @SerializedName("nama_sparepart")
    @Expose
    private String namaSparepart;
    @SerializedName("jumlah")
    @Expose
    private Integer jumlah;
    @SerializedName("harga_satuan")
    @Expose
    private Integer hargaSatuan;
    @SerializedName("subtotal")
    @Expose
    private Integer subtotal;

    public SparepartTransaction(Integer id, String idTransaction, String idSparepart, String namaSparepart, Integer jumlah, Integer hargaSatuan, Integer subtotal) {
        this.id = id;
        this.idTransaction = idTransaction;
        this.idSparepart = idSparepart;
        this.namaSparepart = namaSparepart;
        this.jumlah = jumlah;
        this.hargaSatuan = hargaSatuan;
        this.subtotal = subtotal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(String idTransaction) {
        this.idTransaction = idTransaction;
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

    public Integer getHargaSatuan() {
        return hargaSatuan;
    }

    public void setHargaSatuan(Integer hargaSatuan) {
        this.hargaSatuan = hargaSatuan;
    }

    public Integer getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Integer subtotal) {
        this.subtotal = subtotal;
    }
}
