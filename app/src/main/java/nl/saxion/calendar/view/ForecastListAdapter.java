package nl.saxion.calendar.view;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by jonathan on 21-9-15.
 */
public class ForecastListAdapter extends RecyclerView.Adapter<ForecastViewHolder> {




    @Override
    public ForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {




        // create the weatherview
        ViewGroup v = new ForecastView(parent.getContext());

        // create the viewholder
        ForecastViewHolder holder = new ForecastViewHolder(v);

        return holder;


    }


    /**This is where the view gets manipulated.
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ForecastViewHolder holder, int position) {

    }




    @Override
    public int getItemCount() {
        return 40;
    }


}
