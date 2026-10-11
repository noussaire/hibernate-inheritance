package com.example.model.singletable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("VOITURE")
public class Voiture extends Vehicule {

    private int nombrePortes;

    private boolean climatisation;
    private int nombrePlaces;
    private boolean boiteAutomatique;

    public Voiture() {
    }

    public Voiture(String marque, int nombrePortes) {
        super(marque);
        this.nombrePortes = nombrePortes;
    }

    public int getNombrePortes() {
        return nombrePortes;
    }

    public void setNombrePortes(int nombrePortes) {
        this.nombrePortes = nombrePortes;
    }

    public boolean isClimatisation() {
        return climatisation;
    }

    public void setClimatisation(boolean climatisation) {
        this.climatisation = climatisation;
    }

    public int getNombrePlaces() {
        return nombrePlaces;
    }

    public void setNombrePlaces(int nombrePlaces) {
        this.nombrePlaces = nombrePlaces;
    }

    public boolean isBoiteAutomatique() {
        return boiteAutomatique;
    }

    public void setBoiteAutomatique(boolean boiteAutomatique) {
        this.boiteAutomatique = boiteAutomatique;
    }
}