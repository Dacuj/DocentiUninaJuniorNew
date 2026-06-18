package entity;

import database.GestorePersistenza;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Year;
import java.util.*;

@Entity
public class Corso {


   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long Id;

   @Column(length = 100)
   private String titolo;

   @Column(length = 500)
   private String descr;

   @Column(unique = true, length = 6)

   private int codice;

   private String annoAcc;

   @ManyToOne
   @JoinColumn(name = "gestore")
   private Docente gestore;

   @ManyToMany(mappedBy = "corsiStudente", fetch = FetchType.EAGER)
   @OrderBy("cognome ASC , nome ASC, mail ASC")
   private Set<Studente> listaIscritti = new HashSet<>();


   @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
   @JoinColumn(name = "corso_id")
   private SortedSet<Sezione> listaSezioni = new TreeSet<>(Comparator.comparing(Sezione::getTitolo)
           .thenComparing(Sezione::getId));

   public Corso(){};

   public Corso(String titolo, String descr, int codice, String annoAcc, Docente gestore){
      this.titolo = titolo;
      this.descr=descr;
      this.codice=codice;
      this.annoAcc=annoAcc;
      this.gestore=gestore;
   }




   public long getId(){return Id;}
   public String getTitolo() {return titolo;}
   public String getGestore(){return gestore.getNome()+ " "+ gestore.getCognome();}
   public int getCodice() {return codice;}
   public Set<Studente> getListaIscritti(){return listaIscritti;}
   public String getDescr() {return descr;}
   public void setAnnoAccademico(String annoAcc){ this.annoAcc = annoAcc;}

   public String getAnnoAcc() {return annoAcc;}
}

