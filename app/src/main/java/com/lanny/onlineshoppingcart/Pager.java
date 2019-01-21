package com.lanny.onlineshoppingcart;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lanny.onlineshoppingcart.account.SigninFragment;
import com.lanny.onlineshoppingcart.account.SignupFragment;

public class Pager extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    public Pager(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case 0:
                SigninFragment tab1 = new SigninFragment();
                return tab1;
            case 1:
                SignupFragment tab2 = new SignupFragment();
                return tab2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}