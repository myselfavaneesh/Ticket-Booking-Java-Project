package org.example.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.sql.Time;
import java.util.List;
import java.util.Map;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Train {
    private String trainID;
    private int trainNumber;
    private List<List<Integer>> seats;
    private Map<String , String> stationTimes;
    @JsonProperty("train_stations")
    private List<String> trainStations;


    public Train(String trainID, int trainNumber, List<List<Integer>> seats, Map<String, String> stationTimes, List<String> trainStations) {
        this.trainID = trainID;
        this.trainNumber = trainNumber;
        this.seats = seats;
        this.stationTimes = stationTimes;
        this.trainStations = trainStations;
    }

    public Train() {
    }

    public String getTrainID() {
        return trainID;
    }

    public void setTrainID(String trainID) {
        this.trainID = trainID;
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(int trainNumber) {
        this.trainNumber = trainNumber;
    }

    public List<List<Integer>> getSeats() {
        return seats;
    }

    public void setSeats(List<List<Integer>> seats) {
        this.seats = seats;
    }

    public Map<String, String> getStationTimes() {
        return stationTimes;
    }

    public void setStationTimes(Map<String, String> stationTimes) {
        this.stationTimes = stationTimes;
    }

    public List<String> getTrainStations() {
        return trainStations;
    }

    public void setTrainStations(List<String> trainStations) {
        this.trainStations = trainStations;
    }

    public String getTrainInfo() {
        return String.format("Train ID: %s Train No: %s", trainID, trainNumber);
    }
}
