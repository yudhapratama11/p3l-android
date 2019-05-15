package com.b_laundry.p3l.p3l.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.b_laundry.p3l.p3l.R;
import com.b_laundry.p3l.p3l.api.RetrofitClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminCustomerActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText etNama, etAlamat, etNotelp;
    private Button createButton,editButton;
    private Integer idsupplier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_customer);
        etNama = findViewById(R.id.etNamaCustomer);
        etAlamat = findViewById(R.id.etAlamatCustomer);
        etNotelp = findViewById(R.id.etNotelpCustomer);
        createButton = findViewById(R.id.btnCreateCustomer);
        editButton = findViewById(R.id.btnUpdateCustomer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getActionBar().setTitle("Test");s
        createButton.setOnClickListener(this);
        editButton.setOnClickListener(this);


        Intent intent = getIntent();
        Log.d("intentnya", String.valueOf(intent.getStringExtra("id")));
        if(String.valueOf(intent.getStringExtra("tujuan")).equals("createTransaction")) { //edit
            editButton.setVisibility(View.GONE);
            createButton.setVisibility(View.VISIBLE);
        }
        else if(String.valueOf(intent.getStringExtra("tujuan")).equals("create")) { //edit
            editButton.setVisibility(View.GONE);
            createButton.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.btnCreateCustomer:
                insertCustomer();
                break;
            case R.id.btnUpdateSupplier:
                final ProgressDialog dialog = new ProgressDialog(AdminCustomerActivity.this);
                dialog.setMessage("Please wait.....");
                dialog.show();
                dialog.setCancelable(false);

//                editSupplier();

                dialog.dismiss();
                Runnable progressRunnable = new Runnable() {
                    @Override
                    public void run() {
                        dialog.cancel();
                    }
                };
                Handler pdCanceller = new Handler();
                pdCanceller.postDelayed(progressRunnable, 2000);
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
        final Intent intent = new Intent(AdminCustomerActivity.this, AdminHomeActivity.class);
        intent.putExtra("addDialog", 3);
        finish();
        startActivity(intent);
    }


    private void insertCustomer() {
        if(etNama.getText().toString().isEmpty()) {
            etNama.setError("Nama pelanggan tidak boleh kosong");
            etNama.requestFocus();
            return;
        }

        if(etAlamat.getText().toString().isEmpty()){
            etAlamat.setError("Alamat pelanggan tidak boleh kosong");
            etAlamat.requestFocus();
            return;
        }

        if(etNotelp.getText().toString().isEmpty()){
            etNotelp.setError("Nomor telepon pelanggan tidak boleh kosong");
            etNotelp.requestFocus();
            return;
        }

        String nama = etNama.getText().toString();
        String alamat = etAlamat.getText().toString();
        String notelp = etNotelp.getText().toString();

        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().addCustomer(nama,notelp,alamat);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String responsecode = String.valueOf(response.code());
                Log.d("responseInsert", String.valueOf(response.body()));
                Log.d("responseCode", responsecode);
                if(responsecode.equals("201")) {
                    Toast.makeText(getApplicationContext(), "Sukses Melakukan Penginputan Customer", Toast.LENGTH_SHORT).show();
                    onBackPressed();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Failed", t.toString());
            }
        });
    }

    private void editSupplier()
    {

        Integer id = idsupplier;
        String nama = etNama.getText().toString();
        String alamat = etAlamat.getText().toString();
        String notelp = etNotelp.getText().toString();

        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().editSupplier(id, nama, alamat, notelp);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String responsecode = String.valueOf(response.code());
                Log.d("responsecode", String.valueOf(response.code()));
                Log.d("response", String.valueOf(response.body()));
                if(responsecode.equals("201")) {
                    Toast.makeText(getApplicationContext(), "Sukses Melakukan Edit Supplier", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("faildelete", String.valueOf(t));
            }

        });
    }
}
