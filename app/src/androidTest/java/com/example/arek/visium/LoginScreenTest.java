package com.example.arek.visium;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.arek.visium.screens.login.LoginActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by arek on 11/29/2017.
 */

@RunWith(AndroidJUnit4.class)
public class LoginScreenTest {

    @Rule
    public ActivityTestRule<LoginActivity> LoginActivityTestRule =
            new ActivityTestRule<LoginActivity>(LoginActivity.class);

    @Test
    public void clickLoginButton() throws Exception {
        onView(withId(R.id.btn_login))
                .perform(click());
//        onView(withId(R.id.input_email))
//                .ch
    }
}
