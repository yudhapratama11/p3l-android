package com.b_laundry.p3l.p3l.cs;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
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
import android.widget.Toast;

import com.b_laundry.p3l.p3l.R;
import com.b_laundry.p3l.p3l.adapter.SparepartTransactionAdapter;
import com.b_laundry.p3l.p3l.admin.AdminCustomerActivity;
import com.b_laundry.p3l.p3l.api.Api;
import com.b_laundry.p3l.p3l.api.RetrofitClient;
import com.b_laundry.p3l.p3l.models.Customer;
import com.b_laundry.p3l.p3l.models.CustomerResponse;
import com.b_laundry.p3l.p3l.models.Employees;
import com.b_laundry.p3l.p3l.models.EmployeesReponse;
import com.b_laundry.p3l.p3l.models.Sparepart;
import com.b_laundry.p3l.p3l.models.SparepartResponse;
import com.b_laundry.p3l.p3l.models.SparepartTransaction;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SparepartTransactionActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView sparepartImg;
    private Button daftarPelanggan, addSparepart,addService, addTransaction;
    private RecyclerView recyclerView;
    private Spinner spinnerSparepart,spinnerPelanggan, spinnerPegawai, spinnerMotor, spinnerMontir,
            spinnerService;
    private EditText txtHargaSatuanSparepart,jlhSparepart;
    private String selectedIdCustomer, selectedCustomer, selectedHargaSparepart, selectedIdSparepart,selectedSparepart,
             selectedIdPegawai;
    private RecyclerView sparepartRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter sparepartAdapter;

    private List<String> listSpinnerSparepart = new ArrayList<String>();
    private List<String> listSpinnerSparepartId = new ArrayList<String>();
    private List<String> listSpinnerSparepartHarga = new ArrayList<>();
    private List<String> listSpinnerCustomer = new ArrayList<String>();
    private List<String> listSpinnerIdCustomer = new ArrayList<String>();
    private List<String> listSpinnerPegawai = new ArrayList<String>();
    private List<String> listSpinnerIdPegawai = new ArrayList<String>();
    private List<String> listSpinnerCustomerMotorcycle = new ArrayList<String>();
    private List<String> listSpinnerIdCustomerMotorcycle = new ArrayList<String>();

    ArrayList<SparepartTransaction> sparepartList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int i;
        setContentView(R.layout.activity_sparepart_transaction);

        recyclerView = findViewById(R.id.cardview_procurement);
        jlhSparepart = findViewById(R.id.txtJlhSparepart);
        spinnerSparepart = findViewById(R.id.spinnerSparepart);
        spinnerPelanggan = findViewById(R.id.spinnerCustomer);
        spinnerPegawai = findViewById(R.id.spinnerCS);
        spinnerMotor = findViewById(R.id.spinnerMotor);
        spinnerMontir = findViewById(R.id.spinnerMontir);
        txtHargaSatuanSparepart = findViewById(R.id.txtHargaSatuanSparepart);
        addSparepart = findViewById(R.id.btnAddSparepart);
        daftarPelanggan = findViewById(R.id.btnDaftarCustomer);
        //getActionBar().setTitle("Test");
        addTransaction = findViewById(R.id.btnAddTransaction);

        txtHargaSatuanSparepart.setEnabled(false);
        daftarPelanggan.setOnClickListener(this);
        addSparepart.setOnClickListener(this);
        addTransaction.setOnClickListener(this);

        spinnerPelanggan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedCustomer = listSpinnerCustomer.get(position);
                selectedIdCustomer = listSpinnerIdCustomer.get(position);
                Toast.makeText(getApplicationContext(), "Choose " + selectedIdCustomer, Toast.LENGTH_SHORT).show();
                //String selected = parentView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        spinnerSparepart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedSparepart = listSpinnerSparepart.get(position);
                selectedIdSparepart = listSpinnerSparepartId.get(position);
                selectedHargaSparepart = listSpinnerSparepartHarga.get(position);
                txtHargaSatuanSparepart.setText(selectedHargaSparepart);
                Toast.makeText(getApplicationContext(), "Choose " + selectedIdSparepart, Toast.LENGTH_SHORT).show();
                //String selected = parentView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

    }

    @Override
    public void onResume() {
        listSpinnerSparepart.clear();
        listSpinnerSparepartId.clear();
        listSpinnerSparepartHarga.clear();
        listSpinnerCustomer.clear();
        listSpinnerIdCustomer.clear();
        listSpinnerPegawai.clear();
        listSpinnerIdPegawai.clear();
        spinnerSparepart.setAdapter(null);
        spinnerPelanggan.setAdapter(null);
        spinnerPegawai.setAdapter(null);
        super.onResume();
        getPegawai();
        getPelanggan();
        getSparepart();

    }
    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.btnVerificationProcurement:
