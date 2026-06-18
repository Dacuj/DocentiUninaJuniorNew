package entity;


import jakarta.persistence.*;
import java.util.*;
import java.util.concurrent.Phaser;

@Entity
public class Sezione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 100)
    private String titolo;

     @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
     @JoinColumn(name = "sezione_id")
     @OrderBy("titolo ASC")
     private Set<Materiale> listaMateriali = new HashSet<>();

    public Sezione(){};
    public Sezione(String titolo){this.titolo = titolo;}

    public long getId(){return id;}
    public String getTitolo() {return titolo;}

    public Set<Materiale> getListaMateriali(){return listaMateriali;}
    public List<Materiale> getListaMaterialiVisibili(){
        List<Materiale> listaMatVis = new ArrayList<>();
       for (Materiale materiale : listaMateriali)
            if(materiale.getVisibilitaCorrente()) listaMatVis.add(materiale);
       return listaMatVis;}




    }

