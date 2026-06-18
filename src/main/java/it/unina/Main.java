package it.unina;
import boundary.FormUtenteSelectOp;
import entity.*;

public class Main {
    public static void main(String[] args) {

        CatalogoCorsi CS = CatalogoCorsi.getIstanza();
        CatalogoUtenti CU = CatalogoUtenti.getIstanza();

        CreaTabelle.main(args);
        AllacciaDB.popola(CS,CU);
        FormUtenteSelectOp.main(args);
    }
}