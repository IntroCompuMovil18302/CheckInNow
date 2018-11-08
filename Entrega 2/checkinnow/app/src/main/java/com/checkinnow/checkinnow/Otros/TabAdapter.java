package com.checkinnow.checkinnow.Otros;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabAdapter extends FragmentStatePagerAdapter {


    private String[] titulostab;

    public TabAdapter(FragmentManager manager, String[] titulostab) {
        super(manager);
        this.titulostab = titulostab;
    }

    @Override
    public Fragment getItem(int position) {
        return MainFragment.getInstance(titulostab[position]);
    }

    @Override
    public int getCount() {
        return titulostab.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
}
