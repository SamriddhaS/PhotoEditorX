package com.example.myphotoeditorapp.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdaptar extends FragmentPagerAdapter {

    List<Fragment> fragmentList = new ArrayList<>();
    List<String> fragmentTitleList = new ArrayList<>();

    public ViewPagerAdaptar(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void addFragment(Fragment fragment,String fragmentTitle){

        fragmentList.add(fragment);
        fragmentTitleList.add(fragmentTitle);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }
}
