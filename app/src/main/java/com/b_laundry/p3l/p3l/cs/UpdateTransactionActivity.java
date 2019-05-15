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
import android.widget.Spinner;
import android.widget.Toast;

import com.b_laundry.p3l.p3l.R;
import com.b_laundry.p3l.p3l.adapter.ServiceTransactionAdapter;
import com.b_laundry.p3l.p3l.adapter.ServiceTransactionListAdapter;
import com.b_laundry.p3l.p3l.adapter.SparepartTransactionAdapter;
import com.b_laundry.p3l.p3l.adapter.SparepartTransactionListAdapter;
import com.b_laundry.p3l.p3l.admin.AdminCustomerActivity;
import com.b_laundry.p3l.p3l.api.Api;
import com.b_laundry.p3l.p3l.api.RetrofitClient;
import com.b_laundry.p3l.p3l.models.Customer;
import com.b_laundry.p3l.p3l.models.CustomerMotorcycle;
import com.b_laundry.p3l.p3l.models.CustomerMotorcycleResponse;
import com.b_laundry.p3l.p3l.models.CustomerResponse;
import com.b_laundry.p3l.p3l.models.Employees;
import com.b_laundry.p3l.p3l.models.EmployeesReponse;
import com.b_laundry.p3l.p3l.models.Service;
import com.b_laundry.p3l.p3l.models.ServiceResponse;
import com.b_laundry.p3l.p3l.models.ServiceTransaction;
import com.b_laundry.p3l.p3l.models.ServiceTransactionResponse;
import com.b_laundry.p3l.p3l.models.Sparepart;
import com.b_laundry.p3l.p3l.models.SparepartProcurementDetailList;
import com.b_laundry.p3l.p3l.models.SparepartResponse;
import com.b_laundry.p3l.p3l.models.SparepartTransaction;
import com.b_laundry.p3l.p3l.models.SparepartTransactionResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateTransactionActivity extends AppCompatActivity implements View.OnClickListener{
    private Button addSparepart,addService, addTransaction;
    private RecyclerView recyclerViewSparepart, recyclerViewService;
    private SparepartTransactionListAdapter sparepartTransactionAdapter;
    private ServiceTransactionListAdapter serviceTransactionAdapter;
    private Spinner spinnerSparepart,spinnerPelanggan, spinnerPegawai, spinnerMotor, spinnerMontir,
            spinnerService,spinnerTipeTransaksi;
    private EditText txtHargaSatuanSparepart,txtHargaSatuanService,jlhSparepart;
    private String selectedIdCustomer, selectedCustomer,customer,
            selectedIdService, selectedService, selectedHargaService,selectedMotor,
            selectedIdMotor,selectedPegawai,selectedIdPegawai,idtransaction,idtransactiontype,
            selectedSparepart, selectedIdSparepart, selectedHargaSparepart;
    private RecyclerView serviceRecyclerView, sparepartRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter serviceAdapter, sparepartAdapter;
    private Integer selectedTipeTransaksi = 0;
    private SparepartTransactionResponse sparepartTransactionList1;

    private List<String> listSpinnerCustomer = new ArrayList<String>();
    private List<String> listSpinnerIdCustomer = new ArrayList<String>();
    private List<String> listSpinnerPegawai = new ArrayList<String>();
    private List<String> listSpinnerIdPegawai = new ArrayList<String>();
    private List<String> listSpinnerCustomerMotorcycle = new ArrayList<String>();
    private List<String> listSpinnerIdCustomerMotorcycle = new ArrayList<String>();
    private List<String> listSpinnerIdMontir = new ArrayList<>();
    private List<String> listSpinnerMontir = new ArrayList<>();
    private List<String> listSpinnerService = new ArrayList<>();
    private List<String> listSpinnerServiceId = new ArrayList<>();
    private List<String> listSpinnerServiceHarga = new ArrayList<>();
    private List<String> listSpinnerTipeTransaksi = new ArrayList<>();
    private List<String> listSpinnerSparepart = new ArrayList<String>();
    private List<String> listSpinnerSparepartId = new ArrayList<String>();
    private List<String> listSpinnerSparepartHarga = new ArrayList<>();

    ArrayList<ServiceTransaction> serviceList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int i;
        setContentView(R.layout.activity_service_transaction);

        listSpinnerTipeTransaksi.add("Service");
        listSpinnerTipeTransaksi.add("Sparepart");
        listSpinnerTipeTransaksi.add("Sparepart dan Service");

        recyclerViewSparepart = findViewById(R.id.cardview_sparepart1);
        recyclerViewService = findViewById(R.id.cardview_service1);
        spinnerPelanggan = findViewById(R.id.spinnerCustomer);
        spinnerPegawai = findViewById(R.id.spinnerCS);
        spinnerMotor = findViewById(R.id.spinnerMotor);
        spinnerMontir = findViewById(R.id.spinnerMontir);
        txtHargaSatuanSparepart = findViewById(R.id.txtHargaSatuanSparepart);
        jlhSparepart = findViewById(R.id.txtJlhSparepart);
        spinnerService = findViewById(R.id.spinnerService);
        spinnerSparepart = findViewById(R.id.spinnerSparepart);
        spinnerTipeTransaksi = findViewById(R.id.spinnerTipeTransaksi);
        txtHargaSatuanService = findViewById(R.id.txtHargaSatuanService);
        addService = findViewById(R.id.btnAddService);
        //daftarPelanggan = findViewById(R.id.btnDaftarCustomer);
        //getActionBar().setTitle("Test");
        addTransaction = findViewById(R.id.btnAddTransaction);
        addSparepart = findViewById(R.id.btnAddSparepart);
        Intent intent = getIntent();
        customer = intent.getStringExtra("customer");
        idtransactiontype = intent.getStringExtra("id_transaction_type");
        idtransaction = intent.getStringExtra("id");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listSpinnerTipeTransaksi);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipeTransaksi.setAdapter(adapter);

        for(i=0;i<listSpinnerTipeTransaksi.size();i++)
        {
            String position = String.valueOf(i+1);
            String saless = idtransactiontype.toString().trim();
            if(position.equalsIgnoreCase(saless))
            {
                spinnerTipeTransaksi.setSelection(i);
            }
        }

        txtHargaSatuanService.setEnabled(false);
       // daftarPelanggan.setOnClickListener(this);
        addService.setOnClickListener(this);
        addTransaction.setOnClickListener(this);
        addSparepart.setOnClickListener(this);
        spinnerPelanggan.setEnabled(false);
        spinnerPelanggan.setClickable(false);
        spinnerPegawai.setEnabled(false);
        spinnerPegawai.setClickable(false);
        txtHargaSatuanSparepart.setEnabled(false);

        spinnerTipeTransaksi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTipeTransaksi = spinnerTipeTransaksi.getSelectedItemPosition() + 1;

                findViewById(R.id.serviceView).setVisibility(View.VISIBLE);
                findViewById(R.id.sparepartView).setVisibility(View.VISIBLE);
                findViewById(R.id.cardview_service1).setVisibility(View.VISIBLE);
                findViewById(R.id.txtService).setVisibility(View.VISIBLE);
                findViewById(R.id.cardview_sparepart1).setVisibility(View.VISIBLE);
                findViewById(R.id.txtSparepart).setVisibility(View.VISIBLE);

                if(selectedTipeTransaksi == 1) // service
                {
                    findViewById(R.id.sparepartView).setVisibility(View.GONE);
                    findViewById(R.id.cardview_sparepart1).setVisibility(View.GONE);
                    findViewById(R.id.txtSparepart).setVisibility(View.GONE);
                    getTransactionServiceDetail();
                }
                else if(selectedTipeTransaksi == 2) // sparepart
                {
                    findViewById(R.id.serviceView).setVisibility(View.GONE);
                    findViewById(R.id.cardview_service1).setVisibility(View.GONE);
                    findViewById(R.id.txtService).setVisibility(View.GONE);
                    getTransactionSparepartDetail();
                }
                else if(selectedTipeTransaksi == 3) // sparepart dan service
                {
                    getTransactionSparepartDetail();
                    getTransactionServiceDetail();
                }
                Log.d("pos", String.valueOf(selectedTipeTransaksi));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerPelanggan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                spinnerMotor.setAdapter(null);
                listSpinnerCustomerMotorcycle.clear();
                listSpinnerIdCustomerMotorcycle.clear();
                selectedCustomer = listSpinnerCustomer.get(position);
                selectedIdCustomer = listSpinnerIdCustomer.get(position);
                Toast.makeText(getApplicationContext(), "Choose " + selectedIdCustomer, Toast.LENGTH_SHORT).show();
                //String selected = parentView.getItemAtPosition(position).toString();
                getCustomerMotorcycleById(selectedIdCustomer);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        spinnerPegawai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedPegawai = listSpinnerPegawai.get(position);
                selectedIdPegawai = listSpinnerIdPegawai.get(position);
                Toast.makeText(getApplicationContext(), "Choose " + selectedIdPegawai, Toast.LENGTH_SHORT).show();
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
                //Toast.makeText(getApplicationContext(), "Choose " + selectedIdSparepart, Toast.LENGTH_SHORT).show();
                //String selected = parentView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        spinnerMotor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedMotor = listSpinnerCustomerMotorcycle.get(position);
                selectedIdMotor = listSpinnerIdCustomerMotorcycle.get(position);
                Toast.makeText(getApplicationContext(), "Choose " + selectedIdCustomer, Toast.LENGTH_SHORT).show();
                //String selected = parentView.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });


        spinnerService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedService = listSpinnerService.get(position);
                selectedIdService = listSpinnerServiceId.get(position);
                selectedHargaService = listSpinnerServiceHarga.get(position);
                txtHargaSatuanService.setText(selectedHargaService);
                Toast.makeText(getApplicationContext(), "Choose " + selectedIdService, Toast.LENGTH_SHORT).show();
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
        listSpinnerServiceHarga.clear();
        listSpinnerService.clear();
        listSpinnerServiceId.clear();
        listSpinnerMontir.clear();
        listSpinnerIdMontir.clear();
        listSpinnerCustomer.clear();
        listSpinnerIdCustomer.clear();
        listSpinnerPegawai.clear();
        listSpinnerIdPegawai.clear();
        listSpinnerCustomerMotorcycle.clear();
        listSpinnerIdCustomerMotorcycle.clear();
        spinnerService.setAdapter(null);
        spinnerMontir.setAdapter(null);
        spinnerPelanggan.setAdapter(null);
        spinnerPegawai.setAdapter(null);
        spinnerMotor.setAdapter(null);
        super.onResume();
        getPegawai();
        getPelanggan();
        getMontirOnDuty();
        getService();
        getSparepart();


    }
    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.btnDaftarCustomer:
                Intent intentDaftarCustomer = new Intent(this, AdminCustomerActivity.class);
                intentDaftarCustomer.putExtra("tujuan","createTransaction");
