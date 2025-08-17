 package org.example.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entities.Ticket;
import org.example.entities.Train;
import org.example.entities.User;
import org.example.util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.*;

 public class UserBookingService {
    private User user;
    private List<User> userList;
     TrainService trainService = new TrainService();
    public static final String USER_PATH = "D:\\Java Projects\\TicketBooking\\app\\src\\main\\java\\org\\example\\localDB\\user.json";
    public static final String TRAINS_PATH = "D:\\Java Projects\\TicketBooking\\app\\src\\main\\java\\org\\example\\localDB\\trains.json";
    private static final ObjectMapper mapper = new ObjectMapper();


    public UserBookingService(User user) throws IOException {
        this.user = user;
        File users = new File(USER_PATH);

        try {
            userList = getUserList();

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public UserBookingService() throws IOException {
        File users = new File(USER_PATH);
        userList = getUserList();
    }

    public List<User> getUserList() throws IOException {
        File users = new File(USER_PATH);

        if (!users.exists() || users.length() == 0) {
            return new ArrayList<>(); // no data yet
        }

        return mapper.readValue(users, new TypeReference<List<User>>() {});
    }


    public Boolean loginUser(String name, String password) {

         Optional<User> foundUser = userList.stream().filter(user1 -> {
             return user1.getName().equalsIgnoreCase(name) && UserServiceUtil.checkPassword(password, user1.getHashedPassword());
         }).findFirst();
        foundUser.ifPresent(value -> this.user = value);
         return foundUser.isPresent();
     }

     public Boolean signUp(User user1){
         try{
             userList.add(user1);
             saveUserListToFile();
             userList = getUserList();
             return Boolean.TRUE;
         }catch (IOException ex){
             return Boolean.FALSE;
         }
     }

     public void saveUserListToFile() throws IOException {
         File users = new File(USER_PATH);
         mapper.writeValue(users, userList);
     }

    public void fetchBookings() {
        if (user == null) {
            System.out.println("Login or Create Account \n");
            return;
        }
        Optional<User> userFetched = userList.stream()
                .filter(user1 -> user1.getName().equals(user.getName())
                        && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword()))
                .findFirst();
        if (userFetched.isPresent()) {
            if (userFetched.get().getTicketsBooked() == null || userFetched.get().getTicketsBooked().isEmpty()) {
                System.out.println("You have no bookings yet.");
            } else {
                userFetched.get().printTicketsBooked();
            }
        } else {
            System.out.println("No user found or invalid credentials.");
        }
    }


     public void cancelBooking(String ticketId) throws IOException {
         if (ticketId == null || ticketId.isEmpty()) {
             System.out.println("Ticket ID cannot be null or empty.");
             return;
         }
         if (user == null) {
             System.out.println("Login or Create Account \n");
             return;
         }

         Optional<User> foundUser = userList.stream().filter(user1 ->
                 user1.getName().equalsIgnoreCase(user.getName())
                         && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword())
         ).findFirst();

         if (foundUser.isPresent()) {
             List<Ticket> tickets = foundUser.get().getTicketsBooked();
             Optional<Ticket> found = tickets.stream()
                     .filter(ticket -> ticket.getTicketId().equals(ticketId))
                     .findFirst();

             if (found.isPresent()) {
                 Ticket ticket = found.get();
                 Train train = ticket.getTrain();

                 Map<String, Integer> bookedSeat = ticket.getSeats();
                 List<List<Integer>> trainSeats = fetchSeats(train);

                 int row = bookedSeat.get("row");
                 int seat = bookedSeat.get("seat");

                 if (trainSeats.get(row).get(seat) == 1) {
                     trainSeats.get(row).set(seat, 0);
                     train.setSeats(trainSeats);
                     trainService.addTrain(train);
                 }

                 tickets.remove(ticket);
                 foundUser.get().setTicketsBooked(tickets);

                 saveUserListToFile();
                 System.out.println("Ticket with ID " + ticketId + " has been canceled.");
             } else {
                 System.out.println("No ticket found with ID " + ticketId);
             }
         }
     }


     public void cancelBookingInteractive() throws IOException {
        Scanner s = new Scanner(System.in);
        System.out.print("Enter the ticket id to cancel: ");
        String id = s.next();
        cancelBooking(id);
    }

    public List<Train> findTrain (String source , String destination) throws IOException {
        TrainService trainService = new TrainService();
        return trainService.searchTrains(source, destination);
    }

    public List<List<Integer>> fetchSeats(Train train){
        if (user == null) {
            return new ArrayList<>();
        }
        if (train.getSeats() == null) {
            return new ArrayList<>();
        }
        return train.getSeats();
    }

    public Boolean bookTrainSeat(Train train,int row,int seat) throws IOException {
        List<List<Integer>> seats = train.getSeats();
        if (row >= 0 && row < seats.size() && seat >= 0 && seat < seats.get(row).size()) {
            if (seats.get(row).get(seat) == 0) {
                seats.get(row).set(seat, 1);
                train.setSeats(seats);
                trainService.addTrain(train);

                Optional<User> foundUser = userList.stream().filter(user1 -> {
                    return user1.getName().equalsIgnoreCase(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword());
                }).findFirst();
                Map<String,Integer> bookedSeats = new HashMap<>();
                bookedSeats.put("row",row);
                bookedSeats.put("seat",seat);
                Ticket newTicket = new Ticket(user.getUserID(),"","",new Date(),train,bookedSeats);
                foundUser.ifPresent(value -> {
                    List<Ticket> tickets = value.getTicketsBooked();
                    tickets.add(newTicket);
                    value.setTicketsBooked(tickets);
                });
                user = foundUser.get();
                saveUserListToFile();
                System.out.println("Train has been booked successfully.");
                return true; // Booking successful
            } else {
                System.out.println("Seat is already booked");
                return false; // Seat is already booked
            }
        } else {
            System.out.println("Train has been booking interrupted.Due to invalid row and seat.");
            return false; // Invalid row or seat index
        }
    }
}
