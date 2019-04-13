package com.b_laundry.p3l.p3l.admin;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.b_laundry.p3l.p3l.R;
import com.b_laundry.p3l.p3l.api.Api;
import com.b_laundry.p3l.p3l.api.RetrofitClient;
import com.b_laundry.p3l.p3l.models.SparepartType;
import com.b_laundry.p3l.p3l.models.SparepartTypeResponse;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminServiceActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText etNama, etHarga;
    private Button createButton,editButton;
    private Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_service);

        etNama = findViewById(R.id.etNamaService);
        etHarga = findViewById(R.id.etHargaService);
        createButton = findViewById(R.id.btnCreateService);
        editButton = findViewById(R.id.btnEditService);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getActionBar().setTitle("Test");
        createButton.setOnClickListener(this);
        editButton.setOnClickListener(this);


        Intent intent = getIntent();
        Log.d("intentnya", String.valueOf(intent.getStringExtra("id")));
        if(String.valueOf(intent.getStringExtra("tujuan")).equals("edit")) { //edit
            createButton.setVisibility(View.GONE);
            editButton.setVisibility(View.VISIBLE);
            id = Integer.parseInt(intent.getStringExtra("id"));
            String nama = intent.getStringExtra("nama");
            String harga = intent.getStringExtra("harga");

            etHarga.setText(harga);
            etNama.setText(nama);
        }
        else
        {
            createButton.setVisibility(View.VISIBLE);
            editButton.setVisibility(View.GONE);
        }
    }
    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.btnCreateService:
                insertService();
                break;
            case R.id.btnEditService:
                editService();
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

    private void insertService() {
        String nama = etNama.getText().toString();
        Integer harga = Integer.parseInt(etHarga.getText().toString());

        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().addService(nama,harga);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String responsecode = String.valueOf(response.code());
                Log.d("responseInsert", String.valueOf(response.body()));
                Log.d("responseCode", responsecode);
                if(responsecode.equals("201")) {
                    Toast.makeText(getApplicationContext(), "Sukses Melakukan Penginputan Jasa", Toast.LENGTH_SHORT).show();
                    onSuccess();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Failed", t.toString());
            }
        });
    }

    private void editService() {
        String nama = etNama.getText().toString();
        Integer harga = Integer.parseInt(etHarga.getText().toString());
        Log.d("id", String.valueOf(id));

        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().editService(id,nama,harga);
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
