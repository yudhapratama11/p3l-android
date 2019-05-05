package com.b_laundry.p3l.p3l.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.b_laundry.p3l.p3l.R;
import com.b_laundry.p3l.p3l.admin.AdminSparepartFragment;
import com.b_laundry.p3l.p3l.models.Sparepart;
import com.b_laundry.p3l.p3l.models.SparepartResponse;
import com.b_laundry.p3l.p3l.user.UserSparepartFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class UserSparepartAdapter extends BaseAdapter {
//    private UserSparepartFragment context;
    public SparepartResponse sparepartList;
    List<Sparepart> arrayList = new ArrayList<>();
    private String BASE_URL = "http://10.53.2.213:8000/itemImages/";

    public UserSparepartAdapter(SparepartResponse sparepartList) {
//        this.context = context;
        this.sparepartList = sparepartList;
    }

    @Override
    public int getCount() {
        return sparepartList.getData().size();
    }

    @Override
    public Object getItem(int position) {
        return sparepartList.getData().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_sparepart_card_row,parent,false);
        }
        TextView txtViewNama = convertView.findViewById(R.id.txtViewNama);
        TextView txtViewStok = convertView.findViewById(R.id.txtViewStok);
        TextView txtViewHarga = convertView.findViewById(R.id.txtViewHarga);
        ImageView sparepartImgView = convertView.findViewById(R.id.sparepartImgView);
        //final SparepartResponse thisSparepartResponse = (SparepartResponse) sparepartList.getData().get(position);
        txtViewNama.setText(sparepartList.getData().get(position).getNama());
        txtViewStok.setText(sparepartList.getData().get(position).getStok());
        txtViewHarga.setText(sparepartList.getData().get(position).getHargaJual());
        Picasso.get().load(BASE_URL + sparepartList.getData().get(position).getGambar()).into(sparepartImgView);
        return convertView;
    }


}
