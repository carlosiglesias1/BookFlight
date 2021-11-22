package com.example.bookflight;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
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
        if (parametros != null) {
            this.viewFlights(this.getCurrentFocus(), parametros);
        } else {
            Toast.makeText(this, "kk", Toast.LENGTH_SHORT).show();

        }
        random = new Random();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void viewFlights(View view, Bundle parametros) {
        String traveltype = this.getIntent().getStringExtra("travelType");
        String from = this.getIntent().getStringExtra("from");
        String to = this.getIntent().getStringExtra("to");
        String depart = this.getIntent().getStringExtra("depart");
        String comeback = this.getIntent().getStringExtra("comeback");
        String stops = this.getIntent().getStringExtra("stops");
        try {
            for (int i = 0; i < random.nextInt(8) + 1; i++) {
                flights.add(new Flight(from, to, depart, comeback, stops));
            }
        } catch (NullPointerException e) {
            Toast.makeText(this, "Faltan campos", Toast.LENGTH_SHORT).show();
        }
    }
}