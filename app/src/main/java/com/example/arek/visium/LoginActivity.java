package com.example.arek.visium;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arek.visium.model.TokenAuth;
import com.example.arek.visium.model.UserLogin;
import com.example.arek.visium.rest.ApiAdapter;
import com.example.arek.visium.rest.ApiInterface;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";
    public static final int REQUEST_SIGNUP = 0;

    @BindView(R.id.btn_login)
    Button loginButton;
    @BindView(R.id.input_email)
    EditText emailText;
    @BindView(R.id.input_password)
    EditText passwordText;
    @BindView(R.id.sing_up_text)
    TextView singUpTextView;
    @BindView(R.id.btn_get_secret)
    Button secretButton;

    public static final String DEFAULT = "N/A";
    private String email;
    private String password;
    private String emailStored;
    private String passwordStored;
    private SharedPreferences userSettings;
    private Intent signInActivity;
    private Intent userPrefActivity;
    private ProgressDialog progressDialog;
    private ApiInterface mApiInterface;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mApiInterface = ApiAdapter.getAPIService();

//        userSettings = context.getSharedPreferences("MyData", MODE_PRIVATE);

//        if (userSettings != null){
//
//            email = userSettings.getString("email","");
//            password = userSettings.getString("password", "");
//            emailText.setText(email);
//            passwordText.setText(password);
//
//        }

//        openSingInActivity();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        secretButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSecret();
            }
        });
//        getSecret();
//        if (shared)

//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                login();
//            }
//        });

//        singUpTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                openSingInActivity();
//            }
//        });

    }
    private static String token;

//    @OnClick(R.id.btn_login)
    public void login(){
        Log.d(TAG, "Log in");
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        final UserLogin userLogin = new UserLogin("w@w.pl", "Mistrz123;");
        mApiInterface.loginUser(userLogin).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                    token ="Bearer " + response.body().toString();


                }else {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Incorrect login ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "error: ", Toast.LENGTH_SHORT).show();
            }

//            @Override
//            public void onFailure(Call<TokenAuth> call, Throwable t) {
//                progressDialog.dismiss();
//                Toast.makeText(LoginActivity.this, "error: ", Toast.LENGTH_SHORT).show();
//
//                t.getMessage();
//
//            }
        });

//        if (!validate()) {
//
//            onLoginFailed();
//            progressDialog.dismiss();
//            return;
//
//        }else{
//
//            onLoginSuccess();
//
//        }
    }


//    @OnClick(R.id.btn_get_secret)
    public void getSecret(){
        mApiInterface.validateToken(token).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    Toast.makeText(LoginActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                    token = response.toString();
                    Log.d("response", token);

                }else {
                    Toast.makeText(LoginActivity.this, "Incorrect login !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.sing_up_text)
    public void openSingInActivity(){

        signInActivity = new Intent(getApplicationContext(),SignUpActivity.class);
        startActivityForResult(signInActivity, REQUEST_SIGNUP);

    }

    private void onLoginFailed(){

        Toast.makeText(getBaseContext(), "Failed logging in", Toast.LENGTH_LONG).show();
        loginButton.setEnabled(true);
    }

    private void onLoginSuccess(){

        userPrefActivity = new Intent(getApplicationContext(), UserPreferencesActivity.class);
        startActivity(userPrefActivity);

    }

    private boolean validate(){

        boolean valid = true;

        return valid;
    }
}
