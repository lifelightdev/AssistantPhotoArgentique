package life.light.apa.referentiel.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "objectif")
public class Objectif {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @ManyToMany
    @JoinTable(name = "objectif_ouverture",
            joinColumns = @JoinColumn(name = "objectif_id"),
            inverseJoinColumns = @JoinColumn(name = "ouverture_id"))
    @OrderBy(value = "ordre")
    private Set<Ouverture> ouvertures = new HashSet<>();
    @ManyToMany
    @JoinTable(name = "objectif_vitesse",
            joinColumns = @JoinColumn(name = "objectif_id"),
            inverseJoinColumns = @JoinColumn(name = "vitesse_id"))
    @OrderBy(value = "ordre")
    private Set<Vitesse> vitesses = new HashSet<>();

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

    public Set<Ouverture> getOuvertures() {
        return ouvertures;
    }

    public void setOuvertures(Set<Ouverture> ouvertures) {
        this.ouvertures = ouvertures;
    }

    public Set<Vitesse> getVitesses() {
        return vitesses;
    }

    public void setVitesses(Set<Vitesse> vitesses) {
        this.vitesses = vitesses;
    }
}
