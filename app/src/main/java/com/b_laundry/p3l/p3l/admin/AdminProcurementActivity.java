package com.b_laundry.p3l.p3l.admin;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.b_laundry.p3l.p3l.R;
import com.b_laundry.p3l.p3l.adapter.ProcurementAdapter;
import com.b_laundry.p3l.p3l.adapter.UpdateProcurementCartAdapter;
import com.b_laundry.p3l.p3l.api.Api;
import com.b_laundry.p3l.p3l.api.RetrofitClient;
import com.b_laundry.p3l.p3l.models.Sales;
import com.b_laundry.p3l.p3l.models.SalesResponse;
import com.b_laundry.p3l.p3l.models.Sparepart;
import com.b_laundry.p3l.p3l.models.SparepartProcurementDetail;
import com.b_laundry.p3l.p3l.models.SparepartProcurementDetailList;
import com.b_laundry.p3l.p3l.models.SparepartProcurementDetailPost;
import com.b_laundry.p3l.p3l.models.SparepartProcurementList;
import com.b_laundry.p3l.p3l.models.SparepartResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminProcurementActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView sparepartImg;
    private Button verifButton;
    private RecyclerView recyclerView;
    private UpdateProcurementCartAdapter updateProcurementCartAdapter;
    private String idDetail, idSales;
    private String selectedIdSales,selectedNamaSparepart,selectedId;
    private Spinner spinnerSparepart,spinnerSales;
    private String selectedHargaSparepart;
    private TextView hargaBeliSparepart,jlhSparepart;
    private String sales;
    private Integer status;

    private List<String> listSpinner = new ArrayList<String>();
    private List<String> listSpinnerId = new ArrayList<String>();
    private List<String> listSpinnerSparepart = new ArrayList<String>();
    private List<String> listSpinnerSparepartId = new ArrayList<String>();
    private List<String> listSpinnerSparepartHarga = new ArrayList<>();
    ArrayList<SparepartProcurementDetail> exampleList = new ArrayList<>();
    ArrayList<SparepartProcurementDetailPost> postList = new ArrayList<>();
    SparepartProcurementDetailList exampleList1 = new SparepartProcurementDetailList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int i;
        setContentView(R.layout.admin_activity_update_procurement);

        recyclerView = findViewById(R.id.cardview_procurement);
        sparepartImg = findViewById(R.id.procurementImage);
        spinnerSales = findViewById(R.id.spinnerSalesUpdate);
        verifButton = findViewById(R.id.btnVerificationProcurement);
//        hargaBeliSparepart.setEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        verifButton.setOnClickListener(this);

        //getActionBar().setTitle("Test");
        spinnerSales.setEnabled(false);
        spinnerSales.setClickable(false);
        Intent intent = getIntent();
        idDetail = intent.getStringExtra("id");
        idSales = intent.getStringExtra("idsales");
        sales = intent.getStringExtra("sales");
        status = Integer.parseInt(intent.getStringExtra("status").toString());
