package com.b_laundry.p3l.p3l.api;

import com.b_laundry.p3l.p3l.models.BranchResponse;
import com.b_laundry.p3l.p3l.models.EmployeesReponse;
import com.b_laundry.p3l.p3l.models.LoginResponse;
import com.b_laundry.p3l.p3l.models.ServiceResponse;
import com.b_laundry.p3l.p3l.models.Sparepart;
import com.b_laundry.p3l.p3l.models.SparepartResponse;
import com.b_laundry.p3l.p3l.models.SparepartTypeResponse;
import com.b_laundry.p3l.p3l.models.SupplierResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
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
    @POST("loginAndroid")
    Call<LoginResponse> loginAndroid(
            @Field("username") String username,
            @Field("password") String password
    );

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

    @FormUrlEncoded
    @POST("supplierAndroid")
    Call<ResponseBody> addSupplier(
            @Field("nama") String nama,
            @Field("nomor_telepon") String nomor_telepon,
            @Field("alamat") String alamat

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

    @GET("supplier")
    Call<SupplierResponse> getSuppliers();

    @GET("serviceAndroid")
    Call<ServiceResponse> getServices();

    @FormUrlEncoded
    @POST("service")
    Call<ResponseBody> addService(
            @Field("nama") String nama,
            @Field("harga") Integer harga
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
}
