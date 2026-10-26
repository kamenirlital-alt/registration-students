package com.example.myapplication;
public class Student {

    private String firstName;
    private String lastName;
    private String email;
    private String studentClass;
    private String major;
    private boolean notifications;
    private boolean terms;

    public Student(String firstName, String lastName, String email,
                   String studentClass, String major,
                   boolean notifications, boolean terms) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.studentClass = studentClass;
        this.major = major;
        this.notifications = notifications;
        this.terms = terms;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public String getMajor() {
        return major;
    }

    public boolean isNotifications() {
        return notifications;
    }

    public boolean isTerms() {
        return terms;
    }
}