package entity;

import database.GestorePersistenza;

import java.util.List;
import java.util.Map;

public class CatalogoUtenti {

    private GestorePersistenza gestorePersistenza;


    private static CatalogoUtenti istanza;
    private CatalogoUtenti() {gestorePersistenza = new GestorePersistenza();}

    public static CatalogoUtenti getIstanza() {
        if (istanza == null) {
            istanza = new CatalogoUtenti();
        }
        return istanza;
    }


    public Utente aggiungiUtente(String mail, String nome, String cognome, String password, String ruolo){
        boolean esito=false;
        Utente utente;
        if(ruolo.equalsIgnoreCase("studente")){
            utente = new Studente(mail, nome, cognome, password);
            esito = gestorePersistenza.salva(utente);}
        else{
            utente = new Docente(mail, nome, cognome, password);
            esito = gestorePersistenza.salva(utente);}
        if(esito==true)System.out.print("registrazione avvenuta con successo");
         else System.err.print("errore di registrazione");

        return utente;
    }
    public Utente aggiungiUtente(Utente utente){gestorePersistenza.salva(utente);
    return utente;}

    //questo viene usato per l'autenticazione
    public Utente trovaUtente(String mail, String password){

        Utente trovato = gestorePersistenza.cercaPrimoPerCampi(Utente.class,
                Map.of("mail",mail, "password",password));
        return trovato;
    }

    //questo viene usato nella registrazione per il
    //controlli di unicità della mail
    public boolean trovaUtente(String mail){
        List<Utente> trovato = gestorePersistenza.cercaPerCampo(Utente.class, "mail", mail);
        if(trovato.isEmpty())return false;
            else return true;
    }

    public void eliminaUtente(String mail){
        Utente utente = gestorePersistenza.cercaPrimoPerCampi(Utente.class, Map.of("mail",mail));
        if(utente == null) return;
        gestorePersistenza.elimina(utente.getClass(), utente.getMail());
    }//usato nel blackbox Test
}
