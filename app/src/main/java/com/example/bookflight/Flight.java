package com.example.bookflight;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.Random;

public class Flight {
    private String type;
    private String from;
    private String to;
    private String depart;
    private String comeback;
    private int tickets;
    private int stops;
    private Random random;

    public Flight(String type, String from, String to, String depart, String comeback, int tickets, int stops) {
        this.type = type;
        this.from = from;
        this.to = to;
        this.depart = depart;
        this.comeback = comeback;
        this.tickets = tickets;
        this.stops = stops;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Flight(String from, String to, String depart, String comeback, String stops) {
        random = new Random();
        String[] tipos = {"RoundTrip", "One Way"};
        this.from = from;
        this.to = to;
        this.depart = getTravelDate(LocalDate.parse(depart)).toString();
        this.comeback = getTravelDate(LocalDate.parse(comeback)).toString();
        this.type = tipos[random.nextInt(2)];
        switch (stops) {
            case "Non Stop":
                this.stops = 0;
                break;
            case "One Stop":
                this.stops = 1;
                break;
            case "2 Or More":
                this.stops = random.nextInt(5) + 2;
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getTravelDate(LocalDate date) {
        int departYear = date.getYear();
        int departMonth;
        int departDay;
        do {
            departMonth = random.nextInt(11) + 1;
        } while (departMonth < date.getMonth().getValue());
        switch (departMonth) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                departDay = random.nextInt(30) + 1;
                break;
            case 2:
                if ((departYear % 4 == 0) && ((departYear % 100 != 0) || (departYear % 400 == 0)))
                    departDay = random.nextInt(28) + 1;
                else
                    departDay = random.nextInt(27) + 1;
                break;
            default:
                departDay = random.nextInt(29) + 1;
        }
        return this.depart = departYear + "-" + departMonth + "-" + departDay;
    }

    @Override
    public String toString() {
        return "· " + this.type + "\n· From: " + this.from + "\n· To: " + this.to + "\n· Depart: " + this.depart + "\n· Return: "
                + this.comeback + "\n· Tickets: " + this.tickets + "\n· Stops: " + this.stops + "\n\n";
    }
}
