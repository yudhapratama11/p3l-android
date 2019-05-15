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
import com.b_laundry.p3l.p3l.admin.AdminEmployeeFragment;
import com.b_laundry.p3l.p3l.admin.AdminHistoryFragment;
import com.b_laundry.p3l.p3l.api.RetrofitClient;
import com.b_laundry.p3l.p3l.models.EmployeesReponse;
import com.b_laundry.p3l.p3l.models.HistorySparepartResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>  {
    private AdminHistoryFragment context;
    private HistorySparepartResponse historyList;

    public HistoryAdapter(AdminHistoryFragment context, HistorySparepartResponse historyList) {
        this.context = context;
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_card_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.namaPegawai.setText(historyList.getData().get(i).getNama().toString());
        viewHolder.cabang.setText(historyList.getData().get(i).getId().toString());
        viewHolder.tanggal.setText(historyList.getData().get(i).getTanggal().toString());

        viewHolder.mView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
//                Intent intent = new Intent(v.getContext(), AdminEmployeeActivity.class);
//                intent.putExtra("id",historyList.getData().get(i).getId().toString());
//                intent.putExtra("nama",historyList.getData().get(i).getNama().toString());
//                intent.putExtra("notelp",historyList.getData().get(i).getNomorTelepon().toString());
//                intent.putExtra("gaji",historyList.getData().get(i).getGaji().toString());
//                intent.putExtra("role",historyList.getData().get(i).getRole().toString());
//                intent.putExtra("branch",historyList.getData().get(i).getBranch().toString());
//                v.getContext().startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return historyList.getData().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public View mView;
        private TextView namaPegawai,cabang,tanggal;
        private ImageButton deleteButton;
        public ViewHolder(View view) {
            super(view);
            mView = view;
            namaPegawai = (TextView)view.findViewById(R.id.txtNamaSpHis);
            cabang = (TextView)view.findViewById(R.id.txtIdSpHis);
            tanggal = (TextView)view.findViewById(R.id.txtTanggalSpHis);

        }
    }
}
