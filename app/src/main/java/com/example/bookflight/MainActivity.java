package com.example.bookflight;

import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity {
    /**
     * The Db.
     */
    FirebaseFirestore db;

    
    /** 
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button send = (Button) this.findViewById(R.id.logIn);
        EditText login = (EditText) findViewById(R.id.username);
        EditText pass = (EditText) findViewById(R.id.passWord);
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
            }
        });
        this.db = FirebaseFirestore.getInstance();
        this.db.collection("vuelos").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                    db.collection("vuelos").document(documentSnapshot.getId()).delete();
                }
            }
        });
    }
}