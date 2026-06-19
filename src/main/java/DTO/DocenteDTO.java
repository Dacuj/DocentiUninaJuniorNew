package DTO;
import entity.Corso;
import entity.Docente;
import java.util.ArrayList;
import java.util.List;

public class DocenteDTO {
    private String mail;
    private String nome, cognome;
    private List<String> listaCorsiDocente = new ArrayList<>();

    public DocenteDTO(Docente docente) {
        this.mail = docente.getMail();
        this.nome = docente.getNome();
        this.cognome = docente.getCognome();

        if(docente.getListaCorsiDocente().isEmpty()){listaCorsiDocente.add("Al momento non gestisci nessun corso");}
        else{
            for (Corso corso : docente.getListaCorsiDocente()) {
                listaCorsiDocente.add(corso.getTitolo()+"-"+corso.getCodice()); }}
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

        public List<String> getListaCorsiDocente() {
            return listaCorsiDocente;
        }

        @Override
        public String toString() {
            return nome + " " + cognome + " (" + mail + ")";
        }
    }



