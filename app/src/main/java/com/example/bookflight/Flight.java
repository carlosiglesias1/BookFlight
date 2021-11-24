package com.example.bookflight;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Random;

/**
 * The type Flight.
 */
public class Flight {
    private String type;
    private String from;
    private String to;
    private String depart;
    private String comeback;
    private int tickets;
    private int stops;
    private Random random;

    /**
     * Instantiates a new Flight.
     *
     * @param type     the type
     * @param from     the from
     * @param to       the to
     * @param depart   the depart
     * @param comeback the comeback
     * @param tickets  the tickets
     * @param stops    the stops
     */
    public Flight(String type, String from, String to, String depart, String comeback, int tickets, int stops) {
        this.type = type;
        this.from = from;
        this.to = to;
        this.depart = depart;
        this.comeback = comeback;
        this.tickets = tickets;
        this.stops = stops;
    }

    /**
     * Instantiates a new Flight.
     *
     * @param tipo     the tipo
     * @param from     the from
     * @param to       the to
     * @param depart   the depart
     * @param comeback the comeback
     * @param stops    the stops
     * @throws DateTimeException the date time exception
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Flight(String tipo, String from, String to, String depart, String comeback, String stops) throws DateTimeException {
        this.random = new Random();
        this.type = tipo;
        this.from = from;
        this.to = to;
        this.depart = getTravelDate(LocalDate.parse(depart)).toString();
        if (!tipo.equalsIgnoreCase("One Way"))
            do {
                this.comeback = getTravelDate(LocalDate.parse(comeback)).toString();
            } while (this.depart.equalsIgnoreCase(this.comeback) || !LocalDate.parse(this.comeback).isAfter(LocalDate.parse(this.depart)));
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

    /**
     * Gets travel date.
     *
     * @param date the date
     * @return the travel date
     */
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
        if (Integer.toString(departMonth).length() < 2 && Integer.toString(departDay).length() < 2)
            return departYear + "-0" + departMonth + "-0" + departDay;
        else if (Integer.toString(departMonth).length() < 2)
            return departYear + "-0" + departMonth + "-" + departDay;
        else if (Integer.toString(departDay).length() < 2)
            return departYear + "-" + departMonth + "-0" + departDay;
        else
            return departYear + "-" + departMonth + "-" + departDay;
    }

    @Override
    public String toString() {
        return "· " + this.type + "\n· From: " + this.from + "\n· To: " + this.to + "\n· Depart: " + this.depart + "\n· Return: "
                + this.comeback + "\n· Tickets: " + this.tickets + "\n· Stops: " + this.stops + "\n\n";
    }

    /**
     * Gets to.
     *
     * @return the to
     */
    public String getTo() {
        return to;
    }

    /**
     * Sets to.
     *
     * @param to the to
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * Gets from.
     *
     * @return the from
     */
    public String getFrom() {
        return from;
    }

    /**
     * Sets from.
     *
     * @param from the from
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * Gets depart.
     *
     * @return the depart
     */
    public String getDepart() {
        return depart;
    }

    /**
     * Sets depart.
     *
     * @param depart the depart
     */
    public void setDepart(String depart) {
        this.depart = depart;
    }

    /**
     * Gets comeback.
     *
     * @return the comeback
     */
    public String getComeback() {
        return comeback;
    }

    /**
     * Sets comeback.
     *
     * @param comeback the comeback
     */
    public void setComeback(String comeback) {
        this.comeback = comeback;
    }

    /**
     * Gets tickets.
     *
     * @return the tickets
     */
    public int getTickets() {
        return tickets;
    }

    /**
     * Sets tickets.
     *
     * @param tickets the tickets
     */
    public void setTickets(int tickets) {
        this.tickets = tickets;
    }

    /**
     * Gets stops.
     *
     * @return the stops
     */
    public int getStops() {
        return stops;
    }

    /**
     * Sets stops.
     *
     * @param stops the stops
     */
    public void setStops(int stops) {
        this.stops = stops;
    }

    /**
     * Gets random.
     *
     * @return the random
     */
    public Random getRandom() {
        return random;
    }

    /**
     * Sets random.
     *
     * @param random the random
     */
    public void setRandom(Random random) {
        this.random = random;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
    }
}
