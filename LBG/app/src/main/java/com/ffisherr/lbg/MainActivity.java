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

import static com.ffisherr.lbg.Config.IS_KNOWN_BOOL;
import static com.ffisherr.lbg.Config.LOGIN_TEXT;
import static com.ffisherr.lbg.Config.PREFERENCE_NAME;
import static com.ffisherr.lbg.Config.ROLE_TEXT;
import static com.ffisherr.lbg.Config.UNIVERSITY_ID;
import static com.ffisherr.lbg.Config.UNIVERSITY_TEXT;
import static com.ffisherr.lbg.Config.USER_ID;


public class MainActivity extends AppCompatActivity implements Listener{

    private SharedPreferences sPref;

    private String  uLogin;
    private String  uRole;
    private String  uUniversity;
    private Integer uRoleId;
    private Integer uUniversityId;
    private Integer uId;
    private Boolean isKnown;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*sPref = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(LOGIN_TEXT, "");
        ed.putString(ROLE_TEXT, "");
        ed.putBoolean(IS_KNOWN_BOOL, false);
        ed.commit();*/

        sPref   = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        uLogin  = sPref.getString(LOGIN_TEXT    , "");
        uRole   = sPref.getString(ROLE_TEXT     , "unknownUser");
        isKnown = sPref.getBoolean(IS_KNOWN_BOOL, false);

        uUniversity   = sPref.getString(UNIVERSITY_TEXT, "");
        uUniversityId = sPref.getInt(UNIVERSITY_ID, 0);
        uId           = sPref.getInt(USER_ID, 0);

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
            super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putBoolean(IS_KNOWN_BOOL, isKnown);
            bundle.putString(LOGIN_TEXT, uLogin);
            bundle.putString(UNIVERSITY_TEXT, uUniversity);
            bundle.putInt(UNIVERSITY_ID, uUniversityId);
            bundle.putInt(USER_ID, uId);
            switch (position) {
                case 0:
                    CalendarFragment cal = new CalendarFragment();
                    cal.setArguments(bundle);
                    return cal;
                case 1:
                    ChatFragment chat = new ChatFragment();
                    chat.setArguments(bundle);
                    return chat;
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
    public void eventClicked(Integer id, String title, String about, String dt) {
        Intent intent = new Intent(this, EventDescriptionActivity.class);
        intent.putExtra(EventDescriptionActivity.ABOUT_EXTRA, about);
        intent.putExtra(EventDescriptionActivity.TITLE_EXTRA, title);
        intent.putExtra(EventDescriptionActivity.DT_EXTRA, dt);
        intent.putExtra(EventDescriptionActivity.ID_EXTRA, id);
        startActivity(intent);
    }
}
