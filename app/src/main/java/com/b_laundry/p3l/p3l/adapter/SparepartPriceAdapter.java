package com.b_laundry.p3l.p3l.adapter;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.b_laundry.p3l.p3l.R;
import com.b_laundry.p3l.p3l.admin.AdminSparepartFragment;
import com.b_laundry.p3l.p3l.admin.AdminSparepartFragmentPrice;
import com.b_laundry.p3l.p3l.admin.SparepartActivity;
import com.b_laundry.p3l.p3l.api.RetrofitClient;
import com.b_laundry.p3l.p3l.models.Sparepart;
import com.b_laundry.p3l.p3l.models.SparepartResponse;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SparepartPriceAdapter extends RecyclerView.Adapter<SparepartPriceAdapter.ViewHolder> {
    private AdminSparepartFragmentPrice context;
    public SparepartResponse sparepartList;
    List<Sparepart> arrayList = new ArrayList<>();

    public SparepartPriceAdapter(AdminSparepartFragmentPrice context, SparepartResponse sparepartList) {
        this.context = context;
        this.sparepartList = sparepartList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sparepart_card_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.kodesparepart.setText(sparepartList.getData().get(i).getNama().toString());
        viewHolder.namasparepart.setText(sparepartList.getData().get(i).getId().toString());
        viewHolder.stoksparepart.setText(sparepartList.getData().get(i).getStok().toString());

        viewHolder.mView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), SparepartActivity.class);
                intent.putExtra("id",sparepartList.getData().get(i).getId().toString());
                intent.putExtra("nama",sparepartList.getData().get(i).getNama().toString());
                intent.putExtra("stok",sparepartList.getData().get(i).getStok().toString());
                intent.putExtra("merk",sparepartList.getData().get(i).getMerk().toString());
                intent.putExtra("hargabeli",sparepartList.getData().get(i).getHargaBeli().toString());
                intent.putExtra("hargajual",sparepartList.getData().get(i).getHargaJual().toString());
                intent.putExtra("penempatan",sparepartList.getData().get(i).getPenempatan().toString());
                intent.putExtra("stokminimal",sparepartList.getData().get(i).getStokMinimal().toString());
                intent.putExtra("gambar",sparepartList.getData().get(i).getGambar().toString());
                intent.putExtra("tipe",sparepartList.getData().get(i).getTipeSparepart().toString());
                Log.d("stokminimal",sparepartList.getData().get(i).getStokMinimal().toString());
                intent.putExtra("tujuan","tampil");
                v.getContext().startActivity(intent);
            }
        });

        viewHolder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SparepartActivity.class);
                intent.putExtra("id",sparepartList.getData().get(i).getId().toString());
                intent.putExtra("nama",sparepartList.getData().get(i).getNama().toString());
                intent.putExtra("stok",sparepartList.getData().get(i).getStok().toString());
                intent.putExtra("merk",sparepartList.getData().get(i).getMerk().toString());
                intent.putExtra("hargabeli",sparepartList.getData().get(i).getHargaBeli().toString());
                intent.putExtra("hargajual",sparepartList.getData().get(i).getHargaJual().toString());
                intent.putExtra("penempatan",sparepartList.getData().get(i).getPenempatan().toString());
                intent.putExtra("stokminimal",sparepartList.getData().get(i).getStokMinimal().toString());
                intent.putExtra("gambar",sparepartList.getData().get(i).getGambar().toString());
                intent.putExtra("tipe",sparepartList.getData().get(i).getTipeSparepart().toString());
                Log.d("stokminimal",sparepartList.getData().get(i).getStokMinimal().toString());
                intent.putExtra("tujuan","edit");
                v.getContext().startActivity(intent);
            }
        });

        viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final ProgressDialog dialog = new ProgressDialog(v.getContext());
                dialog.setMessage("Please wait.....");
                dialog.show();
                dialog.setCancelable(false);
                //Log.d("kodesparepart",sparepartList.getData().get(i).getId().toString());
                Call<ResponseBody> call = RetrofitClient.getInstance().getApi().deleteSparepart(sparepartList.getData().get(i).getId().toString());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

//                        Log.d("responsecode", String.valueOf(response.code()));
//                        Log.d("response", String.valueOf(response.body()));
                        sparepartList.getData().remove(viewHolder.getAdapterPosition());
                        notifyItemRemoved(viewHolder.getAdapterPosition());
                        notifyItemRangeChanged(viewHolder.getAdapterPosition(), sparepartList.getData().size());
                        Toast.makeText(v.getContext(), "Sukses menghapus sparepart", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d("faildelete", String.valueOf(t));
                    }

                });
                Runnable progressRunnable = new Runnable() {

                    @Override
                    public void run() {
                        dialog.cancel();
                    }
                };

                Handler pdCanceller = new Handler();
                pdCanceller.postDelayed(progressRunnable, 2000);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sparepartList.getData().size();
    }


    public void setFilter(List<Sparepart> newList){
        sparepartList = new SparepartResponse();
        sparepartList.getData().addAll(newList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        private TextView kodesparepart, namasparepart, stoksparepart;
        private ImageButton deleteButton,editButton;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            kodesparepart = (TextView) view.findViewById(R.id.txtKodeSparepart);
            namasparepart = (TextView) view.findViewById(R.id.txtNamaSparepart);
            stoksparepart = (TextView) view.findViewById(R.id.txtStokSparepart);
            deleteButton = (ImageButton) view.findViewById(R.id.btnDeleteSparepart);
            editButton = (ImageButton)view.findViewById(R.id.btnEditSparepart);
        }
    }

}
