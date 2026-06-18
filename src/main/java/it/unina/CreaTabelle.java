package it.unina;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class CreaTabelle {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("DocentiUninaJR");

        emf.close();
}}
