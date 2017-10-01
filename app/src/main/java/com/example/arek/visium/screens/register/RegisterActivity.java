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
import com.example.arek.visium.presenter.SignUpActivityPresenter;
import com.example.arek.visium.rest.IntentKeys;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SignUpActivity extends AppCompatActivity implements SignUpActivityView {
    
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
    private SignUpActivityPresenter signUpActivityPresenter;
    private Intent userPrefActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

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

    @OnClick(R.id.btn_signUp)
    public void signUp() {
//        if(validate()){
        email = emailText.getText().toString();
        password = passwordText.getText().toString();
        confirmPassword = passwordText.getText().toString();
        signUpActivityPresenter.attemptSignUp(email, password, confirmPassword);
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
        confirmPasswordText.setError("Confirmation password is not the same as password");
    }

    @Override
    public void invalidEmail() {
        emailText.setError("Enter a valid email address");
    }

    @Override
    public void validEmail() {
        emailText.setError(null);
    }

    @Override
    public void incorrectPassword() {
        passwordText.setError("Enter a valid password. The password should consist of 6-14 characters, with at least one big letter, one special character and one number");
    }

    @Override
    public void onSignUpSuccess() {
        Toast.makeText(getBaseContext(), "token received", Toast.LENGTH_LONG).show();
        userPrefActivity = new Intent(getApplicationContext(), UserPreferencesActivity.class);
        startActivity(userPrefActivity);
    }


}