//        String tanggal = intent.getStringExtra("tanggal");
        getSparepartDetail();
        getSales();



        spinnerSales.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedId = listSpinner.get(position);
                selectedIdSales = listSpinnerId.get(position);
                //String selected = parentView.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Choose " + selectedId, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });


    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.btnVerificationProcurement:
                verificationSparepart();
                break;
        }
    }

    public void verificationSparepart()
    {
        final String[] idProcurements = new String[1];

        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().verifProcurement(idDetail,selectedIdSales);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String responsecode = String.valueOf(response.code());
                try {
                    JSONObject jObjError = new JSONObject(response.body().string());
                    idProcurements[0] = jObjError.getString("message");
                    //Toast.makeText(getApplicationContext(), jObjError.getString("error"), Toast.LENGTH_LONG).show();
                    Log.d("responseInsert", idProcurements[0]);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                int jumlahDetail = exampleList1.getData().size();
                int j;
                for(j=0;j<jumlahDetail;j++)
                {
                    String id = exampleList1.getData().get(j).getId().toString();
                    //String namaSp = exampleList1.getData().get(j).getNamaSparepart();
                    Integer jlh = exampleList1.getData().get(j).getJumlah();
                    Integer satuan = exampleList1.getData().get(j).getSatuanHarga();
                    Integer stotal = exampleList1.getData().get(j).getSubtotal();
                    Call<ResponseBody> callDetail = RetrofitClient.getInstance().getApi().verifProcurementDetail(id,jlh,satuan,stotal);
                    callDetail.enqueue(new Callback<ResponseBody>() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onResponse(Call<ResponseBody> callDetail, Response<ResponseBody> responseDetail) {
                            Log.d("responseInsert", String.valueOf(responseDetail.body().toString()));
                            Log.d("responseCode", responseDetail.body().toString());
                            exampleList.clear();
                            Toast.makeText(getApplicationContext(), "Pemesanan Sparepart Sukses", Toast.LENGTH_SHORT).show();
                            onSuccess();
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            //
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Failed", t.toString());
                //
            }
        });
    }
    public void getSparepart(){

        Api service = RetrofitClient.getRetrofitInstance().create(Api.class);

        Call<SparepartResponse> call = service.getSparepart();


        call.enqueue(new Callback<SparepartResponse>() {
            @Override
            public void onResponse(Call<SparepartResponse> call, Response<SparepartResponse> response) {
                Log.d("responsecode", String.valueOf(response.code()));
                if (response.isSuccessful()) {
                    List<Sparepart> spinnerArrayList = response.body().getData();
                    for (int i = 0; i < spinnerArrayList.size(); i++) {
                        String namasp = spinnerArrayList.get(i).getNama();
                        String idtipe = spinnerArrayList.get(i).getId().toString();
                        String harga = spinnerArrayList.get(i).getHargaBeli().toString();
                        listSpinnerSparepart.add(namasp);
                        listSpinnerSparepartId.add(idtipe);
                        listSpinnerSparepartHarga.add(harga);
                    }
                    ArrayAdapter<String> adapterSparepart = new ArrayAdapter<>(getApplicationContext(),
                            android.R.layout.simple_spinner_dropdown_item
                            , listSpinnerSparepart);
                    spinnerSparepart.setAdapter(adapterSparepart);
                }

            }

            @Override
            public void onFailure(Call<SparepartResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }

    public void getSales(){

        Api service = RetrofitClient.getRetrofitInstance().create(Api.class);

        Call<SalesResponse> call = service.getSales();


        call.enqueue(new Callback<SalesResponse>() {
            @Override
            public void onResponse(Call<SalesResponse> call, Response<SalesResponse> response) {
                int i;
                Log.d("responsecode", String.valueOf(response.code()));
                if (response.isSuccessful()) {
                    List<Sales> spinnerArrayList = response.body().getData();
                    for (i = 0; i < spinnerArrayList.size(); i++) {
                        String tipesp = spinnerArrayList.get(i).getNama();
                        String idtipe = spinnerArrayList.get(i).getId().toString();
                        listSpinner.add(tipesp);
                        listSpinnerId.add(idtipe);

                        //listSpinner.add()
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                            android.R.layout.simple_spinner_dropdown_item
                            , listSpinner);
                    spinnerSales.setAdapter(adapter);
                    for(i=0;i<listSpinner.size();i++)
                    {
                        String lsp = listSpinner.get(i).toString().trim();
                        String saless = sales.toString().trim();
                        if(lsp.equalsIgnoreCase(saless))
                        {
                            spinnerSales.setSelection(i);
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<SalesResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }

    public void getSparepartDetail(){

        final Api service = RetrofitClient.getRetrofitInstance().create(Api.class);

        Call<SparepartProcurementDetailList> call = service.getProcurementDetailFromId(idDetail);

        call.enqueue(new Callback<SparepartProcurementDetailList>() {
            @Override
            public void onResponse(Call<SparepartProcurementDetailList> call, final Response<SparepartProcurementDetailList> response) {
                Log.d("responsdecodenya", String.valueOf(response.code()));
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<SparepartProcurementDetailList> call, Throwable t) {
                Log.d("ErrorLess",t.getMessage());
            }
        });
    }

    public void generateDataList(SparepartProcurementDetailList procurementList) {
        recyclerView = recyclerView.findViewById(R.id.cardview_procurement);
        updateProcurementCartAdapter = new UpdateProcurementCartAdapter(this,procurementList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(updateProcurementCartAdapter);
        exampleList1 = procurementList;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0 ) {
            getFragmentManager().popBackStack();
        }
        else {
            super.onBackPressed();
        }
    }

    public void onSuccess() {
        if (getFragmentManager().getBackStackEntryCount() > 0 ) {
            getFragmentManager().popBackStack();
        }
        else {
            super.onBackPressed();
        }
    }
}
