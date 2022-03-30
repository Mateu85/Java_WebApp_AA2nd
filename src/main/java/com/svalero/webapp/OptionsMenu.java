package com.svalero.webapp;

import com.svalero.webapp.dao.Database;
import com.svalero.webapp.dao.TaskDao;
import com.svalero.webapp.domain.Task;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OptionsMenu {

    private Scanner keyboard;
    private Database database;
    private Connection connection;

    public void Menu() {
        keyboard = new Scanner(System.in);
    }

    public void connect() {
        database = new Database();
        connection = database.getConnection();
    }

    public void showMenu() {
        connect();

        String choice = null;

        do {
            System.out.println("List of asigments in your area.");
            System.out.println("1. Add Task.");
            System.out.println("2. Search a Task.");
            System.out.println("3. Delete a Task.");
            System.out.println("4. Modify a Task.");
            System.out.println("5. Look up the list of Tasks.");
            System.out.println("6. EXIT");
            System.out.print("Type your Option: ");
            choice = keyboard.nextLine();

            switch(choice) {
                case "1":
                    addTask();
                    break;
                case "2":
                    findTask();
                    break;
                case "3":
                    deleteTask();
                    break;
                case "4":
                    modifyTask();
                    break;
                case "5":
                    showTaskList();
                    break;
            }
        } while (!choice.equals("6"));
    }

    public void addTask() {
        System.out.print("Title: ");
        String title = keyboard.nextLine();
        System.out.print("Task Description: ");
        String Description = keyboard.nextLine();
        System.out.print("Location: ");
        String taskLocation = keyboard.nextLine();
        Task task = new Task(title.trim(), Description.trim(), taskLocation.trim());

        TaskDao taskDao = new TaskDao(connection);
        taskDao.add(task);
        System.out.println("A new task has been added on the platform! Good Luck");
    }

    public void findTask() {
        System.out.print("Búsqueda por titulo: ");
        String title = keyboard.nextLine();

        TaskDao taskDao = new TaskDao(connection);
        Task task = taskDao.findByTitle(title);
        if (task == null) {
            System.out.println("Ese libro no existe");
            return;
        }

        System.out.println(task.getTitle());
        System.out.println(task.getDescription());
        System.out.println(task.getLocation());
    }
    public void deleteTask() {
        System.out.print("Titulo del libro a eliminar: ");
        String title = keyboard.nextLine();
        TaskDao taskDao = new TaskDao(connection);
        boolean deleted = TaskDao.remove(title);
        if (deleted)
            System.out.println("El libro se ha borrado correctamente");
        else
            System.out.println("El libro no se ha podido borrar. No existe");
    }

    public void modifyTask() {
        System.out.print("Name of the task you wanna modify: ");
        String titulo = keyboard.nextLine();
        System.out.print("Titulo del libro a modificar: ");
        String title = keyboard.nextLine();
        // TODO Buscar el libro antes de pedir los nuevos datos
        System.out.print("Nuevo Título: ");
        String newTitle = keyboard.nextLine();
        System.out.print("Nuevo Autor: ");
        String newAuthor = keyboard.nextLine();
        System.out.print("Nueva Editorial: ");
        String newPublisher = keyboard.nextLine();
        Task newTask = new Task(newTitle.trim(), newAuthor.trim(), newPublisher.trim());

        TaskDao taskDao = new TaskDao(connection);
        boolean modified = taskDao.modify(title, newTask);
        if (modified)
            System.out.println("El libro se ha modificado correctamente");
        else
            System.out.println("El libro no se ha podido modificar. No existe");
    }

    public void showTaskList() {
       TaskDao bookDao = new TaskDao(connection);
        // TODO Propagar la excepción al menú de usuario
        ArrayList<Task> books = bookDao.findAll();
        for (Task book : books) {
            System.out.println(book.getTitle());
        }
    }



