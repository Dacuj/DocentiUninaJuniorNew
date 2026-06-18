package entity;

import jakarta.persistence.*;
import java.util.*;


@Entity
@DiscriminatorValue("Docente")
public class Docente extends Utente{

    @OneToMany(mappedBy = "gestore", fetch = FetchType.EAGER )
    @OrderBy("titolo ASC")
    protected Set<Corso> listaCorsiDocente = new HashSet<>();

    public Docente() {super();}
    public Docente(String mail, String nome, String cognome, String password){
        super(mail, nome, cognome, password, "Docente");
    }

    public Set<Corso> getListaCorsiDocente() {return listaCorsiDocente;}

    //Docente è Creator dei corsi, quindi ha il metodo per la loro creazione
    public Corso creaCorso(String titolo, String descr, int codice, String annoAcc){
        Corso nuovoCorso = new Corso(titolo, descr, codice, annoAcc, this);
        listaCorsiDocente.add(nuovoCorso);
        return nuovoCorso;
    }
}
