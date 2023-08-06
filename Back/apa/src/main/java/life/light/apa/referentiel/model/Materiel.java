package life.light.apa.referentiel.model;

import jakarta.persistence.*;

@Entity
@Table(name = "materiel")
public class Materiel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Lob
    @Column(name = "photo", columnDefinition="BLOB")
    private byte[] photo;
    @Lob
    @Column(name = "mode_emploie", columnDefinition="BLOB")
    private byte[] modeEmploie;
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

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public byte[] getModeEmploie() {
        return modeEmploie;
    }

    public void setModeEmploie(byte[] modeEmploie) {
        this.modeEmploie = modeEmploie;
    }

    public String getRemarque() {
        return remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }
}
