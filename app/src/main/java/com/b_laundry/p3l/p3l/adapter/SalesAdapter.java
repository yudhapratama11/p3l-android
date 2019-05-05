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
import com.b_laundry.p3l.p3l.admin.AdminEmployeeActivity;
import com.b_laundry.p3l.p3l.admin.AdminSalesActivity;
import com.b_laundry.p3l.p3l.admin.AdminSalesFragment;
import com.b_laundry.p3l.p3l.api.RetrofitClient;
import com.b_laundry.p3l.p3l.models.Sales;
import com.b_laundry.p3l.p3l.models.SalesResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.ViewHolder> {
    private AdminSalesFragment context;
    private SalesResponse salesList;

    public SalesAdapter(AdminSalesFragment context, SalesResponse salesList) {
        this.context = context;
        this.salesList = salesList;
    }

    @NonNull
    @Override
    public SalesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sales_card_row, viewGroup, false);
        return new SalesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SalesAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.namaPegawai.setText(salesList.getData().get(i).getNama().toString());
        viewHolder.notelp.setText(salesList.getData().get(i).getNomorTelepon().toString());
        viewHolder.namasupplier.setText(salesList.getData().get(i).getNamaSupplier().toString());

//        viewHolder.mView.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                Intent intent = new Intent(v.getContext(), AdminEmployeeActivity.class);
//                intent.putExtra("id",salesList.getData().get(i).getId().toString());
//                intent.putExtra("nama",salesList.getData().get(i).getNama().toString());
//                intent.putExtra("notelp",salesList.getData().get(i).getNomorTelepon().toString());
//                intent.putExtra("namasupplier",salesList.getData().get(i).getNamaSupplier().toString());
//                v.getContext().startActivity(intent);
//            }
//        });

        viewHolder.editButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AdminSalesActivity.class);
                intent.putExtra("tujuan","edit");
                intent.putExtra("id",salesList.getData().get(i).getId().toString());
                intent.putExtra("nama",salesList.getData().get(i).getNama().toString());
                intent.putExtra("notelp",salesList.getData().get(i).getNomorTelepon().toString());
                intent.putExtra("namasupplier",salesList.getData().get(i).getNamaSupplier().toString());
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
               // Log.d("kodesparepart",employeesList.getData().get(i).getId().toString());
                Call<ResponseBody> call = RetrofitClient.getInstance().getApi().deleteSales(salesList.getData().get(i).getId().toString());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        Log.d("responsecode", String.valueOf(response.code()));
                        Log.d("response", String.valueOf(response.body()));
                        salesList.getData().remove(viewHolder.getAdapterPosition());
                        notifyItemRemoved(viewHolder.getAdapterPosition());
                        notifyItemRangeChanged(viewHolder.getAdapterPosition(), salesList.getData().size());
                        Toast.makeText(v.getContext(), "Sukses menghapus sales", Toast.LENGTH_SHORT).show();
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



    public void setFilter(List<Sales> newList){
        salesList = new SalesResponse();
        salesList.getData().addAll(newList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return salesList.getData().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public View mView;
        private TextView namaPegawai,notelp,namasupplier;
        private ImageButton deleteButton,editButton;
        public ViewHolder(View view) {
            super(view);
            mView = view;
            namaPegawai = (TextView)view.findViewById(R.id.txtNamaSales);
            notelp = (TextView)view.findViewById(R.id.txtNomorTeleponSales);
            namasupplier = (TextView)view.findViewById(R.id.txtNamaSupplier);
            deleteButton = (ImageButton) view.findViewById(R.id.btnDeleteSales);
            editButton = (ImageButton) view.findViewById(R.id.btnEditSales);

        }
    }
}
