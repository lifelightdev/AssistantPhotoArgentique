package life.light.apa.priseDeVue.model;

import jakarta.persistence.*;
import life.light.apa.referentiel.model.*;

@Entity
@Table(name = "vue")
public class Vue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    @Column(name = "photo", columnDefinition="BLOB")
    private byte[] photo;

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
}
