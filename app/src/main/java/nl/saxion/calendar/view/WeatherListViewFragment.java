package nl.saxion.calendar.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import nl.saxion.calendar.R;

/**
 * Created by jonathan on 17-9-15.
 */
@EFragment(R.layout.weather_lisview_fragment)
public class WeatherListViewFragment extends Fragment {



    static WeatherListViewFragment init(int val) {
        WeatherListViewFragment truitonFrag = new WeatherListViewFragment();
        // Supply val input as an argument.
        Bundle args = new Bundle();
        args.putInt("val", val);
        truitonFrag.setArguments(args);
        return truitonFrag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        inflater.inflate(R.layout.weather_lisview_fragment, container, false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @ViewById
    RecyclerView listView;



    RecyclerViewMaterialAdapter mAdapter;

    @AfterViews
    public void init(){


        Log.d("WLFRAGMENT", "INIT");



        mAdapter = new RecyclerViewMaterialAdapter(new WeatherListAdapter());
        listView.setAdapter(mAdapter);

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), listView, null);



    }




}
