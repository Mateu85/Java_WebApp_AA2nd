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

    public void bookHandyperson (User user, List<Task> tasks) throws SQLException {
        String orderSql = "INSERT INTO orders (code, user_id, date) VALUES (?, ?, ?)";

        connection.setAutoCommit(false);

        PreparedStatement orderStatement = connection.prepareStatement(orderSql,
                PreparedStatement.RETURN_GENERATED_KEYS);
        orderStatement.setString(1, UUID.randomUUID().toString());
        orderStatement.setInt(2, user.getId());
        orderStatement.setDate(3, new Date(System.currentTimeMillis()));
        orderStatement.executeUpdate();

        // Obtener el orderId generado en la sentencia anterior (el último AUTO_INCREMENT generado)
        ResultSet orderKeys = orderStatement.getGeneratedKeys();
        orderKeys.next();
        int orderId = orderKeys.getInt(1);
        orderStatement.close();

        for (Task task : tasks) {
            String bookSql = "INSERT INTO order_book (order_id, book_id) VALUES (?, ?)";

            PreparedStatement bookStatement = connection.prepareStatement(bookSql);
            bookStatement.setInt(1, orderId);
            bookStatement.setInt(2, task.getId());
            bookStatement.executeUpdate();
        }

        connection.commit();
        connection.setAutoCommit(true);
    }

    public Booking getOrder() {
        return null;
    }

    public void payOrder() {

    }
}
