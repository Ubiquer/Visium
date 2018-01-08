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
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.widget.RxTextView;

import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;


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
    RegisterActivityPresenter presenter;

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
        presenter.onCreate();
        signInButton.setEnabled(false);

        confirmPasswordText.setTransformationMethod(new PasswordTransformationMethod());

        if (BuildConfig.DEBUG){
            emailText.setText(ApiKeys.GET_EMAIL);
            passwordText.setText(ApiKeys.GET_PASSWORD);
            confirmPasswordText.setText(ApiKeys.GET_PASSWORD);
        }
        haveAccountTextView.setOnClickListener(v -> {
            loginActivity = new Intent(getBaseContext(), LoginActivity.class);
            startActivity(loginActivity);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @OnClick(R.id.btn_signUp)
    public void signUp() {
        email = emailText.getText().toString();
        password = passwordText.getText().toString();
        confirmPassword = passwordText.getText().toString();
        presenter.onRegister(email, password);
    }

    @Override
    public void onSingUpFailed(String message) {

        Toast.makeText(this, getString(R.string.sign_up_failed_message) + message, Toast.LENGTH_LONG);

    }

    @Override
    public Observable<CharSequence> passwordObservable() {
        return RxTextView.textChanges(passwordText);
    }

    @Override
    public Observable<CharSequence> confirmPasswordObservable() {
        return RxTextView.textChanges(confirmPasswordText);
    }

    @Override
    public Observable<CharSequence> emailObservable() {
        return RxTextView.textChanges(emailText);
    }

    @Override
    public void onPasswordsDiffer() {
        passwordText.setError(getString(R.string.on_passwords_differ));
    }

    @Override
    public void onPasswordNotValid() {
        passwordText.setError(getString(R.string.incorrect_password));
    }

    @Override
    public void onConfirmationPasswordNotValid() {
        passwordText.setError(getString(R.string.incorrect_password));
    }

    @Override
    public void onPasswordsMatch() {
        passwordText.setError(null);
        confirmPasswordText.setError(null);
    }

    @Override
    public void onSignUpSuccess() {
        signInButton.setEnabled(false);
        Toast.makeText(getBaseContext(), R.string.token_received, Toast.LENGTH_LONG).show();
        userPrefActivity = new Intent(getApplicationContext(), UserPreferencesActivity.class);
        startActivity(userPrefActivity);
    }

    @Override
    public void onEmailNotValid() {
        emailText.setError(getString(R.string.incorrect_email));
    }

    @Override
    public void enableSignUpButton(boolean enable) {
        signInButton.setEnabled(enable);
    }

}
