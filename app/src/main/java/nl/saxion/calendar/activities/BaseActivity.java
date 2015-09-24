package nl.saxion.calendar.activities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import nl.saxion.calendar.R;
import nl.saxion.calendar.client.OpenweatherClient;
import nl.saxion.calendar.model.Forecast;
import nl.saxion.calendar.model.JsonConverterWeather;
import nl.saxion.calendar.model.Model;
import nl.saxion.calendar.view.MaterialPagerAdapter;


@EActivity()
public class BaseActivity extends AppCompatActivity{








    Drawer navigationDrawer;

    @AfterViews
    public void init(){

            // setup the navigation drawer
            Toolbar toolbar = new Toolbar(this);


            navigationDrawer = new DrawerBuilder().withActivity(this).withToolbar(toolbar).withTranslucentStatusBar(false).build();
        navigationDrawer.addItem(new PrimaryDrawerItem().withName("Settings"));




    }

}
