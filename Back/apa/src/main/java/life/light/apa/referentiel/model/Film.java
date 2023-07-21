package life.light.apa.referentiel.model;

import jakarta.persistence.*;

@Entity
@Table(name = "film")
public class Film {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "produit_id")
    private Produit produit;
    @ManyToOne
    @JoinColumn(name = "statut_film_id")
    private StatutFilm statutFilm;
    @ManyToOne
    @JoinColumn(name = "taille_film_id")
    private TailleFilm tailleFilm;
    @ManyToOne
    @JoinColumn(name = "type_film_id")
    private TypeFilm typeFilm;

    // private List<AppareilPhoto> listeAppareilPhotoCompatible;
    // private List<Chassis> listeChassisCompatible;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public StatutFilm getStatutFilm() {
        return statutFilm;
    }

    public void setStatutFilm(StatutFilm statutFilm) {
        this.statutFilm = statutFilm;
    }

    public TailleFilm getTailleFilm() {
        return tailleFilm;
    }

    public void setTailleFilm(TailleFilm tailleFilm) {
        this.tailleFilm = tailleFilm;
    }

    public TypeFilm getTypeFilm() {
        return typeFilm;
    }

    public void setTypeFilm(TypeFilm typeFilm) {
        this.typeFilm = typeFilm;
    }
}
