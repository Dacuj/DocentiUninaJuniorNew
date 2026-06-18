package controller.blackbox;

import controller.GestoreSistema;
import entity.CatalogoCorsi;
import entity.Docente;
import entity.CatalogoUtenti;
import entity.Corso;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreaCorsoTest {

    private GestoreSistema gestore;

    @BeforeEach
    void setUp() {
        gestore = GestoreSistema.getInstanza();

        // precondizione deve esistere davvero ed essere autenticato
        if(!CatalogoUtenti.getIstanza().trovaUtente("docente.test@docenti.unina.it"))
            CatalogoUtenti.getIstanza().aggiungiUtente("docente.test@docenti.unina.it", "Mario", "Bianchi", "Password123", "Docente");
        gestore.autenticazione("docente.test@docenti.unina.it", "Password123");

        // creazione dei corsi utilizzato nei test che esistono e non esistono

        // 202600 non deve esistere
        if (CatalogoCorsi.getIstanza().trovaCorso(202600) != null)
            CatalogoCorsi.getIstanza().eliminaCorso(202600);

        if(CatalogoCorsi.getIstanza().trovaCorso(789456) == null){
            Docente d = (Docente) CatalogoUtenti.getIstanza()
                    .trovaUtente("docente.test@docenti.unina.it","Password123");
            CatalogoCorsi.getIstanza().addCorso(new Corso("Test","corso test",789456,"2025/2026",d));
        }
    }

    /* * RISULTATI:
     * 4 = ok
     * 0 = errore titolo non valido
     * 1 = errore descrizione troppo lunga
     * 2 = errore codice non valido
     * 3 = errore anno accademico non valido
     */

    @Test
    void id1_tuttiValidi() {
        int esito = gestore.validaDatiCorso("Ingegneria del Software", "Corso di IS, 9 CFU", "202600", "2025/2026");
        assertEquals(4, esito, "Il corso dovrebbe essere creato con successo.");
    }

    @Test
    void id2_titoloVuoto() {
        int esito = gestore.validaDatiCorso("", "Corso di IS, 9 CFU", "202600", "2025/2026");
        assertEquals(0, esito, "Dovrebbe restituire errore per titolo vuoto.");
    }

    @Test
    void id3_titoloTroppoLungo() {
        String titoloLungo = "A".repeat(110);
        int esito = gestore.validaDatiCorso(titoloLungo, "Corso di IS, 9 CFU", "202600", "2025/2026");
        assertEquals(0, esito, "Dovrebbe restituire errore per titolo troppo lungo.");
    }

    @Test
    void id4_descrizioneTroppoLunga() {
        String descLunga = "A".repeat(510);
        int esito = gestore.validaDatiCorso("Ingegneria del Software", descLunga, "202600", "2025/2026");
        assertEquals(1, esito, "Dovrebbe restituire errore per descrizione troppo lunga.");
    }

    @Test
    void id5_codiceLunghezzaErrata() {
        int esito = gestore.validaDatiCorso("Ingegneria del Software", "Corso di IS, 9 CFU", "20600", "2025/2026");
        assertEquals(2, esito, "Dovrebbe restituire errore per lunghezza del codice errata.");
    }

    @Test
    void id6_codiceConSimboli() {
        int esito = gestore.validaDatiCorso("Ingegneria del Software", "Corso di IS, 9 CFU", "20#600", "2025/2026");
        assertEquals(2, esito, "Dovrebbe restituire errore per presenza di simboli nel codice.");
    }

   @Test
    void id9_codiceDuplicato() {
        int esito = gestore.validaDatiCorso("Altro Corso", "Descrizione", "789456", "2025/2026");
        assertEquals(2, esito, "Dovrebbe restituire errore per codice già utilizzato.");

    }

    @Test
    void id10_minimoAccettabile() {
        int esito = gestore.validaDatiCorso("A", "B", "202600", " ");
        assertEquals(4, esito, "Il corso dovrebbe essere creato inserendo i dati minimi indispensabili.");
    }
}