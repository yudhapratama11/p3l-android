package com.b_laundry.p3l.p3l.user;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;

import com.b_laundry.p3l.p3l.R;
import com.b_laundry.p3l.p3l.adapter.UserSparepartAdapter;
import com.b_laundry.p3l.p3l.api.Api;
import com.b_laundry.p3l.p3l.api.RetrofitClient;
import com.b_laundry.p3l.p3l.models.Sparepart;
import com.b_laundry.p3l.p3l.models.SparepartResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserHighestStockSparepartFragment extends Fragment {
    View v;
    private UserSparepartAdapter sparepartAdapter;
    private SparepartResponse sparepartList1;
    private UserSparepartAdapter adapter;
    private GridView mGridView;
    public List<Sparepart> SparepartList;
    private FloatingActionButton addButton;
    private ImageButton deleteButton;
    List<Sparepart> arrayList = new ArrayList<>();
    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        v = inflater.inflate(R.layout.user_activity_sparepart, container, false);
        //recyclerView = v.findViewById(R.id.card_recycler_view);
        sparepartAdapter = new UserSparepartAdapter(sparepartList1);
        addButton = v.findViewById(R.id.btnAddSparepart);
        mGridView = v.findViewById(R.id.mGridView);
        deleteButton = v.findViewById(R.id.btnDeleteSparepart);
        //addButton.setVisibility(View.GONE);
        return v;

    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        int myInt = 0;
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getSparepart();
    }

    public void getSparepart(){

        final Api service = RetrofitClient.getRetrofitInstance().create(Api.class);

        Call<SparepartResponse> call = service.getSparepartHighStock();
        //Log.d("responsecode", String.valueOf(request.getSparepart()));

        call.enqueue(new Callback<SparepartResponse>() {
            @Override
            public void onResponse(Call<SparepartResponse> call, final Response<SparepartResponse> response) {
                populateGridView(response.body());

            }

            @Override
            public void onFailure(Call<SparepartResponse> call, Throwable t) {
                Log.d("ErrorLess",t.getMessage());
            }
        });
    }
        public void populateGridView(SparepartResponse sparepartList) {
            mGridView = mGridView.findViewById(R.id.mGridView);
            adapter = new UserSparepartAdapter(sparepartList);
            mGridView.setAdapter(adapter);
        }
}
