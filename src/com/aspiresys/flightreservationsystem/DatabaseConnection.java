package com.aspiresys.flightreservationsystem;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseConnection {
	 public static Connection getConnection() throws SQLException {
	        String url = "jdbc:mysql://localhost:3306/FlightReservationDB";
	        String user = "root"; 
	        String password = "Aspire@123"; 
	        return DriverManager.getConnection(url, user, password);

}
}