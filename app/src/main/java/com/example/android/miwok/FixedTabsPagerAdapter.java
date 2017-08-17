package com.example.android.miwok;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;


/**
 * Created by toshiba on 8/4/2017.
 */

public class FixedTabsPagerAdapter extends FragmentPagerAdapter {

    public FixedTabsPagerAdapter(FragmentManager fm){

        super(fm);
    }


    //Contain fragments to attach to the view pager
    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0:
                return new NumbersFragment();
            case 1:
                return new FamilyFragment();
            case 2:
                return new ColorsFragment();
            case 3:
                return new PhrasesFragment();
            default:
                return null;

        }
    }

    //number of fragments (number of pages in the view pager)
    @Override
    public int getCount() {
        return 4;
    }


    //title of each page
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){


            case 0:
                return "Numbers";
            case 1:
                return "Family Members";
            case 2:
                return "Colors";
            case 3:
                return "Phrases";

            default:
                return null;
        }
    }
}
