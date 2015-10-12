package nl.saxion.calendar.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.base.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

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
public class GenericListAdapter<T, V extends View & SetData<T>> extends RecyclerView.Adapter<GenericViewHolder<T, V>> implements Observer{

    RecyclerView recyclerView;
    Model model;
    List<T> items = new ArrayList<>();
    Function<Context, V> createView;

    /**model om de observer bij te regristreren
     *
     * @param model
     * @param recyclerView
     */
    public GenericListAdapter(Model model, RecyclerView recyclerView, List<T> items, Function<Context, V> functionCreateView) {
        this.model = model;
        this.recyclerView = recyclerView;
        this.items = items;
        this.createView = functionCreateView;

        Log.d("ITEMS", ""+ items.size());
        model.addObserver(this);



    }


    @Override
    public  GenericViewHolder<T, V> onCreateViewHolder(ViewGroup parent, int viewType) {



        V view = (V) createView.apply(parent.getContext());

        return new GenericViewHolder<>(view);
    }



    @Override
    public void onBindViewHolder(GenericViewHolder<T, V> holder, int position) {

        holder.setData(items.get(position));
    }



    @Override
    public int getItemCount(){
        if(items == null){
            return 0;

        }
        return items.size();
    }


    @Override
    public void update(Observable observable, Object data) {
        notifyDataSetChanged();
    }


}
