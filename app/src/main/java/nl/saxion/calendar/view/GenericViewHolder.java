package nl.saxion.calendar.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import nl.saxion.calendar.model.Forecast;

/**
 * Created by jonathan on 7-10-15.
 */
public class GenericViewHolder<T, V extends View & SetData<T>>  extends RecyclerView.ViewHolder implements SetData<T> {


    V view;

    public GenericViewHolder(View view) {
        super(view);
        this.view = (V) view;

    }

    @Override
    public void setData(T data) {

        view.setData(data);
    }
}
