package it.unina;
import entity.*;
public class AllacciaDB {//classe per riempire il DB

    private AllacciaDB(){}

        public static void popola(CatalogoCorsi cs, CatalogoUtenti cu){
            if(!cu.trovaUtente("PA@docenti.unina.it")) //trova utente, true se esiste
                cu.aggiungiUtente(
                "PA@docenti.unina.it" , "Pino", "Abete",
                "LoSaiCheIPapaveri","Docente");

            if(!cu.trovaUtente("Cb@studenti.unina.it"))
                cu.aggiungiUtente("Cb@studenti.unina.it","Claudio", "Bisio",
                "PinguiniSenzaGinocchia","Studente");

            if(cs.trovaCorso(789456) == null){
                //se non lo trova == null
                Corso corsoProva = new Corso("Prova", "Sta qui per prova", 789456, "2026/2027", (Docente) cu.trovaUtente("PA@docenti.unina.it", "LoSaiCheIPapaveri"));
                cs.addCorso(corsoProva);
            }
    }
}