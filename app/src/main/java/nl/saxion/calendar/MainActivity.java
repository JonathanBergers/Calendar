package nl.saxion.calendar;

import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;

import nl.saxion.calendar.client.OpenweatherClient;
import nl.saxion.calendar.model.Forecast;
import nl.saxion.calendar.model.JsonConverterWeather;


@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {





    @RestService
    OpenweatherClient client;

    @AfterViews
    @Background
    public void getWeather() {

        String s = client.recieveCurrentWeather("Londen").toString();

        //System.out.println(s);

        JsonConverterWeather jcw = new JsonConverterWeather();
        Forecast f =jcw.getForcastfromJson(s);
        if(f!=null){
            showWeather(f.toString());
            Log.d("Weather", f.toString() );
        } else {
            Log.d("Weather", "could not convert to Forcast");
        }



    }
    @UiThread
    public void showWeather(String s){

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
