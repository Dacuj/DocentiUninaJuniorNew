package DTO;

import entity.Sezione;
import entity.Materiale;
import java.util.ArrayList;
import java.util.List;

public class SezioneDTO {
    private long id;
    private String titolo;
    private List<String> listaMateriali = new ArrayList<>();
    public SezioneDTO(Sezione sezione){
        this.id = sezione.getId();
        this.titolo = sezione.getTitolo();
        for (Materiale m: sezione.getListaMateriali()){
            listaMateriali.add(m.getTitolo());
        }
    }

    public Long getId() {
        return id;
    }

    public String getTitolo() {
        return titolo;
    }

    @Override
    public String toString() {
        return titolo;
    }
}
