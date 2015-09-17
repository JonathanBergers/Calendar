package nl.saxion.calendar.view;

import android.support.v4.app.Fragment;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import nl.saxion.calendar.R;

/**
 * Created by jonathan on 17-9-15.
 */
@EFragment(R.layout.listview_layout)
public class WeatherListViewFragment extends Fragment {

    @ViewById
    ListView listViewTest;


    @AfterViews
    public void init(){


        ArrayList<String> arrayList = new ArrayList<>();
        for(int i =0 ; i<10; i++){

            arrayList.add("jo");
        }

        listViewTest.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arrayList));


    }


}
