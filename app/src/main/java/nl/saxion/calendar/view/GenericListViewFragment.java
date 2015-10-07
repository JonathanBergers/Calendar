package nl.saxion.calendar.view;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import nl.saxion.calendar.R;
import nl.saxion.calendar.model.Forecast;
import nl.saxion.calendar.model.Model;

/**
 * Created by jonathan on 7-10-15.
 */
@EFragment(R.layout.weather_lisview_fragment)
public abstract class GenericListViewFragment<T, V extends View & SetData<T>> extends Fragment {





        @Bean
        Model model;

        @ViewById
        RecyclerView recyclerView;

        @ViewById
        SwipeRefreshLayout swipeRefreshLayout;

        RecyclerViewMaterialAdapter mAdapter;


    /**
     * used to build the adapter and viewholder
     * @return
     */
    public abstract V createView();



    @AfterViews
        public void init(){


            // set layout manager, or else bug, nullpointer exep
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            //GridLayoutManager gridLayoutManager = new GridLayoutManager(null, 2);
            recyclerView.setLayoutManager(linearLayoutManager);


            // make view for adapter
            V view  = createView();
            // make viewholder for adapter
            GenericViewHolder<T, V> vh = new GenericViewHolder<T, V>(view);
            // make adapter
            GenericListAdapter<T, V, GenericViewHolder<T, V>> adapter  =
                    new GenericListAdapter(model, recyclerView, view, model.getLocationForecasts());


            // set adapter
            mAdapter = new RecyclerViewMaterialAdapter(adapter);
            recyclerView.setAdapter(mAdapter);

            MaterialViewPagerHelper.registerRecyclerView(getActivity(), recyclerView, null);



            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

                @Override
                public void onRefresh() {

                    onRefreshData();
                    swipeRefreshLayout.setRefreshing(false);

                }

            });


        }


    /**
     * override for action at refresh
     */
        protected void onRefreshData(){

        }



    }


