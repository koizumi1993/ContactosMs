package com.fundamentos.abisu.contactosmas.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fundamentos.abisu.contactosmas.Fragments.AddContactFragment;
import com.fundamentos.abisu.contactosmas.Fragments.ListContactFragment;
import com.fundamentos.abisu.contactosmas.Fragments.LocationContactFragment;

import java.util.ArrayList;
import java.util.List;

public class TabPageAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment> mFragmentList = new ArrayList<>();


    public TabPageAdapter(FragmentManager childFragmentManager) {
        super(childFragmentManager);
        mFragmentList.add(new ListContactFragment());
        mFragmentList.add(new AddContactFragment());
        mFragmentList.add(new LocationContactFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }


}
