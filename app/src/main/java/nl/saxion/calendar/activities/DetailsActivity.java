package nl.saxion.calendar.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import nl.saxion.calendar.R;

@EActivity(R.layout.activity_details)
public class DetailsActivity extends AppCompatActivity {

    @ViewById
    TextView tempText;

    @ViewById
    TextView pressureText;

    @ViewById
    TextView humidityText;

    @ViewById
    TextView temp_minText;

    @ViewById
    TextView temp_maxText;

    @ViewById
    TextView windSpeedText;

    @ViewById
    TextView locationText;

    @ViewById
    Button customizeButton;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
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
