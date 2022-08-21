package com.as.booklibraryapp.domain;

public class Loan {

    private Long id;
    private String name;
    private String surname;
    private String username;
    private String title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Loan() {
    }

    public Loan(Long id, String name, String surname, String username, String title) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.title = title;
    }
}
