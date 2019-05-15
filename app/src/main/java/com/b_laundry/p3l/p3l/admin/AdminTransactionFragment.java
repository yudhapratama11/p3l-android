package com.b_laundry.p3l.p3l.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.b_laundry.p3l.p3l.R;
import com.b_laundry.p3l.p3l.adapter.ProcurementAdapter;
import com.b_laundry.p3l.p3l.adapter.TransactionAdapter;
import com.b_laundry.p3l.p3l.api.Api;
import com.b_laundry.p3l.p3l.api.RetrofitClient;
import com.b_laundry.p3l.p3l.cs.ServiceTransactionActivity;
import com.b_laundry.p3l.p3l.cs.SparepartServiceTransactionActivity;
import com.b_laundry.p3l.p3l.cs.SparepartTransactionActivity;
import com.b_laundry.p3l.p3l.models.SparepartProcurement;
import com.b_laundry.p3l.p3l.models.SparepartProcurementList;
import com.b_laundry.p3l.p3l.models.Transaction;
import com.b_laundry.p3l.p3l.models.TransactionResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminTransactionFragment extends Fragment {
    View v;
    private RecyclerView recyclerView;
    private TransactionAdapter transactionAdapter;
    private TransactionResponse transactionList;
    public List<Transaction> TransactionList;
    private FloatingActionButton addButton;
    List<SparepartProcurement> arrayList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        v = inflater.inflate(R.layout.admin_transaction_fragment, container, false);
        recyclerView = v.findViewById(R.id.card_recycler_view);
        transactionAdapter = new TransactionAdapter(transactionList);
        addButton = v.findViewById(R.id.btnAddSparepart);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
//                final String[] fonts = {"Service", "Sparepart", "Service dan Sparepart"};
//
//                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                builder.setTitle("Pilih Jasa");
//                builder.setItems(fonts, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        if ("Service".equals(fonts[which])){
//                            Intent intentCreate = new Intent(v.getContext(), ServiceTransactionActivity.class);
//                            v.getContext().startActivity(intentCreate);
//                        }
//                        else if ("Sparepart".equals(fonts[which])){
//                            Intent intentCreate = new Intent(v.getContext(), SparepartTransactionActivity.class);
//                            v.getContext().startActivity(intentCreate);
//                        }
//                        else if ("Service dan Sparepart".equals(fonts[which])){
//                            Intent intentCreate = new Intent(v.getContext(), SparepartServiceTransactionActivity.class);
//                            v.getContext().startActivity(intentCreate);
//                        }
////
//                    }
//                });
//                builder.show();
                Intent intentCreate = new Intent(v.getContext(), SparepartServiceTransactionActivity.class);
                v.getContext().startActivity(intentCreate);

            }
        });
        return v;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.sparepart_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase();
                String name = "";
                String name1 = "";
                List<Transaction> newList = new ArrayList<>();

                for (Transaction transaction : TransactionList) {
                    Integer idtrans = Integer.parseInt(transaction.getIdTransactionType().toString());
                    String transname = transaction.getId().toLowerCase();
                    String custname = transaction.getCustomer().toLowerCase();
                    if (idtrans == 1) {
                        name = "service";
                        //name1 = "sv";
                    } else if (idtrans == 2) {
                        name = "sparepart";
                        //name1 = "sp";
                    } else if (idtrans == 3) {
                        name = "service dan sparepart";
                        //name1 = "ss";
                    }
                    //Log.d("sparepartlower",transaction.getSales().toLowerCase().toString());
                    if (name.contains(newText)) {
                        newList.add(transaction);
                    }
//                    if(name1.contains(newText)) {
//                        newList.add(transaction);
//                    }
                    if(transname.contains(newText))
                    {
                        newList.add(transaction);
                    }
                    if(custname.contains(newText))
                    {
                        newList.add(transaction);
                    }

                }
                transactionAdapter.setFilter(newList);

                return true;
            }
        });
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        recyclerView.setAdapter(null);
        getSparepart();
        super.onResume();
    }
    public void getSparepart(){

        final Api service = RetrofitClient.getRetrofitInstance().create(Api.class);

        Call<TransactionResponse> call = service.getTransaction();
        //Log.d("responsecode", String.valueOf(request.getSparepart()));

        call.enqueue(new Callback<TransactionResponse>() {
            @Override
            public void onResponse(Call<TransactionResponse> call, final Response<TransactionResponse> response) {
                Log.d("responsdecodenya", String.valueOf(response.code()));
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<TransactionResponse> call, Throwable t) {
                Log.d("ErrorLess",t.getMessage());
            }
        });
    }

    public void generateDataList(TransactionResponse transactionList) {
        recyclerView = recyclerView.findViewById(R.id.card_recycler_view);
        transactionAdapter = new TransactionAdapter(transactionList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(transactionAdapter);
        TransactionList = transactionList.getData();
    }
}
