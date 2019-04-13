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
import com.b_laundry.p3l.p3l.admin.AdminBranchFragment;
import com.b_laundry.p3l.p3l.api.RetrofitClient;
import com.b_laundry.p3l.p3l.models.BranchResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BranchAdapter extends RecyclerView.Adapter<BranchAdapter.ViewHolder> {

    private AdminBranchFragment context;
    private BranchResponse branchList;

    public BranchAdapter(AdminBranchFragment context, BranchResponse branchList) {
        this.context = context;
        this.branchList = branchList;
    }

    @NonNull
    @Override
    public BranchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.branch_card_row, viewGroup, false);
        return new BranchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BranchAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.namaPegawai.setText(branchList.getData().get(i).getNama().toString());
        viewHolder.cabang.setText(branchList.getData().get(i).getAlamat().toString());

//        viewHolder.mView.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                Intent intent = new Intent(v.getContext(), AdminEmployeeActivity.class);
//                intent.putExtra("id",employeesList.getData().get(i).getId().toString());
//                intent.putExtra("nama",employeesList.getData().get(i).getNama().toString());
//                intent.putExtra("notelp",employeesList.getData().get(i).getNomorTelepon().toString());
//                intent.putExtra("gaji",employeesList.getData().get(i).getGaji().toString());
//                intent.putExtra("role",employeesList.getData().get(i).getRole().toString());
//                intent.putExtra("branch",employeesList.getData().get(i).getBranch().toString());
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
                Log.d("kodesparepart",branchList.getData().get(i).getId().toString());
                Call<ResponseBody> call = RetrofitClient.getInstance().getApi().deleteBranch(branchList.getData().get(i).getId().toString());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

//                        Log.d("responsecode", String.valueOf(response.code()));
//                        Log.d("response", String.valueOf(response.body()));
                        branchList.getData().remove(viewHolder.getAdapterPosition());
                        notifyItemRemoved(viewHolder.getAdapterPosition());
                        notifyItemRangeChanged(viewHolder.getAdapterPosition(), branchList.getData().size());
                        Toast.makeText(v.getContext(), "Sukses menghapus pegawai", Toast.LENGTH_SHORT).show();
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
        return branchList.getData().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public View mView;
        private TextView namaPegawai,cabang;
        private ImageButton deleteButton;
        public ViewHolder(View view) {
            super(view);
            mView = view;
            namaPegawai = (TextView)view.findViewById(R.id.txtNamaCabang);
            cabang = (TextView)view.findViewById(R.id.txtAlamatCabang);
            deleteButton = (ImageButton) view.findViewById(R.id.btnDeleteCabang);

        }
    }
}
