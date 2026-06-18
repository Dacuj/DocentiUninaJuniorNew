package controller.blackbox;
import controller.GestoreSistema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegistrazioneTest {
    //Tabella SRS aggiornata
    private GestoreSistema gestore;

    @BeforeEach
    void setUp() { gestore = GestoreSistema.getInstanza(); }

    @Test
    void id1_tuttiValidiStudente() {
        int esito = gestore.ValidaDati("Mario", "Rossi", "m.rossi@studenti.unina.it", "Studente", "Pass1234");
        assertEquals(4, esito);
    }

    @Test
    void id2_tuttiValidiDocente() {
        int esito = gestore.ValidaDati("Anna", "Bianchi", "a.bianchi@docenti.unina.it", "Docente", "Doc12345");
        assertEquals(4, esito);
    }

    @Test
    void id3_nomeTroppoLungo() {
        int esito = gestore.ValidaDati("A".repeat(60), "Rossi", "m.rossi@studenti.unina.it", "Studente", "Pass1234");
        assertEquals(0, esito);
    }

    @Test
    void id4_nomeConSimboli() {
        int esito = gestore.ValidaDati("Mario_1", "Rossi", "m.rossi@studenti.unina.it", "Studente", "Pass1234");
        assertEquals(0, esito);
    }

    @Test
    void id5_cognomeTroppoLungo() {
        int esito = gestore.ValidaDati("Mario", "A".repeat(55), "m.rossi@studenti.unina.it", "Studente", "Pass1234");
        assertEquals(1, esito);
    }

    @Test
    void id6_cognomeConSimboli() {
        int esito = gestore.ValidaDati("Mario", "Rossi!", "m.rossi@studenti.unina.it", "Studente", "Pass1234");
        assertEquals(1, esito);
    }

    @Test
    void id7_emailSenzaChiocciola() {
        int esito = gestore.ValidaDati("Mario", "Rossi", "m.rossistudenti.unina.it", "Studente", "Pass1234");
        assertEquals(2, esito);
    }

    @Test
    void id8_emailDominioNonIstituzionale() {
        int esito = gestore.ValidaDati("Mario", "Rossi", "m.rossi@gmail.com", "Studente", "Pass1234");
        assertEquals(2, esito);
    }

    @Test
    void id10_passwordTroppoLunga() {
        int esito = gestore.ValidaDati("Mario", "Rossi", "m.rossi@studenti.unina.it", "Studente", "a".repeat(31));
        assertEquals(3, esito);
    }

    @Test
    void id12_emailDuplicata() {
        // Pre-condizione: utente con la stessa email gia' presente sul DB
        gestore.registrazione("m.rossi@studenti.unina.it", "Mario", "Rossi", "Pass1234", "Studente");

        int esito = gestore.ValidaDati("Mario", "Rossi", "m.rossi@studenti.unina.it", "Studente", "Pass1234");
        assertEquals(2, esito);

        // pulizia: rimuove l'utente inserito per rendere il test ripetibile
        gestore.eliminaUtente("m.rossi@studenti.unina.it", "Mario", "Rossi", "Pass1234", "Studente");
    }
}
