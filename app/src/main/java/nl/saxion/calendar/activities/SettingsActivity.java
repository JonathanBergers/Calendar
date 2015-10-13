package nl.saxion.calendar.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import nl.saxion.calendar.R;
import nl.saxion.calendar.model.Model;

@EActivity(R.layout.activity_customize)
public class SettingsActivity extends AppCompatActivity {


    @ViewById
    CheckBox checkBoxTemp;

    @ViewById
    CheckBox checkBoxPressure;

    @ViewById
    CheckBox checkBoxHumidity;

    @ViewById
    CheckBox checkBoxTempMin;

    @ViewById
    CheckBox checkBoxTempMax;

    @ViewById
    CheckBox checkBoxWindSpeed;

    @ViewById
    CheckBox checkBoxLocation;

    @ViewById
    Button buttonSaveForecastSettings;

    @Click
    void buttonSaveForecastSettings() {

        //when save button is clicked, save whether checkbox is checked or not
        model.getViewSettings().setTemp(checkBoxTemp.isChecked());
        model.getViewSettings().setPressure(checkBoxPressure.isChecked());
        model.getViewSettings().setHumidity(checkBoxHumidity.isChecked());
        model.getViewSettings().setTempMin(checkBoxTempMin.isChecked());
        model.getViewSettings().setTempMax(checkBoxTempMax.isChecked());
        model.getViewSettings().setWindspeed(checkBoxWindSpeed.isChecked());
        model.getViewSettings().setLocation(checkBoxLocation.isChecked());
    }

    @Bean
    Model model;

    @AfterViews
    public void initialize() {

        //when Customize is called, sets the checkboxes to true or false
        //depending on the values of the ForeCastSettings object from the model
        checkBoxTemp.setChecked(model.getViewSettings().isTemp());
        checkBoxPressure.setChecked(model.getViewSettings().isPressure());
        checkBoxHumidity.setChecked(model.getViewSettings().isHumidity());
        checkBoxTempMin.setChecked(model.getViewSettings().isTempMin());
        checkBoxTempMax.setChecked(model.getViewSettings().isTempMax());
        checkBoxWindSpeed.setChecked(model.getViewSettings().isWindspeed());
        checkBoxLocation.setChecked(model.getViewSettings().isLocation());

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