//                intent.putExtra("nama",historyList.getData().get(i).getNama().toString());
                startActivity(intentDaftarCustomer);
                break;
            case R.id.btnAddService:
                addDetailService();
                break;
            case R.id.btnAddTransaction:
                updateTransaction();
                break;
            case R.id.btnAddSparepart:
                addDetailSparepart();
                break;
        }
    }

//    public void addDetailSparepart()
//    {
//        final ProgressDialog dialog = new ProgressDialog(this);
//        int i,boleh=1,jlh;
//
//        if(jlhSparepart.getText().toString().trim().isEmpty()) {
//            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
//            alertDialog.setTitle("Warning");
//            alertDialog.setMessage("Jumlah sparepart tidak boleh kosong!");
//            alertDialog.setButton(Dialog.BUTTON_POSITIVE,"OK",new DialogInterface.OnClickListener(){
//
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            });
//            alertDialog.show();
//            return;
//        }
//        else
//        {
//            jlh = Integer.parseInt(jlhSparepart.getText().toString());
//        }
//        Integer harga = Integer.parseInt(selectedHargaSparepart);
//        Integer total = harga * jlh;
//
//        for(i=0;i<sparepartTransactionList1.getData().size();i++) {
//            String idsp = sparepartTransactionList1.getData().get(i).getIdSparepart().toString();
//            Log.d("idsp",idsp);
//            if(idsp.equalsIgnoreCase(selectedIdSparepart))
//            {
//                boleh = 0;
//            }
//        }
//
//        if(boleh == 1) {
//            dialog.setMessage("Please wait.....");
//            dialog.show();
//            dialog.setCancelable(false);
//            sparepartTransactionList1.getData().add(new SparepartTransaction(null, null, selectedIdSparepart, selectedSparepart,jlh, harga, total)); //ini list tampilan
//            //postList.add(new SparepartTransactionPost(null, null, selectedSparepart, jlh, harga, total));
//
//            recyclerViewSparepart = recyclerViewSparepart.findViewById(R.id.cardview_sparepart1);
//            recyclerViewSparepart.setHasFixedSize(true);
//            mLayoutManager = new LinearLayoutManager(this);
//            sparepartTransactionAdapter = new SparepartTransactionListAdapter(sparepartTransactionList1);
//
//            recyclerViewSparepart.setLayoutManager(mLayoutManager);
//            recyclerViewSparepart.setAdapter(sparepartTransactionAdapter);
//            sparepartTransactionAdapter.notifyDataSetChanged();
//            Runnable progressRunnable = new Runnable() {
//
//                @Override
//                public void run() {
//                    dialog.cancel();
//                }
//            };
//
//            Handler pdCanceller = new Handler();
//            pdCanceller.postDelayed(progressRunnable, 2000);
//        }
//        else
//        {
//            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
//            alertDialog.setTitle("Warning");
//            alertDialog.setMessage("Sparepart sudah ditambahkan!");
//            alertDialog.setButton(Dialog.BUTTON_POSITIVE,"OK",new DialogInterface.OnClickListener(){
//
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            });
//            alertDialog.show();
//        }
//    }
    public void updateTransaction()
    {
        final String[] idTransaction = new String[1];
        //int idCustomer = Integer.parseInt(selectedIdCustomer);
        final int[] successService = new int[1];
        final int[] successSparepart = new int[1];


        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().updateTransaction(idtransaction,selectedTipeTransaksi);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //String responsecode = String.valueOf(response.code());
                try {
                    JSONObject jObjError = new JSONObject(response.body().string());
                    idTransaction[0] = jObjError.getString("message");
                    Log.d("responseInsert", idTransaction[0]);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int j;
                int jumlahDetailSparepart = sparepartTransactionList1.getData().size();
                if(jumlahDetailSparepart > 0) {
                    for (j = 0; j < jumlahDetailSparepart; j++) {
                        String idSp = sparepartTransactionList1.getData().get(j).getIdSparepart();
                        String namaSp = sparepartTransactionList1.getData().get(j).getNamaSparepart();
                        Integer jlh = sparepartTransactionList1.getData().get(j).getJumlah();
                        Integer satuan = sparepartTransactionList1.getData().get(j).getHargaSatuan();
                        Integer stotal = sparepartTransactionList1.getData().get(j).getSubtotal();
                        Call<ResponseBody> callDetail = RetrofitClient.getInstance().getApi().addSparepartTransaction(idTransaction[0], idSp, jlh, satuan, stotal);
                        callDetail.enqueue(new Callback<ResponseBody>() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onResponse(Call<ResponseBody> callDetail, Response<ResponseBody> responseDetail) {
                                Log.d("responseInsert", String.valueOf(responseDetail.body().toString()));
                                Log.d("responseCode", responseDetail.body().toString());
                                successSparepart[0] = 1;
                                //sparepartList.clear();
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                //
                            }
                        });
                    }
                }

                int jumlahDetailService = serviceList.size();

                if(jumlahDetailService > 0) {
                    for (j = 0; j < jumlahDetailService; j++) {
                        Integer idSv = serviceList.get(j).getIdService();
                        String namaSp = serviceList.get(j).getService();
                        Integer harga = serviceList.get(j).getSubtotal();
                        Integer motor = serviceList.get(j).getIdCustomerMotorcycle();
                        Integer montir = serviceList.get(j).getIdMontirOnduty();
                        Call<ResponseBody> callService = RetrofitClient.getInstance().getApi().addServiceTransaction(idTransaction[0], idSv, motor, montir, 0, harga);
                        callService.enqueue(new Callback<ResponseBody>() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onResponse(Call<ResponseBody> callService, Response<ResponseBody> responseService) {
                                Log.d("responseInsert", String.valueOf(responseService.body().toString()));
                                Log.d("responseCode", responseService.body().toString());
                                successService[0] = 1;
                                //serviceList.clear();
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                //
                            }
                        });
                    }

                }
                Toast.makeText(getApplicationContext(), "Update Transaksi Sukses", Toast.LENGTH_SHORT).show();
                onSuccess();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Failed", t.toString());
            }
        });
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

        for(i=0;i<sparepartTransactionList1.getData().size();i++) {
            String idsp = sparepartTransactionList1.getData().get(i).getIdSparepart().toString();
            Log.d("idsp",idsp);
            if(idsp.equalsIgnoreCase(selectedIdSparepart))
            {
                boleh = 0;
            }
        }

        if(boleh == 1) {
            String idSparepart = selectedIdSparepart;
            String namaSparepart = selectedSparepart;
            dialog.setMessage("Please wait.....");
            dialog.show();
            dialog.setCancelable(false);
            sparepartTransactionList1.getData().add(new SparepartTransaction(null, null, idSparepart, namaSparepart,jlh, harga, total)); //ini list tampilan

            recyclerViewSparepart = findViewById(R.id.cardview_sparepart1);
            recyclerViewSparepart.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            sparepartAdapter = new SparepartTransactionListAdapter(sparepartTransactionList1);

            recyclerViewSparepart.setLayoutManager(mLayoutManager);
            recyclerViewSparepart.setAdapter(sparepartAdapter);
            sparepartAdapter.notifyDataSetChanged();
            Runnable progressRunnable = new Runnable() {

                @Override
                public void run() {
                    dialog.cancel();
                }
            };

            Handler pdCanceller = new Handler();
            pdCanceller.postDelayed(progressRunnable, 2000);
//
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

    public void addDetailService()
    {
        final ProgressDialog dialog = new ProgressDialog(this);
        int i,boleh=1;
        String motorName;

        motorName = spinnerMotor.getSelectedItem().toString();

        if(motorName == null) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Warning");
            alertDialog.setMessage("Motor customer tidak ada, silahkan daftarkan terlebih dahulu!");
            alertDialog.setButton(Dialog.BUTTON_POSITIVE,"OK",new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.show();
            return;
        }
        Integer harga = Integer.parseInt(selectedHargaService);

        for(i=0;i<serviceList.size();i++) {
            String idsp = serviceList.get(i).getIdService().toString();
            Log.d("idsp",idsp);
            if(idsp.equalsIgnoreCase(selectedIdService))
            {
                boleh = 0;
            }
        }

        if(boleh == 1) {
            Integer idService = Integer.parseInt(selectedIdService);
            Integer idMotor = Integer.parseInt(selectedIdMotor);
            Integer idPegawai = Integer.parseInt(selectedIdPegawai);
            dialog.setMessage("Please wait.....");
            dialog.show();
            dialog.setCancelable(false);
            serviceList.add(new ServiceTransaction(null, null, idService, selectedService, idMotor, selectedMotor, idPegawai, selectedPegawai, 0, harga)); //ini list tampilan

            serviceRecyclerView = findViewById(R.id.cardview_service1);
            serviceRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            serviceAdapter = new ServiceTransactionAdapter(serviceList);

            serviceRecyclerView.setLayoutManager(mLayoutManager);
            serviceRecyclerView.setAdapter(serviceAdapter);
            serviceAdapter.notifyDataSetChanged();
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
            alertDialog.setMessage("Service sudah ditambahkan!");
            alertDialog.setButton(Dialog.BUTTON_POSITIVE,"OK",new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.show();
        }



    }


    public void getService(){

        Api service = RetrofitClient.getRetrofitInstance().create(Api.class);

        Call<ServiceResponse> call = service.getServices();


        call.enqueue(new Callback<ServiceResponse>() {
            @Override
            public void onResponse(Call<ServiceResponse> call, Response<ServiceResponse> response) {
                Log.d("responsecode", String.valueOf(response.code()));
                if (response.isSuccessful()) {
                    List<Service> spinnerArrayList = response.body().getData();
                    for (int i = 0; i < spinnerArrayList.size(); i++) {
                        String namasp = spinnerArrayList.get(i).getNama();
                        String idtipe = spinnerArrayList.get(i).getId().toString();
                        String harga = spinnerArrayList.get(i).getHarga().toString();
                        listSpinnerService.add(namasp);
                        listSpinnerServiceId.add(idtipe);
                        listSpinnerServiceHarga.add(harga);
                    }
                    ArrayAdapter<String> adapterService = new ArrayAdapter<>(getApplicationContext(),
                            android.R.layout.simple_spinner_dropdown_item
                            , listSpinnerService);
                    spinnerService.setAdapter(adapterService);
                    int i;
                }

            }

            @Override
            public void onFailure(Call<ServiceResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }

    public void getMontirOnDuty()
    {

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
                        if(role.equalsIgnoreCase("3")) {
                            String tipesp = spinnerArrayList.get(i).getNama();
                            String idtipe = spinnerArrayList.get(i).getId().toString();
                            listSpinnerMontir.add(tipesp);
                            listSpinnerIdMontir.add(idtipe);
                        }
                        //listSpinner.add()
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                            android.R.layout.simple_spinner_dropdown_item
                            , listSpinnerMontir);
                    spinnerMontir.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<EmployeesReponse> call, Throwable t) {
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

    public void getTransactionSparepartDetail(){

        final Api service = RetrofitClient.getRetrofitInstance().create(Api.class);

        Call<SparepartTransactionResponse> call = service.getSparepartTransactionDetailFromId(idtransaction);

        call.enqueue(new Callback<SparepartTransactionResponse>() {
            @Override
            public void onResponse(Call<SparepartTransactionResponse> call, final Response<SparepartTransactionResponse> response) {
                Log.d("responsdecodenya", String.valueOf(response.code()));
                generateDataListSparepart(response.body());
            }

            @Override
            public void onFailure(Call<SparepartTransactionResponse> call, Throwable t) {
                Log.d("ErrorLess",t.getMessage());
            }
        });
    }

    public void getTransactionServiceDetail(){

        final Api service = RetrofitClient.getRetrofitInstance().create(Api.class);

        Call<ServiceTransactionResponse> call = service.getServiceTransactionDetailFromId(idtransaction);

        call.enqueue(new Callback<ServiceTransactionResponse>() {
            @Override
            public void onResponse(Call<ServiceTransactionResponse> call, final Response<ServiceTransactionResponse> response) {
                Log.d("responsdecodenya", String.valueOf(response.code()));
                generateDataListService(response.body());
            }

            @Override
            public void onFailure(Call<ServiceTransactionResponse> call, Throwable t) {
                Log.d("ErrorLess",t.getMessage());
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
                    for(i=0;i<listSpinnerCustomer.size();i++)
                    {
                        String lsp = listSpinnerIdCustomer.get(i).toString().trim();
                        String customerr = customer.toString().trim();
                        if(lsp.equalsIgnoreCase(customerr))
                        {
                            spinnerPelanggan.setSelection(i);
                        }
                    }
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

    public void getCustomerMotorcycleById(String idcustomer){

        Api service = RetrofitClient.getRetrofitInstance().create(Api.class);

        Call<CustomerMotorcycleResponse> call = service.getCustomerMotorcycleById(idcustomer);


        call.enqueue(new Callback<CustomerMotorcycleResponse>() {
            @Override
            public void onResponse(Call<CustomerMotorcycleResponse> call, Response<CustomerMotorcycleResponse> response) {
                int i;
                String role;
                Log.d("responsecode", String.valueOf(response.code()));
                if (response.isSuccessful()) {
                    List<CustomerMotorcycle> spinnerArrayList = response.body().getData();
                    for (i = 0; i < spinnerArrayList.size(); i++) {
                        String tipesp = spinnerArrayList.get(i).getPlat();
                        String idtipe = spinnerArrayList.get(i).getId().toString();
                        listSpinnerCustomerMotorcycle.add(tipesp);
                        listSpinnerIdCustomerMotorcycle.add(idtipe);
                        //listSpinner.add()
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                            android.R.layout.simple_spinner_dropdown_item
                            , listSpinnerCustomerMotorcycle);
                    spinnerMotor.setAdapter(adapter);
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
            public void onFailure(Call<CustomerMotorcycleResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }


    public void generateDataListSparepart(SparepartTransactionResponse sparepartTransactionList) {
        recyclerViewSparepart = recyclerViewSparepart.findViewById(R.id.cardview_sparepart1);
        sparepartTransactionList1 = sparepartTransactionList;
        sparepartTransactionAdapter = new SparepartTransactionListAdapter(sparepartTransactionList1);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewSparepart.setLayoutManager(layoutManager);
        recyclerViewSparepart.setAdapter(sparepartTransactionAdapter);

    }

    public void generateDataListService(ServiceTransactionResponse serviceTransactionList) {
        recyclerViewService = recyclerViewService.findViewById(R.id.cardview_service1);
        serviceTransactionAdapter = new ServiceTransactionListAdapter(serviceTransactionList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewService.setLayoutManager(layoutManager);
        recyclerViewService.setAdapter(serviceTransactionAdapter);
        //exampleList1 = procurementList;
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
