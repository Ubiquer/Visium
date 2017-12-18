package com.example.arek.visium.screens.register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arek.visium.BuildConfig;
import com.example.arek.visium.R;
import com.example.arek.visium.dependency_injection.screens.register_di.DaggerRegisterActivityComponent;
import com.example.arek.visium.dependency_injection.screens.register_di.RegisterActivityComponent;
import com.example.arek.visium.dependency_injection.screens.register_di.RegisterActivityModule;
import com.example.arek.visium.rest.ApiKeys;
import com.example.arek.visium.screens.login.LoginActivity;
import com.example.arek.visium.screens.user_preferences.UserPreferencesActivity;
import com.example.arek.visium.VisiumApplication;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RegisterActivity extends AppCompatActivity implements RegisterActivityView {
    
    @BindView(R.id.btn_signUp)
    Button signInButton;
    @BindView(R.id.input_email)
    EditText emailText;
    @BindView(R.id.input_name)
    EditText nameText;
    @BindView(R.id.input_lastName)
    EditText lastNameText;
    @BindView(R.id.input_password)
    EditText passwordText;
    @BindView(R.id.input_password2)
    EditText confirmPasswordText;
    @BindView(R.id.have_an_account_text_view)
    TextView haveAccountTextView;

    private ProgressDialog progressDialog;
    private String email, password, confirmPassword;
    private Intent loginActivity;
    private final int REQUEST_LOGIN = 0;
    private Intent userPrefActivity;

    @Inject
    RegisterActivityPresenter registerActivityPresenter;

    private RegisterActivityComponent registerActivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        registerActivityComponent = DaggerRegisterActivityComponent.builder()
                .registerActivityModule(new RegisterActivityModule(this))
                .visiumApplicationComponent(VisiumApplication.get(this).component())
                .build();
        registerActivityComponent.injectRegisterActivity(this);

        confirmPasswordText.setTransformationMethod(new PasswordTransformationMethod());;

        if (BuildConfig.DEBUG){
            emailText.setText(ApiKeys.GET_EMAIL);
            passwordText.setText(ApiKeys.GET_PASSWORD);
            confirmPasswordText.setText(ApiKeys.GET_PASSWORD);
        }
        haveAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginActivity = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(loginActivity);
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        registerActivityPresenter.onAttach(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        registerActivityPresenter.onStop();
    }

    @OnClick(R.id.btn_signUp)
    public void signUp() {
//        if(validate()){
        email = emailText.getText().toString();
        password = passwordText.getText().toString();
        confirmPassword = passwordText.getText().toString();
        registerActivityPresenter.register(email, password);
    }

    @Override
    public void onSingUpFailed(String message) {

        Toast.makeText(this, getString(R.string.sign_up_failed_message) + message, Toast.LENGTH_LONG);

    }

    @Override
    public void onPasswordsDiffer() {
        passwordText.setError(getString(R.string.incorrect_password));
    }

    @Override
    public void onEmailsDiffer() {
        confirmPasswordText.setError(getString(R.string.passwords_differ));
    }

    @Override
    public void onPasswordsMatch() {
        confirmPasswordText.setError(null);
    }

    @Override
    public void onEmailsMatch() {
        emailText.setError(null);
    }

    @Override
    public void onSignUpSuccess() {
        signInButton.setEnabled(false);
        Toast.makeText(getBaseContext(), R.string.token_received, Toast.LENGTH_LONG).show();
        userPrefActivity = new Intent(getApplicationContext(), UserPreferencesActivity.class);
        startActivity(userPrefActivity);
    }


}
