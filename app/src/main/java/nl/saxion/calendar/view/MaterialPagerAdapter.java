package nl.saxion.calendar.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.FragmentByTag;

/**
 * Created by jonathan on 17-9-15.
 */
public class MaterialPagerAdapter extends FragmentStatePagerAdapter{





    public MaterialPagerAdapter(FragmentManager fm) {
        super(fm);
    }



    @Override
    public Fragment getItem(int position) {


        return ForecastListViewFragment_.builder().build();

    }

    @Override
    public CharSequence getPageTitle(int position) {


        switch (position){

            case 0: return "Forecasts";
            case 1: return "Locations";
            case 2: return "Events";


        }
        return "False page title";

    }

    @Override
    public int getCount() {
        return 3;
    }
}
