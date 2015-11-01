package nl.saxion.calendar.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.base.Function;

import java.util.List;

import nl.saxion.calendar.model.Model;

/**
 * Created by jonathan on 7-10-15.
 * <p/>
 * generieke listadapter met daarin de viewholder.
 * V is de view waar in de data gezet moet worden.
 * VH is de viewholder die dit doet, gebruik generic viewholder
 * T is het type data voor de view
 * <p/>
 * de viewholder wordt automatisch gemaakt
 */
public class GenericListAdapter<T, V extends View & SetData<T>> extends RecyclerView.Adapter<GenericViewHolder<T, V>> {

    RecyclerView recyclerView;
    Model model;
    Function<Context, V> createView;
    Function<Void, List<T>> getItems;


    /**
     * model om de observer bij te regristreren
     *
     * @param model
     * @param recyclerView
     */
    public GenericListAdapter(Model model, RecyclerView recyclerView, Function<Void, List<T>> functonGetItems, Function<Context, V> functionCreateView) {
        this.model = model;
        this.recyclerView = recyclerView;
        this.getItems = functonGetItems;
        this.createView = functionCreateView;

    }


    @Override
    public GenericViewHolder<T, V> onCreateViewHolder(ViewGroup parent, int viewType) {


        V view = createView.apply(parent.getContext());

        return new GenericViewHolder<>(view);
    }


    @Override
    public void onBindViewHolder(GenericViewHolder<T, V> holder, int position) {

        holder.setData(getItems().get(position));
    }


    @Override
    public int getItemCount() {
        if (getItems() == null) {
            return 0;

        }
        return getItems().size();
    }


    private List<T> getItems() {
        return getItems.apply(null);
    }


}
