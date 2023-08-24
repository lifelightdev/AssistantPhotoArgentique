package life.light.apa.referentiel.model;

import jakarta.persistence.*;
import life.light.apa.priseDeVue.model.Vue;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "film")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private Integer nbVuePossible;
    private Integer nbVueExpose;
    @ManyToOne
    @JoinColumn(name = "sensibilite_id")
    private Sensibilite sensibilite;
    @ManyToOne
    @JoinColumn(name = "taille_vue_id")
    private TailleVue tailleVue;
    @ManyToMany
    @JoinTable(name = "film_vue",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "vue_id"))
    private Set<Vue> vues = new HashSet<>();

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

    public Integer getNbVuePossible() {
        return nbVuePossible;
    }

    public void setNbVuePossible(Integer nbVuePossible) {
        this.nbVuePossible = nbVuePossible;
    }

    public Integer getNbVueExpose() {
        return nbVueExpose;
    }

    public void setNbVueExpose(Integer nbVueExpose) {
        this.nbVueExpose = nbVueExpose;
    }

    public Sensibilite getSensibilite() {
        return sensibilite;
    }

    public void setSensibilite(Sensibilite sensibilite) {
        this.sensibilite = sensibilite;
    }

    public TailleVue getTailleVue() {
        return tailleVue;
    }

    public void setTailleVue(TailleVue tailleVue) {
        this.tailleVue = tailleVue;
    }

    public Set<Vue> getVues() {
        return vues;
    }

    public void setVues(Set<Vue> vues) {
        this.vues = vues;
    }
}
