package com.example.arek.visium;

import com.example.arek.visium.model.UserLogin;
import com.example.arek.visium.rest.ApiKeys;
import com.example.arek.visium.rest.VisiumService;
import com.example.arek.visium.screens.login.LoginActivityPresenter;
import com.example.arek.visium.screens.login.LoginActivityView;
import com.example.arek.visium.screens.login.LoginRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by arek on 7/25/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginActivityPresenterTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    LoginActivityView view;
    @Mock
    LoginRepository repository;
    @Mock
    LoginRepository.OnCheckSavedPreferences onCheckSavedPreferences;
    @Mock
    LoginActivityPresenter presenter;

    //onLogin
    @Mock
    UserLogin userLogin;
    @Mock
    LoginRepository.OnLoginListener listener;

    @Before
    public void setUp() throws Exception{
        presenter.onCreate();
        verify(presenter).onCreate();

        //UserLogin setup
        userLogin = new UserLogin(ApiKeys.GET_EMAIL, ApiKeys.GET_PASSWORD);

    }

    @Captor
    private ArgumentCaptor<Boolean> preferencesStatus;

    @Captor
    private ArgumentCaptor<UserLogin> loginCredentials;

    @Captor ArgumentCaptor<Response> responseCaptor;

    @Test
    public void onSavedPreferencesStatus(){

        repository.checkSavedPreferences(onCheckSavedPreferences);
        verify(repository).checkSavedPreferences(onCheckSavedPreferences);
        onCheckSavedPreferences.savedPreferencesStatus(true);
        verify(onCheckSavedPreferences).savedPreferencesStatus(preferencesStatus.capture());
        Boolean status = preferencesStatus.getValue();

//        //then
        view.userPreferencesStatus(status);
        verify(view).userPreferencesStatus(true);
//        Assert.assertEquals(true, ((MockLoginView) view).status);
    }

    @Mock
    VisiumService serviceCaptor;
    @Mock
    Call<UserLogin> loginCallCaptor;
    @Captor
    ArgumentCaptor<Boolean> loginStatusCaptor;
    @Captor
    ArgumentCaptor<String> loginInformationCaptor;

    @Test
    public void onLoginSuccess(){

//        presenter.attemptLogin(userLogin);
//        verify(presenter).attemptLogin(loginCredentials.capture());
//        UserLogin loginCaptor = loginCredentials.getValue();
//        assertEquals(userLogin, loginCaptor);
//
//        repository.logIn(loginCaptor, listener);
//        listener.onLoginFinished(true, "loginInfo");
//        verify(listener).onLoginFinished(loginStatusCaptor.capture(), loginInformationCaptor.capture());
//        Boolean loginStatus = loginStatusCaptor.getValue();
//        String loginInformation = loginInformationCaptor.getValue();
//
//        if (loginStatus) {
//            view.onLoginSuccess();
//        }
//        verify(view).onLoginSuccess();

    }

    @Test
    public void onLoginFailed(){

        listener.onLoginFinished(false, "failed");

        verify(listener).onLoginFinished(loginStatusCaptor.capture(), loginInformationCaptor.capture());
        Boolean loginStatus = loginStatusCaptor.getValue();
        String loginInformation = loginInformationCaptor.getValue();

        if (!loginStatus){
            view.onLoginFailed(loginInformation);
        }
        verify(view).onLoginFailed(loginInformation);
    }

}