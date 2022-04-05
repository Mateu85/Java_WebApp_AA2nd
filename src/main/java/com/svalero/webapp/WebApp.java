package com.svalero.webapp;

import java.sql.SQLException;

public class WebApp {

    public static void main(String[] args) throws SQLException {
        OptionsMenu optionsMenu = new OptionsMenu();
        optionsMenu.showMenu();
    }
}
