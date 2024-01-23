package life.light.apa.referentiel.model;

import jakarta.persistence.*;

@Entity
@Table(name = "materiel")
public class Materiel implements CopiableSousFormeDeFichier {
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
    @Column(name = "photo", columnDefinition = "MEDIUMBLOB")
    private byte[] photo;
    @Lob
    @Column(name = "mode_emploi", columnDefinition = "MEDIUMBLOB")
    private byte[] modeEmploi;
    private String remarque;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
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

    @Override
    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Override
    public byte[] getModeEmploi() {
        return modeEmploi;
    }

    public void setModeEmploi(byte[] modeEmploi) {
        this.modeEmploi = modeEmploi;
    }

    public String getRemarque() {
        return remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }
}
