package com.doitutpl.doit.ui.tutorial;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.doitutpl.doit.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        // Todo: Aquí debería retornar una instancia del fragment
        // return PlaceholderFragment.newInstance(position + 1);

        switch (position){
            case 0:
                FragmentTutorial01 fragmentTutorial01 = new FragmentTutorial01();
                return fragmentTutorial01;
            case 1:
                FragmentTutorial02 fragmentTutorial02 = new FragmentTutorial02();
                return fragmentTutorial02;
            case 2:
                FragmentTutorial03 fragmentTutorial03 = new FragmentTutorial03();
                return fragmentTutorial03;
            case 3:
                FragmentTutorial04 fragmentTutorial04 = new FragmentTutorial04();
                return fragmentTutorial04;
            case 4:
                FragmentTutorial05 fragmentTutorial05 = new FragmentTutorial05();
                return fragmentTutorial05;
        }
        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 5 total pages.
        return 5;
    }
}