package com.example.arek.visium.screens.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arek.visium.BuildConfig;
import com.example.arek.visium.R;
import com.example.arek.visium.UserPreferencesActivity;
import com.example.arek.visium.VisiumApplication;
import com.example.arek.visium.rest.IntentKeys;
import com.example.arek.visium.screens.register.RegisterActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginActivityView {

    private static final String MANAGER = LoginActivity.class.getSimpleName();
    @BindView(R.id.btn_login)
    Button loginButton;
    @BindView(R.id.input_email)
    EditText emailText;
    @BindView(R.id.input_password)
    EditText passwordText;
    @BindView(R.id.register_button)
    Button registerButton;
    @BindView(R.id.emailTextInputLayout)
    TextInputLayout emailTextInputLayout;
    @BindView(R.id.passwordTextInputLayout)
    TextInputLayout passwordTextInputLayout;

    private ProgressDialog progressDialog;
    private String email, password, emailStored, passwordStored, token;
    private Intent userPrefActivity;
    private static final String TAG = "HOME";
    private Intent signUpIntent;

    private LoginManager loginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        loginManager = ((VisiumApplication) getApplication()).getLoginManager();

        Log.d(MANAGER, "LoginManager: " + loginManager);

        if (BuildConfig.DEBUG){
            emailText.setText(IntentKeys.GET_EMAIL);
            passwordText.setText(IntentKeys.GET_PASSWORD);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        loginManager.onAttach(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        loginManager.onStop();
    }

    @OnClick(R.id.register_button)
    public void navigateToSignUpActivity() {

        signUpIntent = new Intent(getBaseContext(), RegisterActivity.class);
        startActivity(signUpIntent);
        finish();

    }
    //log in locally
//    private boolean checkUser(String email, String password){
//
//        RealmResults<RegisterRequest> realmRegistrationObjects = realm.where(RegisterRequest.class).findAll();
//
//        for(RegisterRequest userRegistration : realmRegistrationObjects){
//
//            if (email.equals(userRegistration.getmEmail()) && password.equals(userRegistration.getmPassword())){
//                Log.e(TAG, userRegistration.getmEmail());
//            }
//        }
//
//        Log.e(TAG, String.valueOf(realm.where(RegisterRequest.class).contains("Email", email)));
//        return false;
//
//    }

    @OnClick(R.id.btn_login)
    public void login() {
        validate();
    }

    @Override
    public boolean validate() {

        Pattern pattern;
        Matcher matcher;

        boolean hasErrors = false;

        email = emailText.getText().toString();
        password = passwordText.getText().toString();
        final String passwordValidationPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%&*()_+=-|<>?}~;])(?=\\S+$).{6,14}";
        pattern = Pattern.compile(passwordValidationPattern);
        matcher = pattern.matcher(password);

        if (matcher.matches() != true) {
            passwordTextInputLayout.setError("Enter a valid password. The password should consist of 6-14 characters, with at least one big letter, one special character and one number");
            hasErrors = true;
        }
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailTextInputLayout.setError("Enter a valid email address");
            hasErrors = true;
        } else {
            emailTextInputLayout.setError(null);
        }

        if (!hasErrors) {
            loginManager.attemptLogin(email, password);
        }

        return hasErrors;
    }

    @Override
    public void showProgress(boolean progress) {
        loginButton.setEnabled(!progress);
    }

    @Override
    public void onLoginFailed(String errorBody) {
        progressDialog.dismiss();
        Toast.makeText(getBaseContext(), errorBody, Toast.LENGTH_LONG).show();
        loginButton.setEnabled(true);
    }

    @Override
    public void onLoginSuccess() {
        userPrefActivity = new Intent(getApplicationContext(), UserPreferencesActivity.class);
        startActivity(userPrefActivity);
        progressDialog.dismiss();
        finish();
    }


    public void showProgressDialog() {

        progressDialog = ProgressDialog.show(this, "Authenticating...", null);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();
    }
}
