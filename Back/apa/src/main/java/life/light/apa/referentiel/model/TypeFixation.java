package life.light.apa.referentiel.model;

import jakarta.persistence.*;

@Entity
@Table(name = "type_fixation")
public class TypeFixation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    private String nom;
    @ManyToOne
    @JoinColumn(name = "sous_type_materiel")
    private SousTypeMateriel sousTypeMateriel;

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

    public SousTypeMateriel getSousTypeMateriel() {
        return sousTypeMateriel;
    }

    public void setSousTypeMateriel(SousTypeMateriel sousTypeMateriel) {
        this.sousTypeMateriel = sousTypeMateriel;
    }
}
