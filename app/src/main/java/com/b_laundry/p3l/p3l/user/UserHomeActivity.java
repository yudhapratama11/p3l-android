package com.b_laundry.p3l.p3l.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.b_laundry.p3l.p3l.LoginActivity;
import com.b_laundry.p3l.p3l.R;
import com.b_laundry.p3l.p3l.admin.AdminEmployeeFragment;
import com.b_laundry.p3l.p3l.admin.AdminHomeActivity;
import com.b_laundry.p3l.p3l.admin.AdminProcurementFragment;
import com.b_laundry.p3l.p3l.admin.AdminSalesFragment;
import com.b_laundry.p3l.p3l.admin.AdminServiceFragment;
import com.b_laundry.p3l.p3l.admin.AdminSparepartFragment;
import com.b_laundry.p3l.p3l.admin.AdminSparepartFragmentPrice;
import com.b_laundry.p3l.p3l.admin.AdminSparepartFragmentStok;
import com.b_laundry.p3l.p3l.admin.AdminSupplierFragment;
import com.b_laundry.p3l.p3l.models.User;
import com.b_laundry.p3l.p3l.storage.SharedPrefManager;

public class UserHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;
    private TextView textFullname;
    private TextView textUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_main);

        textUsername = findViewById(R.id.viewFullname);
        textFullname = findViewById(R.id.viewFullname);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView navFullname = (TextView) headerView.findViewById(R.id.viewFullname);
        TextView navUsername = (TextView) headerView.findViewById(R.id.viewUsername);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        switch(getIntent().getIntExtra("addDialog",0)){
            case 1:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_spareparts,
                        new UserSparepartFragment()).commit();
                break;
            case 2:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_sortpriceLowest,
                        new UserSparepartFragment()).commit();
                break;
            case 3:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_sortpriceHighest,
                        new UserSparepartFragment()).commit();
                break;
            case 4:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_sortstockLowest,
                        new UserSparepartFragment()).commit();
                break;
            case 5:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_sortstockHighest,
                        new UserSparepartFragment()).commit();
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
            case R.id.viewSparepart:
                Fragment fragment = new Fragment();
                Bundle bundle = new Bundle();
                bundle.putInt("tujuan", 1);
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new UserSparepartFragment()).commit();
                break;
            case R.id.nav_sortpriceLowest:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new UserLowestPriceSparepartFragment()).commit();
                break;
            case R.id.nav_sortpriceHighest:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new UserHighestPriceSparepartFragment()).commit();
                break;
            case R.id.nav_sortstockLowest:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new UserLowestStockSparepartFragment()).commit();
                break;
            case R.id.nav_sortstockHighest:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new UserHighestStockSparepartFragment()).commit();
                break;
            case R.id.nav_logout:
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                SharedPrefManager.getInstance(getApplicationContext()).clear();
                                Intent intent = new Intent(UserHomeActivity.this,LoginActivity.class);
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
