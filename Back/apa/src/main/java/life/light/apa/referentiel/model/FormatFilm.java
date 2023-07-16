package life.light.apa.referentiel.model;

import jakarta.persistence.*;

@Entity
@Table(name = "format_film")
public class FormatFilm {
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
