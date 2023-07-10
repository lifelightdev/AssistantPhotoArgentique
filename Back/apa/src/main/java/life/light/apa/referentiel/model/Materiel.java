package life.light.apa.referentiel.model;

import jakarta.persistence.*;

import java.io.File;

@Entity
@Table(name = "materiel")
public class Materiel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    private String nom;
    @ManyToOne
    @JoinColumn(name = "type_id")
    private TypeMateriel typeMateriel;
    @ManyToOne
    @JoinColumn(name = "sous_type_id")
    private SousTypeMateriel sousType;
    @ManyToOne
    @JoinColumn(name = "statut_id")
    private StatutMateriel statutMateriel;
    @ManyToOne
    @JoinColumn(name = "modele_id")
    private Modele modele;
    private File photo;
    private File modeEmploie;
    private String remarque;

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

    public TypeMateriel getTypeMateriel() {
        return typeMateriel;
    }

    public void setTypeMateriel(TypeMateriel typeMateriel) {
        this.typeMateriel = typeMateriel;
    }

    public SousTypeMateriel getSousType() {
        return sousType;
    }

    public void setSousType(SousTypeMateriel sousType) {
        this.sousType = sousType;
    }

    public StatutMateriel getStatutMateriel() {
        return statutMateriel;
    }

    public void setStatutMateriel(StatutMateriel statutMateriel) {
        this.statutMateriel = statutMateriel;
    }

    public Modele getModele() {
        return modele;
    }

    public void setModele(Modele modele) {
        this.modele = modele;
    }

    public File getPhoto() {
        return photo;
    }

    public void setPhoto(File photo) {
        this.photo = photo;
    }

    public File getModeEmploie() {
        return modeEmploie;
    }

    public void setModeEmploie(File modeEmploie) {
        this.modeEmploie = modeEmploie;
    }

    public String getRemarque() {
        return remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }
}
