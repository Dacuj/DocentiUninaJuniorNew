package entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Materiale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 100)
    private String titolo;

    @Column(length = 500)
    private String descr;
    private boolean visibile;
    private LocalDate dataPubbl;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    public String getDescr() {return descr;}

    public long getId() {return id;}
    public String getTitolo() {return titolo;}
    public boolean getVisibilitaCorrente() {return visibile;}
    public Categoria getCategoria(){return categoria;}
    public LocalDate getDataPubbl(){return dataPubbl;}

}
