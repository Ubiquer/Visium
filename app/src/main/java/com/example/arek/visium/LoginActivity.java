package com.example.arek.visium;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import com.example.arek.visium.model.UserRegistration;
import com.example.arek.visium.realm.Token;
import com.example.arek.visium.rest.ApiAdapter;
import com.example.arek.visium.rest.ApiInterface;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements LoginActivityView {

    @BindView(R.id.btn_login)
    Button loginButton;
    @BindView(R.id.input_email)
    EditText emailText;
    @BindView(R.id.input_password)
    EditText passwordText;
    @BindView(R.id.sing_up_text)
    TextView singUpTextView;

    private ProgressDialog progressDialog;
    private String email, password, emailStored, passwordStored, token;
    private Intent userPrefActivity;
    private static final String TAG = "HOME";
    private LoginActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        if (BuildConfig.DEBUG){
            emailText.setText("admin@visium.io");
            passwordText.setText("Qwe[]123");
        }

        presenter = new LoginActivityPresenter(this);
    }


    //log in locally
//    private boolean checkUser(String email, String password){
//
//        RealmResults<UserRegistration> realmRegistrationObjects = realm.where(UserRegistration.class).findAll();
//
//        for(UserRegistration userRegistration : realmRegistrationObjects){
//
//            if (email.equals(userRegistration.getmEmail()) && password.equals(userRegistration.getmPassword())){
//                Log.e(TAG, userRegistration.getmEmail());
//            }
//        }
//
//        Log.e(TAG, String.valueOf(realm.where(UserRegistration.class).contains("Email", email)));
//        return false;
//
//    }

    @OnClick(R.id.btn_login)
    public void login(){

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();
        email = emailText.getText().toString();
        password = passwordText.getText().toString();
        presenter.attemptLogin(email, password);

    }

    @Override
    public void onLoginFailed() {
        progressDialog.dismiss();
        Toast.makeText(getBaseContext(), "Failed logging in", Toast.LENGTH_LONG).show();
        loginButton.setEnabled(true);
    }

    @Override
    public void onLoginSuccess() {
        userPrefActivity = new Intent(getApplicationContext(), UserPreferencesActivity.class);
        startActivity(userPrefActivity);
    }

//    public boolean validate(){
//
//        boolean valid = true;
//
//        return valid;
//    }


}
