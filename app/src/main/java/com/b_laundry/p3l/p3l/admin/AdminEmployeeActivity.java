package com.b_laundry.p3l.p3l.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.b_laundry.p3l.p3l.R;
import com.squareup.picasso.Picasso;

public class AdminEmployeeActivity extends AppCompatActivity implements View.OnClickListener {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_employee);



        Intent intent = getIntent();
        //Log.d("intentnya", String.valueOf(intent.getStringExtra("id")));
        if(!String.valueOf(intent.getStringExtra("id")).equals("null")) { //edit

        }
        else
        {

        }
    }
    @Override
    public void onClick(View v) {

    }
}
