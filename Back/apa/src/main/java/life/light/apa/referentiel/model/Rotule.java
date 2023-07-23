package life.light.apa.referentiel.model;

import jakarta.persistence.*;

@Entity
@Table(name = "rotule")
public class Rotule {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "materiel_id")
    private Materiel materiel;
    private String pasDeVisMateriel;
    private String pasDeVisPied;
    private Boolean avecFixationRapide;

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

    public String getPasDeVisMateriel() {
        return pasDeVisMateriel;
    }

    public void setPasDeVisMateriel(String pasDeVisMateriel) {
        this.pasDeVisMateriel = pasDeVisMateriel;
    }

    public String getPasDeVisPied() {
        return pasDeVisPied;
    }

    public void setPasDeVisPied(String pasDeVisPied) {
        this.pasDeVisPied = pasDeVisPied;
    }

    public Boolean getAvecFixationRapide() {
        return avecFixationRapide;
    }

    public void setAvecFixationRapide(Boolean avecFixationRapide) {
        this.avecFixationRapide = avecFixationRapide;
    }
}
