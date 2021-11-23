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
    public Flight(String tipo, String from, String to, String depart, String comeback, String stops) {
        this.random = new Random();
        this.from = from;
        this.to = to;
        this.depart = getTravelDate(LocalDate.parse(depart)).toString();
        do {
            this.comeback = getTravelDate(LocalDate.parse(comeback)).toString();
        } while (this.depart.equalsIgnoreCase(this.comeback));
        this.type = tipo;
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
        this.tickets = this.random.nextInt(100) + 1;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getTravelDate(LocalDate date) {
        this.random = new Random();
        int departYear = date.getYear();
        int departMonth;
        int departDay;
        do {
            departMonth = this.random.nextInt(11) + 1;
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
                    departDay = this.random.nextInt(28) + 1;
                else
                    departDay = this.random.nextInt(27) + 1;
                break;
            default:
                departDay = this.random.nextInt(29) + 1;
                break;
        }
        return departYear + "-" + departMonth + "-" + departDay;
    }

    @Override
    public String toString() {
        return "· " + this.type + "\n· From: " + this.from + "\n· To: " + this.to + "\n· Depart: " + this.depart + "\n· Return: "
                + this.comeback + "\n· Tickets: " + this.tickets + "\n· Stops: " + this.stops + "\n\n";
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getComeback() {
        return comeback;
    }

    public void setComeback(String comeback) {
        this.comeback = comeback;
    }

    public int getTickets() {
        return tickets;
    }

    public void setTickets(int tickets) {
        this.tickets = tickets;
    }

    public int getStops() {
        return stops;
    }

    public void setStops(int stops) {
        this.stops = stops;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
