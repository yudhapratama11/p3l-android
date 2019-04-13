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
import com.b_laundry.p3l.p3l.adapter.SparepartAdapter;
import com.b_laundry.p3l.p3l.adapter.SupplierAdapter;
import com.b_laundry.p3l.p3l.api.Api;
import com.b_laundry.p3l.p3l.api.RetrofitClient;
import com.b_laundry.p3l.p3l.models.Sparepart;
import com.b_laundry.p3l.p3l.models.SparepartResponse;
import com.b_laundry.p3l.p3l.models.Supplier;
import com.b_laundry.p3l.p3l.models.SupplierResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminSupplierFragment extends Fragment {
    View v;
    private RecyclerView recyclerView;
    private SupplierAdapter supplierAdapter;
    private SupplierResponse supplierList;
    public List<Supplier> SupplierList;
    private FloatingActionButton addButton;
    private ImageButton deleteButton;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        v = inflater.inflate(R.layout.admin_sparepart_fragment, container, false);
        recyclerView = v.findViewById(R.id.card_recycler_view);
        supplierAdapter = new SupplierAdapter(this,supplierList);
        addButton = v.findViewById(R.id.btnAddSparepart);
        deleteButton = v.findViewById(R.id.btnDeleteSparepart);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCreate = new Intent(v.getContext(), AdminSupplierActivity.class);
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
                List<Supplier> newList = new ArrayList<>();
                //Sparepart newList = new Sparepart();

                for (Supplier supplier : SupplierList)
                {
                    String name = supplier.getNama().toLowerCase();
                    Log.d("supplierlower",supplier.getNama().toLowerCase().toString());
                    if(name.contains(newText))
                        newList.add(supplier);
                }
                supplierAdapter.setFilter(newList);

                return true;
            }
        });
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        getSupplier();
    }

    public void getSupplier(){

        Api service = RetrofitClient.getRetrofitInstance().create(Api.class);

        Call<SupplierResponse> call = service.getSuppliers();
        //Log.d("responsecode", String.valueOf(request.getSparepart()));

        call.enqueue(new Callback<SupplierResponse>() {
            @Override
            public void onResponse(Call<SupplierResponse> call, Response<SupplierResponse> response) {
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<SupplierResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }
    private void generateDataList(SupplierResponse supplierList) {
        recyclerView = recyclerView.findViewById(R.id.card_recycler_view);
        supplierAdapter = new SupplierAdapter(this,supplierList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(supplierAdapter);
        SupplierList = supplierList.getData();
    }
}
