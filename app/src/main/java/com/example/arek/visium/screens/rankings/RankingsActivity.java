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
        viewpager = (ViewPager) findViewById(R.id.viewPager);
        viewPagerAdapter = new RankingViewPagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(viewPagerAdapter);
        tablayout = (TabLayout) findViewById(R.id.tabs);
        tablayout.setupWithViewPager(viewpager);
    }

    private void tabWithIcon() {
        for (int i = 0; i < tabIcons.length; i++) {
            TabLayout.Tab tab = tablayout.getTabAt(i);
            if (tab != null)
                tab.setIcon(tabIcons[i]);
        }
    }

//    private void setUpCustomTabs() {
//        for (int i = 0; i < tabArray.length; i++) {
//            TextView customTab = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab_layout, null);//get custom view
//            customTab.setText(tabArray[i]);//set text over view
//            customTab.setCompoundDrawablesWithIntrinsicBounds(0, tabIcons[i], 0, 0);//set icon above the view
//            TabLayout.Tab tab = tabLayout.getTabAt(i);//get tab via position
//            if (tab != null)
//                tab.setCustomView(customTab);//set custom view
//        }
}
