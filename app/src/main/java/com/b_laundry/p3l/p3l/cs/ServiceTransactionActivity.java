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
import com.b_laundry.p3l.p3l.adapter.ServiceTransactionAdapter;
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
import com.b_laundry.p3l.p3l.models.SparepartProcurementDetailList;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceTransactionActivity extends AppCompatActivity implements View.OnClickListener{
    private Button daftarPelanggan, addSparepart,addService, addTransaction;
    private RecyclerView recyclerView;
    private Spinner spinnerSparepart,spinnerPelanggan, spinnerPegawai, spinnerMotor, spinnerMontir,
            spinnerService;
    private EditText txtHargaSatuanSparepart,txtHargaSatuanService;
    private String selectedIdCustomer, selectedCustomer,
            selectedIdService, selectedService, selectedHargaService,selectedMotor,selectedIdMotor,selectedPegawai,selectedIdPegawai;
    private RecyclerView serviceRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter serviceAdapter;

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

    ArrayList<ServiceTransaction> serviceList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int i;
        setContentView(R.layout.activity_service_transaction);

        recyclerView = findViewById(R.id.cardview_procurement);
        spinnerPelanggan = findViewById(R.id.spinnerCustomer);
        spinnerPegawai = findViewById(R.id.spinnerCS);
        spinnerMotor = findViewById(R.id.spinnerMotor);
        spinnerMontir = findViewById(R.id.spinnerMontir);
        spinnerService = findViewById(R.id.spinnerService);
        txtHargaSatuanService = findViewById(R.id.txtHargaSatuanService);
        addService = findViewById(R.id.btnAddService);
        daftarPelanggan = findViewById(R.id.btnDaftarCustomer);
        //getActionBar().setTitle("Test");
        addTransaction = findViewById(R.id.btnAddTransaction);

        txtHargaSatuanService.setEnabled(false);
        daftarPelanggan.setOnClickListener(this);
        addService.setOnClickListener(this);
        addTransaction.setOnClickListener(this);

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
                postTransaction();
                break;
        }
    }


    public void postTransaction()
    {
        final String[] idTransaction = new String[1];
        int idCustomer = Integer.parseInt(selectedIdCustomer);
        final int[] successService = new int[1];

        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().addTransaction(1,1,0,idCustomer, selectedIdPegawai);
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


                int jumlahDetailService = serviceList.size();
                int j;
                for(j=0;j<jumlahDetailService;j++)
                {
                    Integer idSv = serviceList.get(j).getIdService();
                    String namaSp = serviceList.get(j).getService();
                    Integer harga = serviceList.get(j).getSubtotal();
                    Integer motor = serviceList.get(j).getIdCustomerMotorcycle();
                    Integer montir = serviceList.get(j).getIdMontirOnduty();
                    Call<ResponseBody> callService = RetrofitClient.getInstance().getApi().addServiceTransaction(idTransaction[0], idSv, motor, montir,0, harga);
                    callService.enqueue(new Callback<ResponseBody>() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onResponse(Call<ResponseBody> callService, Response<ResponseBody> responseService) {
                            Log.d("responseInsert", String.valueOf(responseService.body().toString()));
                            Log.d("responseCode", responseService.body().toString());
                            successService[0] = 1;
                            serviceList.clear();
                            Toast.makeText(getApplicationContext(), "Transaksi Service Sukses", Toast.LENGTH_SHORT).show();
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

            serviceRecyclerView = findViewById(R.id.cardview_service);
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
