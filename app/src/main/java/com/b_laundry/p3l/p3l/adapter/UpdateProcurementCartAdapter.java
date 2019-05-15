package com.b_laundry.p3l.p3l.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.b_laundry.p3l.p3l.R;
import com.b_laundry.p3l.p3l.admin.AdminProcurementActivity;
import com.b_laundry.p3l.p3l.admin.AdminSparepartFragment;
import com.b_laundry.p3l.p3l.models.SparepartProcurementDetail;
import com.b_laundry.p3l.p3l.models.SparepartProcurementDetailList;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.ArrayList;


public class UpdateProcurementCartAdapter extends RecyclerView.Adapter<UpdateProcurementCartAdapter.ViewHolder>{
    private SparepartProcurementDetailList mExampleList;
    private AdminProcurementActivity context;

    public UpdateProcurementCartAdapter(AdminProcurementActivity context, SparepartProcurementDetailList exampleList) {
        this.context = context;
        mExampleList = exampleList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UpdateProcurementCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.procurement_cart_card_row, viewGroup, false);
        return new UpdateProcurementCartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final SparepartProcurementDetail currentItem = mExampleList.getData().get(position);

        holder.namasparepart.setText(currentItem.getNamaSparepart().toString());
        holder.jumlahSparepart.setNumber(currentItem.getJumlah().toString());
        holder.stokSparepart.setText(currentItem.getSubtotal().toString());
        holder.jumlahSparepart.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = holder.jumlahSparepart.getNumber();
                Integer jumlah = Integer.parseInt(num);
                Integer satuanharga = Integer.parseInt(currentItem.getSatuanHarga().toString());
                currentItem.setJumlah(jumlah);
                currentItem.setSubtotal(jumlah * satuanharga);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mExampleList.getData().size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        private TextView kodesparepart, namasparepart, jumlahsparepart, hargasparepart,stokSparepart;
        private ImageButton deleteButton,editButton;
        private ElegantNumberButton jumlahSparepart;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            namasparepart = (TextView) view.findViewById(R.id.txtNamaCartSparepart);
            jumlahSparepart = (ElegantNumberButton) view.findViewById(R.id.jlhsparepartelegant);
            stokSparepart = view.findViewById(R.id.txtStokSparepart);
            deleteButton = (ImageButton) view.findViewById(R.id.btnDeleteSparepart);
            editButton = (ImageButton)view.findViewById(R.id.btnEditSparepart);
        }
    }
}
