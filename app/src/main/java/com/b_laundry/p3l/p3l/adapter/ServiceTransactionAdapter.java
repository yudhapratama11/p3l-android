package com.b_laundry.p3l.p3l.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.b_laundry.p3l.p3l.R;
import com.b_laundry.p3l.p3l.models.Service;
import com.b_laundry.p3l.p3l.models.ServiceTransaction;
import com.b_laundry.p3l.p3l.models.SparepartTransaction;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.ArrayList;


public class ServiceTransactionAdapter extends RecyclerView.Adapter<ServiceTransactionAdapter.ViewHolder>{
    private ArrayList<ServiceTransaction> mExampleList;

    public ServiceTransactionAdapter(ArrayList<ServiceTransaction> exampleList) {
        mExampleList = exampleList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ServiceTransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.service_transaction_card_row, viewGroup, false);
        return new ServiceTransactionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final ServiceTransaction currentItem = mExampleList.get(position);

        holder.namaService.setText(currentItem.getService().toString());
        holder.platMotor.setText(currentItem.getCustomerMotorcycle().toString());
        holder.harga.setText(currentItem.getSubtotal().toString());
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        private TextView kodesparepart, namaService, jumlahsparepart, harga,platMotor;
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
