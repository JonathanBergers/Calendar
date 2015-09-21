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


        return WeatherListViewFragment_.builder().build();

    }

    @Override
    public CharSequence getPageTitle(int position) {

        return "JO";

    }

    @Override
    public int getCount() {
        return 3;
    }
}
