package com.b_laundry.p3l.p3l.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.b_laundry.p3l.p3l.R;
import com.b_laundry.p3l.p3l.adapter.CustomerAdapter;
import com.b_laundry.p3l.p3l.adapter.SupplierAdapter;
import com.b_laundry.p3l.p3l.api.Api;
import com.b_laundry.p3l.p3l.api.RetrofitClient;
import com.b_laundry.p3l.p3l.models.Customer;
import com.b_laundry.p3l.p3l.models.CustomerResponse;
import com.b_laundry.p3l.p3l.models.Supplier;
import com.b_laundry.p3l.p3l.models.SupplierResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminCustomerFragment extends Fragment {
    View v;
    private RecyclerView recyclerView;
    private CustomerAdapter customerAdapter;
    private CustomerResponse customerList;
    public List<Customer> CustomerList;
    private FloatingActionButton addButton;
    private ImageButton deleteButton;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        v = inflater.inflate(R.layout.admin_sparepart_fragment, container, false);
        recyclerView = v.findViewById(R.id.card_recycler_view);
        customerAdapter = new CustomerAdapter(this,customerList);
        addButton = v.findViewById(R.id.btnAddSparepart);
        //deleteButton = v.findViewById(R.id.btnDeleteSparepart);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCreate = new Intent(v.getContext(), AdminCustomerActivity.class);
                intentCreate.putExtra("tujuan","create");
                v.getContext().startActivity(intentCreate);
            }
        });
        return v;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.supplier_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase();
                List<Customer> newList = new ArrayList<>();
                //Sparepart newList = new Sparepart();

                for (Customer customer : CustomerList)
                {
                    String name = customer.getNama().toLowerCase();
                    Log.d("supplierlower",customer.getNama().toLowerCase().toString());
                    if(name.contains(newText))
                        newList.add(customer);
                }
                customerAdapter.setFilter(newList);

                return true;
            }
        });
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        getCustomer();
        super.onResume();
    }

    public void getCustomer(){

        Api service = RetrofitClient.getRetrofitInstance().create(Api.class);

        Call<CustomerResponse> call = service.getCustomer();

        call.enqueue(new Callback<CustomerResponse>() {
            @Override
            public void onResponse(Call<CustomerResponse> call, Response<CustomerResponse> response) {
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<CustomerResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }
    private void generateDataList(CustomerResponse customerList) {
        recyclerView = recyclerView.findViewById(R.id.card_recycler_view);
        customerAdapter = new CustomerAdapter(this,customerList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(customerAdapter);
        CustomerList = customerList.getData();
    }
}
