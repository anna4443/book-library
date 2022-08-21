package com.as.booklibraryapp.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity // annotation that finds classes mapped with tables of the db
public class Users {

    @Id
    // generated value is how primary key is generated, most by the database
    // identity goes because of the same definition in schema for database
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@NotEmpty(message = "You haven't enter a name")
    @NotEmpty(message = "{validation.users.name.notEmpty}")
    private String name;


    @NotEmpty(message = "{validation.users.surname.notEmpty}")
    private String surname;
    @NotEmpty(message="{validation.users.username.notEmpty}")
    private String username;
    @NotEmpty(message="{validation.users.email.notEmpty}")
    private String email;
    @NotEmpty(message="{validation.users.password.notEmpty}")
    private String password;
    private boolean enabled;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Users() {
    }

    public Users(Long id, @NotEmpty(message = "You haven't enter a name") String name, @NotEmpty(message = "You haven't enter a surname") String surname, @NotEmpty(message = "You haven't entered an username") String username, @NotEmpty(message = "You haven't entered an email") String email, @NotEmpty(message = "You haven't entered a password") String password, boolean enabled) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
    }
}
