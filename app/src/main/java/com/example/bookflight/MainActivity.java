package com.example.bookflight;

import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button send = (Button) this.findViewById(R.id.send);
        EditText login = (EditText) findViewById(R.id.login);
        EditText pass = (EditText) findViewById(R.id.pass);
        pass.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                if (login.getText().toString().equalsIgnoreCase("perros") && pass.getText().toString().equalsIgnoreCase("sarnosos")) {
                    Intent intent = new Intent(this, FlightSearch.class);
                    MainActivity.this.startActivity(intent);
                    MainActivity.this.finish();
                    return true;
                }
            }
            return false;
        });
        send.setOnClickListener(view -> {
            if (login.getText().toString().equalsIgnoreCase("perros") && pass.getText().toString().equalsIgnoreCase("sarnosos")) {
                Intent intent = new Intent(this, FlightSearch.class);
                MainActivity.this.startActivity(intent);
                MainActivity.this.finish();
            }        });
    }
}