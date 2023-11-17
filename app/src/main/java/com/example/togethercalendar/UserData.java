package com.example.togethercalendar;

public class UserData {
    private String username;
    private String email;
    private String mobile;
    private String availability;
    private String location;
    private String about;

    public UserData(String username, String email, String mobile,
                    String availability, String location, String about) {
        this.username = username;
        this.email = email;
        this.mobile = mobile;
        this.availability = availability;
        this.location = location;
        this.about = about;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getAvailability() {
        return availability;
    }

    public String getLocation() {
        return location;
    }

    public String getAbout() {
        return about;
    }
}
