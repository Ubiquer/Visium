package com.example.arek.visium.dependency_injection.screens.register_di;

import com.example.arek.visium.UserStorage;
import com.example.arek.visium.model.RegisterRequest;
import com.example.arek.visium.rest.VisiumService;
import com.example.arek.visium.screens.CredentialsValidator;
import com.example.arek.visium.screens.register.RegisterActivity;
import com.example.arek.visium.screens.register.RegisterActivityPresenter;
import com.example.arek.visium.screens.register.RegisterActivityPresenterImpl;
import com.example.arek.visium.screens.register.RegisterActivityView;
import com.example.arek.visium.screens.register.RegisterRepository;
import com.example.arek.visium.screens.register.RegisterRepositoryImpl;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by arek on 12/12/2017.
 */
@Module
public class RegisterActivityModule {

    private final RegisterActivity registerActivity;

    public RegisterActivityModule(RegisterActivity registerActivity) {
        this.registerActivity = registerActivity;
    }

    @Provides
    @RegisterActivityScope
    RegisterActivity registerActivity(){
        return registerActivity;
    }

    @Provides
    @RegisterActivityScope
    RegisterActivityView view(){
        return registerActivity;
    }

    @Provides
    @RegisterActivityScope
    RegisterRequest registerRequest(){
        return new RegisterRequest();
    }

    @Provides
    @RegisterActivityScope
    RegisterActivityPresenter registerActivityPresenter(RegisterActivityView view, RegisterRepository repository, CredentialsValidator validator){
        return new RegisterActivityPresenterImpl(view, repository, validator);
    }

    @Provides
    @RegisterActivityScope
    RegisterRepository repository(UserStorage userStorage, VisiumService visiumService, RegisterRequest registerRequest){
        return new RegisterRepositoryImpl(userStorage, visiumService, registerRequest);
    }


}
