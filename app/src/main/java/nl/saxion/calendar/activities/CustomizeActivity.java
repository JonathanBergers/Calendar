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
public class CustomizeActivity extends AppCompatActivity {


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
        model.getSettings().setTemp(checkBoxTemp.isChecked());
        model.getSettings().setPressure(checkBoxPressure.isChecked());
        model.getSettings().setHumidity(checkBoxHumidity.isChecked());
        model.getSettings().setTempMin(checkBoxTempMin.isChecked());
        model.getSettings().setTempMax(checkBoxTempMax.isChecked());
        model.getSettings().setWindspeed(checkBoxWindSpeed.isChecked());
        model.getSettings().setLocation(checkBoxLocation.isChecked());
    }

    @Bean
    Model model;

    @AfterViews
    public void initialize() {

        checkBoxTemp.setChecked(model.getSettings().isTemp());
        checkBoxPressure.setChecked(model.getSettings().isPressure());
        checkBoxHumidity.setChecked(model.getSettings().isHumidity());
        checkBoxTempMin.setChecked(model.getSettings().isTempMin());
        checkBoxTempMax.setChecked(model.getSettings().isTempMax());
        checkBoxWindSpeed.setChecked(model.getSettings().isWindspeed());
        checkBoxLocation.setChecked(model.getSettings().isLocation());

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
