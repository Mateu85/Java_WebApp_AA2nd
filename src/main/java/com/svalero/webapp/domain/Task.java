package com.svalero.webapp.domain;

public class Task {

    private String title;
    private String description;
    private String location;

    public Task(String title, String description, String location) {
        this.title = title;
        this.description = title;
        this.location = location;
    }

    public Task() {

    }

    public static void addTask(Task task) {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
