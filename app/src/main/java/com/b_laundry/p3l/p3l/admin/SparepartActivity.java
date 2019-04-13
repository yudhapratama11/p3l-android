package com.b_laundry.p3l.p3l.admin;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
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

import com.b_laundry.p3l.p3l.LoginActivity;
import com.b_laundry.p3l.p3l.R;
import com.b_laundry.p3l.p3l.api.Api;
import com.b_laundry.p3l.p3l.api.RetrofitClient;
import com.b_laundry.p3l.p3l.models.LoginResponse;
import com.b_laundry.p3l.p3l.models.Sparepart;
import com.b_laundry.p3l.p3l.models.SparepartResponse;
import com.b_laundry.p3l.p3l.models.SparepartType;
import com.b_laundry.p3l.p3l.models.SparepartTypeResponse;
import com.b_laundry.p3l.p3l.storage.SharedPrefManager;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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

public class SparepartActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView sparepartImg,imageViewInsert;
    private EditText etId, etNama, etStok, etMerk, etHargaBeli, etHargaJual, etPenempatan, etStokMinimal,etTipe;
    private Spinner spinnerTipe;
    private Button createButton,buttonInsertImage, editButton;
    private static final int IMG_REQUEST = 777;
    private String selectedId;
    private Bitmap bitmap;
    private String BASE_URL = "http://10.53.12.30:8000/itemImages/";
    private Bitmap ImageBitmap;
    private List<String> listSpinner = new ArrayList<String>();
    private List<String> listSpinnerIdTipe = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sparepart);

        sparepartImg = findViewById(R.id.sparepartImg);
        etId = findViewById(R.id.etKode);
        etNama = findViewById(R.id.etNama);
        etStok = findViewById(R.id.etStok);
        etMerk = findViewById(R.id.etMerk);
        etHargaBeli = findViewById(R.id.etHargaBeli);
        etHargaJual = findViewById(R.id.etHargaJual);
        etPenempatan = findViewById(R.id.etPenempatan);
        etStok = findViewById(R.id.etStok);
        etStokMinimal = findViewById(R.id.etStokMinimal);
        createButton = findViewById(R.id.btnCreate);
        editButton = findViewById(R.id.btnEditSparepart);
        spinnerTipe = findViewById(R.id.spinnerTipe);
        etTipe = findViewById(R.id.etTipe);
        buttonInsertImage = (Button)findViewById(R.id.buttonInsert);
        imageViewInsert = findViewById(R.id.imageViewAdd);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getActionBar().setTitle("Test");
        buttonInsertImage.setOnClickListener(this);
        createButton.setOnClickListener(this);
        editButton.setOnClickListener(this);

        Intent intent = getIntent();
        Log.d("intentnya", String.valueOf(intent.getStringExtra("id")));
        if(!String.valueOf(intent.getStringExtra("id")).equals("null")) { //edit
            String id = intent.getStringExtra("id");
            String nama = intent.getStringExtra("nama");
            String stok = intent.getStringExtra("stok");
            String merk = intent.getStringExtra("merk");
            String hargabeli = intent.getStringExtra("hargabeli");
            String hargajual = intent.getStringExtra("hargajual");
            String penempatan = intent.getStringExtra("penempatan");
            String stokminimal = intent.getStringExtra("stokminimal");
            String gambar = intent.getStringExtra("gambar");
            String tipe = intent.getStringExtra("tipe");
            Picasso.get().load("BASE_URL" + gambar).into(sparepartImg);
            etId.setText(id);
            etNama.setText(nama);
            etStok.setText(stok);
            etHargaJual.setText(hargajual);
            etHargaBeli.setText(hargabeli);
            etMerk.setText(merk);
            etPenempatan.setText(penempatan);
            etStokMinimal.setText(stokminimal);
            etTipe.setText(tipe);
            buttonInsertImage.setVisibility(View.GONE);

            if(String.valueOf(intent.getStringExtra("tujuan")).equals("edit")) { //edit
                etId.setEnabled(false);
                sparepartImg.setVisibility(View.GONE);
                spinnerTipe.setVisibility(View.VISIBLE);
                etTipe.setVisibility(View.GONE);
                imageViewInsert.setVisibility(View.VISIBLE);
                Picasso.get().load(BASE_URL + gambar).into(imageViewInsert);
                buttonInsertImage.setVisibility(View.VISIBLE);
                spinnerTipe.setVisibility(View.VISIBLE);
                createButton.setVisibility(View.GONE);
                editButton.setVisibility(View.VISIBLE);
                getSparepartType();
                spinnerTipe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        selectedId = listSpinnerIdTipe.get(position);
                        //String selected = parentView.getItemAtPosition(position).toString();
                        //Toast.makeText(getApplicationContext(), "Choose " + selectedId, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {
                        // your code here
                    }
                });
            }
            else //tampil
            {
                Picasso.get().load(BASE_URL + gambar).into(sparepartImg);
                editButton.setVisibility(View.GONE);
                createButton.setVisibility(View.GONE);
                sparepartImg.setVisibility(View.VISIBLE);
            }
        }
        else // create
        {
            spinnerTipe.setVisibility(View.VISIBLE);
            createButton.setVisibility(View.VISIBLE);
            imageViewInsert.setVisibility(View.VISIBLE);
            buttonInsertImage.setVisibility(View.VISIBLE);
            editButton.setVisibility(View.GONE);
            getSparepartType();
            spinnerTipe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    selectedId = listSpinnerIdTipe.get(position);
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
            case R.id.buttonInsert:
                selectImage();
                break;
            case R.id.btnCreate:
                insertSparepart();
                break;
            case R.id.btnEditSparepart:
                updateSparepart();
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
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                    new AdminSparepartFragment()).commit();
        }
        else {
            super.onBackPressed();
        }
    }

    private void selectImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_REQUEST);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMG_REQUEST && resultCode==RESULT_OK && data!=null)
        {
            Uri path = data.getData();

            try{
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                imageViewInsert.setImageBitmap(bitmap);
                ImageBitmap = bitmap;

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public  String getPath(Context context, Uri uri) throws URISyntaxException {
        Log.d("uri", String.valueOf(uri));
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { "_data" };
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public String getFileExtension(String filePath){
        String extension = "";
        try{
            extension = filePath.substring(filePath.lastIndexOf("."));
        }catch(Exception exception){
            Log.d("exceptio", String.valueOf(exception));
        }
        return  extension;
    }

    private void insertSparepart() {
        RequestBody id = RequestBody.create(MediaType.parse("multipart/form-data"), etId.getText().toString());
        RequestBody nama = RequestBody.create(MediaType.parse("multipart/form-data"), etNama.getText().toString());
        RequestBody merk = RequestBody.create(MediaType.parse("multipart/form-data"), etMerk.getText().toString());
        RequestBody harga_jual = RequestBody.create(MediaType.parse("multipart/form-data"), etHargaJual.getText().toString());
        RequestBody harga_beli = RequestBody.create(MediaType.parse("multipart/form-data"), etNama.getText().toString());
        RequestBody stok = RequestBody.create(MediaType.parse("multipart/form-data"), etStok.getText().toString());
        RequestBody stokminimal = RequestBody.create(MediaType.parse("multipart/form-data"), etStokMinimal.getText().toString());
        RequestBody penempatan = RequestBody.create(MediaType.parse("multipart/form-data"), etPenempatan.getText().toString());
        RequestBody tipe = RequestBody.create(MediaType.parse("multipart/form-data"), selectedId);

        MultipartBody.Part body = null;

        if(etId.getText().toString().isEmpty()) {
            etId.setError("Username tidak boleh kosong");
            etId.requestFocus();
            return;
        }
        if(etNama.getText().toString().isEmpty()) {
            etNama.setError("Nama tidak boleh kosong");
            etNama.requestFocus();
            return;
        }
        if(etMerk.getText().toString().isEmpty()) {
            etMerk.setError("Merk tidak boleh kosong");
            etMerk.requestFocus();
            return;
        }
        if(etHargaBeli.getText().toString().isEmpty()) {
            etHargaBeli.setError("Harga Beli tidak boleh kosong");
            etHargaBeli.requestFocus();
            return;
        }
        if(etHargaJual.getText().toString().isEmpty()) {
            etHargaJual.setError("Harga Jual tidak boleh kosong");
            etHargaJual.requestFocus();
            return;
        }
        if(etStok.getText().toString().isEmpty()) {
            etStok.setError("Stok tidak boleh kosong");
            etStok.requestFocus();
            return;
        }
        if(etPenempatan.getText().toString().isEmpty()) {
            etPenempatan.setError("Penempatan tidak boleh kosong");
            etPenempatan.requestFocus();
            return;
        }
        if(etStokMinimal.getText().toString().isEmpty()) {
            etStokMinimal.setError("Penempatan tidak boleh kosong");
            etStokMinimal.requestFocus();
            return;
        }
        if(etTipe.getText().toString().isEmpty()) {
            etTipe.setError("Tipe tidak boleh kosong");
            etTipe.requestFocus();
            return;
        }

        if(ImageBitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), data);


            body = MultipartBody.Part.createFormData("gambar", "image.jpg", requestFile);
            Log.d("requestFile", body.body().toString());
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Silahkan mengisi gambar", Toast.LENGTH_SHORT).show();
            return;
        }

        if(etId.getText().toString().isEmpty()) {
            etId.setError("Username tidak boleh kosong");
            etId.requestFocus();
            return;
        }
        if(etNama.getText().toString().isEmpty()) {
            etNama.setError("Nama tidak boleh kosong");
            etNama.requestFocus();
            return;
        }
        if(etMerk.getText().toString().isEmpty()) {
            etMerk.setError("Merk tidak boleh kosong");
            etMerk.requestFocus();
            return;
        }
        if(etHargaBeli.getText().toString().isEmpty()) {
            etHargaBeli.setError("Harga Beli tidak boleh kosong");
            etHargaBeli.requestFocus();
            return;
        }
        if(etHargaJual.getText().toString().isEmpty()) {
            etHargaJual.setError("Harga Jual tidak boleh kosong");
            etHargaJual.requestFocus();
            return;
        }
        if(etStok.getText().toString().isEmpty()) {
            etStok.setError("Stok tidak boleh kosong");
            etStok.requestFocus();
            return;
        }
        if(etPenempatan.getText().toString().isEmpty()) {
            etPenempatan.setError("Penempatan tidak boleh kosong");
            etPenempatan.requestFocus();
            return;
        }
        if(etStokMinimal.getText().toString().isEmpty()) {
            etStokMinimal.setError("Penempatan tidak boleh kosong");
            etStokMinimal.requestFocus();
            return;
        }
        if(etTipe.getText().toString().isEmpty()) {
            etTipe.setError("Tipe tidak boleh kosong");
            etTipe.requestFocus();
            return;
        }

        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().addSparepart(id, merk, nama, harga_beli, harga_jual, stok, stokminimal, penempatan, body, tipe);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String responsecode = String.valueOf(response.code());
                Log.d("responseInsert", String.valueOf(response.body()));
                Log.d("responseCode", responsecode);
                if(responsecode.equals("201")) {
                    Toast.makeText(getApplicationContext(), "Sukses Melakukan Penginputan Sparepart", Toast.LENGTH_SHORT).show();
                    onSuccess();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Failed", t.toString());
            }
        });
    }

    private void updateSparepart() {
        RequestBody id = RequestBody.create(MediaType.parse("multipart/form-data"), etId.getText().toString());
        String id1 = etId.getText().toString();
        String nama = etNama.getText().toString();
        String merk = etMerk.getText().toString();
        Integer harga_jual = Integer.parseInt(etHargaJual.getText().toString());
        Integer harga_beli = Integer.parseInt(etHargaBeli.getText().toString());
        Integer stok = Integer.parseInt(etStok.getText().toString());
        Integer stokminimal = Integer.parseInt(etStokMinimal.getText().toString());
        String penempatan = etPenempatan.getText().toString();
        Integer tipe = Integer.parseInt(selectedId);

        MultipartBody.Part body = null;

        if(ImageBitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), data);


            body = MultipartBody.Part.createFormData("gambar", "image.jpg", requestFile);
            Log.d("requestFile", body.body().toString());

            Call<ResponseBody> call = RetrofitClient.getInstance().getApi().updateSparepartPicture(id, body);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    String responsecode = String.valueOf(response.code());
                    Log.d("responseUpdatePicture", String.valueOf(response.body()));
                    Log.d("responseCodePicture", responsecode);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d("Failed", t.toString());
                }
            });

        }


        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().updateSparepart(id1, nama, merk, harga_beli, harga_jual, stok, stokminimal, penempatan, tipe);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String responsecode = String.valueOf(response.code());
                Log.d("responseUpdateSparepart", String.valueOf(response.body()));
                Log.d("responseCodeSp", responsecode);
                if(responsecode.equals("201")) {
                    Toast.makeText(getApplicationContext(), "Sukses Melakukan Penginputan Sparepart", Toast.LENGTH_SHORT).show();
                    onSuccess();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Failed", t.toString());
            }
        });
    }

    public void getSparepartType(){

        Api service = RetrofitClient.getRetrofitInstance().create(Api.class);

        Call<SparepartTypeResponse> call = service.getSparepartType();
        //Log.d("responsecode", String.valueOf(request.getSparepart()));

        call.enqueue(new Callback<SparepartTypeResponse>() {
            @Override
            public void onResponse(Call<SparepartTypeResponse> call, Response<SparepartTypeResponse> response) {
                if (response.isSuccessful()) {
                    List<SparepartType> spinnerArrayList = response.body().getData();
                    for (int i = 0; i < spinnerArrayList.size(); i++) {
                        String tipesp = spinnerArrayList.get(i).getNama();
                        String idtipe = spinnerArrayList.get(i).getId().toString();
                        listSpinner.add(tipesp);
                        listSpinnerIdTipe.add(idtipe);

                        //listSpinner.add()
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                            android.R.layout.simple_spinner_dropdown_item
                            , listSpinner);
                    spinnerTipe.setAdapter(adapter);
                }
                // leaveType.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<SparepartTypeResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
//            @Override
//            public void onResponse(Call<List<Sparepart>> call, Response<List<Sparepart>> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<List<Sparepart>> call, Throwable t) {
//
//            }
        });
    }
}
