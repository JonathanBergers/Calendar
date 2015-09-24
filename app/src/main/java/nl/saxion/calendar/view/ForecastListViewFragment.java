package nl.saxion.calendar.view;


import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import nl.saxion.calendar.R;

/**
 * Created by jonathan on 17-9-15.
 */

@EFragment(R.layout.weather_lisview_fragment)
public class ForecastListViewFragment extends Fragment {



    @ViewById
    RecyclerView recyclerView;



    RecyclerViewMaterialAdapter mAdapter;

    @ItemClick (R.id.recyclerView)
     public void itemClicked(int position) {

    }

    @AfterViews
    public void init(){


        // set layout manager, or else bug, nullpointer exep
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        //GridLayoutManager gridLayoutManager = new GridLayoutManager(null, 2);
        recyclerView.setLayoutManager(linearLayoutManager);

        // set adapter
        mAdapter = new RecyclerViewMaterialAdapter(new ForecastListAdapter());
        recyclerView.setAdapter(mAdapter);

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), recyclerView, null);





    }






}
