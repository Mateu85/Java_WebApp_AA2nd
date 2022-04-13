package com.svalero.webapp;

import com.svalero.webapp.dao.BookingDao;
import com.svalero.webapp.dao.Database;
import com.svalero.webapp.dao.TaskDao;
import com.svalero.webapp.dao.UserDao;
import com.svalero.webapp.domain.Booking;
import com.svalero.webapp.domain.Task;
import com.svalero.webapp.domain.User;
import com.svalero.webapp.exception.TaskNotFoundException;


import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class OptionsMenu {

    private Scanner keyboard;
    private Database database;
    private Connection connection;
    private Task task;
    TaskDao taskDao;
    UserDao userDao;
    BookingDao bookingDao;
    private Optional<User> currentUser;


    public OptionsMenu() {
        keyboard = new Scanner(System.in);

    }

    public void connect() {
        database = new Database();
        connection = database.getConnection();
        taskDao = new TaskDao(connection);
        bookingDao = new BookingDao(connection);
        userDao = new UserDao(connection);
        //TODO CREAR CONSTRUCTOR USERDAO
        // userdao = new UserDao(connection);
    }

    public void showMenu() throws SQLException, TaskNotFoundException {
        connect();
        login(userDao);

        String choice = null;

        do {
            System.out.println("List of asigments in your area.");
            System.out.println("1. Register a new Task.");
            System.out.println("2. Search a Task.");
            System.out.println("3. Delete a Task.");
            System.out.println("4. Modify a Task.");
            System.out.println("5. See Task List");
            System.out.println("6. Register a new User");
            System.out.println("7. Book a HandyPerson");
            System.out.println("8. Print list of a User");
            System.out.println("9. EXIT");
            System.out.print("Type your Option: ");
            choice = keyboard.nextLine();

            switch (choice) {
                case "1":
                    addTask(taskDao);
                    break;
                case "2":
                    findTask(taskDao);
                    break;
                case "3":
                    deleteTask(taskDao);
                    break;
                case "4":
                    modifyTask(taskDao);
                    break;
                case "5":
                    showTaskList(taskDao);
                    break;
                case "6":
                    addUser();
                    break;
                case "7":
                    bookHandyperson(bookingDao);
                    break;
                case "8":
                   showBookingList(taskDao);
                    break;
            }
        } while (!choice.equals("9"));
    }

    public void login(UserDao userDao) throws SQLException {
        System.out.print("User Name: ");
        String name = keyboard.nextLine();
        System.out.print("Password: ");
        String password = keyboard.nextLine();

        if(userDao.getUser(name.trim(), password.trim()) != null ){
            System.out.println("you are logedIn");
        }else{
            System.out.println("Log In Incorrect");
        }


    }

    public void addTask(TaskDao taskDao) {
        System.out.print("Title: ");
        String title = keyboard.nextLine();
        System.out.print("Task Description: ");
        String Description = keyboard.nextLine();
        System.out.print("Location: ");
        String taskLocation = keyboard.nextLine();
        Task task = new Task(title.trim(), Description.trim(), taskLocation.trim());
        taskDao.add(task);


    }

    public void findTask(TaskDao taskDao) {
        System.out.print("Search by Title: ");
        String title = keyboard.nextLine();
        Task task = taskDao.findByTitle(title);
        if (task == null) {
            System.out.println("No task Task was found by that name");
            return;
        }

        System.out.println(task.getTitle());
        System.out.println(task.getDescription());
        System.out.println(task.getLocation());
    }

    public void deleteTask(TaskDao taskDao) {
        System.out.print("Type the Task you´d like to delete: ");
        String title = keyboard.nextLine();
        boolean deleted = taskDao.remove(title);
        if (deleted)
            System.out.println("The task has been deleted successfully");
        else
            System.out.println("The task could not be deleted successfully. Try again!");
    }

    public void modifyTask(TaskDao taskDao) {
        System.out.print("Name of the task you wanna modify: ");
        String title = keyboard.nextLine();
        // TODO Buscar el libro antes de pedir los nuevos datos
        System.out.print("Nuevo Título: ");
        String newTitle = keyboard.nextLine();
        System.out.print("Nuevo Autor: ");
        String newAuthor = keyboard.nextLine();
        System.out.print("Nueva Editorial: ");
        String newPublisher = keyboard.nextLine();
        Task newTask = new Task(newTitle.trim(), newAuthor.trim(), newPublisher.trim());
        boolean modified = taskDao.modify(title, newTask);
        if (modified)
            System.out.println("El libro se ha modificado correctamente");
        else
            System.out.println("El libro no se ha podido modificar. No existe");
    }

    public void showTaskList(TaskDao taskDao) {
        ArrayList<Task> tasks = taskDao.findAll();
        for (Task task : tasks) {
            System.out.println(task.getTitle());
        }
    }

    private void addUser() {
        UserDao userDao = new UserDao(connection);

        System.out.print("Type your User Name: ");
        String username = keyboard.nextLine();
        System.out.print("Choose a password:  ");
        String password = keyboard.nextLine();
        System.out.print("postCode (XXX XXX):  ");
        String postCode = keyboard.nextLine();
        User user = new User(username.trim(),username.trim(),password.trim(),postCode.trim());
        try {
            userDao.add(user);
        } catch (SQLException sqle) {

        }
    }

    private void bookHandyperson(BookingDao bookingDao) throws SQLException {

        System.out.print("Type the Type of Asigment: ");
        String code = keyboard.nextLine();

        System.out.print("Is this a paid job?: ");
        String ispaid = keyboard.nextLine();
        boolean paid = false;
        if (ispaid.equals("YES")){
            paid = true;
        }

        System.out.print("User id : ");
        int user_id = Integer.parseInt(keyboard.nextLine());

        System.out.print("Task idf: ");
        int task_id = Integer.parseInt(keyboard.nextLine());

        Booking bookingService  = new Booking( code, paid, LocalDate.now(), user_id, task_id);
        bookingDao.add(bookingService);
    }

    public void showBookingList(TaskDao taskdao) throws TaskNotFoundException {
        System.out.print("User id : ");
        int user_id = Integer.parseInt(keyboard.nextLine());

       try {
           ArrayList<Task> tasks = taskDao.queryUserTaskList(user_id);
           for (Task task : tasks) {

               System.out.println(task.getTitle());

           }
       }catch (TaskNotFoundException e){
           System.out.print("NOT FOUND");
       }

    }

}



