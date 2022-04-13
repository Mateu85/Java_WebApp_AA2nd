package com.svalero.webapp.dao;


import com.svalero.webapp.domain.Booking;
import com.svalero.webapp.domain.Task;
import com.svalero.webapp.domain.User;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BookingDao {
    private Connection connection;

    public BookingDao(Connection connection) {
        this.connection = connection;
    }

    public void add(Booking asignedBooking) throws SQLException {
        String sql = "INSERT INTO bookings (code, paid, user_id, task_id) VALUES ( ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, asignedBooking.getCode());
            statement.setBoolean(2, asignedBooking.getPaid());
            //statement.setString(3, String.valueOf(asignedBooking.getDate()));
            statement.setInt(3, (asignedBooking.getUser_id()));
            statement.setInt(4, asignedBooking.getTask_id());
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

    public ArrayList<Booking> queryBookingUser (int user_id){
        String sql = "SELECT * FROM  bookings WHERE user_id = ? ";


        ArrayList<Booking> bookings = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, user_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Booking booking = new Booking();
                booking.setId(resultSet.getInt("id" ));
                booking.setCode(resultSet.getString("code" ));
                booking.setPaid(resultSet.getBoolean("paid" ));
                booking.setUser_id(resultSet.getInt("user_id" ));
                booking.setTask_id(resultSet.getInt("task_id" ));

                bookings.add(booking);
            }
        } catch (SQLException sqle) {
            System.out.println("\n" +
                    "Could not connect to the database server. Check that the data is correct and that the server has been started");
            sqle.printStackTrace();
        }

        return bookings;

    }
}
