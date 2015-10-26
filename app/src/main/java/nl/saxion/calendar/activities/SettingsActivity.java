package nl.saxion.calendar.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.services.calendar.model.CalendarListEntry;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import nl.saxion.calendar.R;
import nl.saxion.calendar.model.Model;
import nl.saxion.calendar.utils.Updatable;

@EActivity(R.layout.activity_customize)
public class SettingsActivity extends BaseActivity implements Updatable<List<CalendarListEntry>> {


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
    Button buttonSaveForecastSettings;

    @ViewById
    Button buttonChooseWeatherAgenda;

    @ViewById
    Button buttonLogin;

    @ViewById
    Button buttonLogOut;

    @ViewById
    Button buttonExportToCalendar;

    @ViewById
    TextView textViewCurrentWeatherAgenda;


    @Bean
    Model model;

    @Click
    void buttonLogin() {
        LoginActivity_.intent(SettingsActivity.this).start();
    }

    @Click
    void buttonExportToCalendar(){
        model.exportWeatherToAgenda(this);
    }

    @Click
    void buttonLogOut() {
        model.setCredentials(null);
        Toast.makeText(getBaseContext(), "uitgelogt", Toast.LENGTH_SHORT).show();
       //Log.d("SETTINGS", "hay thereeee "+ model.getCredentials().getSelectedAccountName());
    }

    @Click
    void buttonSaveForecastSettings() {

        //when save button is clicked, save whether checkbox is checked or not
        model.getViewSettings().setPressure(checkBoxPressure.isChecked());
        model.getViewSettings().setHumidity(checkBoxHumidity.isChecked());
        model.getViewSettings().setTempMin(checkBoxTempMin.isChecked());
        model.getViewSettings().setTempMax(checkBoxTempMax.isChecked());
        model.getViewSettings().setWindspeed(checkBoxWindSpeed.isChecked());

    }

    @Click
    void buttonChooseWeatherAgenda(){
        //geef gebruiker keuze
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        model.selectWeatherAgenda(SettingsActivity.this);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        makeWeatherAgenda();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Wilt u een bestaande agenda kiezen als weeragenda?").setPositiveButton("kies een agenda", dialogClickListener)
                .setNegativeButton("maak een agenda", dialogClickListener).show();
    }





    @AfterViews
    public void initialize() {

        //when Customize is called, sets the checkboxes to true or false
        //depending on the values of the ForeCastSettings object from the model
        checkBoxPressure.setChecked(model.getViewSettings().isPressure());
        checkBoxHumidity.setChecked(model.getViewSettings().isHumidity());
        checkBoxTempMin.setChecked(model.getViewSettings().isTempMin());
        checkBoxTempMax.setChecked(model.getViewSettings().isTempMax());
        checkBoxWindSpeed.setChecked(model.getViewSettings().isWindspeed());

        setCorrectAgendaToTextview();



    }

    private void setCorrectAgendaToTextview(){
        //sets the current agenda textview to the correct agenda name
        CalendarListEntry currentWeatherAgenda = model.getWeatherAgenda();
        if(currentWeatherAgenda == null){
            textViewCurrentWeatherAgenda.setText("uw huidige weeragenda is: \n" + "geen agenda");
        } else {
            textViewCurrentWeatherAgenda.setText("uw huidige weeragenda is: \n" + currentWeatherAgenda.getSummary());
        }

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


    @Override
    @UiThread
    public void update(final List<CalendarListEntry> input) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(SettingsActivity.this);
        //builderSingle.setIcon(R.drawable.ic_launcher);
        builderSingle.setTitle("Kies een agenda: ");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(SettingsActivity.this, android.R.layout.select_dialog_singlechoice);
        for(CalendarListEntry cle : input){
            arrayAdapter.add(cle.getSummary());
        }
        builderSingle.setNegativeButton("annuleren", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = input.get(which).getSummary();
                model.setWeatherAgenda(input.get(which));
                setCorrectAgendaToTextview();

                AlertDialog.Builder builderInner = new AlertDialog.Builder(SettingsActivity.this);
                builderInner.setMessage(strName);
                builderInner.setTitle("De gekozen agenda is");
                builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builderInner.show();
            }
        });
        builderSingle.show();

    }

    private void makeWeatherAgenda(){
        // Create a new calendar list entry
        final com.google.api.services.calendar.model.Calendar agenda = new com.google.api.services.calendar.model.Calendar();

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        alert.setMessage("Geef een naam voor de agenda");
        alert.setView(input);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString().trim();
                agenda.setSummary(value);
                agenda.setTimeZone("Europe/Amsterdam");

                model.makeWeatherAgenda(agenda);
                Toast.makeText(getApplicationContext(), "agenda aan het maken...",
                        Toast.LENGTH_LONG).show();
                setCorrectAgendaToTextview();
            }
        });

        alert.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });
        alert.show();

    }

    @UiThread
    public void showToast(String message){
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_LONG).show();
    }

}



