package com.example.bookflight;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

/**
 * The type Login.
 */
public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextInputEditText username = this.findViewById(R.id.username);
        TextInputEditText pass = this.findViewById(R.id.passWord);
        Button login = this.findViewById(R.id.logIn);
        login.setOnClickListener(view -> {
            if (username.getText().toString().equalsIgnoreCase("Carlos")) {
                if (pass.getText().toString().equalsIgnoreCase("1234"))
                    this.finish();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setMessage("Campos incorrectos");
                builder.setNegativeButton("Cancelar", (dialog, which) -> {
                    dialog.dismiss();
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}