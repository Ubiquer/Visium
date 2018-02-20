package com.example.arek.visium.dependency_injection.screens.login_di;

import com.example.arek.visium.RealmService;
import com.example.arek.visium.UserStorage;
import com.example.arek.visium.rest.VisiumService;
import com.example.arek.visium.screens.CredentialsValidator;
import com.example.arek.visium.screens.login.LoginActivity;
import com.example.arek.visium.screens.login.LoginActivityPresenter;
import com.example.arek.visium.screens.login.LoginActivityPresenterImpl;
import com.example.arek.visium.screens.login.LoginActivityView;
import com.example.arek.visium.screens.login.LoginRepository;
import com.example.arek.visium.screens.login.LoginRepositoryImpl;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import io.realm.Realm;

/**
 * Created by arek on 12/5/2017.
 */

@Module
public class LoginActivityModule {

    private final LoginActivity loginActivity;

    public LoginActivityModule(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }

    @Provides
    @LoginActivityScope
    public LoginActivity loginActivity(){
        return loginActivity;
    }

    @Provides
    @LoginActivityScope
    LoginActivityView view(){
        return loginActivity;
    }

    @Provides
    @LoginActivityScope
    LoginActivityPresenter presenter(LoginActivityView view, LoginRepository repository, CredentialsValidator validator){
        return new LoginActivityPresenterImpl(view, repository, validator);
    }

    @Provides
    @LoginActivityScope
    LoginRepository repository(final RealmService realmService, UserStorage userStorage, VisiumService visiumService){
        return new LoginRepositoryImpl(realmService, userStorage, visiumService);
    }

}
