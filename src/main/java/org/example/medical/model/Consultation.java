package org.example.medical.model;

public class Consultation {
    public Medecin medecin; // Utilisation de Medecin comme objet
    public Patient patient ;
    public String heure;
    public String lieu;

    public Consultation(Medecin medecin, Patient patient, String heure, String lieu) {
        this.medecin = medecin ;
        this.patient = patient ;
        this.heure = heure ;
        this.lieu = lieu ;
    }
    // Getters/Setters et propriétés JavaFX...
}
