package entity;

import database.GestorePersistenza;

import java.util.*;

public class CatalogoCorsi {

    private GestorePersistenza gestorePersistenza;
    private static CatalogoCorsi istanza;
    private Set<Corso> listaCorsi;

    private CatalogoCorsi() {
        gestorePersistenza = new GestorePersistenza();
        this.listaCorsi = new HashSet<>();}



    public static CatalogoCorsi getIstanza() {
        if (istanza == null) {
            istanza = new CatalogoCorsi();
        }
        return istanza;
    }



    //poichè CatalogoCorsi è information expert dei corsi...
    //tutte le operazioni di interrogazione sui corsi le gestisce lui
    public static Set<Corso> getListaCorsiUtente(Utente utente){
        if(utente instanceof Docente) return (((Docente) utente).getListaCorsiDocente());
        return (((Studente) utente).getListaCorsiStudente());
    }

    public boolean addCorso(Corso corso){
        boolean esito = this.gestorePersistenza.salva(corso);
        if (esito) this.listaCorsi.add(corso);

        return esito;
    }
    public boolean AggiungiCorso(Corso corso){
        return gestorePersistenza.salva(corso);
    }//metodo di prova

    public void eliminaCorso(int codice){
        Corso corso = gestorePersistenza.cercaPrimoPerCampi(Corso.class, Map.of("codice",codice));
        if (corso == null) return;
        gestorePersistenza.elimina(Corso.class, corso.getId());
    }

    //aggiunge lo studente al corso se non è già iscritto (il controllo lo effettua già il GestoreSistema)
    public void aggiungiIscritto(Studente studente, Corso corso){
        studente.getListaCorsiStudente().add(corso);
        corso.getListaIscritti().add(studente);
        //si mettono entrambi per sincronizzare l'operazione
        gestorePersistenza.aggiorna(studente);
    }

    public Corso trovaCorso(int codice){
        return gestorePersistenza.cercaPrimoPerCampi(Corso.class, Map.of("codice", codice));
    }
}
