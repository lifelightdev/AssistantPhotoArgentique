package life.light.apa.priseDeVue.model;

import jakarta.persistence.*;
import life.light.apa.referentiel.model.Materiel;
import life.light.apa.referentiel.model.Produit;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "prise_de_vue")
public class PriseDeVue {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    private String nom;
    private LocalDate date;
    private String position;
    private Double latitude;
    private Double longitude;
    private String adresse;
    @ManyToOne
    @JoinColumn(name = "statut_id")
    private StatutPriseDeVue statutPriseDeVue;
    private String remarque;
    @ManyToMany
    @JoinTable(name = "prise_de_vue_materiel",
            joinColumns = @JoinColumn(name = "prise_de_vue_id"),
            inverseJoinColumns = @JoinColumn(name = "materiel_id"))
    private List<Materiel> materiels = new ArrayList<>();
    @ManyToMany
    @JoinTable(name = "prise_de_vue_produit",
            joinColumns = @JoinColumn(name = "prise_de_vue_id"),
            inverseJoinColumns = @JoinColumn(name = "produit_id"))
    private List<Produit> produits = new ArrayList<>();

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public StatutPriseDeVue getStatutPriseDeVue() {
        return statutPriseDeVue;
    }

    public void setStatutPriseDeVue(StatutPriseDeVue statutPriseDeVue) {
        this.statutPriseDeVue = statutPriseDeVue;
    }

    public String getRemarque() {
        return remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }

    public List<Materiel> getMateriels() {
        return materiels;
    }

    public void setMateriels(List<Materiel> materiels) {
        this.materiels = materiels;
    }

    public List<Produit> getProduits() {
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }
}
