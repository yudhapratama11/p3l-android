package com.b_laundry.p3l.p3l.adapter;

import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.b_laundry.p3l.p3l.R;
import com.b_laundry.p3l.p3l.admin.AdminProcurementActivity;
import com.b_laundry.p3l.p3l.admin.AdminProcurementFragment;
import com.b_laundry.p3l.p3l.admin.SparepartActivity;
import com.b_laundry.p3l.p3l.models.Sparepart;
import com.b_laundry.p3l.p3l.models.SparepartProcurement;
import com.b_laundry.p3l.p3l.models.SparepartProcurementDetail;
import com.b_laundry.p3l.p3l.models.SparepartProcurementDetailList;
import com.b_laundry.p3l.p3l.models.SparepartProcurementList;
import com.b_laundry.p3l.p3l.models.SparepartResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProcurementAdapter extends RecyclerView.Adapter<ProcurementAdapter.ViewHolder> {
    private AdminProcurementFragment context;
    public SparepartProcurementList sparepartList;
    List<SparepartProcurement> arrayList = new ArrayList<>();

    public ProcurementAdapter(AdminProcurementFragment context, SparepartProcurementList sparepartList) {
        this.context = context;
        this.sparepartList = sparepartList;
    }

    @NonNull
    @Override
    public ProcurementAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.procurement_card_row, viewGroup, false);
        return new ProcurementAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProcurementAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.kodesparepart.setText(sparepartList.getData().get(i).getTanggal().toString());
        viewHolder.namasparepart.setText(sparepartList.getData().get(i).getSales().toString());
        Integer status = Integer.parseInt(sparepartList.getData().get(i).getStatus().toString());

        if (status.equals(0))
        {
            viewHolder.textSelesai.setVisibility(View.GONE);
        }
        else if (status.equals(1))
        {
            viewHolder.textBelum.setVisibility(View.GONE);
        }

        viewHolder.mView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), AdminProcurementActivity.class);
                Log.d("aidi",sparepartList.getData().get(i).getId().toString());
                intent.putExtra("id",sparepartList.getData().get(i).getId().toString());
                intent.putExtra("idsales",sparepartList.getData().get(i).getIdSales().toString());
                intent.putExtra("sales",sparepartList.getData().get(i).getSales().toString());
                intent.putExtra("status",sparepartList.getData().get(i).getStatus().toString());
                intent.putExtra("tanggal",sparepartList.getData().get(i).getTanggal().toString());
                intent.putExtra("tujuan","tampil");
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sparepartList.getData().size();
    }

    public void setFilter(List<SparepartProcurement> newList){
        sparepartList = new SparepartProcurementList();
        sparepartList.getData().addAll(newList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        private TextView kodesparepart, namasparepart ,textSelesai,textBelum;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            kodesparepart = (TextView) view.findViewById(R.id.txtKodeSparepart);
            namasparepart = (TextView) view.findViewById(R.id.txtNamaSparepart);
            textSelesai = (TextView) view.findViewById(R.id.txtViewSelesai);
            textBelum = (TextView) view.findViewById(R.id.txtViewBelum);
        }
    }
}
