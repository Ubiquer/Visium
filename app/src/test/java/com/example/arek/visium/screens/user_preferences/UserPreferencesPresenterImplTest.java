package com.example.arek.visium.screens.user_preferences;

import com.example.arek.visium.model.UserPreferencesWithImage;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arek on 11/30/2017.
 */
public class UserPreferencesPresenterImplTest {


    @Test
    public void shouldPassPreferencesToView(){

        //given
//        UserPreferencesView view = new MockView();
//        UserPreferencesRepository repository = new MockPreferencesRepository();
//
//        //when
//        UserPreferencesPresenterImpl presenter = new UserPreferencesPresenterImpl();
//        presenter.onAttach(view);
//
//        //then
//        Assert.assertEquals(true, ((MockView) view).passed);


    }


    private class MockView implements UserPreferencesView{

        boolean passed;

        @Override
        public void showData(ArrayList<UserPreferencesWithImage> userPreferencesWithImages) {
            passed = true;
        }

        @Override
        public void onPreferencesDownloadFailed(String message) {

        }

        @Override
        public void onResponseFailure(String message) {

        }
    }

    private class MockPreferencesRepository implements UserPreferencesRepository {

        boolean passed;

        @Override
        public void commitPreferencesToDB(ArrayList<Integer> chosenPreferences) {


        }

        @Override
        public void commitSelectedCategoriesToRealm(List selectedPreferences) {

        }

        @Override
        public void commitAllCategoriesToRealm(List allCategories) {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public void loadPreferenceModels(OnDownLoadFinishedListener listener) {

            passed = true;

        }
    }

}