package com.example.bookflight;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * The type View flights.
 */
public class ViewFlights extends AppCompatActivity {

    /**
     * The Db.
     */
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
            this.viewFlights();
            System.out.println(parametros.getString("traveltype"));
        } else {
            this.showHist();
        }
    }

    /**
     * La información de los vuelos se procesa aquí, un listview sencillo en el que se cargan los vuelos
     * previamente generados
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void viewFlights() {
        try {
            this.generateFlights();
            ListView lv = (ListView) findViewById(R.id.flightList);
            ArrayAdapter<Flight> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, this.flights);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener((adapterView, view1, i, l) -> {
                Flight flight = this.flights.get(i);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setTitle("¿Reservar vuelo?\nPlazas disponibles: " + flight.getTickets());
                builder.setPositiveButton("RESERVAR", (dialogInterface, j) -> {
                    db.collection("vuelos").document("vuelo: " + lv.getSelectedItem()).get().addOnSuccessListener(resultado -> {
                        try {
                            flight.setTickets(flight.getTickets() - ViewFlights.this.getIntent().getIntExtra("passengers", 0));
                            setData("vuelos", i, flight);
                            try {
                                this.db.collection("history").get().addOnSuccessListener(queryDocumentSnapshots -> {
                                    List<DocumentSnapshot> documentSnapshots = queryDocumentSnapshots.getDocuments();
                                    if (documentSnapshots.size() == 0)
                                        setData("history", 0, flight);
                                    else {
                                        DocumentSnapshot lastDocument = documentSnapshots.get(documentSnapshots.size() - 1);
                                        int numberOfHist = Integer.parseInt(lastDocument.getId().substring(7));
                                        setData("history", numberOfHist + 1, flight);
                                    }
                                });

                            } catch (NullPointerException ex) {
                                System.out.println("null");
                                setData("history", 0, flight);
                            }
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
        } catch (DateTimeException e) {
            e.printStackTrace();
        }
    }

    /**
     * Genera los vuelos con las fechas y los billetes aleatorios según los parámetros que
     * introduzcamos.
     *
     * @throws DateTimeException suele ser bastante sensible a los parámetros de tipo fecha, durante
     *                           el desarrollo fue necesario implementarlo para poder tener algo de control de errores
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void generateFlights() throws DateTimeException {
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

    /**
     * Da de alta la información en la BBDD para procesarlo mientras la aplicación esté en uso, dado
     * que al salir sólo se guardará el historial.
     *
     * @param collection la colección de fireStore en la que vamos a insertar los datos
     * @param index      el índice de inserción
     * @param obj        el "vuelo", en realidad será un objeto que luego se procesará
     */
    public void setData(String collection, int index, Object obj) {
        Map<String, String> flightMap = new HashMap<>();
        if (obj instanceof Flight) {
            Flight flight = (Flight) obj;
            flightMap.put("type", flight.getType());
            flightMap.put("from", flight.getFrom());
            flightMap.put("to", flight.getTo());
            flightMap.put("depart", flight.getDepart());
            flightMap.put("comeback", flight.getComeback());
            flightMap.put("stops", Integer.toString(flight.getStops()));
            flightMap.put("billetes", Integer.toString(ViewFlights.this.getIntent().getIntExtra("passengers", 0)));
        } else if (obj instanceof Reservation) {
            Reservation flight = (Reservation) obj;
            flightMap.put("type", flight.getType());
            flightMap.put("from", flight.getFrom());
            flightMap.put("to", flight.getTo());
            flightMap.put("depart", flight.getDepart());
            flightMap.put("comeback", flight.getComeback());
            flightMap.put("stops", Integer.toString(flight.getStops()));
            flightMap.put("billetes", Integer.toString(flight.getTickets()));
        }
        db.collection(collection).document("vuelo: " + index).set(flightMap);
    }

    /**
     * Muestra el historial de busquedas de nuestro usuario
     */
    @SuppressLint("NonConstantResourceId")
    public void showHist() {
        List<Reservation> reservations = new ArrayList<>();
        ListView lv = findViewById(R.id.flightList);
        ArrayAdapter<Reservation> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, reservations);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener((parent, view, position, id) -> {
            PopupMenu popupMenu = new PopupMenu(ViewFlights.this, view);
            popupMenu.inflate(R.menu.popup_menu);
            popupMenu.show();
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()) {
                    case R.id.moreItems:
                        reservations.get(position).setTickets((reservations.get(position).getTickets() + 1));
                        setData("history", position, reservations.get(position));
                        adapter.notifyDataSetChanged();
                        return true;
                    case R.id.lessItems:
                        reservations.get(position).setTickets((reservations.get(position).getTickets() - 1));
                        setData("history", position, reservations.get(position));
                        adapter.notifyDataSetChanged();
                        return true;
                    case R.id.eliminateFlight:
                        Reservation eliminate = reservations.get(position);
                        db.collection("history").get().addOnSuccessListener(queryDocumentSnapshots -> {
                            db.collection("history").document("vuelo: "+position).delete();
                            List<DocumentSnapshot> documentSnapshots = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot document : documentSnapshots) {
                                int posDoc = Integer.parseInt(document.getId().substring(7));
                                if (posDoc > position) {
                                    Reservation update = new Reservation(document.getString("type"), document.getString("from"), document.getString("to"), document.getString("depart"), document.getString("comeback"), Integer.parseInt(document.getString("billetes")), Integer.parseInt(document.getString("stops")));
                                    setData("history", posDoc - 1, update);
                                    db.collection("history").document(document.getId()).delete();
                                }
                            }
                        });
                        reservations.remove(position);
                        adapter.notifyDataSetChanged();
                        return true;
                    case R.id.useData:
                        Reservation selected = reservations.get(position);
                        Intent intent = new Intent(ViewFlights.this, FlightSearch.class);
                        intent.putExtra("travelType", selected.getType());
                        intent.putExtra("from", selected.getFrom());
                        intent.putExtra("to", selected.getTo());
                        intent.putExtra("depart", selected.getDepart());
                        intent.putExtra("comeback", selected.getComeback());
                        intent.putExtra("passengers", selected.getTickets());
                        intent.putExtra("stops", Integer.toString(selected.getStops()));
                        ViewFlights.this.startActivity(intent);
                        ViewFlights.this.finish();
                        return true;
                    default:
                        return false;
                }
            });
        });
        gerReservations(adapter, reservations);
    }

    /*
     * En este método proceso el historial de reservas de la base de datos de FireBase, para poder imprimirselas al usuario
     * @return Estaba pensado para devolver la lista de las reservas de la base de datos,
     *   lamentablemente, debe de haber un bug con firebase y no me devolvía bien la lista;
     *   con lo que es una función vacía
     * */
    private void gerReservations(ArrayAdapter<Reservation> adapter, List<Reservation> reservations) {
        this.db.collection("history")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<DocumentSnapshot> doc = queryDocumentSnapshots.getDocuments();
                    for (int i = 0; i < doc.size(); i++) {
                        String type = doc.get(i).getString("type");
                        String from = doc.get(i).getString("from");
                        String to = doc.get(i).getString("to");
                        String depart = doc.get(i).getString("depart");
                        String comeback = doc.get(i).getString("comeback");
                        int tickets = Integer.parseInt(doc.get(i).getString("billetes"));
                        int stops = Integer.parseInt(doc.get(i).getString("stops"));
                        reservations.add(new Reservation(type, from, to, depart, comeback, tickets, stops));
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}
/*AlertDialog.Builder builder = new AlertDialog.Builder(ViewFlights.this);
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setTitle("¿Quieres modificar este viaje o reutilizar la busqueda?");
                builder.setPositiveButton("SUMAR PASAJEROS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reservations.get(position).setTickets((reservations.get(position).getTickets() + 1));
                        adapter.notifyDataSetChanged();
                    }
                }).setNegativeButton("ELIMINAR VIAJE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reservations.remove(position);
                        ViewFlights.this.db.collection("history").document("vuelo: " + (position - 1)).delete();
                        adapter.notifyDataSetChanged();
                    }
                }).setNeutralButton("USAR DATOS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Reservation selected = reservations.get(position);
                        Intent intent = new Intent(ViewFlights.this, FlightSearch.class);
                        intent.putExtra("travelType", selected.getType());
                        intent.putExtra("from", selected.getFrom());
                        intent.putExtra("to", selected.getTo());
                        intent.putExtra("depart", selected.getDepart());
                        intent.putExtra("comeback", selected.getComeback());
                        intent.putExtra("passengers", selected.getTickets());
                        intent.putExtra("stops", Integer.toString(selected.getStops()));
                        ViewFlights.this.startActivity(intent);
                        ViewFlights.this.finish();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();*/