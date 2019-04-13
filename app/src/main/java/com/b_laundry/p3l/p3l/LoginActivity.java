package com.b_laundry.p3l.p3l;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.b_laundry.p3l.p3l.admin.AdminHomeActivity;
import com.b_laundry.p3l.p3l.models.LoginResponse;
import com.b_laundry.p3l.p3l.api.RetrofitClient;
import com.b_laundry.p3l.p3l.storage.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextUsername;
    private EditText editTextPassword;
    //private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //getActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.txtUsername);
        editTextPassword = findViewById(R.id.txtPassword);
        getSupportActionBar().hide();
        findViewById(R.id.buttonLogin).setOnClickListener(this);
    }

    public void userLogin()
    {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(username.isEmpty()) {
            editTextUsername.setError("Username tidak boleh kosong");
            editTextUsername.requestFocus();
            return;
        }

        if(password.isEmpty()) {
            editTextPassword.setError("Password tidak boleh kosong");
            editTextPassword.requestFocus();
            return;
        }

        Call<LoginResponse> call = RetrofitClient
                .getInstance().getApi().loginAndroid(username, password);

        //Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_LONG).show();
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                int Responsecode = response.code();
//
//               Log.d("responsecode", String.valueOf(Responsecode));
               if(Responsecode != 200)
               {
                   try {
                       JSONObject jObjError = new JSONObject(response.errorBody().string());
                       Log.d("login", String.valueOf(jObjError));
                       Toast.makeText(LoginActivity.this, jObjError.getString("message"), Toast.LENGTH_SHORT).show();
                   } catch (JSONException e) {
                       e.printStackTrace();
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
                   //Toast.makeText(LoginActivity.this, "Email / Password Salah", Toast.LENGTH_LONG).show();
               } else {
                   Log.d("response", response.body().toString());
                   SharedPrefManager.getInstance(LoginActivity.this)
                           .saveUser(loginResponse.getData());
                   Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_LONG).show();

                   Intent intent = new Intent(LoginActivity.this, AdminHomeActivity.class);
                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                   startActivity(intent);
                    //Log.d("shared",SharedPrefManager.getInstance(LoginActivity.this).getUser().getNama().toString());
                }
        }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("failed", String.valueOf(t));
            }


        });


    }

    @Override
    public void onClick(View v){
        switch(v.getId())
        {
            case R.id.buttonLogin:
                userLogin();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (SharedPrefManager.getInstance(this).isLoggedIn())
        {
            if(SharedPrefManager.getInstance(this).getUser().getRole().equals("Administrator"))
            {
                Intent intent = new Intent(LoginActivity.this,AdminHomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
            else
            {
                Intent intent = new Intent(LoginActivity.this,AdminHomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }
    }
}
