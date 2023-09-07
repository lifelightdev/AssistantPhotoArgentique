package life.light.apa.referentiel.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pied")
public class Pied {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "materiel_id")
    private Materiel materiel;
    private String dimensionOuvert;
    private String dimensionFerme;
    @ManyToOne
    @JoinColumn(name = "type_fixation_pied_id")
    private TypeFixation typeFixationPied;
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

    public TypeFixation getTypeFixationPied() {
        return typeFixationPied;
    }

    public void setTypeFixationPied(TypeFixation typeFixationPied) {
        this.typeFixationPied = typeFixationPied;
    }
}
