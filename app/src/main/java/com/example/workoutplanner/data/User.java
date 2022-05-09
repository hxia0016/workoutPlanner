package com.example.workoutplanner.data;

public class User {

    public String fName, lName, age, email, gender, zipcode, address;

    public User() {

    }

    public User(String fName, String lName, String age, String email, String gender, String zipcode, String address) {
        this.fName = fName;
        this.lName = lName;
        this.age = age;
        this.email = email;
        this.address = address;
        this.zipcode = zipcode;
        this.gender = gender;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

