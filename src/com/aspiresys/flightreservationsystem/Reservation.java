package com.aspiresys.flightreservationsystem;
import java.util.Date;
public class Reservation {
	 private Customer customer;
	    private Flight flight;
	    private Date reservationDate;  // New field for reservation date
	    
	    public Reservation(Customer customer, Flight flight, Date reservationDate) {
	        this.customer = customer;
	        this.flight = flight;
	        this.reservationDate = reservationDate;
	    }

	    public Date getReservationDate() {
	        return reservationDate;
	    }

	    @Override
	    public String toString() {
	        return "Reservation [Customer: " + customer.getName() + ", Flight: " + flight.getFlightNumber() + ", Reservation Date: " + reservationDate + "]";
	    }
	}
	    