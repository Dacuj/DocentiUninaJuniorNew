package database.DTO;
import entity.Corso;
import entity.Studente;

import java.util.ArrayList;
import java.util.List;
public class StudenteDTO {
    private String mail;
    private String nome, cognome;
    private List<String> listaCorsiStudente = new ArrayList<>();

    public StudenteDTO(Studente studente) {
        this.mail = studente.getMail();
        this.nome = studente.getNome();
        this.cognome = studente.getCognome();

        if(studente.getListaCorsiStudente().isEmpty()){listaCorsiStudente.add("Al momento non sei iscritto ad alcun corso");}
        else{
            for (Corso corso : studente.getListaCorsiStudente()) {
                listaCorsiStudente.add(corso.getTitolo()+"-"+corso.getCodice());}
        }
    }

    public String getMail() {
        return mail;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public List<String> getListaCorsiStudente() {
        return listaCorsiStudente;}

    @Override
    public String toString() {
        return nome + " " + cognome + " (" + mail + ")";
    }

}
