package com.b_laundry.p3l.p3l.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.b_laundry.p3l.p3l.R;
import com.b_laundry.p3l.p3l.cs.UpdateTransactionActivity;
import com.b_laundry.p3l.p3l.models.Transaction;
import com.b_laundry.p3l.p3l.models.TransactionResponse;

import java.util.ArrayList;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {
    public TransactionResponse transactionList;
    List<Transaction> arrayList = new ArrayList<>();

    public TransactionAdapter(TransactionResponse transactionList) {
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public TransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.transaction_card_row, viewGroup, false);
        return new TransactionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TransactionAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.textBelum.setVisibility(View.VISIBLE);
        viewHolder.textSelesai.setVisibility(View.VISIBLE);
        viewHolder.idTransaction.setText(transactionList.getData().get(i).getId().toString());
        final Integer transactiontype = Integer.parseInt(transactionList.getData().get(i).getIdTransactionType().toString());
        final Integer status = Integer.parseInt(transactionList.getData().get(i).getStatus().toString());
        if(transactiontype == 1) {
            viewHolder.namaTransaction.setText("Service");
        }
        else if (transactiontype == 2)
        {
            viewHolder.namaTransaction.setText("Sparepart");
        }
        else if (transactiontype == 3)
        {
            viewHolder.namaTransaction.setText("Service dan Sparepart");
        }

        if (status == 0)
        {
            viewHolder.textSelesai.setVisibility(View.GONE);
        }
        else if (status == 1)
        {
            viewHolder.textBelum.setVisibility(View.GONE);
        }
        //Integer status = Integer.parseInt(transactionList.getData().get(i).ge().toString());
        viewHolder.namaPelanggan.setText(transactionList.getData().get(i).getCustomer().toString());
        viewHolder.mView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), UpdateTransactionActivity.class);
                Log.d("aidi", transactionList.getData().get(i).getId().toString());
                intent.putExtra("id", transactionList.getData().get(i).getId().toString());
                intent.putExtra("customer", transactionList.getData().get(i).getIdCustomer().toString());
                intent.putExtra("status", transactionList.getData().get(i).getStatus().toString());
                intent.putExtra("statuspaid", transactionList.getData().get(i).getStatusPaid().toString());
                intent.putExtra("id_transaction_type", transactionList.getData().get(i).getIdTransactionType().toString());
                intent.putExtra("tanggal", transactionList.getData().get(i).getTanggal().toString());
                intent.putExtra("subtotal", transactionList.getData().get(i).getSubtotal().toString());
                intent.putExtra("discount", transactionList.getData().get(i).getDiscount().toString());
                intent.putExtra("tujuan", "edit");
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return transactionList.getData().size();
    }

    public void setFilter(List<Transaction> newList){
        transactionList = new TransactionResponse();
        transactionList.getData().addAll(newList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        private TextView idTransaction, namaTransaction ,textSelesai,textBelum,namaPelanggan;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            idTransaction = (TextView) view.findViewById(R.id.txtIdTransaction);
            namaTransaction = (TextView) view.findViewById(R.id.txtNamaTransaction);
            textSelesai = (TextView) view.findViewById(R.id.txtViewSelesai);
            textBelum = (TextView) view.findViewById(R.id.txtViewBelum);
            namaPelanggan = view.findViewById(R.id.txtNamaCustomer);
        }
    }
}
