package life.light.apa.referentiel.model;

import jakarta.persistence.*;

@Entity
@Table(name = "chassis")
public class Chassis {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "materiel_id")
    private Materiel materiel;
    @ManyToOne
    @JoinColumn(name = "statut_chassis_id")
    private StatutChassis statutChassis;
    @ManyToOne
    @JoinColumn(name = "dimension_chassis_id")
    private DimensionChassis dimensionChassis;
    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;

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

    public StatutChassis getStatutChassis() {
        return statutChassis;
    }

    public void setStatutChassis(StatutChassis statutChassis) {
        this.statutChassis = statutChassis;
    }

    public DimensionChassis getDimensionChassis() {
        return dimensionChassis;
    }

    public void setDimensionChassis(DimensionChassis dimensionChassis) {
        this.dimensionChassis = dimensionChassis;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }
}
