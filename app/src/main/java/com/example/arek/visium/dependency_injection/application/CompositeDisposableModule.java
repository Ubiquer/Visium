package com.example.arek.visium.dependency_injection.application;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by arek on 1/8/2018.
 */
@Module
public class CompositeDisposableModule {

    @Provides
    @VisiumApplicationScope
    CompositeDisposable compositeDisposable(){

        CompositeDisposable compositeDisposable = new CompositeDisposable();
        return compositeDisposable;
    }

}
