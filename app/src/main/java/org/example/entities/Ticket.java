package org.example.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Ticket {
    private String ticketId;
    private String userID;
    private String source;
    private String destination;
    private Date dateOfTravel;
    private Train train;
    private Map<String,Integer> seats;

    public Ticket( String userID, String source, String destination, Date dateOfTravel, Train train,Map<String, Integer> seats) {
        this.ticketId = UUID.randomUUID().toString();
        this.userID = userID;
        this.source = source;
        this.destination = destination;
        this.dateOfTravel = dateOfTravel;
        this.train = train;
        this.seats = seats;
    }


    public Ticket(){}

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getSourse() {
        return source;
    }

    public void setSourse(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getDateOfTravel() {
        return dateOfTravel;
    }

    public void setDateOfTravel(Date dateOfTravel) {
        this.dateOfTravel = dateOfTravel;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public Map<String, Integer> getSeats() {
        return seats;
    }

    public void setSeats(Map<String, Integer> seats) {
        this.seats = seats;
    }


    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId='" + ticketId + '\'' +
                ", userID='" + userID + '\'' +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", dateOfTravel=" + dateOfTravel +
                ", train=" + train +
                ", seats=" + seats +
                '}';
    }
}
