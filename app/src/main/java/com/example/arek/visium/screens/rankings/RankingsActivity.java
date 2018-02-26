package com.example.arek.visium.screens.rankings;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.arek.visium.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RankingsActivity extends AppCompatActivity {

    private TabLayout tablayout;
    private ViewPager viewpager;
    private RankingViewPagerAdapter viewPagerAdapter;
    private static final Integer[] tabIcons = {R.drawable.ic_camera,R.drawable.crown};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setContentView(R.layout.activity_rankings);
        initViewPager();
        tabWithIcon();
    }

    private void initViewPager(){
        viewpager = findViewById(R.id.viewPager);
        viewPagerAdapter = new RankingViewPagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(viewPagerAdapter);
        tablayout = findViewById(R.id.tabs);
        tablayout.setupWithViewPager(viewpager);
    }

    private void tabWithIcon() {
        for (int i = 0; i < tabIcons.length; i++) {
            TabLayout.Tab tab = tablayout.getTabAt(i);
            if (tab != null)
                tab.setIcon(tabIcons[i]);
        }
    }

}
