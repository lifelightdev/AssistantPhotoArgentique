package life.light.apa.priseDeVue.model;

import jakarta.persistence.*;
import life.light.apa.referentiel.model.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "vue")
public class Vue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "prise_de_vue_id")
    private PriseDeVue priseDeVue;
    @ManyToOne
    @JoinColumn(name = "statut_vue_id")
    private StatutVue statutVue;
    private String nom;
    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;
    @ManyToOne
    @JoinColumn(name = "appareil_photo_id")
    private AppareilPhoto appareilPhoto;
    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;
    @ManyToOne
    @JoinColumn(name = "vitesse_id")
    private Vitesse vitesse;
    @ManyToOne
    @JoinColumn(name = "ouverture_id")
    private Ouverture ouverture;
    @Lob
    @Column(name = "photo", columnDefinition="MEDIUMBLOB")
    private byte[] photo;
    private LocalDateTime date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PriseDeVue getPriseDeVue() {
        return priseDeVue;
    }

    public void setPriseDeVue(PriseDeVue priseDeVue) {
        this.priseDeVue = priseDeVue;
    }

    public StatutVue getStatutVue() {
        return statutVue;
    }

    public void setStatutVue(StatutVue statutVue) {
        this.statutVue = statutVue;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public AppareilPhoto getAppareilPhoto() {
        return appareilPhoto;
    }

    public void setAppareilPhoto(AppareilPhoto appareilPhoto) {
        this.appareilPhoto = appareilPhoto;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Vitesse getVitesse() {
        return vitesse;
    }

    public void setVitesse(Vitesse vitesse) {
        this.vitesse = vitesse;
    }

    public Ouverture getOuverture() {
        return ouverture;
    }

    public void setOuverture(Ouverture ouverture) {
        this.ouverture = ouverture;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
