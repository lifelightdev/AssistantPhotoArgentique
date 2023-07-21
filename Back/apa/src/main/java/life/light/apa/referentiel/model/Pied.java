package life.light.apa.referentiel.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pied")
public class Pied {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "materiel_id")
    private Materiel materiel;
    private String dimensionOuvert;
    private String dimensionFerme;
    private String pasDeVis;
    @ManyToOne
    private Rotule rotule;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Materiel getMateriel() {
        return materiel;
    }

    public void setMateriel(Materiel materiel) {
        this.materiel = materiel;
    }

    public String getDimensionOuvert() {
        return dimensionOuvert;
    }

    public void setDimensionOuvert(String dimensionOuvert) {
        this.dimensionOuvert = dimensionOuvert;
    }

    public String getDimensionFerme() {
        return dimensionFerme;
    }

    public void setDimensionFerme(String dimensionFerme) {
        this.dimensionFerme = dimensionFerme;
    }

    public Rotule getRotule() {
        return rotule;
    }

    public void setRotule(Rotule rotule) {
        this.rotule = rotule;
    }

    public String getPasDeVis() {
        return pasDeVis;
    }

    public void setPasDeVis(String pasDeVis) {
        this.pasDeVis = pasDeVis;
    }
}