//                verificationSparepart();
                break;
            case R.id.btnDaftarCustomer:
                Intent intentDaftarCustomer = new Intent(this, AdminCustomerActivity.class);
                intentDaftarCustomer.putExtra("tujuan","createTransaction");
//                intent.putExtra("nama",historyList.getData().get(i).getNama().toString());
                startActivity(intentDaftarCustomer);
                break;
            case R.id.btnAddSparepart:
                addDetailSparepart();
                break;
            case R.id.btnAddTransaction:
                postTransaction();
                break;
        }
    }

    public void addDetailSparepart()
    {
        final ProgressDialog dialog = new ProgressDialog(this);
        int i,boleh=1,jlh;



        if(jlhSparepart.getText().toString().trim().isEmpty()) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Warning");
            alertDialog.setMessage("Jumlah sparepart tidak boleh kosong!");
            alertDialog.setButton(Dialog.BUTTON_POSITIVE,"OK",new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.show();
            return;
        }
        else
        {
            jlh = Integer.parseInt(jlhSparepart.getText().toString());
        }
        Integer harga = Integer.parseInt(selectedHargaSparepart);
        Integer total = harga * jlh;

        for(i=0;i<sparepartList.size();i++) {
            String idsp = sparepartList.get(i).getIdSparepart().toString();
            Log.d("idsp",idsp);
            if(idsp.equalsIgnoreCase(selectedIdSparepart))
            {
                boleh = 0;
            }
        }

        if(boleh == 1) {
            dialog.setMessage("Please wait.....");
            dialog.show();
            dialog.setCancelable(false);
            sparepartList.add(new SparepartTransaction(null, null, selectedIdSparepart, selectedSparepart,jlh, harga, total)); //ini list tampilan

            sparepartRecyclerView = findViewById(R.id.cardview_sparepart);
            sparepartRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            sparepartAdapter = new SparepartTransactionAdapter(sparepartList);

            sparepartRecyclerView.setLayoutManager(mLayoutManager);
            sparepartRecyclerView.setAdapter(sparepartAdapter);
            sparepartAdapter.notifyDataSetChanged();
            Runnable progressRunnable = new Runnable() {

                @Override
                public void run() {
                    dialog.cancel();
                }
            };

            Handler pdCanceller = new Handler();
            pdCanceller.postDelayed(progressRunnable, 2000);
        }
        else
        {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Warning");
            alertDialog.setMessage("Sparepart sudah ditambahkan!");
            alertDialog.setButton(Dialog.BUTTON_POSITIVE,"OK",new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.show();
        }
    }

    public void postTransaction()
    {
        final String[] idTransaction = new String[1];
        int idCustomer = Integer.parseInt(selectedIdCustomer);
        final int[] successSparepart = new int[1];

        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().addTransaction(1,2,0,idCustomer, selectedIdPegawai);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String responsecode = String.valueOf(response.code());
                try {
                    JSONObject jObjError = new JSONObject(response.body().string());
                    idTransaction[0] = jObjError.getString("message");
                    Log.d("responseInsert", idTransaction[0]);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                int jumlahDetailSparepart = sparepartList.size();
                int j;
                for(j=0;j<jumlahDetailSparepart;j++)
                {
                    String idSp = sparepartList.get(j).getIdSparepart();
                    String namaSp = sparepartList.get(j).getNamaSparepart();
                    Integer jlh = sparepartList.get(j).getJumlah();
                    Integer satuan = sparepartList.get(j).getHargaSatuan();
                    Integer stotal = sparepartList.get(j).getSubtotal();
                    Call<ResponseBody> callDetail = RetrofitClient.getInstance().getApi().addSparepartTransaction(idTransaction[0], idSp, jlh, satuan, stotal);
                    callDetail.enqueue(new Callback<ResponseBody>() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onResponse(Call<ResponseBody> callDetail, Response<ResponseBody> responseDetail) {
                            Log.d("responseInsert", String.valueOf(responseDetail.body().toString()));
                            Log.d("responseCode", responseDetail.body().toString());
                            successSparepart[0] = 1;
                            sparepartList.clear();
                            Toast.makeText(getApplicationContext(), "Transaksi Sparepart Sukses", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            //
                        }
                    });
                }
                onSuccess();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Failed", t.toString());
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
                        String harga = spinnerArrayList.get(i).getHargaJual().toString();
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

    public void getPelanggan()
    {

        Api service = RetrofitClient.getRetrofitInstance().create(Api.class);

        Call<CustomerResponse> call = service.getCustomer();


        call.enqueue(new Callback<CustomerResponse>() {
            @Override
            public void onResponse(Call<CustomerResponse> call, Response<CustomerResponse> response) {
                int i;
                Log.d("responsecode", String.valueOf(response.code()));
                if (response.isSuccessful()) {
                    List<Customer> spinnerArrayList = response.body().getData();
                    for (i = 0; i < spinnerArrayList.size(); i++) {
                        String tipesp = spinnerArrayList.get(i).getNama();
                        String idtipe = spinnerArrayList.get(i).getId().toString();
                        listSpinnerCustomer.add(tipesp);
                        listSpinnerIdCustomer.add(idtipe);

                        //listSpinner.add()
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                            android.R.layout.simple_spinner_dropdown_item
                            , listSpinnerCustomer);
                    spinnerPelanggan.setAdapter(adapter);
//                    for(i=0;i<listSpinner.size();i++)
//                    {
//                        String lsp = listSpinner.get(i).toString().trim();
//                        String saless = sales.toString().trim();
//                        if(lsp.equalsIgnoreCase(saless))
//                        {
//                            spinnerPelanggan.setSelection(i);
//                        }
//                    }
                }

            }

            @Override
            public void onFailure(Call<CustomerResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }
    public void getPegawai(){

        Api service = RetrofitClient.getRetrofitInstance().create(Api.class);

        Call<EmployeesReponse> call = service.getEmployees();


        call.enqueue(new Callback<EmployeesReponse>() {
            @Override
            public void onResponse(Call<EmployeesReponse> call, Response<EmployeesReponse> response) {
                int i;
                String role;
                Log.d("responsecode", String.valueOf(response.code()));
                if (response.isSuccessful()) {
                    List<Employees> spinnerArrayList = response.body().getData();
                    for (i = 0; i < spinnerArrayList.size(); i++) {
                        role = spinnerArrayList.get(i).getIdRoles().toString().trim();
                        Log.d("role", String.valueOf(role));
                        if(role.equalsIgnoreCase("2")) {
                            String tipesp = spinnerArrayList.get(i).getNama();
                            String idtipe = spinnerArrayList.get(i).getId().toString();
                            listSpinnerPegawai.add(tipesp);
                            listSpinnerIdPegawai.add(idtipe);
                        }
                        //listSpinner.add()
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                            android.R.layout.simple_spinner_dropdown_item
                            , listSpinnerPegawai);
                    spinnerPegawai.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<EmployeesReponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
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
