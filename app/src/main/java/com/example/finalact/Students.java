package com.example.finalact;

public class Students {
    private String name;
    private String email;
    private String datetime;

    private Students() {}

    public Students(String name, String email, String datetime) {
        this.name = name;
        this.email = email;
        this.datetime = datetime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
