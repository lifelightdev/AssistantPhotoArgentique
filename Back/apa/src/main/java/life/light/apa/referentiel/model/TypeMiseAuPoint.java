package life.light.apa.referentiel.model;

import jakarta.persistence.*;

@Entity
@Table(name = "type_mise_au_point_photo")
public class TypeMiseAuPoint {
    @Id
    @Column(name = "id", nullable = false)
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
