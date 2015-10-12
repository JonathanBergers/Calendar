package nl.saxion.calendar.activities;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
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

    @ViewById
    Button buttonChooseWeatherAgenda;

    @ViewById
    TextView textViewCurrentWeatherAgenda;

    @Bean
    Model model;

    @Click
    void buttonSaveForecastSettings() {

        //when save button is clicked, save whether checkbox is checked or not
        model.getSettings().setTemp(checkBoxTemp.isChecked());
        model.getSettings().setPressure(checkBoxPressure.isChecked());
        model.getSettings().setHumidity(checkBoxHumidity.isChecked());
        model.getSettings().setTempMin(checkBoxTempMin.isChecked());
        model.getSettings().setTempMax(checkBoxTempMax.isChecked());
        model.getSettings().setWindspeed(checkBoxWindSpeed.isChecked());
        model.getSettings().setLocation(checkBoxLocation.isChecked());
    }

    @Click
    void ButtonChooseWeatherAgenda(){
        //geef gebruiker keuze
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        model.selectWeatherAgenda();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        model.makeWeatherAgenda();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setMessage("Wilt u een bestaande agenda kiezen als weeragenda?").setPositiveButton("Ja", dialogClickListener)
                .setNegativeButton("Nee, ik wil een nieuwe agenda maken", dialogClickListener).show();
    }



    @AfterViews
    public void initialize() {

        //when Customize is called, sets the checkboxes to true or false
        //depending on the values of the ForeCastSettings object from the model
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
    @UiThread
    public void chooseWeatherCalendar(){

            //geef gebruiker keuze
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            //Yes button clicked

                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked

                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
            builder.setMessage("Wilt u een bestaande agenda kiezen als weeragenda?").setPositiveButton("Ja", dialogClickListener)
                    .setNegativeButton("Nee, ik wil een nieuwe agenda maken", dialogClickListener).show();
    }



}



