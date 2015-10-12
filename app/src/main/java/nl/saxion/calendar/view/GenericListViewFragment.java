package nl.saxion.calendar.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.google.common.base.Function;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import nl.saxion.calendar.R;
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

        RecyclerViewMaterialAdapter mAdapter;




    public abstract List<T> getItems();
    protected abstract Function<Context, V> createView();



    @AfterViews
    public void init(){


            // set layout manager, or else bug, nullpointer exep
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());


        //linearLayoutManager.setRecycleChildrenOnDetach(false);
            //GridLayoutManager gridLayoutManager = new GridLayoutManager(null, 2);

            System.out.println(recyclerView==null);
            recyclerView.setLayoutManager(linearLayoutManager);


            // make view for adapter
            // make adapter
            GenericListAdapter<T, V> adapter  =
                    new GenericListAdapter<T, V>(model, recyclerView, getItems(), createView() );


            // set adapter
            mAdapter = new RecyclerViewMaterialAdapter(adapter);
            recyclerView.setAdapter(mAdapter);

            MaterialViewPagerHelper.registerRecyclerView(getActivity(), recyclerView, null);







        }



    }


