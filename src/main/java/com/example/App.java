package com.example;

import com.example.model.singletable.Vehicule;
import com.example.model.singletable.Voiture;
import com.example.model.singletable.Moto;

import com.example.model.joined.Employe;
import com.example.model.joined.Developpeur;
import com.example.model.joined.Manager;

import com.example.model.tableperclass.Produit;
import com.example.model.tableperclass.Livre;
import com.example.model.tableperclass.Electronique;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class App {

    public static void main(String[] args) {

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("hibernate-inheritance");

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            // ==========================
            // 1. SINGLE_TABLE
            // ==========================

            Voiture voiture = new Voiture("Toyota", 4);

            // Propriétés héritées de Vehicule
            voiture.setCouleur("Noir");
            voiture.setAnneeFabrication(2022);
            voiture.setKilometrage(45000.0);

            // Propriétés ajoutées dans Voiture
            voiture.setClimatisation(true);
            voiture.setNombrePlaces(5);
            voiture.setBoiteAutomatique(false);

            Moto moto = new Moto("Harley", true);

            // Propriétés héritées de Vehicule
            moto.setCouleur("Rouge");
            moto.setAnneeFabrication(2021);
            moto.setKilometrage(18000.0);

            // Propriétés ajoutées dans Moto
            moto.setCylindree(1200);
            moto.setTypeMoto("Routière");
            moto.setPoids(300.0);

            em.persist(voiture);
            em.persist(moto);

            // ==========================
            // 2. JOINED
            // ==========================

            Developpeur dev = new Developpeur("Benassou", "Java");

            // Propriétés héritées de Employe
            dev.setPrenom("Nousaire");
            dev.setTelephone("0612345678");
            dev.setVille("Marrakech");

            // Propriétés ajoutées dans Developpeur
            dev.setNiveau("Junior");
            dev.setAnneesExperience(1);
            dev.setSpecialite("Backend");

            Manager manager = new Manager("Alaoui", 3);

            // Propriétés héritées de Employe
            manager.setPrenom("Sara");
            manager.setTelephone("0623456789");
            manager.setVille("Casablanca");

            // Propriétés ajoutées dans Manager
            manager.setService("Informatique");
            manager.setBureau("B12");
            manager.setPrime(1500.0);

            em.persist(dev);
            em.persist(manager);

            // ==========================
            // 3. TABLE_PER_CLASS
            // ==========================

            Livre livre = new Livre("Clean Code", "Robert C. Martin");

            // Propriétés héritées de Produit
            livre.setPrix(350.0);
            livre.setStock(10);
            livre.setDescription("Livre sur la qualité du code.");

            // Propriétés ajoutées dans Livre
            livre.setNombrePages(464);
            livre.setLangue("Anglais");
            livre.setEditeur("Prentice Hall");

            Electronique electronique =
                    new Electronique("Smartphone", 24);

            // Propriétés héritées de Produit
            electronique.setPrix(3200.0);
            electronique.setStock(8);
            electronique.setDescription("Téléphone portable.");

            // Propriétés ajoutées dans Electronique
            electronique.setMarque("Samsung");
            electronique.setModele("Galaxy A54");
            electronique.setCouleur("Noir");

            em.persist(livre);
            em.persist(electronique);

            // Valider les enregistrements
            em.getTransaction().commit();

            // Vider le contexte pour relire depuis la base
            em.clear();

            // ==========================
            // 4. LECTURE DES DONNÉES
            // ==========================

            System.out.println("--- Liste des véhicules ---");

            for (Vehicule v : em.createQuery(
                    "SELECT v FROM Vehicule v", Vehicule.class)
                    .getResultList()) {

                System.out.println(
                        v.getClass().getSimpleName()
                                + ": " + v.getMarque()
                                + " | Couleur: " + v.getCouleur()
                                + " | Année: " + v.getAnneeFabrication()
                                + " | Kilométrage: " + v.getKilometrage()
                );

                if (v instanceof Voiture) {
                    Voiture voitureLue = (Voiture) v;

                    System.out.println(
                            "  Portes: " + voitureLue.getNombrePortes()
                                    + " | Climatisation: "
                                    + voitureLue.isClimatisation()
                                    + " | Places: "
                                    + voitureLue.getNombrePlaces()
                                    + " | Boîte automatique: "
                                    + voitureLue.isBoiteAutomatique()
                    );

                } else if (v instanceof Moto) {
                    Moto motoLue = (Moto) v;

                    System.out.println(
                            "  Sidecar: " + motoLue.isSidecar()
                                    + " | Cylindrée: "
                                    + motoLue.getCylindree()
                                    + " | Type: " + motoLue.getTypeMoto()
                                    + " | Poids: " + motoLue.getPoids()
                    );
                }
            }

            System.out.println("--- Liste des employés ---");

            for (Employe e : em.createQuery(
                    "SELECT e FROM Employe e", Employe.class)
                    .getResultList()) {

                System.out.println(
                        e.getClass().getSimpleName()
                                + ": " + e.getNom()
                                + " " + e.getPrenom()
                                + " | Téléphone: " + e.getTelephone()
                                + " | Ville: " + e.getVille()
                );

                if (e instanceof Developpeur) {
                    Developpeur devLu = (Developpeur) e;

                    System.out.println(
                            "  Langage: " + devLu.getLangage()
                                    + " | Niveau: " + devLu.getNiveau()
                                    + " | Expérience: "
                                    + devLu.getAnneesExperience()
                                    + " | Spécialité: "
                                    + devLu.getSpecialite()
                    );

                } else if (e instanceof Manager) {
                    Manager managerLu = (Manager) e;

                    System.out.println(
                            "  Équipes: " + managerLu.getNombreEquipes()
                                    + " | Service: " + managerLu.getService()
                                    + " | Bureau: " + managerLu.getBureau()
                                    + " | Prime: " + managerLu.getPrime()
                    );
                }
            }

            System.out.println("--- Liste des produits ---");

            for (Produit p : em.createQuery(
                    "SELECT p FROM Produit p", Produit.class)
                    .getResultList()) {

                System.out.println(
                        p.getClass().getSimpleName()
                                + ": " + p.getNom()
                                + " | Prix: " + p.getPrix()
                                + " | Stock: " + p.getStock()
                                + " | Description: " + p.getDescription()
                );

                if (p instanceof Livre) {
                    Livre livreLu = (Livre) p;

                    System.out.println(
                            "  Auteur: " + livreLu.getAuteur()
                                    + " | Pages: " + livreLu.getNombrePages()
                                    + " | Langue: " + livreLu.getLangue()
                                    + " | Éditeur: " + livreLu.getEditeur()
                    );

                } else if (p instanceof Electronique) {
                    Electronique appareilLu = (Electronique) p;

                    System.out.println(
                            "  Garantie: " + appareilLu.getGarantie()
                                    + " mois | Marque: "
                                    + appareilLu.getMarque()
                                    + " | Modèle: " + appareilLu.getModele()
                                    + " | Couleur: " + appareilLu.getCouleur()
                    );
                }
            }

        } catch (RuntimeException exception) {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw exception;

        } finally {
            em.close();
            emf.close();
        }
    }
}
