package com.example.arek.visium;

import com.example.arek.visium.rest.VisiumService;
import com.example.arek.visium.screens.login.LoginActivityPresenter;
import com.example.arek.visium.screens.login.LoginActivityPresenterImpl;
import com.example.arek.visium.screens.login.LoginActivityView;
import com.example.arek.visium.screens.login.LoginRepository;
import com.example.arek.visium.screens.login.LoginRepositoryImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import io.realm.Realm;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Created by arek on 7/25/2017.
 */
public class LoginActivityPresenterImplTest {

    private LoginActivityPresenter loginActivityPresenter;
    private LoginActivityView loginActivityView;
    private LoginRepository loginRepository;
    private UserStorage userStorage;
    private Realm realm;
    private VisiumService visiumService;


    @Test
    public void shouldPass(){
        Assert.assertEquals(1, 1);
    }

    @Before
    public void setUp() throws Exception{

        loginActivityView = Mockito.mock(LoginActivityView.class);
        loginRepository = Mockito.mock(LoginRepository.class);
        userStorage = Mockito.mock(UserStorage.class);
        realm = Mockito.mock(Realm.class);
        visiumService = Mockito.mock(VisiumService.class);

        loginActivityPresenter = new LoginActivityPresenterImpl(loginActivityView, loginRepository);
        ArgumentCaptor valueCapture = ArgumentCaptor.forClass(Boolean.class);
        doNothing().when(loginActivityView).userPreferencesStatus((Boolean) valueCapture.capture());
        loginActivityView.userPreferencesStatus(false);

        assertEquals(true, valueCapture.getValue());
//        Mockito.when(loginActivityView.onLoginSuccess()).thenReturn()
    }



    @Test
    public void onSavedPreferencesStatus(){

//        Mockito.when()

    }



}