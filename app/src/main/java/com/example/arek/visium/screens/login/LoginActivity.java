package com.example.arek.visium.screens.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.arek.visium.BuildConfig;
import com.example.arek.visium.dependency_injection.screens.login_di.DaggerLoginActivityComponent;
import com.example.arek.visium.dependency_injection.screens.login_di.LoginActivityComponent;
import com.example.arek.visium.dependency_injection.screens.login_di.LoginActivityModule;
import com.example.arek.visium.screens.menu.MenuActivity;
import com.example.arek.visium.R;
import com.example.arek.visium.rest.ApiKeys;
import com.example.arek.visium.screens.user_preferences.UserPreferencesActivity;
import com.example.arek.visium.VisiumApplication;
import com.example.arek.visium.screens.register.RegisterActivity;

import javax.inject.Inject;

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
    private String email, password;
    private Intent userPrefActivity;
    private static final String TAG = "HOME";
    private Intent signUpIntent;
    private Intent menuActivity;
    private boolean preferencesDefined;
    private LoginActivityComponent loginActivityComponent;

    @Inject
    LoginActivityPresenter presenter;

    @Inject
    LoginActivityView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        passwordText.setTransformationMethod(new PasswordTransformationMethod());

        loginActivityComponent = DaggerLoginActivityComponent.builder()
                .loginActivityModule(new LoginActivityModule(this))
                .visiumApplicationComponent(VisiumApplication.get(this).component())
                .build();
        loginActivityComponent.injectLoginActivity(this);

        if (BuildConfig.DEBUG){
            emailText.setText(ApiKeys.GET_EMAIL);
            passwordText.setText(ApiKeys.GET_PASSWORD);
        }
        presenter.onCreate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @OnClick(R.id.register_button)
    public void navigateToSignUpActivity() {
        signUpIntent = new Intent(getBaseContext(), RegisterActivity.class);
        startActivity(signUpIntent);
        finish();
    }

    @OnClick(R.id.btn_login)
    public void login() {
        email = emailText.getText().toString();
        password = passwordText.getText().toString();
        presenter.validateLoginData(email, password);
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

        if (preferencesDefined){
            menuActivity = new Intent(getApplicationContext(), MenuActivity.class);
            startActivity(menuActivity);
        }else{
            userPrefActivity = new Intent(getApplicationContext(), UserPreferencesActivity.class);
            startActivity(userPrefActivity);
        }
        progressDialog.dismiss();
        finish();
    }

    @Override
    public void showProgressDialog() {
        progressDialog = ProgressDialog.show(this, getString(R.string.authentication_progress), null);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }

    @Override
    public void setEmailError(boolean hasError) {
        if(hasError){
            emailTextInputLayout.setError(getString(R.string.email_error));
        }else {
            emailTextInputLayout.setError(null);
        }
    }

    @Override
    public void setPasswordError(boolean hasError) {
        if (hasError){
            passwordTextInputLayout.setError(getString(R.string.password_error));
        }else {
            passwordTextInputLayout.setError(null);
        }
    }

    @Override
    public void userPreferencesStatus(boolean status) {
        this.preferencesDefined = status;
    }
}
