package database.DTO;
import entity.Materiale;
import entity.Categoria;
import java.time.LocalDate;

public class MaterialeDTO {
    private long id;
    private String titolo, descr;
    private LocalDate dataPubbl;
    private Categoria categoria;
    private boolean visibile;

    public MaterialeDTO(Materiale materiale){
        this.id = materiale.getId();
        this.titolo = materiale.getTitolo();
        this.descr = materiale.getDescr();
        this.categoria = materiale.getCategoria();
        this.dataPubbl = materiale.getDataPubbl();
        this.visibile = materiale.getVisibilitaCorrente();
    }
    public Long getId() {
        return id;
    }

    public String getTitolo() {
        return titolo;
    }

    public String getDescrizione() {
        return descr;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public LocalDate getDataPub() {
        return dataPubbl;
    }

    public boolean isVisibile() {
        return visibile;
    }


    @Override
    public String toString() {
        return titolo + " (" + categoria + ", " + dataPubbl + ")";
    }
}

