package org.example;


 import org.example.entities.Train;
 import org.example.entities.User;
 import org.example.service.UserBookingService;
 import org.example.util.UserServiceUtil;

 import java.io.IOException;
 import java.util.*;

   public class App {

    public static void main(String[] args) throws IOException {
        System.out.println("Running Train Booking System");
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        UserBookingService userBookingService;
        Train trainSelectedForBooking =new Train();

        try {
            userBookingService = new UserBookingService();
        } catch (IOException e) {
            throw e;
        }

        while(choice != 7) {
            System.out.println("Enter choice \n1. Add User \n2. login \n3. Fetch Bookings \n4. Search Train \n5. Book Seat \n6. Cancel my Booking \n7. Exit");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Password: ");
                    String password = sc.nextLine();

                    User newUser = new User(name,password,UserServiceUtil.hashPassword(password),new ArrayList<>(), UUID.randomUUID().toString());
                    userBookingService.signUp(newUser);
                    break;
                    case 2:
                        System.out.println("Enter Name");
                        String nameToLogin = sc.nextLine();
                        System.out.println("Enter Password");
                        String passwordToLogin = sc.nextLine();
                        if (userBookingService.loginUser(nameToLogin,passwordToLogin)){
                            System.out.println("\nLogin Successful\n");
                        }else {
                            System.out.println("\nLogin Failed\n");
                        }
                        break;
                        case 3:
                            System.out.println("\nFetching your bookings");
                            userBookingService.fetchBookings();
                            break;
                            case 4:
                                System.out.println("Enter Source Platform");
                                String sourcePlatform = sc.nextLine();
                                System.out.println("Enter Destination Platform");
                                String destinationPlatform = sc.nextLine();
                                List<Train> trains = userBookingService.findTrain(sourcePlatform,destinationPlatform);
                               if (trains.isEmpty()) {
                                   System.out.println("No trains found");
                                   break;
                               }
                                int index = 1;
                               for (Train train : trains) {
                                   System.out.println(("\n")+(index++)+" Train id : "+train.getTrainNumber());
                                   for (Map.Entry<String, String> entry : train.getStationTimes().entrySet()){
                                       System.out.println("station " + entry.getKey() + " time: " + entry.getValue());
                                   }
                               }
                                System.out.println("\nSelect a Train by typing 1,2,3....");
                                trainSelectedForBooking = trains.get(sc.nextInt()-1);
                                break;
                                    case 5:
                                        System.out.println("Select a seat out of these seats");
                                        List<List<Integer>> seats = userBookingService.fetchSeats(trainSelectedForBooking);
                                        if (seats.isEmpty()) {
                                            System.out.println("\nNo seats found may be full or you are not loged in.");
                                            break;
                                        }
                                        for (List<Integer> row: seats){
                                            for (Integer val: row){
                                                System.out.print(val+" ");
                                            }
                                            System.out.println();
                                        }
                                        System.out.println("Select the seat by typing the row and column");
                                        System.out.println("Enter the row");
                                        int row = sc.nextInt();
                                        System.out.println("Enter the column");
                                        int col = sc.nextInt();
                                        System.out.println("Booking your seat....");
                                        Boolean booked = userBookingService.bookTrainSeat(trainSelectedForBooking, row, col);
                                        if(booked.equals(Boolean.TRUE)){
                                            System.out.println("Booked! Enjoy your journey");
                                        }else{
                                            System.out.println("Can't book this seat");
                                        }
                                        break;
                case 6:
                    userBookingService.cancelBookingInteractive();
                    break;
                    case 7:
                        System.out.println("Goodbye!");
                        System.exit(0);

            }
        }
    }
}
