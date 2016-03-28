package com.ashleyjain.iitdcomplaintsystem;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int NumOfTabs;
    int a = 0;
    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.NumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0) {
            IndividualFragment frag = new IndividualFragment();
            return frag;
        }
        if(position == 1){
            HostelComplaintList frag1 = new HostelComplaintList();
            return frag1;
        }
        if(position == 2){
            InstituteComplaintList frag2 = new InstituteComplaintList();
            return frag2;
        }
        return null;

    }
    @Override
    public int getCount() {
        return NumOfTabs;
    }




    @Override
    public int getItemPosition(Object object) {
        //don't return POSITION_NONE, avoid fragment recreation.
        return POSITION_NONE;
    }

}