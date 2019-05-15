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
import com.b_laundry.p3l.p3l.adapter.ProcurementAdapter;
import com.b_laundry.p3l.p3l.adapter.SparepartAdapter;
import com.b_laundry.p3l.p3l.api.Api;
import com.b_laundry.p3l.p3l.api.RetrofitClient;
import com.b_laundry.p3l.p3l.models.Sparepart;
import com.b_laundry.p3l.p3l.models.SparepartProcurement;
import com.b_laundry.p3l.p3l.models.SparepartProcurementList;
import com.b_laundry.p3l.p3l.models.SparepartResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminProcurementFragment extends Fragment {
    View v;
    private RecyclerView recyclerView;
    private ProcurementAdapter procurementAdapter;
    private SparepartProcurementList sparepartList1;
    public List<SparepartProcurement> SparepartList;
    private FloatingActionButton addButton;
    List<SparepartProcurement> arrayList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        v = inflater.inflate(R.layout.admin_sparepart_fragment, container, false);
        recyclerView = v.findViewById(R.id.card_recycler_view);
        procurementAdapter = new ProcurementAdapter(this,sparepartList1);
        addButton = v.findViewById(R.id.btnAddSparepart);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCreate = new Intent(v.getContext(), AdminCartProcurementActivity.class);
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
                List<SparepartProcurement> newList = new ArrayList<>();

                for (SparepartProcurement sparepart : SparepartList)
                {
                    String name = sparepart.getSales().toLowerCase();
                    Log.d("sparepartlower",sparepart.getSales().toLowerCase().toString());
                    if(name.contains(newText))
                        newList.add(sparepart);
                }
                procurementAdapter.setFilter(newList);

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

        Call<SparepartProcurementList> call = service.getProcurement();
        //Log.d("responsecode", String.valueOf(request.getSparepart()));

        call.enqueue(new Callback<SparepartProcurementList>() {
            @Override
            public void onResponse(Call<SparepartProcurementList> call, final Response<SparepartProcurementList> response) {
                Log.d("responsdecodenya", String.valueOf(response.code()));
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<SparepartProcurementList> call, Throwable t) {
                Log.d("ErrorLess",t.getMessage());
            }
        });
    }

    public void generateDataList(SparepartProcurementList sparepartList) {
        recyclerView = recyclerView.findViewById(R.id.card_recycler_view);
        procurementAdapter = new ProcurementAdapter(this,sparepartList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(procurementAdapter);
        SparepartList = sparepartList.getData();
    }


}


