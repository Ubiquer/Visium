package com.example.arek.visium.screens.user_preferences;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.Toast;

import com.example.arek.visium.MyAlertDialogFragment;
import com.example.arek.visium.PreferencesConfirmationDialog;
import com.example.arek.visium.VisiumApplication;
import com.example.arek.visium.dependency_injection.screens.user_preferences_di.DaggerUserPreferencesActivityComponent;
import com.example.arek.visium.dependency_injection.screens.user_preferences_di.UserPreferencesActivityComponent;
import com.example.arek.visium.dependency_injection.screens.user_preferences_di.UserPreferencesActivityModule;
import com.example.arek.visium.screens.menu.MenuActivity;
import com.example.arek.visium.MyGridLayoutManager;
import com.example.arek.visium.R;
import com.example.arek.visium.dao.UserPreferencesWithImage;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.VISIBLE;


public class UserPreferencesActivity extends Activity implements UserPreferencesView, MyAlertDialogFragment.OnSubmitListener {

    @BindView(R.id.preferences_recyclerview)
    public RecyclerView recyclerView;
    @BindView(R.id.btn_confirm_preferences)
    public Button confirmButton;
    @BindView(R.id.reveal)
    public View reveal;

    UserPreferencesRecyclerAdapter recyclerViewAdapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    private Intent menuActivityIntent;
    private static final int columnsNumber = 3;
    private ArrayList<Integer> chosenPreferences;
    int resId;
    private ValueAnimator animator;
    private ObjectAnimator objAnim;
    private int shrinkedButtonWidth = 140;
    private List selectedPreferences;

    @Inject
    UserPreferencesPresenter presenter;
    private UserPreferencesActivityComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_start);
        ButterKnife.bind(this);

        component = DaggerUserPreferencesActivityComponent.builder()
                .userPreferencesActivityModule(new UserPreferencesActivityModule(this))
                .visiumApplicationComponent(VisiumApplication.get(this).component())
                .build();

        component.injectUserPreferencesActivity(this);
        presenter.onCreate();
        initRecyclerView();
    }

    private void animateButtonWidth() {
        animator = ValueAnimator.ofInt(confirmButton.getMeasuredWidth(), shrinkedButtonWidth);
        animator.addUpdateListener(valueAnimator -> {
            int val = (Integer) valueAnimator.getAnimatedValue();
            ViewGroup.LayoutParams layoutParams = confirmButton.getLayoutParams();
            layoutParams.width = val;
            confirmButton.requestLayout();
        });
        animator.setDuration(250);
        animator.start();
    }

    private void initRecyclerView(){
        resId = R.anim.grid_layout_animation_from_bottom;
        recyclerViewLayoutManager = new MyGridLayoutManager(this, columnsNumber);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerViewAdapter = new UserPreferencesRecyclerAdapter();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @OnClick(R.id.btn_confirm_preferences)
    public void confirmPreferences(){
        showAlertDialog();
    }

    private void fadeOutText() {
        confirmButton.setText("");
        objAnim= ObjectAnimator.ofPropertyValuesHolder(confirmButton, PropertyValuesHolder.ofFloat("scaleX", 1.2f), PropertyValuesHolder.ofFloat("scaleY", 1.2f));
        objAnim.setDuration(600);
        objAnim.setRepeatCount(ObjectAnimator.INFINITE);
        objAnim.setRepeatMode(ObjectAnimator.REVERSE);
        objAnim.start();
        confirmButton.setEnabled(false);
    }

    private void revealButton(){

        confirmButton.setElevation(0f);

        reveal.setVisibility(VISIBLE);
        int cx = reveal.getWidth();
        int cy = reveal.getHeight();

        int startX = (int) (shrinkedButtonWidth / 2 + confirmButton.getX());
        int startY = (int) (shrinkedButtonWidth / 2 + confirmButton.getY());

        float finalRadius = Math.max(cx, cy) * 1.2f;

        Animator revealAnim = ViewAnimationUtils
                .createCircularReveal(reveal, startX, startY, shrinkedButtonWidth, finalRadius);

        revealAnim.setDuration(350);
        revealAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                finish();
            }
        });
        revealAnim.start();
    }

    private void delayedStartNextActivity() {
        new Handler().postDelayed(() -> startActivity(new Intent(UserPreferencesActivity.this, MenuActivity.class)), 100);
    }

    private void nextAction(){
        new Handler().postDelayed(()->{
            revealButton();
            sendPreferencesToDB();
            delayedStartNextActivity();
        }, 2000);
    }

    @Override
    public void showData(ArrayList<UserPreferencesWithImage> userPreferencesWithImages) {
        recyclerViewAdapter.setData(userPreferencesWithImages);
        recyclerView.setAdapter(recyclerViewAdapter);
        final LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, resId);
        recyclerView.setLayoutAnimation(animation);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    @Override
    public void onPreferencesDownloadFailed(String message) {
        Toast.makeText(getBaseContext(), getString(R.string.loading_preferences_failure) + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponseFailure(String message) {
        Toast.makeText(getBaseContext(), getString(R.string.api_connection_failure) + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sendPreferencesToDB() {
        if (recyclerViewAdapter.getSelectedItems() != null) {
            selectedPreferences = recyclerViewAdapter.getSelectedItems();
            chosenPreferences = recyclerViewAdapter.getPreferences();
            presenter.commitSelectedPreferencesToRealm(selectedPreferences);
            presenter.sendPreferencesToDB(chosenPreferences);
        }
    }


    private void showAlertDialog() {
        List selectedPreferences = recyclerViewAdapter.getSelectedItems();
        FragmentManager manager = getFragmentManager();
        Fragment frag = manager.findFragmentByTag("fragment_preferences");
        Bundle args = new Bundle();
        args.putStringArrayList("preferences_list", (ArrayList<String>) selectedPreferences);

        if (frag != null) {
            manager.beginTransaction().remove(frag).commit();
        }
        MyAlertDialogFragment alertDialogFragment = new MyAlertDialogFragment();
        alertDialogFragment.setArguments(args);
        alertDialogFragment.show(manager, "fragment_preferences");

        }

    @Override
    public void onSubmitPreferences(String myPreferences) {
        menuActivityIntent = new Intent(getBaseContext(), MenuActivity.class);
        animateButtonWidth();
        fadeOutText();
        nextAction();
    }
}


