package com.example.bookflight;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class ViewFlights extends AppCompatActivity {

    private ArrayList<Flight> flights;
    private Random random;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flights);
        Bundle parametros = this.getIntent().getExtras();
        this.random = new Random();
        if (parametros != null) {
            this.viewFlights(this.getCurrentFocus());
        } else {
            Toast.makeText(this, "kk", Toast.LENGTH_SHORT).show();

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void viewFlights(View view) {
        try {
            generateFlights();
            ListView lv = (ListView) findViewById(R.id.flightList);
            ArrayAdapter<Flight> adapter = new ArrayAdapter<Flight>(this, android.R.layout.simple_list_item_1, flights);
            lv.setAdapter(adapter);
            lv.setOnClickListener(view1 -> {
                Flight selected = (Flight) lv.getSelectedItem();
                selected.setTickets(selected.getTickets() - Integer.parseInt(this.getIntent().getStringExtra("tickets")));
            });
        } catch (NullPointerException e) {
            Toast.makeText(this, "Faltan campos: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void generateFlights() {
        String traveltype = this.getIntent().getStringExtra("travelType");
        String from = this.getIntent().getStringExtra("from");
        String to = this.getIntent().getStringExtra("to");
        String depart = this.getIntent().getStringExtra("depart");
        String comeback = this.getIntent().getStringExtra("comeback");
        String stops = this.getIntent().getStringExtra("stops");
        for (int i = 0; i < random.nextInt(8) + 1; i++) {
            this.flights.add(new Flight(traveltype, from, to, depart, comeback, stops));
        }
    }
}