package com.svalero.webapp.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Booking {

    private int id;
    private String code;
    private boolean paid;
    private LocalDate date;

    private User user;
    private List<Booking> task;


    public Booking() {
        task = new ArrayList<>();
    }

    public Booking(int id, String code, boolean paid, LocalDate date){
        this.code = code;
        this.paid = paid;
        this.date = date;
        task = new ArrayList<>();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}


