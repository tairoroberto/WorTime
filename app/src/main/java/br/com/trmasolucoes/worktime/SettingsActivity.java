package br.com.trmasolucoes.worktime;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class SettingsActivity extends AppCompatActivity {
    private static Button btnSalvarConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnSalvarConfig = (Button) findViewById(R.id.btnSalvarConfig);
        assert btnSalvarConfig != null;
        btnSalvarConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void selecionarHorario(View view){
        DialogFragment datePiker = new TimePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("view", view.getId());
        datePiker.setArguments(bundle);
        datePiker.show(getSupportFragmentManager(), "timePicker");
    }


    /** Mostra um data picker pata seleçoã de horário */
    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), R.style.datepicker, this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        /** Pega o id da view enviada pelo getArguments() do fragment */
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String hora, minuto;
            minuto = (minute < 10)?"0"+minute:""+minute;
            hora = (hourOfDay < 10)?"0"+hourOfDay:""+hourOfDay;

            Toast.makeText(getActivity(), hora +":"+minuto, Toast.LENGTH_SHORT).show();
            Bundle bundle = this.getArguments();
            Button btn = (Button) getActivity().findViewById(bundle.getInt("view"));
            btn.setText(hora +":"+minuto);
        }
    }
}
