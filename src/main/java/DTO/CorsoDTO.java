package DTO;
import entity.Corso;

public class CorsoDTO {

    private  Long id;
    private  String titolo;
    private String descr;
    private int codice;
    private  String annoAcc;
    private  String gestore;
    private int numIscritti;

    public CorsoDTO(){}
    public CorsoDTO(Corso corso) {
        this.id = corso.getId();
        this.titolo = corso.getTitolo();
        this.descr = corso.getDescr();
        this.codice = corso.getCodice();
        this.annoAcc = corso.getAnnoAcc();
        if (corso.getGestore() == null) {
            this.gestore = "";
        } else {
            this.gestore = corso.getGestore();
        }
        this.numIscritti = corso.getListaIscritti().size();
    }

    public String getTitolo() {
        return titolo;
    }
    public void setTitolo(String titolo) {this.titolo = titolo;}

    public String getDescr() {
        return descr;
    }
    public void setDescr(String descr) {this.descr = descr;}

    public int getCodice() {
        return codice;
    }
    public void setCodice(int codice) {this.codice = codice;}

    public String getAnnoAcc(){return annoAcc;}
    public void setAnnoAccademico(String annoAcc){ this.annoAcc = annoAcc;}

}