package org.example.medical.model;

public class Patient {
    public int id ;
    public String name ;
    public String firstName;
    public String sexe ;
    public String age ;
    public String weight ;
    public String height ;
    public String tension ;
    public String temperature ;

    public Patient(String name, String firstName, String age, String sexe, String height, String temperature, String tension) {
        this.name = name ;
        this.firstName = firstName ;
        this.age = age;
        this.sexe = sexe ;
        this.height = height ;
        this.temperature = temperature ;
        this.tension = tension ;
    }
}
