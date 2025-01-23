package com.aspiresys.flightreservationsystem;
import java.util.ArrayList;
import java.sql.*;
public class FlightReservation {

	public void addFlight(Flight flight) {
        String query = "INSERT INTO flights (flight_number, boarding_point, destination, departure_time, arrival_time, available_seats) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setString(1, flight.getFlightNumber());
            statement.setString(2, flight.getBoardingPoint());
            statement.setString(3, flight.getDestination());
            statement.setString(4, flight.getDepartureTime());
            statement.setString(5, flight.getArrivalTime());
            statement.setInt(6, flight.getAvailableSeats());
            
            statement.executeUpdate();
            flights.add(flight);  // Add the flight to the in-memory list for testing
            System.out.println("Flight added successfully.");
        } catch (SQLException exception) {
            System.out.println("Error adding flight: " + exception.getMessage());
        }
    }

    // Method to view all flights in the database
    public void viewFlights() {
        String query = "SELECT * FROM flights";
        
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            
            while (resultSet.next()) {
                System.out.println("Flight Number: " + resultSet.getString("flight_number") +
                        ", Boarding point: " + resultSet.getString("boarding_point") +
                        ", Destination: " + resultSet.getString("destination") +
                        ", Departure Time: " + resultSet.getString("departure_time") +
                        ", Arrival Time: " + resultSet.getString("arrival_time") +
                        ", Available Seats: " + resultSet.getInt("available_seats"));
            }
        } catch (SQLException exception) {
            System.out.println("Error retrieving flights: " + exception.getMessage());
        }
    }

    // Method to search for a flight by boarding point and destination
    public Flight searchFlightByRoute(String boardingPoint, String destination) {
        String query = "SELECT * FROM flights WHERE boarding_point = ? AND destination = ?";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, boardingPoint);
            statement.setString(2, destination);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // If a flight is found, create and return a Flight object
                String flightNumber = resultSet.getString("flight_number");
                String departureTime = resultSet.getString("departure_time");
                String arrivalTime = resultSet.getString("arrival_time");
                int availableSeats = resultSet.getInt("available_seats");
                
                return new Flight(flightNumber, boardingPoint, destination, departureTime, arrivalTime);
            } else {
                System.out.println("No flights found from " + boardingPoint + " to " + destination);
            }
        } catch (SQLException exception) {
            System.out.println("Error searching flights: " + exception.getMessage());
        }
        return null;
    }

    // Method to make a reservation for a customer
    public void makeReservation(String customerName, String customerEmail, String flightNumber, java.sql.Date reservationDate) {
        String flightQuery = "SELECT * FROM flights WHERE flight_number = ?";
        String reservationQuery = "INSERT INTO reservations (customer_name, customer_email, flight_number, reservation_date) VALUES (?, ?, ?, ?)";
        String updateSeatsQuery = "UPDATE flights SET available_seats = available_seats - 1 WHERE flight_number = ?";
        
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Check if the flight exists
            PreparedStatement flightStatement = connection.prepareStatement(flightQuery);
            flightStatement.setString(1, flightNumber);
            ResultSet resultSet = flightStatement.executeQuery();

            if (!resultSet.next()) {
                System.out.println("Flight not found.");
                return;
            }

            // Check if there are available seats
            int availableSeats = resultSet.getInt("available_seats");
            if (availableSeats <= 0) {
                System.out.println("No available seats.");
                return;
            }

            // Insert the reservation into the database
            PreparedStatement reservationStatement = connection.prepareStatement(reservationQuery);
            reservationStatement.setString(1, customerName);
            reservationStatement.setString(2, customerEmail);
            reservationStatement.setString(3, flightNumber);
            reservationStatement.setDate(4, reservationDate);  
            reservationStatement.executeUpdate();

            // Update available seats
            PreparedStatement updateSeatsStatement = connection.prepareStatement(updateSeatsQuery);
            updateSeatsStatement.setString(1, flightNumber);
            updateSeatsStatement.executeUpdate();

            System.out.println("Reservation successful for " + customerName + " on flight " +flightNumber);
        } catch (SQLException exception) {
            System.out.println("Error making reservation: " + exception.getMessage());
        }
    }

    // Method to view all reservations
    public void viewReservations() {
        String query = "SELECT * FROM reservations";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            
            while (resultSet.next()) {
                System.out.println("Reservation ID: " + resultSet.getInt("reservation_id") +
                        ", Customer Name: " + resultSet.getString("customer_name") +
                        ", Flight Number: " + resultSet.getString("flight_number") +
                        ", Reservation Date: " + resultSet.getDate("reservation_date"));
            }
        } catch (SQLException exception) {
            System.out.println("Error retrieving reservations: " + exception.getMessage());
        }
    }

    // Method to delete a flight (and associated reservations)
    public void deleteFlight(String flightNumber) {
        String deleteReservationsQuery = "DELETE FROM reservations WHERE flight_number = ?";
        String deleteFlightQuery = "DELETE FROM flights WHERE flight_number = ?";
        
        try (Connection connection = DatabaseConnection.getConnection()) {
            // First, delete the reservations for the flight
            try (PreparedStatement deleteReservationsStatement = connection.prepareStatement(deleteReservationsQuery)) {
                deleteReservationsStatement.setString(1, flightNumber);
                int reservationsDeleted = deleteReservationsStatement.executeUpdate();
                System.out.println(reservationsDeleted + " reservations deleted.");
            }

            // Then, delete the flight
            try (PreparedStatement deleteFlightStatement = connection.prepareStatement(deleteFlightQuery)) {
                deleteFlightStatement.setString(1, flightNumber);
                int rowsAffected = deleteFlightStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Flight " + flightNumber + " has been deleted successfully.");
                } else {
                    System.out.println("Flight " + flightNumber + " not found.");
                }
            }
        } catch (SQLException exception) {
            System.out.println("Error deleting flight: " + exception.getMessage());
        }
    }

    // Method to reset available seats for all flights every day (sets to 30)
    public void resetSeats() {
        String query = "UPDATE flights SET available_seats = 30";
        
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.executeUpdate();
            
        } catch (SQLException exception) {
            
        }
    }
}
