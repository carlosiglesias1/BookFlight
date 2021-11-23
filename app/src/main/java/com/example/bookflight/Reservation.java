package com.example.bookflight;

public class Reservation {
    private String type;
    private String from;
    private String to;
    private String depart;
    private String comeback;
    private int tickets;
    private int stops;

    public Reservation(String type, String from, String to, String depart, String comeback, int tickets, int stops) {
        this.type = type;
        this.from = from;
        this.to = to;
        this.depart = depart;
        this.comeback = comeback;
        this.tickets = tickets;
        this.stops = stops;
    }

    public int getStops() {
        return stops;
    }

    public void setStops(int stops) {
        this.stops = stops;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTickets() {
        return tickets;
    }

    public void setTickets(int tickets) {
        this.tickets = tickets;
    }

    public String getComeback() {
        return comeback;
    }

    public void setComeback(String comeback) {
        this.comeback = comeback;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
