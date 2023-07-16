package life.light.apa.referentiel.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sous_type_materiel")
public class SousTypeMateriel {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "type_id")
    private TypeMateriel type;
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
