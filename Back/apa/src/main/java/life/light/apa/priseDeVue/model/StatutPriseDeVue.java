package life.light.apa.priseDeVue.model;

import jakarta.persistence.*;

@Entity
@Table(name = "statut_prise_de_vue")
public class StatutPriseDeVue {

    public final static Long ID_PREPARATOIRE = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
