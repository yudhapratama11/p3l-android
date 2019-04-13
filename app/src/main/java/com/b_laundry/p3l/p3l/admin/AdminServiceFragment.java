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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.b_laundry.p3l.p3l.R;
import com.b_laundry.p3l.p3l.adapter.ServiceAdapter;
import com.b_laundry.p3l.p3l.api.Api;
import com.b_laundry.p3l.p3l.api.RetrofitClient;
import com.b_laundry.p3l.p3l.models.ServiceResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminServiceFragment extends Fragment {
    View v;
    private RecyclerView recyclerView;
    private ServiceAdapter serviceAdapter;
    private ServiceResponse serviceList;
    private FloatingActionButton addButton;
    private ImageButton deleteButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        v = inflater.inflate(R.layout.admin_sparepart_fragment, container, false);
        recyclerView = v.findViewById(R.id.card_recycler_view);
        serviceAdapter = new ServiceAdapter(this,serviceList);
        addButton = v.findViewById(R.id.btnAddSparepart);
        deleteButton = v.findViewById(R.id.btnDeleteSparepart);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCreate = new Intent(v.getContext(), AdminServiceActivity.class);
                v.getContext().startActivity(intentCreate);
            }
        });
        return v;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getService();
    }

    public void getService(){

        Api service = RetrofitClient.getRetrofitInstance().create(Api.class);

        Call<ServiceResponse> call = service.getServices();
        //Log.d("responsecode", String.valueOf(request.getSparepart()));

        call.enqueue(new Callback<ServiceResponse>() {
            @Override
            public void onResponse(Call<ServiceResponse> call, Response<ServiceResponse> response) {
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<ServiceResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
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
    private void generateDataList(ServiceResponse serviceList) {
        recyclerView = recyclerView.findViewById(R.id.card_recycler_view);
        serviceAdapter = new ServiceAdapter(this,serviceList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(serviceAdapter);
    }
}
