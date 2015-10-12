package nl.saxion.calendar.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;

import nl.saxion.calendar.model.Forecast;
import nl.saxion.calendar.view.fragments.EventListViewFragment_;
import nl.saxion.calendar.view.fragments.ForecastListViewFragment_;
import nl.saxion.calendar.view.fragments.LocationListViewFragment_;

/**
 * Created by jonathan on 17-9-15.
 */
public class MaterialPagerAdapter extends FragmentStatePagerAdapter{





    public MaterialPagerAdapter(FragmentManager fm) {
        super(fm);
    }



    @Override
    public Fragment getItem(int position) {



        if(position == 1){
            return LocationListViewFragment_.builder().build();
        }
        if (position == 2) {
            return EventListViewFragment_.builder().build();
        }

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
