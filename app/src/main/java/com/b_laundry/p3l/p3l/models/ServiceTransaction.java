package com.b_laundry.p3l.p3l.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceTransaction {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("id_transaction")
    @Expose
    private String idTransaction;
    @SerializedName("id_service")
    @Expose
    private Integer idService;
    @SerializedName("service")
    @Expose
    private String service;
    @SerializedName("id_customer_motorcycle")
    @Expose
    private Integer idCustomerMotorcycle;
    @SerializedName("customer_motorcycle")
    @Expose
    private String customerMotorcycle;
    @SerializedName("id_montir_onduty")
    @Expose
    private Integer idMontirOnduty;
    @SerializedName("montir_onduty")
    @Expose
    private String montirOnduty;
    @SerializedName("status_montir_onduty")
    @Expose
    private Integer statusMontirOnduty;
    @SerializedName("subtotal")
    @Expose
    private Integer subtotal;


    public ServiceTransaction(Integer id, String idTransaction, Integer idService, String service, Integer idCustomerMotorcycle, String customerMotorcycle, Integer idMontirOnduty, String montirOnduty, Integer statusMontirOnduty, Integer subtotal) {
        this.id = id;
        this.idTransaction = idTransaction;
        this.idService = idService;
        this.service = service;
        this.idCustomerMotorcycle = idCustomerMotorcycle;
        this.customerMotorcycle = customerMotorcycle;
        this.idMontirOnduty = idMontirOnduty;
        this.montirOnduty = montirOnduty;
        this.statusMontirOnduty = statusMontirOnduty;
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

    public Integer getIdService() {
        return idService;
    }

    public void setIdService(Integer idService) {
        this.idService = idService;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Integer getIdCustomerMotorcycle() {
        return idCustomerMotorcycle;
    }

    public void setIdCustomerMotorcycle(Integer idCustomerMotorcycle) {
        this.idCustomerMotorcycle = idCustomerMotorcycle;
    }

    public String getCustomerMotorcycle() {
        return customerMotorcycle;
    }

    public void setCustomerMotorcycle(String customerMotorcycle) {
        this.customerMotorcycle = customerMotorcycle;
    }

    public Integer getIdMontirOnduty() {
        return idMontirOnduty;
    }

    public void setIdMontirOnduty(Integer idMontirOnduty) {
        this.idMontirOnduty = idMontirOnduty;
    }

    public String getMontirOnduty() {
        return montirOnduty;
    }

    public void setMontirOnduty(String montirOnduty) {
        this.montirOnduty = montirOnduty;
    }

    public Integer getStatusMontirOnduty() {
        return statusMontirOnduty;
    }

    public void setStatusMontirOnduty(Integer statusMontirOnduty) {
        this.statusMontirOnduty = statusMontirOnduty;
    }

    public Integer getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Integer subtotal) {
        this.subtotal = subtotal;
    }
}
