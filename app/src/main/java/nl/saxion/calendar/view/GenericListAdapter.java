package nl.saxion.calendar.view;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import nl.saxion.calendar.model.Forecast;
import nl.saxion.calendar.model.Model;

/**
 * Created by jonathan on 7-10-15.
 *
 * generieke listadapter met daarin de viewholder.
 * V is de view waar in de data gezet moet worden.
 * VH is de viewholder die dit doet, gebruik generic viewholder
 * T is het type data voor de view
 *
 * de viewholder wordt automatisch gemaakt
 */
public class GenericListAdapter<T, V extends View & SetData<T>, VH extends GenericViewHolder<T, V>> extends RecyclerView.Adapter<VH> implements Observer {

    RecyclerView recyclerView;
    Model model;
    VH viewHolder;
    V view;
    List<T> items = new ArrayList<>();

    /**model om de observer bij te regristreren
     *
     * @param model
     * @param recyclerView
     * @param view de view die weergegeven moet worden
     */
    public GenericListAdapter(Model model, RecyclerView recyclerView, V view, List<T> items) {
        this.model = model;
        this.recyclerView = recyclerView;
        this.view = view;
        this.items = items;
        model.addObserver(this);



        Log.d("OBSERVERS", "" + model.countObservers());

    }


    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {

        GenericViewHolder<T, V> holder = new GenericViewHolder<>(view);
        return (VH) holder;

    }

    @Override
    public void onBindViewHolder(VH holder, int position) {

        holder.setData(items.get(position));
    }

    @Override
    public int getItemCount(){
        return items.size();
    }




    @Override
    public void update(Observable observable, Object data) {
        notifyDataSetChanged();
    }

}
