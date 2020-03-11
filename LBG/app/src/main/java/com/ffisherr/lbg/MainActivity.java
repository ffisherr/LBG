package com.ffisherr.lbg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {

    private SharedPreferences sPref;


    public static final String PREFERENCE_NAME = "my_settings";
    public static final String ROLE_TEXT       = "role_text";
    public static final String LOGIN_TEXT      = "login_text";
    public static final String IS_KNOWN_BOOL   = "is_known_bool";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* sPref = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(LOGIN_TEXT, "");
        ed.putString(ROLE_TEXT, "");
        ed.putBoolean(IS_KNOWN_BOOL, false);
        ed.commit();*/


        Intent i = new Intent(MainActivity.this, ChatActivity.class);
        startActivity(i);
        sPref = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        String  uLogin  = sPref.getString(LOGIN_TEXT    , "");
        String  uRole   = sPref.getString(ROLE_TEXT     , "unknownUser");
        Boolean isKnown = sPref.getBoolean(IS_KNOWN_BOOL, false);

        if (isKnown.equals(false)) {
            Intent intent = new Intent(MainActivity.this, Dima.class);
            startActivity(intent);
        } else {
            SectionPagerAdapter spa = new SectionPagerAdapter(getSupportFragmentManager());
            ViewPager viewPager = findViewById(R.id.pager);
            viewPager.setAdapter(spa);
            TabLayout tabLayout = findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    private class SectionPagerAdapter extends FragmentPagerAdapter {
        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new CalendarFragment();
                case 1:
                    return new ChatFragment();
            }
            return new CalendarFragment();
        }

        @Override
        public CharSequence getPageTitle(int position){
            switch (position){
                case 0:
                    return getResources().getText(R.string.calendar_tab);
                case 1:
                    return getResources().getText(R.string.chat_tab);
            }
            return null;
        }
    }
}
