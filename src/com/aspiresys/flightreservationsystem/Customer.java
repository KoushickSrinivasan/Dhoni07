package com.aspiresys.flightreservationsystem;

public class Customer {
	 private String username;
	 private String email;

	    public Customer(String username, String email) {
	        this.username = username;
	        this.email = email;
	    }

	    public String getName() {
	        return username;
	    }

	    public String getEmail() {
	        return email;
	    }

	    @Override
	    public String toString() {
	        return "Name: " + username + ", Email: " + email;
	    }
}
