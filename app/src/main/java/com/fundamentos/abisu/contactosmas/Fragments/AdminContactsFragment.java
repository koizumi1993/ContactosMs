package com.fundamentos.abisu.contactosmas.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fundamentos.abisu.contactosmas.Adapters.ListContactAdapter;
import com.fundamentos.abisu.contactosmas.Adapters.TabPageAdapter;
import com.fundamentos.abisu.contactosmas.R;

import java.util.ArrayList;
import java.util.List;


public class AdminContactsFragment extends Fragment {

    View infladtedView;
    TabLayout tabLayout;
    ViewPager viewPager;
    TabPageAdapter myAdapter;
    private int[] tabIcons={R.drawable.ic_ver_cnt,R.drawable.ic_add_cnt,R.drawable.ic_location_cnt};

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        infladtedView = inflater.inflate(R.layout.fragment_admin_contacts, container, false);

        myAdapter = new TabPageAdapter(getChildFragmentManager());

        viewPager = (ViewPager) infladtedView.findViewById(R.id.viewPager);
        viewPager.setAdapter(myAdapter);

        tabLayout = (TabLayout) infladtedView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        return infladtedView;
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }
}
