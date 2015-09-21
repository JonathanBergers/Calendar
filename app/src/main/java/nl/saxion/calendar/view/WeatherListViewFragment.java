package nl.saxion.calendar.view;

import android.support.v4.app.Fragment;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import nl.saxion.calendar.R;

/**
 * Created by jonathan on 17-9-15.
 */
@EFragment(R.layout.weather_lisview_fragment)
public class WeatherListViewFragment extends Fragment {



    @ViewById
    ListView listView;

    @AfterViews
    public void init(){


    }




}
