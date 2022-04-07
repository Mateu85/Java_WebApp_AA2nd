package com.svalero.webapp;

import com.svalero.webapp.exception.TaskNotFoundException;

import java.sql.SQLException;

public class WebApp {

    public static void main(String[] args) throws SQLException, TaskNotFoundException {
        OptionsMenu optionsMenu = new OptionsMenu();
        optionsMenu.showMenu();
    }
}
