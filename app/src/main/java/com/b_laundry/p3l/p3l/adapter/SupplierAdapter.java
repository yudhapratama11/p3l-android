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
import com.b_laundry.p3l.p3l.admin.AdminSupplierActivity;
import com.b_laundry.p3l.p3l.admin.AdminSupplierFragment;
import com.b_laundry.p3l.p3l.admin.SparepartActivity;
import com.b_laundry.p3l.p3l.api.RetrofitClient;
import com.b_laundry.p3l.p3l.models.Sparepart;
import com.b_laundry.p3l.p3l.models.SparepartResponse;
import com.b_laundry.p3l.p3l.models.Supplier;
import com.b_laundry.p3l.p3l.models.SupplierResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupplierAdapter extends RecyclerView.Adapter<SupplierAdapter.ViewHolder> {
    private AdminSupplierFragment context;
    private SupplierResponse supplierList;

    public SupplierAdapter(AdminSupplierFragment context, SupplierResponse supplierList) {
        this.context = context;
        this.supplierList = supplierList;
    }

    @NonNull
    @Override
    public SupplierAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.supplier_card_row, viewGroup, false);
        return new SupplierAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SupplierAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.nama.setText(supplierList.getData().get(i).getNama().toString());
        viewHolder.alamat.setText(supplierList.getData().get(i).getAlamat().toString());
        viewHolder.notelp.setText(supplierList.getData().get(i).getNomorTelepon().toString());

        viewHolder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent intent = new Intent(v.getContext(), AdminSupplierActivity.class);
                intent.putExtra("id", supplierList.getData().get(i).getId().toString());
                intent.putExtra("nama", supplierList.getData().get(i).getNama().toString());
                intent.putExtra("alamat", supplierList.getData().get(i).getAlamat().toString());
                intent.putExtra("notelp", supplierList.getData().get(i).getNomorTelepon().toString());
                intent.putExtra("tujuan", "edit");
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
                Call<ResponseBody> call = RetrofitClient.getInstance().getApi().deleteSupplier(supplierList.getData().get(i).getId().toString());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

//                        Log.d("responsecode", String.valueOf(response.code()));
//                        Log.d("response", String.valueOf(response.body()));
                        supplierList.getData().remove(viewHolder.getAdapterPosition());
                        notifyItemRemoved(viewHolder.getAdapterPosition());
                        notifyItemRangeChanged(viewHolder.getAdapterPosition(), supplierList.getData().size());
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
    }

    @Override
    public int getItemCount() {
        return supplierList.getData().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public View mView;
        private TextView nama,alamat,notelp;
        private ImageButton deleteButton,editButton;
        public ViewHolder(View view) {
            super(view);
            mView = view;
            nama = (TextView)view.findViewById(R.id.txtNamaSupplier);
            alamat = (TextView)view.findViewById(R.id.txtAlamatSupplier);
            notelp = (TextView)view.findViewById(R.id.txtNotelpSupplier);
            deleteButton = (ImageButton) view.findViewById(R.id.btnDeleteSupplier);
            editButton = (ImageButton)view.findViewById(R.id.btnEditSupplier);

        }
    }

    public void setFilter(List<Supplier> newList){
        supplierList = new SupplierResponse();
        supplierList.getData().addAll(newList);
        notifyDataSetChanged();
    }
}
