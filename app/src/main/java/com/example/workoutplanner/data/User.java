package com.example.workoutplanner.data;

public class User {

    public String fName, lName, age, email;
//    public int uid;

    public User(){

    }

    public User(String fName, String lName, String age, String email) {
        this.fName = fName;
        this.lName = lName;
        this.age = age;
        this.email = email;
//        this.uid = uid;
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

//    public int getUid(){return uid};
//    public void setUid(int uid){this.uid = uid};
}

