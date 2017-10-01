package com.example.arek.visium.presenter;

import android.util.Log;
import android.util.Patterns;

import com.example.arek.visium.model.RegisterRequest;
import com.example.arek.visium.screens.register.SignUpActivityView;
import com.example.arek.visium.realm.UserRegistrationData;
import com.example.arek.visium.rest.ApiAdapter;
import com.example.arek.visium.rest.ApiInterface;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.realm.Realm;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by arek on 7/26/2017.
 */

public class SignUpActivityPresenter {

    private SignUpActivityView view;
    private UserRegistrationData userRegistrationRealm ;
    private RegisterRequest registerRequest;
    Realm realm;
    private ApiInterface mApiInterface;
    private static String TAG = "Response: ";

    public SignUpActivityPresenter(SignUpActivityView signUpview) {
        this.view = signUpview;
    }

    public void attemptSignUp(String email, String password, String confirmPassword){
        mApiInterface = ApiAdapter.getAPIService();

        validate(email, password, confirmPassword);

        registerRequest = new RegisterRequest(email, password, confirmPassword);

        try {
            mApiInterface.registerUser(registerRequest).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    if (response.isSuccessful()) {
                        Log.i(TAG, "post submitted to API." + response.body().toString());
                        registerToRealm(email, password, confirmPassword);
                    }
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e(TAG, "Unable to submit post to API.");
                }
            });
        }catch(Exception e){
            Log.d("Exception", e.getMessage());
        }
        view.onSignUpSuccess();
    }


    private void registerToRealm(String email, String password, String confirmPassword){
        try {
            realm.beginTransaction();
            userRegistrationRealm = realm.createObject(UserRegistrationData.class);
            userRegistrationRealm.setmEmail(email);
            userRegistrationRealm.setmPassword(password);
            userRegistrationRealm.setmConfirmPassword(confirmPassword);
            realm.commitTransaction();

        }catch (RealmPrimaryKeyConstraintException e){
            e.printStackTrace();
//            Toast.makeText(getBaseContext(), "User found on db ", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean validate(String email, String password, String confirmPassword) {

        Pattern pattern;
        Matcher matcher;

        boolean valid=true;

        final String passwordValidationPattern =  "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%&*()_+=-|<>?}~;])(?=\\S+$).{6,14}";

        pattern = Pattern.compile(passwordValidationPattern);
        matcher = pattern.matcher(password);

        if (matcher.matches() != true){
            view.incorrectPassword();
            valid = false;
        }
        if (confirmPassword.equals(password)){
            view.passwordsMatch();
        } else {
            view.passwordsDiffer();
            valid = false;
        }
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            view.invalidEmail();
            valid = false;
        } else {
            view.validEmail();
        }
        return valid;
    }

}
