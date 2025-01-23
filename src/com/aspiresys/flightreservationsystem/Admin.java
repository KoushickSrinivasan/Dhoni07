package com.aspiresys.flightreservationsystem;

public class Admin {
	private String username;
    private String password;

    // Constructor to initialize Admin with default credentials
    public Admin() {
        this.username = "Admin";
        this.password = "Admin123";
    }

    // Method to authenticate Admin login
    public boolean authenticate(String inputUsername, String inputPassword) {
        return username.equals(inputUsername) && password.equals(inputPassword);
    }

    // Admin can add new flights
    public void addFlight(Flight flight, FlightReservation system) {
        system.addFlight(flight);
    }

    // Admin can view all flights
    public void viewAllFlights(FlightReservation system) {
        system.viewFlights();
    }

    // Admin can view all reservations
    public void viewAllReservations(FlightReservation system) {
        system.viewReservations();
    }
    
    public void deleteFlight(String flightNumber, FlightReservation system) {
        system.deleteFlight(flightNumber);
}
}