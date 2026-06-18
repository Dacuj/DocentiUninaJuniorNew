package entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//insomma le sottoclassi non hanno tabelle separate: qui va bene perchè non hanno attributi extra
public abstract class Utente {

    @Id
    @Column(length = 50)
    private String mail;

    @Column(length = 50)
    private String nome, cognome;


    @Column(length = 30)
    private String password;
    private String ruolo;

    protected Utente(){};
    protected Utente(String mail, String nome, String cognome, String password, String ruolo) {
        this.mail = mail;
        this.nome = nome;
        this.cognome = cognome;
        this.password = password;
        this.ruolo = ruolo;
    }

    public String getMail(){return mail;}
    public String getCognome() {return cognome;}
    public String getNome() {return nome;}
    public String getPassword(){return  password;}
    public String getRuolo() {return ruolo;}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Utente)) {
            return false;
        }
        Utente altro = (Utente) o;
        return Objects.equals(mail, altro.mail);
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(mail);}
    //questi override servono perchè un utente è discriminato dalla sua mail (PK), non da nome e cognome
}
