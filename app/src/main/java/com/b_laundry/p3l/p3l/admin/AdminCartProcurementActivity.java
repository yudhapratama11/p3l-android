package com.b_laundry.p3l.p3l.admin;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.b_laundry.p3l.p3l.R;
import com.b_laundry.p3l.p3l.adapter.ProcurementCartAdapter;
import com.b_laundry.p3l.p3l.api.Api;
import com.b_laundry.p3l.p3l.api.RetrofitClient;
import com.b_laundry.p3l.p3l.models.Sales;
import com.b_laundry.p3l.p3l.models.SalesResponse;
import com.b_laundry.p3l.p3l.models.Sparepart;
import com.b_laundry.p3l.p3l.models.SparepartProcurementDetail;
import com.b_laundry.p3l.p3l.models.SparepartProcurementDetailList;
import com.b_laundry.p3l.p3l.models.SparepartProcurementDetailPost;
import com.b_laundry.p3l.p3l.models.SparepartResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminCartProcurementActivity extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private SparepartProcurementDetailList sparepartProcDetailList;

    private List<String> listSpinner = new ArrayList<String>();
    private List<String> listSpinnerId = new ArrayList<String>();
    private List<String> listSpinnerSparepart = new ArrayList<String>();
    private List<String> listSpinnerSparepartId = new ArrayList<String>();
    private List<String> listSpinnerSparepartHarga = new ArrayList<>();
    ArrayList<SparepartProcurementDetail> exampleList = new ArrayList<>();
    ArrayList<SparepartProcurementDetailPost> postList = new ArrayList<>();

    private TextView hargaBeliSparepart,jlhSparepart;
    private String selectedId, selectedIdSparepart,selectedNamaSparepart;
    private String selectedHargaSparepart;
    private Spinner spinnerSales, spinnerSparepart;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button tambahSparepartDetail;
    private Button postSparepartProcurement;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_add_procurement);

        recyclerView = findViewById(R.id.cardview_procurement);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        spinnerSales = findViewById(R.id.spinnerSales);
        jlhSparepart = findViewById(R.id.txtJlhSparepartProc);
        tambahSparepartDetail = (Button) findViewById(R.id.btnTambahProcurementSp);
        spinnerSparepart = findViewById(R.id.spinnerSparepart);
        postSparepartProcurement = findViewById(R.id.btnPostProcurement);
        hargaBeliSparepart = findViewById(R.id.txtHBSparepartProc);
        hargaBeliSparepart.setEnabled(false);
        tambahSparepartDetail.setOnClickListener(this);
        postSparepartProcurement.setOnClickListener(this);

        getSales();
        getSparepart();


        spinnerSales.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedId = listSpinnerId.get(position);
                //String selected = parentView.getItemAtPosition(position).toString();
                //Toast.makeText(getApplicationContext(), "Choose " + selectedId, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
        spinnerSparepart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedIdSparepart = listSpinnerSparepartId.get(position);
                selectedNamaSparepart = listSpinnerSparepart.get(position);
                selectedHargaSparepart = listSpinnerSparepartHarga.get(position);
                hargaBeliSparepart.setText(selectedHargaSparepart);
                //String selected = parentView.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Choose " + selectedIdSparepart, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });


    }


    public void getSales(){

        Api service = RetrofitClient.getRetrofitInstance().create(Api.class);

        Call<SalesResponse> call = service.getSales();


        call.enqueue(new Callback<SalesResponse>() {
            @Override
            public void onResponse(Call<SalesResponse> call, Response<SalesResponse> response) {
                Log.d("responsecode", String.valueOf(response.code()));
                if (response.isSuccessful()) {
                    List<Sales> spinnerArrayList = response.body().getData();
                    for (int i = 0; i < spinnerArrayList.size(); i++) {
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
                }

            }

            @Override
            public void onFailure(Call<SalesResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
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

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.btnTambahProcurementSp:
                addDetailProcurement();
                break;
            case R.id.btnPostProcurement:
                int jumlahDetail = exampleList.size();
                if(jumlahDetail>0) {
                    postProcurement();
                }
                else
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setTitle("Warning");
                    alertDialog.setMessage("Sparepart yang dimasukan kosong, silahkan masukkan sparepart yang ingin dipesan!");
                    alertDialog.setButton(Dialog.BUTTON_POSITIVE,"OK",new DialogInterface.OnClickListener(){

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();
                }
                break;
        }
    }

    public void postProcurement()
    {
        final String[] idProcurements = new String[1];

        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().addSparepartProcurement(selectedId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String responsecode = String.valueOf(response.code());
                try {
                    JSONObject jObjError = new JSONObject(response.body().string());
                    idProcurements[0] = jObjError.getString("message");
                    Log.d("responseInsert", idProcurements[0]);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                int jumlahDetail = exampleList.size();
                int j;
                for(j=0;j<jumlahDetail;j++)
                {
                    String idSp = exampleList.get(j).getIdSparepart();
                    String namaSp = exampleList.get(j).getNamaSparepart();
                    Integer jlh = exampleList.get(j).getJumlah();
                    Integer satuan = exampleList.get(j).getSatuanHarga();
                    Integer stotal = exampleList.get(j).getSubtotal();
                    Call<ResponseBody> callDetail = RetrofitClient.getInstance().getApi().addSparepartDetailProcurement(idSp, idProcurements[0],jlh,satuan,stotal);
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
                            exampleList.clear();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Failed", t.toString());
                exampleList.clear();
            }
        });
    }
    public void addDetailProcurement()
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

        for(i=0;i<exampleList.size();i++) {
            String idsp = exampleList.get(i).getIdSparepart().toString();
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
            exampleList.add(new SparepartProcurementDetail(null, selectedIdSparepart, selectedNamaSparepart, jlh, harga, total, null)); //ini list tampilan
            postList.add(new SparepartProcurementDetailPost(selectedIdSparepart, jlh, harga, total, null));

            mRecyclerView = findViewById(R.id.cardview_procurement);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            mAdapter = new ProcurementCartAdapter(exampleList);

            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onSuccess() {
        if (getFragmentManager().getBackStackEntryCount() > 0 ) {
            //getFragmentManager ().popBackStack();
            getSupportFragmentManager().beginTransaction().replace(R.id.card_recycler_view,
                    new AdminProcurementFragment()).commit();
        }
        else {
            super.onBackPressed();
        }

    }

    public void onBackPressed(){
        if (getFragmentManager().getBackStackEntryCount() > 0 ) {
            getFragmentManager().popBackStack();
        }
        else {
            super.onBackPressed();
        }
    }
}
