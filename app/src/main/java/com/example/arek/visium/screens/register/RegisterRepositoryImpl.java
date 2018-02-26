package com.example.arek.visium.screens.register;
import com.example.arek.visium.RealmService;
import com.example.arek.visium.UserStorage;
import com.example.arek.visium.dao.RegisterRequest;
import com.example.arek.visium.rest.VisiumService;
import javax.inject.Inject;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by arek on 11/28/2017.
 */

//TODO: REST API fails

public class RegisterRepositoryImpl implements RegisterRepository {

    private final RealmService realmService;
    private final VisiumService visiumService;
    private Call<String> userResponseCall;
    private final RegisterRequest registerRequest;

    @Inject
    public RegisterRepositoryImpl(RealmService realmService, VisiumService visiumService, RegisterRequest registerRequest) {
        this.realmService = realmService;
        this.visiumService = visiumService;
        this.registerRequest = registerRequest;
    }

    @Override
    public void register(String email, String password, OnSignUpListener onSignUpListener) {

        registerRequest.setmEmail(email);
        registerRequest.setmPassword(password);

        if (userResponseCall == null) {
            userResponseCall = visiumService.registerUser(registerRequest);
            userResponseCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        response.body();
                        onSignUpListener.onSignUpFinishedSuccess(response.message());
                    } else {
                        ResponseBody responseBody = response.errorBody();
//                        Converter<ResponseBody, ErrorResponse> converter = retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[]{});
                        //                            ErrorResponse errorResponse = converter.convert(responseBody);
                        onSignUpListener.onResponseUnsuccessful(String.valueOf(responseBody));
                    }
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {

                    onSignUpListener.onSignUpFinishedFailure(t.getMessage());

                }
            });
        }

    }
}

