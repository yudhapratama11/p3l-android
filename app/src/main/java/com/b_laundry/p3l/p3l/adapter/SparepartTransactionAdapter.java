package com.b_laundry.p3l.p3l.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.b_laundry.p3l.p3l.R;
import com.b_laundry.p3l.p3l.models.SparepartProcurementDetail;
import com.b_laundry.p3l.p3l.models.SparepartTransaction;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.ArrayList;


public class SparepartTransactionAdapter extends RecyclerView.Adapter<SparepartTransactionAdapter.ViewHolder>{
    private ArrayList<SparepartTransaction> mExampleList;

    public SparepartTransactionAdapter(ArrayList<SparepartTransaction> exampleList) {
        mExampleList = exampleList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SparepartTransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sparepart_transaction_card_row, viewGroup, false);
        return new SparepartTransactionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final SparepartTransaction currentItem = mExampleList.get(position);

        holder.namasparepart.setText(currentItem.getNamaSparepart().toString());
        holder.jumlahSparepart.setNumber(currentItem.getJumlah().toString());
        holder.stokSparepart.setText(currentItem.getSubtotal().toString());
        holder.jumlahSparepart.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = holder.jumlahSparepart.getNumber();
                Integer jumlah = Integer.parseInt(num);
                Integer satuanharga = Integer.parseInt(currentItem.getHargaSatuan().toString());
                currentItem.setJumlah(jumlah);
                currentItem.setSubtotal(jumlah * satuanharga);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        private TextView kodesparepart, namasparepart, jumlahsparepart, hargasparepart,stokSparepart;
        private ImageButton deleteButton,editButton;
        private ElegantNumberButton jumlahSparepart;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            namasparepart = (TextView) view.findViewById(R.id.txtNamaSparepart);
            jumlahSparepart = (ElegantNumberButton) view.findViewById(R.id.jlhsparepartelegant);
            stokSparepart = view.findViewById(R.id.txtStokSparepart);
            deleteButton = (ImageButton) view.findViewById(R.id.btnDeleteSparepart);
            editButton = (ImageButton)view.findViewById(R.id.btnEditSparepart);
        }
    }
}
