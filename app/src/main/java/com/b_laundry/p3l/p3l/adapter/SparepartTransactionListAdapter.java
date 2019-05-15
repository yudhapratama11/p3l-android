package com.b_laundry.p3l.p3l.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.b_laundry.p3l.p3l.R;
import com.b_laundry.p3l.p3l.models.SparepartTransaction;
import com.b_laundry.p3l.p3l.models.SparepartTransactionResponse;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.ArrayList;


public class SparepartTransactionListAdapter extends RecyclerView.Adapter<SparepartTransactionListAdapter.ViewHolder>{
    private SparepartTransactionResponse sparepartTransactionList;

    public SparepartTransactionListAdapter(SparepartTransactionResponse sparepartTransactionList) {
        this.sparepartTransactionList = sparepartTransactionList;
    }

    @NonNull
    @Override
    public SparepartTransactionListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sparepart_transaction_card_row, viewGroup, false);
        return new SparepartTransactionListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SparepartTransactionListAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.namasparepart.setText(sparepartTransactionList.getData().get(i).getNamaSparepart().toString());
        viewHolder.jumlahSparepart.setNumber(sparepartTransactionList.getData().get(i).getJumlah().toString());
        viewHolder.stokSparepart.setText(sparepartTransactionList.getData().get(i).getSubtotal().toString());
        viewHolder.jumlahSparepart.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = viewHolder.jumlahSparepart.getNumber();
                Integer jumlah = Integer.parseInt(num);
                Integer satuanharga = Integer.parseInt(sparepartTransactionList.getData().get(i).getHargaSatuan().toString());
                sparepartTransactionList.getData().get(i).setJumlah(jumlah);
                sparepartTransactionList.getData().get(i).setSubtotal(jumlah * satuanharga);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return sparepartTransactionList.getData().size();
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
