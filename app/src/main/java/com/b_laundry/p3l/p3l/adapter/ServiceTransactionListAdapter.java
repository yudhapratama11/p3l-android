package com.b_laundry.p3l.p3l.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.b_laundry.p3l.p3l.R;
import com.b_laundry.p3l.p3l.models.ServiceTransaction;
import com.b_laundry.p3l.p3l.models.ServiceTransactionResponse;

import java.util.ArrayList;


public class ServiceTransactionListAdapter extends RecyclerView.Adapter<ServiceTransactionListAdapter.ViewHolder>{
    private ServiceTransactionResponse serviceList;

    public ServiceTransactionListAdapter(ServiceTransactionResponse exampleList) {
        serviceList = exampleList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ServiceTransactionListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.service_transaction_card_row, viewGroup, false);
        return new ServiceTransactionListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ServiceTransactionListAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.namaService.setText(serviceList.getData().get(i).getService().toString());
        viewHolder.platMotor.setText(serviceList.getData().get(i).getCustomerMotorcycle().toString());
        viewHolder.harga.setText(serviceList.getData().get(i).getSubtotal().toString());
    }

    @Override
    public int getItemCount() {
        return serviceList.getData().size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        private TextView namaService, harga,platMotor;
        private ImageButton deleteButton,editButton;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            namaService = (TextView) view.findViewById(R.id.txtNamaService);
            platMotor = view.findViewById(R.id.txtNamaPlat);
            harga = view.findViewById(R.id.txtHargaService);
        }
    }
}
