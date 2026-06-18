package controller.whitebox;

import controller.GestoreSistema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class CheckMailWhiteBoxTest {

    private GestoreSistema gestore;

    @BeforeEach
    void setUp() { gestore = GestoreSistema.getInstanza(); }

    @Test
    void path1_mailDuplicata() {
        String mail = "cm.dup@studenti.unina.it";
        gestore.registrazione(mail, "Mario", "Rossi", "Pass1234", "Studente"); // pre-condizione
        try {
            assertEquals(2, gestore.ValidaDati("Mario", "Rossi", mail, "Studente", "Pass1234"));
        } finally {
            gestore.eliminaUtente(mail, "Mario", "Rossi", "Pass1234", "Studente"); // ripetibilita'
        }
    }

    @Test
    void path2_contoChioccioleErrato() {
        assertEquals(2, gestore.ValidaDati("Mario", "Rossi", "m.rossistudenti.unina.it", "Studente", "Pass1234"));
    }

    @Test
    void path3_studentiEStudente() {
        assertEquals(4, gestore.ValidaDati("Mario", "Rossi", "cm.stud@studenti.unina.it", "Studente", "Pass1234"));
    }

    @Test
    void path4_studentiMaRuoloDocente() {
        assertEquals(2, gestore.ValidaDati("Mario", "Rossi", "cm.mis1@studenti.unina.it", "Docente", "Pass1234"));
    }

    @Test
    void path5_docentiEDocente() {
        assertEquals(4, gestore.ValidaDati("Anna", "Bianchi", "cm.doc@docenti.unina.it", "Docente", "Doc12345"));
    }

    @Test
    void path6_docentiMaRuoloStudente() {
        assertEquals(2, gestore.ValidaDati("Anna", "Bianchi", "cm.mis2@docenti.unina.it", "Studente", "Doc12345"));
    }

    @Test
    void path7_dominioNonIstituzionale() {
        assertEquals(2, gestore.ValidaDati("Mario", "Rossi", "m.rossi@gmail.com", "Studente", "Pass1234"));
    }
}