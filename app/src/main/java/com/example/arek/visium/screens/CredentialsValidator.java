package com.example.arek.visium.screens;

import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by arek on 1/7/2018.
 */

public class CredentialsValidator {

    private String password;
    private String email;
    private String confirmEmail;
    private Pattern pattern;
    private Matcher matcher;
    boolean passwordValid;
    boolean emailValid;

    public CredentialsValidator() {
    }

    public CredentialsValidator(String password, String email, String confirmEmail) {
        this.password = password;
        this.email = email;
        this.confirmEmail = confirmEmail;
    }

    public boolean validPassword(CharSequence password) {

        final String passwordValidationPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%&*()_+=-|<>?}~;])(?=\\S+$).{6,14}";
        pattern = Pattern.compile(passwordValidationPattern);
        matcher = pattern.matcher(password.toString());

        if (!matcher.matches()){
            passwordValid = false;
        }else{
            passwordValid = true;
        }
        return passwordValid;
    }

    public boolean validEmail(CharSequence charSequenceEmail){

        email = charSequenceEmail.toString();

        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailValid = false;
        }else {
            emailValid = true;
        }

        return emailValid;
    }

}
