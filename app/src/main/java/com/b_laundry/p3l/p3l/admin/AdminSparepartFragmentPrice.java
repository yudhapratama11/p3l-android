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
import com.b_laundry.p3l.p3l.adapter.SparepartPriceAdapter;
import com.b_laundry.p3l.p3l.api.Api;
import com.b_laundry.p3l.p3l.api.RetrofitClient;
import com.b_laundry.p3l.p3l.models.Sparepart;
import com.b_laundry.p3l.p3l.models.SparepartResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminSparepartFragmentPrice extends Fragment {
    View v;
    private RecyclerView recyclerView;
    private SparepartPriceAdapter sparepartAdapter;
    private SparepartResponse sparepartList1;
    public List<Sparepart> SparepartList;
    private FloatingActionButton addButton;
    private ImageButton deleteButton;
    List<Sparepart> arrayList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        v = inflater.inflate(R.layout.admin_sparepart_price_fragment, container, false);
        recyclerView = v.findViewById(R.id.card_recycler_view);
        sparepartAdapter = new SparepartPriceAdapter(this,sparepartList1);
        addButton = v.findViewById(R.id.btnAddSparepart);
        deleteButton = v.findViewById(R.id.btnDeleteSparepart);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCreate = new Intent(v.getContext(), SparepartActivity.class);
                v.getContext().startActivity(intentCreate);
            }
        });
        return v;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.sparepart_menu, menu);
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
                List<Sparepart> newList = new ArrayList<>();
                //Sparepart newList = new Sparepart();

                for (Sparepart sparepart : SparepartList)
                {
                    String name = sparepart.getNama().toLowerCase();
                    Log.d("sparepartlower",sparepart.getNama().toLowerCase().toString());
                    if(name.contains(newText))
                        newList.add(sparepart);
                }
                sparepartAdapter.setFilter(newList);

                return true;
            }
        });
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getSparepart();
    }

    public void getSparepart(){

        final Api service = RetrofitClient.getRetrofitInstance().create(Api.class);

        Call<SparepartResponse> call = service.getSparepartHighPrice();
        //Log.d("responsecode", String.valueOf(request.getSparepart()));

        call.enqueue(new Callback<SparepartResponse>() {
            @Override
            public void onResponse(Call<SparepartResponse> call, final Response<SparepartResponse> response) {
                Call<SparepartResponse> callLess = service.getSparepartLess();
                callLess.enqueue(new Callback<SparepartResponse>() {
                    @Override
                    public void onResponse(Call<SparepartResponse> callLess, Response<SparepartResponse> responseLess) {
                        Log.d("responsecodesparepart", String.valueOf(response.code()));
                        generateDataList(response.body());
                    }

                    @Override
                    public void onFailure(Call<SparepartResponse> callLess, Throwable t) {
                        Log.d("ErrorA",t.getMessage());
                    }
                });

            }

            @Override
            public void onFailure(Call<SparepartResponse> call, Throwable t) {
                Log.d("ErrorLess",t.getMessage());
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

    public void generateDataList(SparepartResponse sparepartList) {
        recyclerView = recyclerView.findViewById(R.id.card_recycler_view);
        sparepartAdapter = new SparepartPriceAdapter(this,sparepartList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(sparepartAdapter);
        SparepartList = sparepartList.getData();
    }


}


