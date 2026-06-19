package DTO;

import entity.Utente;

public class UtenteDTO {
   private String mail, nome, cognome, password, ruolo;

    public UtenteDTO(Utente utente) {
        this.mail = utente.getMail();
        this.nome = utente.getNome();
        this.cognome = utente.getCognome();
        this.ruolo = utente.getRuolo();
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

    public String getRuolo() {
        return ruolo;
    }

    @Override
    public String toString() {
        return nome + " " + cognome + " (" + mail + ")";
    }
}


