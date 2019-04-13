package com.b_laundry.p3l.p3l.adapter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.b_laundry.p3l.p3l.R;
import com.b_laundry.p3l.p3l.admin.AdminServiceActivity;
import com.b_laundry.p3l.p3l.admin.AdminServiceFragment;
import com.b_laundry.p3l.p3l.admin.SparepartActivity;
import com.b_laundry.p3l.p3l.api.RetrofitClient;
import com.b_laundry.p3l.p3l.models.ServiceResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {
    private AdminServiceFragment context;
    private ServiceResponse serviceList;

    public ServiceAdapter(AdminServiceFragment context, ServiceResponse serviceList) {
        this.context = context;
        this.serviceList = serviceList;
    }

    @NonNull
    @Override
    public ServiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.service_card_row, viewGroup, false);
        return new ServiceAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ServiceAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.nama.setText(serviceList.getData().get(i).getNama().toString());
        viewHolder.harga.setText(serviceList.getData().get(i).getHarga().toString());

//        viewHolder.mView.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                Intent intent = new Intent(v.getContext(), AdminSupplierActivity.class);
//                intent.putExtra("id",supplierList.getData().get(i).getId().toString());
//                intent.putExtra("nama",supplierList.getData().get(i).getNama().toString());
//                intent.putExtra("notelp",supplierList.getData().get(i).getNomorTelepon().toString());
//                intent.putExtra("alamat",supplierList.getData().get(i).getAlamat().toString());
//                v.getContext().startActivity(intent);
//            }
//        });

        viewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final ProgressDialog dialog = new ProgressDialog(v.getContext());
                dialog.setMessage("Please wait.....");
                dialog.show();
                dialog.setCancelable(false);
                Call<ResponseBody> call = RetrofitClient.getInstance().getApi().deleteService(serviceList.getData().get(i).getId().toString());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

//                        Log.d("responsecode", String.valueOf(response.code()));
//                        Log.d("response", String.valueOf(response.body()));
                        serviceList.getData().remove(viewHolder.getAdapterPosition());
                        notifyItemRemoved(viewHolder.getAdapterPosition());
                        notifyItemRangeChanged(viewHolder.getAdapterPosition(), serviceList.getData().size());
                        Toast.makeText(v.getContext(), "Sukses menghapus Supplier", Toast.LENGTH_SHORT).show();
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

        viewHolder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent intent = new Intent(v.getContext(), AdminServiceActivity.class);
                intent.putExtra("id",serviceList.getData().get(i).getId().toString());
                intent.putExtra("nama",serviceList.getData().get(i).getNama().toString());
                intent.putExtra("harga",serviceList.getData().get(i).getHarga().toString());
                intent.putExtra("tujuan","edit");
                v.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return serviceList.getData().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public View mView;
        private TextView nama,harga;
        private ImageButton deleteButton,editButton;
        public ViewHolder(View view) {
            super(view);
            mView = view;
            nama = (TextView)view.findViewById(R.id.txtNamaService);
            harga = (TextView)view.findViewById(R.id.txtHargaService);
            deleteButton = (ImageButton) view.findViewById(R.id.btnDeleteService);
            editButton = view.findViewById(R.id.btnEditService);

        }
    }
}
