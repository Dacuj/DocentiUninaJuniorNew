package entity;

import jakarta.persistence.*;


import java.util.*;

@Entity
@DiscriminatorValue("Studente")
public class Studente extends Utente{

    public Studente(){};
    public Studente(String mail, String nome, String cognome, String password){
        super(mail, nome, cognome, password, "Studente");}

    @ManyToMany(fetch = FetchType.EAGER)
    @OrderBy("titolo ASC, codice ASC")
    @JoinTable(name = "CORSI_STUD",
        joinColumns = @JoinColumn(name = "mail_Stud"),
        inverseJoinColumns = @JoinColumn(name = "id_Corso"))
    private Set<Corso> corsiStudente = new HashSet<>();

    public Set<Corso> getListaCorsiStudente() {return corsiStudente;}
}


