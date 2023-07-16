package life.light.apa.referentiel.model;

import jakarta.persistence.*;

@Entity
@Table(name = "taille_film")
public class TailleFilm {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    private String nom;
    private String taille;
    @ManyToOne
    @JoinColumn(name = "format_film_id")
    private FormatFilm formatFilm;

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

    public String getTaille() {
        return taille;
    }

    public void setTaille(String taille) {
        this.taille = taille;
    }

    public FormatFilm getFormatFilm() {
        return formatFilm;
    }

    public void setFormatFilm(FormatFilm formatFilm) {
        this.formatFilm = formatFilm;
    }
}
