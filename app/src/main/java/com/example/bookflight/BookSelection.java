package com.example.bookflight;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Locale;

public class BookSelection extends AppCompatActivity {
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
            RadioGroup travelType = (RadioGroup) findViewById(R.id.travelType);
            RadioButton travelTypeSelect = (RadioButton) findViewById(travelType.getCheckedRadioButtonId());
            TextInputEditText from = (TextInputEditText) findViewById(R.id.from);
            EditText to = (EditText) findViewById(R.id.to);
            EditText depart = (EditText) findViewById(R.id.depart);
            EditText comeBack = (EditText) findViewById(R.id.comeback);
            EditText people = (EditText) findViewById(R.id.tickets);
            RadioGroup travelStops = (RadioGroup) findViewById(R.id.numberOfStops);
            RadioButton numberOfStops = findViewById(travelStops.getCheckedRadioButtonId());
            Intent intent = new Intent(this, ViewFlights.class);
            intent.putExtra("travelType", from.getText().toString());
            intent.putExtra("from", from.getText());
            intent.putExtra("to", to.getText());
            intent.putExtra("depart", depart.getText());
            intent.putExtra("comeback", comeBack.getText());
            intent.putExtra("pasengers", Integer.parseInt(people.getText().toString()));
            intent.putExtra("stops", numberOfStops.getText().toString());
            BookSelection.this.startActivity(intent);
        } catch (NullPointerException ex) {
            Toast toast = Toast.makeText(this, "Faltan campos: " + ex.getMessage(), Toast.LENGTH_SHORT);
            toast.show();
        } catch (Exception e) {
            Toast toast = Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void showHist(View view) {
        try {
            LinearLayout scroll = (LinearLayout) findViewById(R.id.scrollView);
            scroll.removeAllViews();
            for (Trip trip : this.tripList) {
                TextView tv = new TextView(this);
                tv.setText(trip.toString());
                scroll.addView(tv);
            }
        } catch (Exception e) {
            Toast toast = Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}