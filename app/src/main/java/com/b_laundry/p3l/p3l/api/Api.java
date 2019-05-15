package com.b_laundry.p3l.p3l.api;

import com.b_laundry.p3l.p3l.models.BranchResponse;
import com.b_laundry.p3l.p3l.models.CustomerMotorcycleResponse;
import com.b_laundry.p3l.p3l.models.CustomerResponse;
import com.b_laundry.p3l.p3l.models.EmployeesReponse;
import com.b_laundry.p3l.p3l.models.HistorySparepartResponse;
import com.b_laundry.p3l.p3l.models.LoginResponse;
import com.b_laundry.p3l.p3l.models.SalesResponse;
import com.b_laundry.p3l.p3l.models.ServiceResponse;

import com.b_laundry.p3l.p3l.models.ServiceTransactionResponse;
import com.b_laundry.p3l.p3l.models.SparepartProcurementDetailList;
import com.b_laundry.p3l.p3l.models.SparepartProcurementList;
import com.b_laundry.p3l.p3l.models.SparepartResponse;
import com.b_laundry.p3l.p3l.models.SparepartTransactionResponse;
import com.b_laundry.p3l.p3l.models.SparepartTypeResponse;
import com.b_laundry.p3l.p3l.models.SupplierResponse;
import com.b_laundry.p3l.p3l.models.TransactionResponse;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface Api {
    @FormUrlEncoded
    @POST("auth/loginAndroid")
    Call<LoginResponse> loginAndroid(
            @Field("username") String username,
            @Field("password") String password
    );

    @GET("historysparepart")
    Call<HistorySparepartResponse> getHistory();

    @GET("sparepart")
    Call<SparepartResponse> getSparepart();

    @GET("sparepartkurang")
    Call<SparepartResponse> getSparepartLess();

    @GET("spareparttype")
    Call<SparepartTypeResponse> getSparepartType();

    @Multipart
    @POST("sparepart")
    Call<ResponseBody> addSparepart(
            @Part("id") RequestBody id,
            @Part("nama") RequestBody nama,
            @Part("merk") RequestBody merk,
            @Part("harga_beli") RequestBody hargaBeli,
            @Part("harga_jual") RequestBody hargaJual,
            @Part("stok") RequestBody stok,
            @Part("stok_minimal") RequestBody stokMinimal,
            @Part("penempatan") RequestBody penempatan,
            @Part MultipartBody.Part gambar,
            @Part("id_sparepart_type") RequestBody idSparepartType
    );

    @GET("sparepartstokterdikit")
    Call<SparepartResponse> getSparepartLowStock();

    @GET("sparepartstokterbanyak")
    Call<SparepartResponse> getSparepartHighStock();

    @GET("sparepartpricetermurah")
    Call<SparepartResponse> getSparepartLowPrice();

    @GET("sparepartpricetermahal")
    Call<SparepartResponse> getSparepartHighPrice();

    @DELETE("sparepart/{id}")
    Call<ResponseBody> deleteSparepart(
            @Path("id") String id
    );

    @GET("employee")
    Call<EmployeesReponse> getEmployees();

    @DELETE("employee/{id}")
    Call<ResponseBody> deleteEmployee(
            @Path("id") String id
    );

    @GET("sales")
    Call<SalesResponse> getSales();

    @FormUrlEncoded
    @POST("supplier")
    Call<ResponseBody> addSupplier(
            @Field("nama") String nama,
            @Field("nomor_telepon") String nomor_telepon,
            @Field("alamat") String alamat

    );

    @FormUrlEncoded
    @POST("procurementAndroid")
    Call<ResponseBody> addSparepartProcurement(
            @Field("id_sales") String id_sales
            );

    @FormUrlEncoded
    @POST("procurementDetailAndroid")
    Call<ResponseBody> addSparepartDetailProcurement(
            @Field("id_sparepart") String id_sparepart,
            @Field("id_sparepart_procurement") String nomor_telepon,
            @Field("jumlah") Integer jumlah,
            @Field("satuan_harga") Integer satuan_harga,
            @Field("subtotal") Integer subtotal
    );

    @GET("procurementDetailAndroid/{id}")
    Call<SparepartProcurementDetailList> getProcurementDetailFromId(
            @Path("id") String id
    );

    @GET("sparepartprocurement")
    Call<SparepartProcurementList> getProcurement(
    );

    @FormUrlEncoded
    @PATCH("procurementAndroid/{id}")
    Call<ResponseBody> verifProcurement(
        @Path("id") String id,
        @Field("id_sales") String id_sales
    );

    @FormUrlEncoded
    @PATCH("procurementDetailAndroid/{id}")
    Call<ResponseBody> verifProcurementDetail(
        @Path("id") String id,
        @Field("jumlah") Integer jumlah,
        @Field("satuan_harga") Integer satuan_harga,
        @Field("subtotal") Integer subtotal
    );

    @DELETE("supplier/{id}")
    Call<ResponseBody> deleteSupplier(
            @Path("id") String id
    );

    @FormUrlEncoded
    @PATCH("supplier/{id}")
    Call<ResponseBody> editSupplier(
            @Path("id") Integer id,
            @Field("nama") String nama,
            @Field("alamat") String alamat,
            @Field("nomor_telepon") String nomor_telepon
    );

    @GET("branchesAndroid")
    Call<BranchResponse> getBranches();

    @DELETE("branches/{id}")
    Call<ResponseBody> deleteBranch(
            @Path("id") String id
    );

    @DELETE("service/{id}")
    Call<ResponseBody> deleteService(
            @Path("id") String id
    );

    @DELETE("sales/{id}")
    Call<ResponseBody> deleteSales(
            @Path("id") String id
    );

    @GET("supplier")
    Call<SupplierResponse> getSuppliers();

    @GET("service")
    Call<ServiceResponse> getServices();

    @FormUrlEncoded
    @POST("service")
    Call<ResponseBody> addService(
            @Field("nama") String nama,
            @Field("harga") Integer harga
    );

    @FormUrlEncoded
    @POST("sales")
    Call<ResponseBody> addSales(
            @Field("nama") String nama,
            @Field("nomor_telepon") String nomor_telepon,
            @Field("id_supplier") Integer id_supplier
    );

    @FormUrlEncoded
    @PATCH("sales/{id}")
    Call<ResponseBody> editSales(
            @Path("id") Integer id,
            @Field("nama") String nama,
            @Field("nomor_telepon") String nomor_telepon,
            @Field("id_supplier") Integer id_supplier
    );

    @FormUrlEncoded
    @PATCH("service/{id}")
    Call<ResponseBody> editService(
            @Path("id") Integer id,
            @Field("nama") String nama,
            @Field("harga") Integer harga
    );

    @FormUrlEncoded
    @PATCH("sparepartAndroid/{id}")
    Call<ResponseBody> updateSparepart(
            @Path("id") String id,
            @Field("nama") String nama,
            @Field("merk") String merk,
            @Field("harga_beli") Integer hargaBeli,
            @Field("harga_jual") Integer hargaJual,
            @Field("stok") Integer stok,
            @Field("stok_minimal") Integer stokMinimal,
            @Field("penempatan") String penempatan,
            @Field("id_sparepart_type") Integer idSparepartType
    );

    @Multipart
    @POST("gambarsparepartAndroid")
    Call<ResponseBody> updateSparepartPicture(
            @Part("id") RequestBody id,
            @Part MultipartBody.Part gambar
    );

    @GET("transaction")
    Call<TransactionResponse> getTransaction();

    @GET("customer")
    Call<CustomerResponse> getCustomer();

    @GET("customermotorcyclebyid/{id}")
    Call<CustomerMotorcycleResponse> getCustomerMotorcycleById(
        @Path("id") String Id
    );

    @FormUrlEncoded
    @POST("customer")
    Call<ResponseBody> addCustomer(
            @Field("nama") String nama,
            @Field("nomor_telepon") String nomor_telepon,
            @Field("alamat") String alamat
    );

    @FormUrlEncoded
    @POST("transactionAndroid")
    Call<ResponseBody> addTransaction(
            @Field("status") Integer status,
            @Field("id_transaction_type") Integer id_transaction_type,
            @Field("subtotal") Integer subtotal,
            @Field("id_customer") Integer id_customer,
            @Field("id_employee") String id_employee
    );

    @FormUrlEncoded
    @PATCH("transactionAndroid/{id}")
    Call<ResponseBody> updateTransaction(
            @Path("id") String id,
            @Field("id_transaction_type") Integer id_transaction_type
    );

    @FormUrlEncoded
    @POST("spareparttransaction")
    Call<ResponseBody> addSparepartTransaction(
            @Field("id_transaction") String id_transaction,
            @Field("id_sparepart") String id_sparepart,
            @Field("jumlah") Integer jumlah,
            @Field("harga_satuan") Integer harga_satuan,
            @Field("subtotal") Integer subtotal
    );

    @FormUrlEncoded
    @POST("servicetransaction")
    Call<ResponseBody> addServiceTransaction(
            @Field("id_transaction") String id_transaction,
            @Field("id_service") Integer id_service,
            @Field("id_customer_motorcycle") Integer id_customer_motorcycle,
            @Field("id_montir_onduty") Integer id_montir_onduty,
            @Field("status_montir_onduty") Integer status_montir_onduty,
            @Field("subtotal") Integer subtotal
    );

    @DELETE("customer/{id}")
    Call<ResponseBody> deleteCustomer(
            @Path("id") String id
    );

    @GET("spareparttransaction/{id}")
    Call<SparepartTransactionResponse> getSparepartTransactionDetailFromId(
            @Path("id") String id
    );

    @GET("servicetransaction/{id}")
    Call<ServiceTransactionResponse> getServiceTransactionDetailFromId(
            @Path("id") String id
    );


}
