package com.b_laundry.p3l.p3l.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.b_laundry.p3l.p3l.R;
import com.b_laundry.p3l.p3l.api.Api;
import com.b_laundry.p3l.p3l.api.RetrofitClient;
import com.b_laundry.p3l.p3l.models.SparepartType;
import com.b_laundry.p3l.p3l.models.SparepartTypeResponse;
import com.b_laundry.p3l.p3l.models.Supplier;
import com.b_laundry.p3l.p3l.models.SupplierResponse;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminSalesActivity extends AppCompatActivity implements View.OnClickListener{
    private List<String> listSpinner = new ArrayList<String>();
    private List<String> listSpinnerIdSupplier = new ArrayList<String>();
    private Spinner spinnerSupplier;
    private Button createButton, editButton;
    private EditText etNama, etNotelp;
    private String selectedId;
    private Integer id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_sales);
        createButton = findViewById(R.id.btnCreateSales);
        editButton = findViewById(R.id.btnEditSaless);

        etNama = findViewById(R.id.etNamaSales);
        etNotelp = findViewById(R.id.etNotelpSales);
        spinnerSupplier = findViewById(R.id.spinnerSupplier);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        //getActionBar().setTitle("Test");
        createButton.setOnClickListener(this);
        editButton.setOnClickListener(this);


        Intent intent = getIntent();
        Log.d("intentnya", String.valueOf(intent.getStringExtra("id")));
        if(String.valueOf(intent.getStringExtra("tujuan")).equals("edit")) { //edit
            createButton.setVisibility(View.GONE);
            editButton.setVisibility(View.VISIBLE);
            getSupplierType();
            spinnerSupplier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    selectedId = listSpinnerIdSupplier.get(position);
                    //String selected = parentView.getItemAtPosition(position).toString();
                    //Toast.makeText(getApplicationContext(), "Choose " + selectedId, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });
            id = Integer.parseInt(intent.getStringExtra("id"));
            String nama = intent.getStringExtra("nama");
            String notelp = intent.getStringExtra("notelp");
            String supplier = intent.getStringExtra("namasupplier");

            etNotelp.setText(notelp);
            etNama.setText(nama);
        }
        else
        {
            createButton.setVisibility(View.VISIBLE);
            editButton.setVisibility(View.GONE);
            getSupplierType();
            spinnerSupplier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    selectedId = listSpinnerIdSupplier.get(position);
                    //String selected = parentView.getItemAtPosition(position).toString();
                    //Toast.makeText(getApplicationContext(), "Choose " + selectedId, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }

            });
        }
    }
    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.btnCreateSales:
                insertSales();
                break;
            case R.id.btnEditSaless:
                editSales();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //Title bar back press triggers onBackPressed()
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
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new AdminServiceFragment()).commit();
        }
        else {
            super.onBackPressed();
        }
    }

    public void getSupplierType(){

        Api service = RetrofitClient.getRetrofitInstance().create(Api.class);

        Call<SupplierResponse> call = service.getSuppliers();
        //Log.d("responsecode", String.valueOf(request.getSparepart()));

        call.enqueue(new Callback<SupplierResponse>() {
            @Override
            public void onResponse(Call<SupplierResponse> call, Response<SupplierResponse> response) {
                if (response.isSuccessful()) {
                    List<Supplier> spinnerArrayList = response.body().getData();
                    for (int i = 0; i < spinnerArrayList.size(); i++) {
                        String tipesp = spinnerArrayList.get(i).getNama();
                        String idtipe = spinnerArrayList.get(i).getId().toString();
                        listSpinner.add(tipesp);
                        listSpinnerIdSupplier.add(idtipe);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                            android.R.layout.simple_spinner_dropdown_item
                            , listSpinner);
                    spinnerSupplier.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<SupplierResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }

    private void insertSales() {
        String nama = etNama.getText().toString();
        String notelp = etNotelp.getText().toString();
        Integer idsupplier = Integer.parseInt(selectedId);

        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().addSales(nama,notelp,idsupplier);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String responsecode = String.valueOf(response.code());
                Log.d("responseInsert", String.valueOf(response.body()));
                Log.d("responseCode", responsecode);
                if(responsecode.equals("201")) {
                    Toast.makeText(getApplicationContext(), "Sukses Melakukan Penginputan Sales", Toast.LENGTH_SHORT).show();
                    onSuccess();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Failed", t.toString());
            }
        });
    }

    private void editSales() {
        String nama = etNama.getText().toString();
        String notelp = etNotelp.getText().toString();
        Integer idsupplier = Integer.parseInt(selectedId);
        Log.d("id", String.valueOf(id));

        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().editSales(id,nama,notelp,idsupplier);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String responsecode = String.valueOf(response.code());
                Log.d("responseInsert", String.valueOf(response.body()));
                Log.d("responseCode", responsecode);
                if(responsecode.equals("201")) {
                    Toast.makeText(getApplicationContext(), "Sukses Melakukan Edit Jasa", Toast.LENGTH_SHORT).show();
                    onSuccess();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Failed", t.toString());
            }
        });
    }
}
