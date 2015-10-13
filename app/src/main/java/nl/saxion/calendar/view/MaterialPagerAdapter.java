package nl.saxion.calendar.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;

import nl.saxion.calendar.model.Forecast;



/**
 * Created by jonathan on 17-9-15.
 */
public class MaterialPagerAdapter extends FragmentStatePagerAdapter{





    public MaterialPagerAdapter(FragmentManager fm) {
        super(fm);
    }



    @Override
    public Fragment getItem(int position) {


        switch (position){
            case 0: return ForecastListViewFragment_.builder().build();
            case 1: return LocationListViewFragment_.builder().build();
            case 2: return EventListViewFragment_.builder().build();
        }
        assert false: "get fragment outside pager";
        return null;


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
