package com.example.bookflight;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ViewFlights extends AppCompatActivity {

    FirebaseFirestore db;
    private ArrayList<Flight> flights;
    private Random random;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flights);
        this.db = FirebaseFirestore.getInstance();
        Bundle parametros = this.getIntent().getExtras();
        this.random = new Random();
        if (parametros != null) {
            this.viewFlights(this.getCurrentFocus());
            System.out.println(parametros.getString("traveltype"));
        } else {
            this.showHist();
            Toast.makeText(this, "kk", Toast.LENGTH_SHORT).show();

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void viewFlights(View view) {
        try {
            this.generateFlights();
            ListView lv = (ListView) findViewById(R.id.flightList);
            ArrayAdapter<Flight> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, this.flights);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener((adapterView, view1, i, l) -> {
                Flight flight = this.flights.get(i);
                LayoutInflater li = (LayoutInflater) ViewFlights.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setTitle("¿Reservar vuelo?\nPlazas disponibles: " + flight.getTickets());
                /*View inflado = li.inflate(R.layout.reservation_dialog, null);
                builder.setView(inflado);
                findViewById(R.id.moreTickets).setOnClickListener(view2 -> {
                    int value = Integer.parseInt(((TextView) findViewById(R.id.tickets)).getText().toString());
                    value++;
                    ((TextView) findViewById(R.id.tickets)).setText(Integer.toString(value));
                });
                findViewById(R.id.lessTickets).setOnClickListener(view2 -> {
                    int value = Integer.parseInt(((TextView) findViewById(R.id.tickets)).getText().toString());
                    value--;
                    ((TextView) findViewById(R.id.tickets)).setText(Integer.toString(value));
                });*/
                builder.setPositiveButton("RESERVAR", (dialogInterface, j) -> {
                    db.collection("vuelos").document("vuelo: " + lv.getSelectedItem()).get().addOnSuccessListener(resultado -> {
                        try {
                            flight.setTickets(flight.getTickets() - ViewFlights.this.getIntent().getIntExtra("passengers", 0));
                            setData("vuelos", i, flight);
                            setData("history", i, flight);
                            adapter.notifyDataSetChanged();
                        } catch (NullPointerException e) {
                            Toast.makeText(this, "No los pilla", Toast.LENGTH_SHORT).show();
                        }
                    });
                }).setNegativeButton("CANCELAR", (dialogInterface, i1) -> {
                    dialogInterface.dismiss();
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            });
        } catch (NullPointerException e) {
            Toast.makeText(this, "Faltan campos: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void generateFlights() {
        this.flights = new ArrayList<>();
        String traveltype = this.getIntent().getStringExtra("travelType");
        String from = this.getIntent().getStringExtra("from");
        String to = this.getIntent().getStringExtra("to");
        String depart = this.getIntent().getStringExtra("depart");
        String comeback = this.getIntent().getStringExtra("comeback");
        String stops = this.getIntent().getStringExtra("stops");
        for (int i = 0; i < random.nextInt(8) + 1; i++) {
            if (this.flights.add(new Flight(traveltype, from, to, depart, comeback, stops))) {
                setData("vuelos", i, flights.get(i));
            } else
                Toast.makeText(this, "No se ha podido encontrar el vuelo", Toast.LENGTH_SHORT).show();
        }
    }

    public void setData(String collection, int index, Flight flight) {
        Map<String, String> flightMap = new HashMap<>();
        flightMap.put("type", flight.getType());
        flightMap.put("from", flight.getFrom());
        flightMap.put("to", flight.getTo());
        flightMap.put("depart", flight.getDepart());
        flightMap.put("comeback", flight.getComeback());
        flightMap.put("stops", Integer.toString(flight.getStops()));
        flightMap.put("billetes", Integer.toString(flight.getTickets()));
        db.collection(collection).document("vuelo: " + index).set(flightMap);
    }

    public void showHist(){

    }

    private List<Reservation> gerReservations(){
        return new ArrayList<>();
    }
}