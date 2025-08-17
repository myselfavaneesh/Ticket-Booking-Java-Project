package org.example.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entities.Train;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TrainService {
    private List<Train> trainList;
    private ObjectMapper mapper = new ObjectMapper();
    public static final String TRAINS_PATH = "D:\\Java Projects\\TicketBooking\\app\\src\\main\\java\\org\\example\\localDB\\trains.json";

    public List<Train> searchTrains(String source, String destination) {
        if (trainList == null) {
            System.out.println("No Trains found");
            return trainList;
        }
        return trainList.stream().filter(train -> validTrain(train, source, destination)).collect(Collectors.toList());
    }

    public List<Train> getTrainList() throws IOException {
        File trains = new File(TRAINS_PATH);
        return mapper.readValue(trains, new TypeReference<List<Train>>() {
        });
    }

    public TrainService() throws IOException {
        if (getTrainList() == null) {
            trainList = new ArrayList<>();
        }else  {
            trainList = getTrainList();
        }
    }

    private boolean validTrain(Train train, String source, String destination) {
        List<String> stationOrder = train.getTrainStations();

        int sourceIndex = stationOrder.indexOf(source.toLowerCase());
        int destinationIndex = stationOrder.indexOf(destination.toLowerCase());

        return sourceIndex != -1 && destinationIndex != -1 && sourceIndex < destinationIndex;
    }

    public void addTrain(Train newTrain) {
        // Check if a train with the same trainId already exists
        Optional<Train> existingTrain = trainList.stream()
                .filter(train -> train.getTrainNumber() == newTrain.getTrainNumber())
                .findFirst();

        if (existingTrain.isPresent()) {
            // If a train with the same trainId exists, update it instead of adding a new one
            updateTrain(newTrain);
        } else {
            // Otherwise, add the new train to the list
            trainList.add(newTrain);
            saveTrainListToFile();
        }
    }

    public void updateTrain(Train updatedTrain) {
        // Find the index of the train with the same trainId
        OptionalInt index = IntStream.range(0, trainList.size())
                .filter(i -> trainList.get(i).getTrainNumber() == updatedTrain.getTrainNumber())
                .findFirst();

        if (index.isPresent()) {
            // If found, replace the existing train with the updated one
            trainList.set(index.getAsInt(), updatedTrain);
            saveTrainListToFile();
        } else {
            // If not found, treat it as adding a new train
            addTrain(updatedTrain);
        }
    }

    private void saveTrainListToFile() {
        try {
            mapper.writeValue(new File(TRAINS_PATH), trainList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}