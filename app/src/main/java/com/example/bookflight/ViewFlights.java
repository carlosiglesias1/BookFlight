package com.example.bookflight;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ViewFlights extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flights);
        Bundle parametros = this.getIntent().getExtras();
        if (parametros != null) {
            this.generateFlights(this.getCurrentFocus(), parametros);
        } else {
            Toast.makeText(this, "kk", Toast.LENGTH_SHORT).show();

        }
    }

    public void generateFlights(View view, Bundle parametros) {
        String traveltype = this.getIntent().getStringExtra("travelType");
        try {
            Toast toast = Toast.makeText(this, traveltype, Toast.LENGTH_SHORT);
            toast.show();
        } catch (NullPointerException e) {
            Toast.makeText(this, "Faltan campos", Toast.LENGTH_SHORT).show();
        }
    }
}