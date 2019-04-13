package com.b_laundry.p3l.p3l.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.b_laundry.p3l.p3l.R;
import com.b_laundry.p3l.p3l.adapter.BranchAdapter;
import com.b_laundry.p3l.p3l.api.Api;
import com.b_laundry.p3l.p3l.api.RetrofitClient;
import com.b_laundry.p3l.p3l.models.BranchResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminBranchFragment extends Fragment {
    View v;
    private RecyclerView recyclerView;
    private BranchAdapter branchAdapter;
    private BranchResponse branchList;
    private FloatingActionButton addButton;
    private ImageButton deleteButton;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        v = inflater.inflate(R.layout.admin_sparepart_fragment, container, false);
        recyclerView = v.findViewById(R.id.card_recycler_view);
        branchAdapter = new BranchAdapter(this,branchList);
        addButton = v.findViewById(R.id.btnAddSparepart);
        deleteButton = v.findViewById(R.id.btnDeleteSparepart);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCreate = new Intent(v.getContext(), AdminEmployeeActivity.class);
                v.getContext().startActivity(intentCreate);
            }
        });
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sparepart_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getBranches();
    }

    public void getBranches(){

        Api service = RetrofitClient.getRetrofitInstance().create(Api.class);

        Call<BranchResponse> call = service.getBranches();
        //Log.d("responsecode", String.valueOf(request.getSparepart()));

        call.enqueue(new Callback<BranchResponse>() {
            @Override
            public void onResponse(Call<BranchResponse> call, Response<BranchResponse> response) {
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<BranchResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }

    private void generateDataList(BranchResponse branchList) {
        recyclerView = recyclerView.findViewById(R.id.card_recycler_view);
        branchAdapter = new BranchAdapter(this,branchList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(branchAdapter);
    }
}
