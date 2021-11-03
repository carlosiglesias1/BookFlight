package com.example.bookflight;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Trip> tripList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tripList = new ArrayList<>();
        Button submit = (Button) findViewById(R.id.submit);
        FloatingActionButton moreTickets = (FloatingActionButton) findViewById(R.id.moreTickets);
        FloatingActionButton lessTickets = (FloatingActionButton) findViewById(R.id.lessTickets);
        Button historic = (Button) findViewById(R.id.history);
        submit.setOnClickListener(this::viewData);
        moreTickets.setOnClickListener(view -> {
            EditText people = (EditText) findViewById(R.id.tickets);
            int i = Integer.parseInt(people.getText().toString());
            i++;
            if (i > 20) {
                Toast toast = Toast.makeText(this, "Máximo número de pasajeros alcanzado", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                people.setText(String.format(Locale.getDefault(), "%d", i));
            }
        });
        lessTickets.setOnClickListener(view -> {
            EditText people = (EditText) findViewById(R.id.tickets);
            int i = Integer.parseInt(people.getText().toString());
            i--;
            if (i < 0) {
                Toast toast = Toast.makeText(this, "No puedes poner menos", Toast.LENGTH_SHORT);
                toast.show();
            } else
                people.setText(String.format(Locale.getDefault(), "%d", i));
        });
        historic.setOnClickListener(this::showHist);
    }

    public void viewData(View view) {
        try {
            TextView resultado = (TextView) findViewById(R.id.resultado);
            RadioGroup travelType = (RadioGroup) findViewById(R.id.travelType);
            RadioButton travelTypeSelect = (RadioButton) findViewById(travelType.getCheckedRadioButtonId());
            TextInputEditText from = (TextInputEditText) findViewById(R.id.from);
            EditText to = (EditText) findViewById(R.id.to);
            EditText depart = (EditText) findViewById(R.id.depart);
            EditText comeBack = (EditText) findViewById(R.id.comeback);
            EditText people = (EditText) findViewById(R.id.tickets);
            RadioGroup travelStops = (RadioGroup) findViewById(R.id.numberOfStops);
            RadioButton numberOfStops = findViewById(travelStops.getCheckedRadioButtonId());
            tripList.add(new Trip(travelTypeSelect.getText().toString(), from.getText().toString(), to.getText().toString(), depart.getText().toString(), comeBack.getText().toString(), Integer.parseInt(people.getText().toString()), numberOfStops.getText().toString()));
            resultado.setText(tripList.get(tripList.size() - 1).toString());
        } catch (NullPointerException ex) {
            Toast toast = Toast.makeText(this, "Faltan campos", Toast.LENGTH_SHORT);
            toast.show();
        } catch (Exception e) {
            Toast toast = Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void showHist(View view) {
        try {
            String resource = "";
            TextView resultado = (TextView) findViewById(R.id.resultado);
            for (Trip trip : this.tripList) {
                resource = resource.concat(trip.toString());
            }
            resultado.setText(resource);
        } catch (Exception e) {
            Toast toast = Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}