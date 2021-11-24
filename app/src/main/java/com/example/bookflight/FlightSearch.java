package com.example.bookflight;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Locale;

/**
 * The type Flight search.
 */
public class FlightSearch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            Bundle data = this.getIntent().getExtras();
            if (!data.isEmpty())
                completeFields(data);

        } catch (NullPointerException ex) {
            System.out.println("Non hai perigo");
        }
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
        RadioGroup travelType = (RadioGroup) findViewById(R.id.travelType);
        travelType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                EditText comeback = (EditText) findViewById(R.id.comeback);
                if (checkedId == R.id.oneWay) {
                    comeback.setVisibility(View.GONE);
                } else {
                    comeback.setVisibility(View.VISIBLE);
                }
            }
        });
        historic.setOnClickListener(this::showHist);
    }

    /**
     * View data.
     *
     * @param view the view
     */
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
            intent.putExtra("travelType", travelTypeSelect.getText().toString());
            intent.putExtra("from", from.getText().toString());
            intent.putExtra("to", to.getText().toString());
            intent.putExtra("depart", depart.getText().toString());
            intent.putExtra("comeback", comeBack.getText().toString());
            intent.putExtra("passengers", Integer.parseInt(people.getText().toString()));
            intent.putExtra("stops", numberOfStops.getText().toString());
            FlightSearch.this.startActivity(intent);
        } catch (NullPointerException ex) {
            Toast.makeText(this, "Faltan campos: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast toast = Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /**
     * Show hist.
     *
     * @param view the view
     */
    public void showHist(View view) {
        try {
            this.startActivity(new Intent(this, ViewFlights.class));
        } catch (Exception e) {
            Toast toast = Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void completeFields(Bundle data) {
        if (data.get("travelType").equals("One Way")) {
            ((RadioGroup) this.findViewById(R.id.travelType)).check(R.id.oneWay);
            ((EditText) this.findViewById(R.id.comeback)).setVisibility(View.GONE);
        } else {
            ((RadioGroup) this.findViewById(R.id.travelType)).check(R.id.roundtrip);
            ((EditText) this.findViewById(R.id.comeback)).setText(data.getString("comeback"));
        }
        ;
        ((EditText) findViewById(R.id.from)).setText(data.getString("from"));
        ((EditText) findViewById(R.id.to)).setText(data.getString("to"));
        ((EditText) findViewById(R.id.depart)).setText(data.getString("depart"));
        ((EditText) findViewById(R.id.tickets)).setText(Integer.toString(data.getInt("passengers")));
        switch (Integer.parseInt(data.getString("stops"))) {
            case 0:
                ((RadioGroup) this.findViewById(R.id.numberOfStops)).check(R.id.nonStop);
                break;
            case 1:
                ((RadioGroup) this.findViewById(R.id.numberOfStops)).check(R.id.oneStop);
                break;
            default:
                ((RadioGroup) this.findViewById(R.id.numberOfStops)).check(R.id.twoStop);
                break;
        }
    }
}