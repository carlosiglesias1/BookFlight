package com.example.bookflight;

import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button send = (Button) this.findViewById(R.id.send);
        EditText login = (EditText) findViewById(R.id.login);
        EditText pass = (EditText) findViewById(R.id.pass);
        pass.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (login.getText().toString().equalsIgnoreCase("perros") && pass.getText().toString().equalsIgnoreCase("sarnosos")) {
                    Login.this.finish();
                    return true;
                }
            }
            return false;
        });
        send.setOnClickListener(view -> {
            if (login.getText().toString().equalsIgnoreCase("perros") && pass.getText().toString().equalsIgnoreCase("sarnosos"))
                Login.this.finish();
        });
    }
}