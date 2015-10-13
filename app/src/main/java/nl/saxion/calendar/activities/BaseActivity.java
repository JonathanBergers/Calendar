package nl.saxion.calendar.activities;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;


@EActivity()
public class BaseActivity extends AppCompatActivity{

    Drawer navigationDrawer;

    @AfterViews
    public void init(){

            // setup the navigation drawer
            Toolbar toolbar = new Toolbar(this);


            navigationDrawer = new DrawerBuilder().withActivity(this).withToolbar(toolbar).withTranslucentStatusBar(false).build();


        PrimaryDrawerItem settingsItem = new PrimaryDrawerItem().withName("Settings").withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int i, IDrawerItem iDrawerItem) {

                SettingsActivity_.intent(BaseActivity.this).start();
                return false;
            }
        });


        PrimaryDrawerItem gpsItem = new PrimaryDrawerItem().withName("Current Location Demo").withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int i, IDrawerItem iDrawerItem) {

                GPSTrackerActivity_.intent(BaseActivity.this).start();
                return false;
            }
        });

        PrimaryDrawerItem loginItem = new PrimaryDrawerItem().withName("Login").withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int i, IDrawerItem iDrawerItem) {

                LoginActivity_.intent(BaseActivity.this).start();
                return false;
            }
        });
        //when settings is clicked it fires an intent to customize activity
        navigationDrawer.addItem(settingsItem);
        navigationDrawer.addItem(gpsItem);
        navigationDrawer.addItem(loginItem);

    }

}
