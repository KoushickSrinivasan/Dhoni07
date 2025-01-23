package com.aspiresys.flightreservationsystem;
public class Flight {
	private String flightNumber;
	private String boardingPoint; 
    private String destination;
    private String departureTime;
    private String arrivalTime;  
    private int availableSeats;

    public Flight(String flightNumber, String boardingPoint, String destination, String departureTime, String arrivalTime) {
        this.flightNumber = flightNumber;
        this.boardingPoint = boardingPoint; 
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime; 
        this.availableSeats = 30;
    }

    public String getFlightNumber() {
        return flightNumber;
    }
    public String getBoardingPoint() {
        return boardingPoint; 
    }

    public String getDestination() {
        return destination;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime; 
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public boolean reserveSeat() {
        if (availableSeats > 0) {
            availableSeats--;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Flight Number: " + flightNumber + ", Boarding point: " + boardingPoint + 
               ", Destination: " + destination + ", Departure Time: " + departureTime +
               ", Arrival Time: " + arrivalTime + ", Available Seats: " + availableSeats;
    }

}