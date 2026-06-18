package controller.blackbox;

import controller.GestoreSistema;
import entity.CatalogoCorsi;
import entity.CatalogoUtenti;
import entity.Corso;
import entity.Docente;
import entity.Studente;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class IscrizioneCorsoTest {

    private GestoreSistema gestore;
    private Docente docente;
    private Studente studente;
    private Corso corso;

    private static final int CODICE_ESISTENTE   = 245689; // al posto di "IS2026"
    private static final int CODICE_INESISTENTE = 999999; // al posto di "ZZZ999"

    @BeforeEach
    void setUp() {
        gestore  = GestoreSistema.getInstanza();

        docente = (Docente) CatalogoUtenti.getIstanza().aggiungiUtente(
                "anna.bianchi@docenti.unina.it", "Anna", "Bianchi", "Doc12345", "Docente"
        );

        studente = (Studente) CatalogoUtenti.getIstanza().aggiungiUtente(
                "m.rossi@studenti.unina.it", "Mario", "Rossi", "Pass1234", "Studente"
        );

        corso    = new Corso("Basi di Dati", "Corso di BD, 9 CFU", CODICE_ESISTENTE, "2026/2027", docente);


        CatalogoCorsi.getIstanza().AggiungiCorso(corso);
        gestore.autenticazione(studente.getMail(), studente.getPassword());
    }

    @AfterEach
    void tearDown() {

        if (CatalogoUtenti.getIstanza().trovaUtente(studente.getMail())) {
            gestore.eliminaUtente(studente.getMail(), null, null, null, null);
        }
    }

    @Test
    void id1_iscrizioneEffettuata() {
        int esito = gestore.IscrivitiaCorso(CODICE_ESISTENTE);

        assertEquals(0, esito, "atteso 0 = iscrizione effettuata");

        //per recuperare lo studente aggiornato dei corsi dal database
        Studente studenteAggiornato = (Studente) CatalogoUtenti.getIstanza().trovaUtente(studente.getMail(), studente.getPassword());

        assertTrue(studenteAggiornato.getListaCorsiStudente()
                .stream().anyMatch(c -> c.getCodice() == CODICE_ESISTENTE));
    }

    @Test
    void id2_corsoNonTrovato() {
        int esito = gestore.IscrivitiaCorso(CODICE_INESISTENTE);

        assertEquals(2, esito, "atteso 2 = corso non trovato");
    }

    @Test
    void id3_studenteGiaIscritto() {
        gestore.IscrivitiaCorso(CODICE_ESISTENTE);              // prima iscrizione (pre-condizione)
        int esito = gestore.IscrivitiaCorso(CODICE_ESISTENTE);  // tentativo ripetuto

        assertEquals(1, esito, "atteso 1 = gia' iscritto");
    }


    @Test
    void id4_codiceNonValido() {
        assertFalse(gestore.ValidaDati("IS26"));
    }
}