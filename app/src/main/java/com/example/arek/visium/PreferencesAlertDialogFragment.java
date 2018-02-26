package com.example.arek.visium;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import java.util.ArrayList;

/**
 * Created by arek on 2/21/2018.
 */

public class PreferencesAlertDialogFragment extends DialogFragment {

    OnSubmitListener listener;
    private String myPreferences="";
    private ArrayList<String> preferencesList;
    private String title;
    private String message;

    public interface OnSubmitListener{
        void onSubmitPreferences(String myPreferences);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle args = getArguments();
        preferencesList = args.getStringArrayList("preferences_list");

        getPreferences();
        trimMessage();
        specifyMessage();

        return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Submit", (dialog, which) -> {
                    listener = (OnSubmitListener) getActivity();
                    listener.onSubmitPreferences(myPreferences);
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                }).create();
    }

    private void getPreferences(){
            for (int i=0; i<preferencesList.size(); i++) {
                myPreferences += preferencesList.get(i) + ", ";
            }
    }

    private void specifyMessage(){
        if (preferencesList.isEmpty()){
            title = "No preferences chosen";
            message = "Do you still want to continue?";
        }else {
            title = "Chosen preferences:";
            message = myPreferences;
        }
    }
    private void trimMessage(){
        if (myPreferences.length()>2){
            myPreferences = myPreferences.substring(0, myPreferences.length()-2);
        }
    }

}