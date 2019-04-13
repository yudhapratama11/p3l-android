package com.b_laundry.p3l.p3l.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.b_laundry.p3l.p3l.adapter.EmployeeAdapter;
import com.b_laundry.p3l.p3l.adapter.SparepartAdapter;
import com.b_laundry.p3l.p3l.api.Api;
import com.b_laundry.p3l.p3l.api.RetrofitClient;
import com.b_laundry.p3l.p3l.models.EmployeesReponse;
import com.b_laundry.p3l.p3l.models.SparepartResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminEmployeeFragment extends Fragment {
    View v;
    private RecyclerView recyclerView;
    private EmployeeAdapter employeeAdapter;
    private EmployeesReponse employeesList;
    private FloatingActionButton addButton;
    private ImageButton deleteButton;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        v = inflater.inflate(R.layout.admin_sparepart_fragment, container, false);
        recyclerView = v.findViewById(R.id.card_recycler_view);
        employeeAdapter = new EmployeeAdapter(this,employeesList);
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

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sparepart_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getEmployees();
        setHasOptionsMenu(true);
    }

    public void getEmployees(){

        Api service = RetrofitClient.getRetrofitInstance().create(Api.class);

        Call<EmployeesReponse> call = service.getEmployees();
        //Log.d("responsecode", String.valueOf(request.getSparepart()));

        call.enqueue(new Callback<EmployeesReponse>() {
            @Override
            public void onResponse(Call<EmployeesReponse> call, Response<EmployeesReponse> response) {
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<EmployeesReponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }

    private void generateDataList(EmployeesReponse employeesList) {
        recyclerView = recyclerView.findViewById(R.id.card_recycler_view);
        employeeAdapter = new EmployeeAdapter(this,employeesList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(employeeAdapter);
    }
}
