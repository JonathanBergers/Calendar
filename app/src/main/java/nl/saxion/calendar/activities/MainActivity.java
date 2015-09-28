package nl.saxion.calendar.activities;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import nl.saxion.calendar.R;
import nl.saxion.calendar.client.OpenweatherClient;
import nl.saxion.calendar.model.Model;
import nl.saxion.calendar.view.MaterialPagerAdapter;

/**
 * Created by jonathan on 24-9-15.
 */
@EActivity(R.layout.activity_material_view_pager)
public class MainActivity extends BaseActivity {




    @Bean
    Model model;

    @RestService
    OpenweatherClient client;




    @ViewById
    MaterialViewPager materialViewPager;



    @AfterViews
    public void initialize(){

        model.retrieveForecasts("Londen");
        model.retrieveForecasts("Paris");
        model.retrieveForecasts("Amsterdam");
        createMaterialViewpager();



    }

    /** inititalizes the material viewpager with the recyclerviews
     *
     */
    private void createMaterialViewpager(){


        // Header design
        materialViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.blue,
                                "http://cdn1.tnwcdn.com/wp-content/blogs.dir/1/files/2014/06/wallpaper_51.jpg");
                    case 1:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.green,
                                "https://fs01.androidpit.info/a/63/0e/android-l-wallpapers-630ea6-h900.jpg");
                    case 2:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.cyan,
                                "http://www.droid-life.com/wp-content/uploads/2014/10/lollipop-wallpapers10.jpg");
                    case 3:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.red,
                                "http://www.tothemobile.com/wp-content/uploads/2014/07/original.jpg");
                }

                //execute others actions if needed (ex : modify your header logo)

                return null;
            }
        });




        // get the viewpager
        ViewPager viewPager = materialViewPager.getViewPager();
        // set the pager adapter
        viewPager.setAdapter(new MaterialPagerAdapter(getSupportFragmentManager()));
        // add the viewpager to the pagertitlestrip
        materialViewPager.getPagerTitleStrip().setViewPager(materialViewPager.getViewPager());


    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_material_view_pager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
