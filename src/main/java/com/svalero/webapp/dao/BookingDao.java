package com.svalero.webapp.dao;


import com.svalero.webapp.domain.Booking;
import com.svalero.webapp.domain.Task;
import com.svalero.webapp.domain.User;


import java.sql.*;
import java.util.List;
import java.util.UUID;

public class BookingDao {
    private Connection connection;

    public BookingDao(Connection connection) {
        this.connection = connection;
    }

    public void add(Booking asignedBooking) throws SQLException {
        String sql = "INSERT INTO bookings (code, paid, date, user_id, task_id) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, asignedBooking.getCode());
            statement.setString(2, String.valueOf(asignedBooking.getPaid()));
            statement.setString(3, String.valueOf(asignedBooking.getDate()));
            statement.setString(3, String.valueOf(asignedBooking.getUser_id()));
            statement.setString(3, String.valueOf(asignedBooking.getTask_id()));
            statement.executeUpdate();
        } catch (SQLException sqle) {
            System.out.println("Could not connect to the database server. Check that the data is correct and that the server has been started");
            sqle.printStackTrace();
        }
    }

    public Booking getOrder() {
        return null;
    }

    public void payOrder() {

    }
}
