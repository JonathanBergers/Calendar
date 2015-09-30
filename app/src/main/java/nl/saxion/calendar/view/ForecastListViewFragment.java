package nl.saxion.calendar.view;


import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import nl.saxion.calendar.R;
import nl.saxion.calendar.model.Model;

/**
 * Created by jonathan on 17-9-15.
 */

@EFragment(R.layout.weather_lisview_fragment)
public class ForecastListViewFragment extends Fragment {


    @Bean
    Model model;

    @ViewById
    RecyclerView recyclerView;


    @ViewById
    SwipeRefreshLayout swipeRefreshLayout;



    RecyclerViewMaterialAdapter mAdapter;

    @AfterViews
    public void init(){


        // set layout manager, or else bug, nullpointer exep
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        //GridLayoutManager gridLayoutManager = new GridLayoutManager(null, 2);
        recyclerView.setLayoutManager(linearLayoutManager);

        // set adapter

        ForecastListAdapter adapter  = new ForecastListAdapter(model, recyclerView);


        mAdapter = new RecyclerViewMaterialAdapter(adapter);
        recyclerView.setAdapter(mAdapter);

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), recyclerView, null);



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                model.retrieveForecasts("New York");
                //TODO kan miss nog luisteren naar observer
                swipeRefreshLayout.setRefreshing(false);


            }

        });


    }






}
