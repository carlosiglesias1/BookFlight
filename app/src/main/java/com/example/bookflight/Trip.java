package com.example.bookflight;

public class Trip {
    private String type;
    private String from;
    private String to;
    private String depart;
    private String comeback;
    private int tickets;
    private String stops;

    public Trip(String type, String from, String to, String depart, String comeback, int tickets, String stops) {
        this.type = type;
        this.from = from;
        this.to = to;
        this.depart = depart;
        this.comeback = comeback;
        this.tickets = tickets;
        this.stops = stops;
    }

    @Override
    public String toString() {
        return "· " + this.type + "\n· From: " + this.from + "\n· To: " + this.to + "\n· Depart: " + this.depart + "\n· Return: "
                + this.comeback + "\n· Tickets: " + this.tickets + "\n· Stops: " + this.stops + "\n\n";
    }
}
