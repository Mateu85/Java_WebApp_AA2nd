package com.svalero.webapp.dao;

import com.svalero.webapp.domain.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TaskDao {

    private Connection connection;

    public TaskDao(Connection connection) {
        this.connection = connection;
    }


    public void add(Task task) {
        String sql = "INSERT INTO books (title, author, publisher) VALUES (?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setString(3, task.getLocation());
            statement.executeUpdate();
        } catch (SQLException sqle) {
            System.out.println("No se ha podido conectar con el servidor de base de datos. Comprueba que los datos son correctos y que el servidor se ha iniciado");
            sqle.printStackTrace();
        }
    }


    public void findone() {

    }

    public ArrayList<Task> findAll() {

        return null;
    }

    public void modify() {

    }

    public static boolean remove(String title) {
        String sql = "DELETE FROM books WHERE title = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, title);
            int rows = statement.executeUpdate();

            return rows == 1;
        } catch (SQLException sqle) {
            System.out.println("No se ha podido conectar con el servidor de base de datos. Comprueba que los datos son correctos y que el servidor se ha iniciado");
            sqle.printStackTrace();
        }

        return false;
    }

    public boolean modify(String title, Task newTask) {
        return false;
    }

    public Task findByTitle(String title) {
        String sql = "SELECT * FROM books WHERE title = ?";
        Task task = null;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, title);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                task = new Task();
                task.setTitle(resultSet.getString("title"));
                task.setDescription(resultSet.getString("author"));
                task.setLocation(resultSet.getString("publisher"));
            }
        } catch (SQLException sqle) {
            System.out.println("No se ha podido conectar con el servidor de base de datos. Comprueba que los datos son correctos y que el servidor se ha iniciado");
            sqle.printStackTrace();
        }

        return task;
    }
}






