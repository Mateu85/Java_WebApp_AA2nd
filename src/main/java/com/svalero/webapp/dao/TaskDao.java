package com.svalero.webapp.dao;

import com.svalero.webapp.domain.Booking;
import com.svalero.webapp.domain.Task;
import com.svalero.webapp.exception.TaskNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TaskDao {

    private static Connection connection;

    public TaskDao(Connection connection) {
        this.connection = connection;
    }


    public void add(Task task)   {
        String sql = "INSERT INTO task (title, description, location) VALUES (?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setString(3, task.getLocation());
            statement.executeUpdate();
        } catch (SQLException sqle) {
            System.out.println("Could not connect to the database server. Check that the data is correct and that the server has been started");
            sqle.printStackTrace();
        }
    }


    public static boolean remove(String title) {
        String sql = "DELETE FROM task WHERE title = ?";
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

    public boolean modify(String title, Task task) {
        String sql = "UPDATE task SET title = ?, description = ?, location = ? WHERE title = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setString(3, task.getLocation());
            statement.setString(4, title);
            int rows = statement.executeUpdate();
            return rows == 1;
        } catch (SQLException sqle) {
            System.out.println("No se ha podido conectar con el servidor de base de datos. Comprueba que los datos son correctos y que el servidor se ha iniciado");
            sqle.printStackTrace();
        }

        return false;
    }
    public static ArrayList<Task> findAll() {
        String sql = "SELECT * FROM task ORDER BY title";
        ArrayList<Task> task = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Task tasks = new Task();
                tasks.setTitle(resultSet.getString("title"));
                tasks.setDescription(resultSet.getString("description"));
                tasks.setLocation(resultSet.getString("location"));
                task.add(tasks);
            }
        } catch (SQLException sqle) {
            System.out.println("\n" +
                    "Could not connect to the database server. Check that the data is correct and that the server has been started");
            sqle.printStackTrace();
        }

        return task;
    }

    public Task findByTitle(String title) {
        String sql = "SELECT * FROM task WHERE title = ?";
        Task task = null;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, title);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                task = new Task();
                task.setTitle(resultSet.getString("title"));
                task.setDescription(resultSet.getString("description"));
                task.setLocation(resultSet.getString("location"));
            }
        } catch (SQLException sqle) {
            System.out.println("No se ha podido conectar con el servidor de base de datos. Comprueba que los datos son correctos y que el servidor se ha iniciado");
            sqle.printStackTrace();
        }

        return task;
    }

    public Task getTaskById(int id) throws TaskNotFoundException {
        String sql = "SELECT * FROM task WHERE id = ?";
        Task task = null;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                task = new Task();
                task.setTitle(resultSet.getString("title"));
                task.setDescription(resultSet.getString("description"));
                task.setLocation(resultSet.getString("location"));
            }
        } catch (SQLException sqle) {
            System.out.println("No se ha podido conectar con el servidor de base de datos. Comprueba que los datos son correctos y que el servidor se ha iniciado");
            sqle.printStackTrace();
        }
        if (task == null){
            throw new TaskNotFoundException("msm");
        }
        return task;
    }

    public ArrayList<Task> queryUserTaskList (int user_id) throws TaskNotFoundException {
        String sql = "SELECT * FROM  task INNER JOIN bookings ON bookings.task_id = task_id WHERE bookings.user_id = ? ";
        // TODO MODIFY QUERY " WHERE" DOES NOT WORK
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, user_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Task task = new Task();
                task.setTitle(resultSet.getString("title"));
                task.setDescription(resultSet.getString("description"));
                task.setLocation(resultSet.getString("location"));

                tasks.add(task);
            }
        } catch (SQLException sqle) {
            System.out.println("\n" +
                    "Could not connect to the database server. Check that the data is correct and that the server has been started");
            sqle.printStackTrace();
        }

        if(tasks.isEmpty()){
            throw new TaskNotFoundException("Task Not Found");
        }

        return tasks;

    }



}






