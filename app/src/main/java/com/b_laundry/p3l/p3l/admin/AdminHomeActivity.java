package com.b_laundry.p3l.p3l.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.b_laundry.p3l.p3l.LoginActivity;
import com.b_laundry.p3l.p3l.R;
import com.b_laundry.p3l.p3l.models.User;
import com.b_laundry.p3l.p3l.storage.SharedPrefManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class AdminHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private TextView textFullname;
    private TextView textUsername;
    static int view_position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textUsername = findViewById(R.id.viewFullname);
        textFullname = findViewById(R.id.viewFullname);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView navFullname = (TextView) headerView.findViewById(R.id.viewFullname);
        TextView navUsername = (TextView) headerView.findViewById(R.id.viewUsername);
        User user = SharedPrefManager.getInstance(this).getUser();
        navUsername.setText(user.getUsername().toString());
        navFullname.setText(user.getNama().toString());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (user != null) {
            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            if (!task.isSuccessful()) {
                                Log.w("getInstanceId failed", task.getException());
                                return;
                            }

                            // Get new Instance ID token
                            String token = task.getResult().getToken();

                            // Log and toast

                            Log.d("XXXXX", token);
                            Toast.makeText(AdminHomeActivity.this, token, Toast.LENGTH_SHORT).show();
                        }
                    });

//        Intent intent = new Intent(getApplicationContext(), Beranda.class);
//        startActivity(intent);
        }

        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        switch(getIntent().getIntExtra("addDialog",0)){
            case 1:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AdminSparepartFragment()).commit();
                break;
            case 2:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AdminEmployeeFragment()).commit();
                break;
            case 3:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AdminSupplierFragment()).commit();
                break;
            case 4:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AdminServiceFragment()).commit();
                break;
            case 5:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AdminServiceFragment()).commit();
                break;
            case 6:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AdminSalesFragment()).commit();
                break;
            case 7:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AdminProcurementFragment()).commit();
                break;
            case 8:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AdminSparepartFragmentPrice()).commit();
                break;
        }

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId())
        {
            case R.id.nav_spareparts:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AdminSparepartFragment()).commit();
                break;
            case R.id.nav_pegawai:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AdminEmployeeFragment()).commit();
                break;
            case R.id.nav_supplier:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AdminSupplierFragment()).commit();
                break;
            case R.id.nav_service:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AdminServiceFragment()).commit();
                break;
            case R.id.nav_sales:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AdminSalesFragment()).commit();
                break;
            case R.id.nav_procurement:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AdminProcurementFragment()).commit();
                break;
<<<<<<< HEAD
            case R.id.nav_customer:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AdminCustomerFragment()).commit();
                break;
            case R.id.nav_history:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AdminHistoryFragment()).commit();
                break;
            case R.id.nav_transaction:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AdminTransactionFragment()).commit();
=======
            case R.id.nav_sortprice:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AdminSparepartFragmentPrice()).commit();
                break;
            case R.id.nav_sortstock:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AdminSparepartFragmentStok()).commit();
                break;
            case R.id.nav_history:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AdminHistoryFragment()).commit();
>>>>>>> d3b1d4f8885d8cccabe2c663eae846647c43701c
                break;
            case R.id.nav_logout:
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                SharedPrefManager.getInstance(getApplicationContext()).clear();
                                Intent intent = new Intent(AdminHomeActivity.this,LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Apakah Anda Yakin Ingin Logout?").setPositiveButton("Ya", dialogClickListener)
                        .setNegativeButton("Tidak", dialogClickListener).show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
