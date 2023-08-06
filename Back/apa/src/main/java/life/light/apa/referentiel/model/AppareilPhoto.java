package life.light.apa.referentiel.model;

import jakarta.persistence.*;

@Entity
@Table(name = "appareil_photo")
public class AppareilPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "materiel_id")
    private Materiel materiel;
    @ManyToOne
    @JoinColumn(name = "type_appareil_photo_id")
    private TypeAppareilPhoto typeAppareilPhoto;
    @ManyToOne
    @JoinColumn(name = "type_fixation_objectif_id")
    private TypeFixation typeFixationObjectif;
    @OneToOne
    @JoinColumn(name = "objectif_id")
    private Objectif objectif;
    private Boolean flashIntegre;
    @ManyToOne
    @JoinColumn(name = "type_fixation_flash_id")
    private TypeFixation typeFixationFlash;
    @ManyToOne
    @JoinColumn(name = "type_fixation_pied_id")
    private TypeFixation typeFixationPied;
    @ManyToOne
    @JoinColumn(name = "chassis_id")
    private Chassis chassis;
    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film filmCharge;
    @ManyToOne
    @JoinColumn(name = "type_mise_au_point_id")
    private TypeMiseAuPoint typeMiseAuPoint;
    private Integer nombrePile;

    // private List<Pile> listePiles;
    // private List<Objectif> listeObjectifsCompatible;
    // private List<Mouvement> listeMouvements;
    // private List<Overture> listeOuvertures;
    // private List<Vitesse> listeVitesses;
    // private Liste<Focal> listeForal;
    // private List<Chassis> listeChassisCompatible;
    // private List<Film> listeFilmsCompatible;
    // private List<TaillePriseDeVue> listeTaillesPriseDeVue;
    // private List<Focal> listeFocales;
    // private list<Flash> listeFlashesCompatiqble;
    // private list<PorteFiltre> listePorteFiltreCompatible;


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

    public TypeAppareilPhoto getTypeAppareilPhoto() {
        return typeAppareilPhoto;
    }

    public void setTypeAppareilPhoto(TypeAppareilPhoto typeAppareilPhoto) {
        this.typeAppareilPhoto = typeAppareilPhoto;
    }

    public TypeFixation getTypeFixationObjectif() {
        return typeFixationObjectif;
    }

    public void setTypeFixationObjectif(TypeFixation typeFixationObjectif) {
        this.typeFixationObjectif = typeFixationObjectif;
    }

    public Objectif getObjectif() {
        return objectif;
    }

    public void setObjectif(Objectif objectifMonte) {
        this.objectif = objectifMonte;
    }

    public Boolean getFlashIntegre() {
        return flashIntegre;
    }

    public void setFlashIntegre(Boolean flashIntegre) {
        this.flashIntegre = flashIntegre;
    }

    public TypeFixation getTypeFixationFlash() {
        return typeFixationFlash;
    }

    public void setTypeFixationFlash(TypeFixation typeFixationFlash) {
        this.typeFixationFlash = typeFixationFlash;
    }

    public TypeFixation getTypeFixationPied() {
        return typeFixationPied;
    }

    public void setTypeFixationPied(TypeFixation typeFixationPied) {
        this.typeFixationPied = typeFixationPied;
    }

    public Chassis getChassis() {
        return chassis;
    }

    public void setChassis(Chassis chassis) {
        this.chassis = chassis;
    }

    public Film getFilmCharge() {
        return filmCharge;
    }

    public void setFilmCharge(Film filmCharge) {
        this.filmCharge = filmCharge;
    }

    public TypeMiseAuPoint getTypeMiseAuPoint() {
        return typeMiseAuPoint;
    }

    public void setTypeMiseAuPoint(TypeMiseAuPoint typeMiseAuPoint) {
        this.typeMiseAuPoint = typeMiseAuPoint;
    }

    public Integer getNombrePile() {
        return nombrePile;
    }

    public void setNombrePile(Integer nombrePile) {
        this.nombrePile = nombrePile;
    }
}
