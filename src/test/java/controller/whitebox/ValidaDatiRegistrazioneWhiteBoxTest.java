package controller.whitebox;

import controller.GestoreSistema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ValidaDatiRegistrazioneWhiteBoxTest {

    private GestoreSistema gestore;

    // email istituzionale valida e non presente nel DB
    private static final String MAIL_OK = "wb.path@studenti.unina.it";

    @BeforeEach
    void setUp() { gestore = GestoreSistema.getInstanza(); }

    @Test
    void path01_nomeNull() {
        assertEquals(0, gestore.ValidaDati(null, "Rossi", MAIL_OK, "Studente", "Pass1234"));
    }

    @Test
    void path02_nomeTroppoLungo() {
        assertEquals(0, gestore.ValidaDati("A".repeat(51), "Rossi", MAIL_OK, "Studente", "Pass1234"));
    }

    @Test
    void path03_nomeConSimbolo() {
        assertEquals(0, gestore.ValidaDati("Mario1", "Rossi", MAIL_OK, "Studente", "Pass1234"));
    }

    @Test
    void path04_cognomeNull() {
        assertEquals(1, gestore.ValidaDati("Mario", null, MAIL_OK, "Studente", "Pass1234"));
    }

    @Test
    void path05_cognomeTroppoLungo() {
        assertEquals(1, gestore.ValidaDati("Mario", "A".repeat(51), MAIL_OK, "Studente", "Pass1234"));
    }

    @Test
    void path06_cognomeConSimbolo() {
        assertEquals(1, gestore.ValidaDati("Mario", "Rossi!", MAIL_OK, "Studente", "Pass1234"));
    }
    
    @Test
    void path07_mailNull() {
        assertEquals(2, gestore.ValidaDati("Mario", "Rossi", null, "Studente", "Pass1234"));
    }

    @Test
    void path08_checkMailZero() {
        assertEquals(2, gestore.ValidaDati("Mario", "Rossi", "m.rossi@gmail.com", "Studente", "Pass1234"));
    }

    @Test
    void path09_mailTroppoLunga() {
        String mailLunga = "a".repeat(40) + "@studenti.unina.it"; // 58 caratteri, dominio valido
        assertEquals(2, gestore.ValidaDati("Mario", "Rossi", mailLunga, "Studente", "Pass1234"));
    }

    @Test
    void path10_passwordNull() {
        assertEquals(3, gestore.ValidaDati("Mario", "Rossi", MAIL_OK, "Studente", null));
    }

    @Test
    void path11_passwordTroppoLunga() {
        assertEquals(3, gestore.ValidaDati("Mario", "Rossi", MAIL_OK, "Studente", "a".repeat(31)));
    }

    @Test
    void path12_tuttiValidi() {
        assertEquals(4, gestore.ValidaDati("Mario", "Rossi", MAIL_OK, "Studente", "Pass1234"));
    }
}