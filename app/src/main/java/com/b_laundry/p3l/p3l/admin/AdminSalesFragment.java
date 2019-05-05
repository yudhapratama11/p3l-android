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
import com.b_laundry.p3l.p3l.adapter.SalesAdapter;
import com.b_laundry.p3l.p3l.api.Api;
import com.b_laundry.p3l.p3l.api.RetrofitClient;
import com.b_laundry.p3l.p3l.models.Sales;
import com.b_laundry.p3l.p3l.models.SalesResponse;
import com.b_laundry.p3l.p3l.models.Supplier;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminSalesFragment extends Fragment {
    View v;
    private RecyclerView recyclerView;
    private SalesAdapter salesAdapter;
    private SalesResponse salesList;
    private FloatingActionButton addButton;
    private ImageButton deleteButton;
    private List<Sales> SalesList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        v = inflater.inflate(R.layout.admin_sparepart_fragment, container, false);
        recyclerView = v.findViewById(R.id.card_recycler_view);
        salesAdapter = new SalesAdapter(this,salesList);
        addButton = v.findViewById(R.id.btnAddSparepart);
        deleteButton = v.findViewById(R.id.btnDeleteSales);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCreate = new Intent(v.getContext(), AdminSalesActivity.class);
                v.getContext().startActivity(intentCreate);
            }
        });
        return v;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.sales_menu, menu);
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
                List<Sales> newList = new ArrayList<>();

                for (Sales sales : SalesList)
                {
                    String name = sales.getNama().toLowerCase();
                    Log.d("supplierlower",sales.getNama().toLowerCase().toString());
                    if(name.contains(newText))
                        newList.add(sales);
                }
                salesAdapter.setFilter(newList);

                return true;
            }
        });
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getSales();
    }

    public void getSales(){

        Api service = RetrofitClient.getRetrofitInstance().create(Api.class);

        Call<SalesResponse> call = service.getSales();
        //Log.d("responsecode", String.valueOf(request.getSparepart()));

        call.enqueue(new Callback<SalesResponse>() {
            @Override
            public void onResponse(Call<SalesResponse> call, Response<SalesResponse> response) {
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<SalesResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }
    private void generateDataList(SalesResponse salesList) {
        recyclerView = recyclerView.findViewById(R.id.card_recycler_view);
        salesAdapter = new SalesAdapter(this,salesList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(salesAdapter);
        SalesList = salesList.getData();
    }
}
