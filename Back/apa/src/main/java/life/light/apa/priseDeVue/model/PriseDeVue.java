package life.light.apa.priseDeVue.model;

import jakarta.persistence.*;
import life.light.apa.referentiel.model.Materiel;
import life.light.apa.referentiel.model.Produit;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "prise_de_vue")
public class PriseDeVue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private LocalDateTime date;
    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;
    private String remarque;
    @ManyToOne
    @JoinColumn(name = "statut_id")
    private StatutPriseDeVue statutPriseDeVue;
    @ManyToMany
    @JoinTable(name = "prise_de_vue_materiel",
            joinColumns = @JoinColumn(name = "prise_de_vue_id"),
            inverseJoinColumns = @JoinColumn(name = "materiel_id"))
    private Set<Materiel> materiels = new HashSet<>();
    @ManyToMany
    @JoinTable(name = "prise_de_vue_produit",
            joinColumns = @JoinColumn(name = "prise_de_vue_id"),
            inverseJoinColumns = @JoinColumn(name = "produit_id"))
    private Set<Produit> produits = new HashSet<>();

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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getRemarque() {
        return remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }

    public StatutPriseDeVue getStatutPriseDeVue() {
        return statutPriseDeVue;
    }

    public void setStatutPriseDeVue(StatutPriseDeVue statutPriseDeVue) {
        this.statutPriseDeVue = statutPriseDeVue;
    }

    public Set<Materiel> getMateriels() {
        return materiels;
    }

    public void setMateriels(Set<Materiel> materiels) {
        this.materiels = materiels;
    }

    public Set<Produit> getProduits() {
        return produits;
    }

    public void setProduits(Set<Produit> produits) {
        this.produits = produits;
    }

}
