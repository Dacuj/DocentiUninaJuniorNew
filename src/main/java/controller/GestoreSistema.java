package controller;
import database.DTO.*;
import entity.*;

import java.util.ArrayList;
import java.util.List;

public class GestoreSistema {
	private static GestoreSistema istanza;
    private static Utente currentUtente;//Utente attualmente autenticato (null se nessuno)
	private final CatalogoUtenti catUt; //= CatalogoUtenti.getIstanza();
	private final CatalogoCorsi catCo; //= CatalogoCorsi.getIstanza();

	private GestoreSistema(){
		this.catUt = CatalogoUtenti.getIstanza();
		this.catCo = CatalogoCorsi.getIstanza();
	}
	public static GestoreSistema getInstanza(){
		if(istanza == null){
			istanza = new GestoreSistema();
		}
		return istanza;
	}

	//Registrazione--UC01
	public UtenteDTO registrazione(String mail, String nome, String cognome, String password, String Ruolo) {


		Utente utente = catUt.aggiungiUtente(mail, nome, cognome, password, Ruolo);
		return new UtenteDTO(utente);}

	//Autenticazione--UC02
    public int autenticazione(String mail, String password){
        Utente trovato = catUt.trovaUtente(mail, password);
		currentUtente = trovato;
		if(trovato instanceof Docente)return 1;
		else if(trovato instanceof Studente)return 2;
		return 0;
	}


	//Creazione Corso--UC03
	public boolean CreaCorso(CorsoDTO corso) {
		Docente docente = (Docente) currentUtente;
		Corso nuovoCorso = docente.creaCorso(corso.getTitolo(), corso.getDescr(), corso.getCodice(), corso.getAnnoAcc());
//		catCo.addCorso(nuovoCorso);
//		return true;
		return catCo.addCorso(nuovoCorso);
	}



	//iscrizione autonoma UC05
	public int IscrivitiaCorso(int codice) {
		Studente stud = (Studente) currentUtente;
		StudenteDTO studd = new StudenteDTO(stud);
		Corso c = catCo.trovaCorso(codice);
			if(c != null){
				if (!StudenteIsIscritto(c, stud)){
					if (studd.getListaCorsiStudente().contains("Al momento non sei iscritto ad alcun corso")){
						studd.getListaCorsiStudente().remove("Al momento non sei iscritto ad alcun corso");
					}

					catCo.aggiungiIscritto(stud, c);
					aggiornaListaCorsiUtente(c, stud);
					return 0;}//studente iscritto
				else return 1;//studente era già iscritto
			}
			return 2;//corso non esiste
	}



    //METODI DI UTILITY

	public UtenteDTO getCurrentUtente(){
		//verifichiamo prima utente autenticato
		if(currentUtente==null) return null;

		return new UtenteDTO(currentUtente);
	}

	public void setCurrentUtente(Utente currentUtente) {
		GestoreSistema.currentUtente = currentUtente;
	}

	public void logOut(){currentUtente = null;}


	public  List<String> caricaListaCorsiUtente(){
		List<String> corsi = new ArrayList<>();
		if(currentUtente instanceof Docente) {
			DocenteDTO docente = new DocenteDTO((Docente) currentUtente);
			corsi = docente.getListaCorsiDocente();
		}
		else if (currentUtente instanceof Studente){
			StudenteDTO studente = new StudenteDTO((Studente) currentUtente);
			corsi= studente.getListaCorsiStudente();
		}
		return corsi;
		// chiede il catalogo di currentUtente al CatalogoCorsi, lo converte in Arraylist di stringhe per la GUI
	}

	public static void aggiornaListaCorsiUtente(Corso corso, Utente utente){
		CatalogoCorsi.getListaCorsiUtente(utente).add(corso);}

	public static boolean StudenteIsIscritto(Corso corso, Studente studente){
		return corso.getListaIscritti().contains(studente);
	}

    public  int ValidaDati(String nome, String cognome, String mail, String ruolo, String password){
        if (nome==null || nome.length()>50 || !checkForSpecialChar(nome)) return 0 ;
        else if (cognome==null || cognome.length()>50 || !checkForSpecialChar(cognome)) return 1;
        else if (mail ==null || checkMail(mail, ruolo)==0 || mail.length()>50) return 2;
		else if (password==null || password.length()>30) return 3;
        else return 4;

    }//versione per registrazione
	public boolean ValidaDati(String codice){
		if(codice.length()==6 && codice.matches("[0-9]+"))return true;
		return false;
	}//questo è per l'iscrizione al corso


    private static boolean checkForSpecialChar(String s){
		boolean valida = s.matches("[\\p{L}@]+");//range di valori accettati
		if(valida)return true;
		else return false;
    }

    private int checkMail(String mail, String ruolo){
		long count = mail.chars().filter(c -> c == '@').count();
		// 1) controlli sintattici (nessun accesso al DB)
		if (count != 1) return 0;
		boolean dominioOk = (mail.endsWith("@studenti.unina.it") && ruolo.equals("Studente"))
				|| (mail.endsWith("@docenti.unina.it")  && ruolo.equals("Docente"));
		if (!dominioOk) return 0;
		// 2) solo se la mail è ben formata, interroga il DB per l'unicità
		if (catUt.trovaUtente(mail)) return 0;
		return 1;
    }

	public void eliminaUtente(String mail, String nome, String cognome, String password, String Ruolo){
		catUt.eliminaUtente(mail);
	}

	public boolean eliminaCorso (String titolo, String descr, int codice, String annoAcc, Docente gestore){
		catCo.eliminaCorso(codice);
		return true;}

	public int validaDatiCorso(String titolo, String descr, String codice, String anno) {
		// Controllo Titolo: non deve essere nullo, vuoto o superare i 100 caratteri
		if (titolo == null || titolo.trim().isEmpty() || titolo.length() > 100) {
			return 0; // 0 = Errore titolo
		}

		// Controllo Descrizione: non deve superare i 500 caratteri
		if (descr == null || descr.length() > 500) {
			return 1; // 1 = Errore descrizione
		}

		// Controllo Codice: deve essere esattamente di 6 caratteri, numerico e unico
		if (codice == null || codice.length() != 6 || ValidaDati(codice)==false || catCo.trovaCorso(Integer.parseInt(codice))!=null)
			//ParseInt qui è safe perchè per Short Circuit evaluation se arrivo a quel punto della condizione ho già superato ValidaDati(codice) che controlla sia un numero
			return 2; // 2 = Errore codice


		/*if (anno != null && !anno.trim().isEmpty()) {
			if (!anno.matches("\\d{4}/\\d{4}")) {
				return 3; // 3 = Errore formato anno
			}
		} non è un controllo necessario dato che l'anno è sempre autogenerato*/

		// Se supera tutti i controlli, i dati sono validi
		return 4; // 4 = OK
	}

}