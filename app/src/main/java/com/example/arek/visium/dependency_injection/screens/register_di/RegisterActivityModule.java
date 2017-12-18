package com.example.arek.visium.dependency_injection.screens.register_di;

import com.example.arek.visium.UserStorage;
import com.example.arek.visium.rest.VisiumService;
import com.example.arek.visium.screens.rankings.Pictures.RankingImagesRepositoryImpl;
import com.example.arek.visium.screens.register.RegisterActivity;
import com.example.arek.visium.screens.register.RegisterActivityPresenter;
import com.example.arek.visium.screens.register.RegisterRepository;
import com.example.arek.visium.screens.register.RegisterRepositoryImpl;

import dagger.Module;
import dagger.Provides;

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
    RegisterActivityPresenter registerActivityPresenter(RegisterRepository repository){
        return new RegisterActivityPresenter(repository);
    }

    @Provides
    @RegisterActivityScope
    RegisterRepository repository(UserStorage userStorage, VisiumService visiumService){
        return new RegisterRepositoryImpl(userStorage, visiumService);
    }


}
