package com.example.bookflight;

/**
 * The type Reservation.
 */
public class Reservation {
    private String type;
    private String from;
    private String to;
    private String depart;
    private String comeback;
    private int tickets;
    private int stops;

    /**
     * Instantiates a new Reservation.
     *
     * @param type     the type
     * @param from     the from
     * @param to       the to
     * @param depart   the depart
     * @param comeback the comeback
     * @param tickets  the tickets
     * @param stops    the stops
     */
    public Reservation(String type, String from, String to, String depart, String comeback, int tickets, int stops) {
        this.type = type;
        this.from = from;
        this.to = to;
        this.depart = depart;
        this.comeback = comeback;
        this.tickets = tickets;
        this.stops = stops;
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

    @Override
    public String toString() {
        return "Reservation{" + "\n\t"+
        "type='" + type + "'\n\t" +
                "from='" + from + "'\n\t" +
                "to='" + to + "'\n\t" +
                "depart='" + depart + "'\n\t" +
                "comeback='" + comeback + "'\n\t" +
                "tickets=" + tickets + "'\n\t" +
                "stops=" + stops + "'\n" +
                '}';
    }
}
