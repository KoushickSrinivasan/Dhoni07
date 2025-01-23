package com.aspiresys.flightreservationsystem;
import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;
public class FlightReservationSystemApplication {
	 private FlightReservation system;
	    private Admin admin;
	    private Scanner scanner;

	    public FlightReservationSystemApplication() {
	        system = new FlightReservation();
	        admin = new Admin();
	        scanner = new Scanner(System.in);

	        // Reset available seats every day
	        Timer timer = new Timer();
	        timer.scheduleAtFixedRate(new TimerTask() {
	            @Override
	            public void run() {
	                system.resetSeats();  // Reset available seats to 30 every day
	            }
	        }, 0, 24 * 60 * 60 * 1000);  // 24 hours in milliseconds
	    }

	    public void start() {
	        while (true) {
	            System.out.println("\n--- Welcome to Flight Reservation System ---");
	            System.out.println("1. Admin Login");
	            System.out.println("2. View Flights");
	            System.out.println("3. Make Reservation");
	            System.out.println("4. View Reservation");
	            System.out.println("5. Exit");
	            System.out.print("Choose an option: ");
	            int choice = scanner.nextInt();
	            scanner.nextLine();  // Consume the newline

	            switch (choice) {
	                case 1:
	                    handleAdminLogin();
	                    break;
	                case 2:
	                    // Customer viewing available flights
	                    system.viewFlights();
	                    break;
	                case 3:
	                    handleReservation();
	                    break;
	                case 4:
	                    system.viewReservations();
	                    break;
	                case 5:
	                    System.out.println("Exiting system.");
	                    scanner.close();
	                    return;
	                default:
	                    System.out.println("Invalid choice. Try again.");
	            }
	        }
	    }

	    private void handleAdminLogin() {
	        System.out.print("Enter Admin Username: ");
	        String inputUsername = scanner.nextLine();
	        System.out.print("Enter Admin Password: ");
	        String inputPassword = scanner.nextLine();

	        // Authenticate Admin
	        if (admin.authenticate(inputUsername, inputPassword)) {
	            System.out.println("Admin login successful!");
	            handleAdminMenu();
	        } else {
	            System.out.println("Invalid Admin credentials.");
	        }
	    }

	    private void handleAdminMenu() {
	        while (true) {
	            System.out.println("\n--- Admin Menu ---");
	            System.out.println("1. Add Flight");
	            System.out.println("2. View Flights");
	            System.out.println("3. View Reservations");
	            System.out.println("4. Delete Flight");
	            System.out.println("5. Logout");
	            System.out.print("Choose an option: ");
	            int adminChoice = scanner.nextInt();
	            scanner.nextLine();  // Consume the newline

	            switch (adminChoice) {
	                case 1:
	                    addFlight();
	                    break;
	                case 2:
	                    admin.viewAllFlights(system);
	                    break;
	                case 3:
	                    admin.viewAllReservations(system);
	                    break;
	                case 4:
	                    deleteFlight();
	                    break;
	                case 5:
	                    System.out.println("Logging out...");
	                    return; // Exit the admin menu and return to the main menu
	                default:
	                    System.out.println("Invalid choice. Try again.");
	            }
	        }
	    }

	    private void addFlight() {
	        System.out.print("Enter flight number: ");
	        String flightNum = scanner.nextLine();
	        System.out.print("Enter boarding point: ");
	        String boardingPoint = scanner.nextLine();
	        System.out.print("Enter destination: ");
	        String destination = scanner.nextLine();
	        System.out.print("Enter departure time (HH:mm): ");
	        String departureTime = scanner.nextLine();
	        System.out.print("Enter arrival time (HH:mm): ");
	        String arrivalTime = scanner.nextLine();

	        system.addFlight(new Flight(flightNum, boardingPoint, destination, departureTime, arrivalTime));
	    }

	    private void deleteFlight() {
	        System.out.print("Enter flight number to delete: ");
	        String flightToDelete = scanner.nextLine();
	        admin.deleteFlight(flightToDelete, system);
	    }

	    private void handleReservation() {
	        System.out.print("Enter customer name: ");
	        String name = scanner.nextLine();
	        System.out.print("Enter customer email: ");
	        String email = scanner.nextLine();
	        System.out.print("Enter boarding point: ");
	        String customerBoardingPoint = scanner.nextLine();
	        System.out.print("Enter destination: ");
	        String customerDestination = scanner.nextLine();

	        // Search for flights based on customer input
	        Flight flight = system.searchFlightByRoute(customerBoardingPoint, customerDestination);

	        if (flight != null) {
	            // If a flight is found, proceed with reservation
	            System.out.print("Enter reservation date (dd-MM-yyyy): ");
	            String dateString = scanner.nextLine();

	            try {
	                SimpleDateFormat simpledataformat = new SimpleDateFormat("dd-MM-yyyy");
	                Date reservationDate = simpledataformat.parse(dateString);

	                // Convert java.util.Date to java.sql.Date
	                java.sql.Date sqlReservationDate = new java.sql.Date(reservationDate.getTime());

	                // Now call makeReservation with java.sql.Date
	                system.makeReservation(name, email, flight.getFlightNumber(), sqlReservationDate);
	            } catch (Exception e) {
	                System.out.println("Invalid date format. Please use dd-MM-yyyy.");
	            }
	        }
	    }
	}
		
