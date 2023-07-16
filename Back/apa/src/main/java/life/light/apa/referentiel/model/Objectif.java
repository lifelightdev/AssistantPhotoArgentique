package life.light.apa.referentiel.model;

import jakarta.persistence.*;

@Entity
@Table(name = "objectif")
public class Objectif {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "materiel_id")
    private Materiel materiel;
    @ManyToOne
    @JoinColumn(name = "type_fixation_objectif_id")
    private TypeFixation typeFixationObjectif;
    @ManyToOne
    @JoinColumn(name = "type_fixation_flash_id")
    private TypeFixation typeFixationFlash;
    @ManyToOne
    @JoinColumn(name = "type_fixation_pied_id")
    private TypeFixation typeFixationPied;
    @ManyToOne
    @JoinColumn(name = "type_fixation_filtre_id")
    private TypeFixation typeFixationFiltre;

    // private List<Ouveture> listeOuvertures;
    // private List<Vitesse> listeVitesses;
    // private List<Focal> ListeFocales;

    // private List<Flash> ListeFlashsCompatible;
    // private List<Filte> ListeFiltresCompatible;
    // private List<PorteFilte> ListePortesFiltreCompatible;
    // private List<TailleFilm> ListeTaillesFilmCompatible;
    // private List<AppareilPhoto> listeAppareilsPhotoCompatible;


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

    public TypeFixation getTypeFixationObjectif() {
        return typeFixationObjectif;
    }

    public void setTypeFixationObjectif(TypeFixation typeFixationObjectif) {
        this.typeFixationObjectif = typeFixationObjectif;
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

    public TypeFixation getTypeFixationFiltre() {
        return typeFixationFiltre;
    }

    public void setTypeFixationFiltre(TypeFixation typeFixationFiltre) {
        this.typeFixationFiltre = typeFixationFiltre;
    }
}
