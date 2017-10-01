package com.example.arek.visium.screens.register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arek.visium.BuildConfig;
import com.example.arek.visium.R;
import com.example.arek.visium.UserPreferencesActivity;
import com.example.arek.visium.VisiumApplication;
import com.example.arek.visium.presenter.SignUpActivityPresenter;
import com.example.arek.visium.rest.IntentKeys;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RegisterActivity extends AppCompatActivity implements SignUpActivityView {
    
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
    private Intent loginActivityIntent;
    private final int REQUEST_LOGIN = 0;
    private Intent userPrefActivity;
    private RegisterManager registerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        registerManager = ((VisiumApplication)getApplication()).getRegisterManager();

        if (BuildConfig.DEBUG){
            emailText.setText(IntentKeys.GET_EMAIL);
            passwordText.setText(IntentKeys.GET_PASSWORD);
            confirmPasswordText.setText(IntentKeys.GET_PASSWORD);
        }

        haveAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        registerManager.onAttach(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        registerManager.onStop();
    }

    @OnClick(R.id.btn_signUp)
    public void signUp() {
//        if(validate()){
        email = emailText.getText().toString();
        password = passwordText.getText().toString();
        confirmPassword = passwordText.getText().toString();
        registerManager.register(email, password);
    }

    @Override
    public void onSingUpFailed() {
    }

    @Override
    public void passwordsMatch() {
        confirmPasswordText.setError(null);
    }

    @Override
    public void passwordsDiffer() {
        confirmPasswordText.setError(getString(R.string.passwords_differ));
    }

    @Override
    public void invalidEmail() {
        emailText.setError(getString(R.string.enter_valid_email));
    }

    @Override
    public void validEmail() {
        emailText.setError(null);
    }

    @Override
    public void incorrectPassword() {
        passwordText.setError(getString(R.string.incorrect_password));
    }

    @Override
    public void onSignUpSuccess() {
        signInButton.setEnabled(false);
        Toast.makeText(getBaseContext(), R.string.token_received, Toast.LENGTH_LONG).show();
        userPrefActivity = new Intent(getApplicationContext(), UserPreferencesActivity.class);
        startActivity(userPrefActivity);
    }


}
