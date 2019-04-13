package com.b_laundry.p3l.p3l.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.b_laundry.p3l.p3l.LoginActivity;
import com.b_laundry.p3l.p3l.R;
import com.b_laundry.p3l.p3l.models.User;
import com.b_laundry.p3l.p3l.storage.SharedPrefManager;

public class AdminHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private TextView textFullname;
    private TextView textUsername;

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


        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

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
            case R.id.nav_branch:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AdminBranchFragment()).commit();
                break;
            case R.id.nav_supplier:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AdminSupplierFragment()).commit();
                break;
            case R.id.nav_service:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AdminServiceFragment()).commit();
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
