package com.example.arek.visium;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arek.visium.model.UserRegistration;
import com.example.arek.visium.rest.ApiAdapter;
import com.example.arek.visium.rest.ApiInterface;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class SignUpActivity extends AppCompatActivity {

    private static int TAG_2 = 0;

    private static String TAG = "Response ";

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
    private ApiInterface mApiInterface;
//    private UserRegistration userRegistration;
    Context context;
    Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

//        Realm.init(context);
//        realm = Realm.getDefaultInstance();

        mApiInterface = ApiAdapter.getAPIService();

        ButterKnife.bind(this);

        haveAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sign();
            }
        });

    }


    public void sign() {

        SharedPreferences userSettings = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userSettings.edit();

//        if(validate() == true){

//            email = emailText.getText().toString().trim();
//            password = passwordText.getText().toString().trim();
//            confirmPassword = confirmPasswordText.getText().toString().trim();

            email= "elo2@elo2.pl";
            password= "Mistrz123;";
            confirmPassword= "Mistrz123;";
//            try {
//
//                realm.beginTransaction();
//                userRegistration = realm.createObject(UserRegistration.class);
//                userRegistration.setmEmail(email);
//                userRegistration.setmPassword(password);
//                userRegistration.setmConfirmPassword(confirmPassword);
//                realm.commitTransaction();
//
//            }catch (RealmPrimaryKeyConstraintException e){
//
//                e.printStackTrace();
//                Toast.makeText(getBaseContext(), "User found on db ", Toast.LENGTH_SHORT).show();
//            }
//            progressDialog.setMessage("Registering User...");
//            progressDialog.show();

            editor.putString("email", email);
            editor.putString("password", password);
            editor.apply();
            final UserRegistration userRegistration = new UserRegistration(email, password, confirmPassword);

        try {
            mApiInterface.registerUser(userRegistration).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    if (response.isSuccessful()) {

                        Log.i(TAG, "post submitted to API." + response.body().toString());
                    }
                    Toast.makeText(getBaseContext(), "token received", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e(TAG, "Unable to submit post to API.");
                }
            });
        }catch(Exception e){
            Log.d("Exception", e.getMessage());
        }
            Toast.makeText(this, "correct", Toast.LENGTH_LONG).show();
            onSignUpSuccess();
        }

//        if(!validate()){
//            onSignUpFailed();
//            return;
//        }
//    }

//    public void registerUser(UserRegistration userRegistration){
//
//    this.userRegistration = userRegistration;
//
//        mApiInterface.registerUser(userRegistration).enqueue(new Callback<UserRegistration>() {
//            @Override
//            public void onResponse(Call<UserRegistration> call, Response<UserRegistration> response) {
//                Log.i(TAG, " API." + response.body().toString());
//            }
//
//            @Override
//            public void onFailure(Call<UserRegistration> call, Throwable t) {
//                Log.e(TAG, "Unable to submit post to API.");
//            }
//        });
//
//    }

    private boolean validate() {

        Pattern pattern;
        Matcher matcher;

        boolean valid=true;

        email = emailText.getText().toString();
        password = passwordText.getText().toString();
        confirmPassword = confirmPasswordText.getText().toString();

        final String passwordValidationPattern =  "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%&*()_+=-|<>?}~;])(?=\\S+$).{6,14}";

        pattern = Pattern.compile(passwordValidationPattern);
        matcher = pattern.matcher(password);

        if (matcher.matches() != true){

            passwordText.setError("Enter a valid password. The password should consist of 6-14 characters, with at least one big letter, one special character and one number");
            valid = false;
        }
        if (confirmPassword.equals(password)){

            confirmPasswordText.setError(null);

        } else {
            confirmPasswordText.setError("Confirmation password is not the same as password");
            valid = false;
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            emailText.setError("Enter a valid email address");
            valid = false;

        } else {
            emailText.setError(null);
        }

        return valid;
    }

    public void onSignUpSuccess(){

        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignUpFailed(){

        Toast.makeText(getBaseContext(), "Sign up failed", Toast.LENGTH_LONG).show();

    }

}
