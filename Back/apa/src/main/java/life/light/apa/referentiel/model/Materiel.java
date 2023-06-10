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
    private String name;
    @ManyToOne
    @JoinColumn(name = "type_id")
    private TypeMateriel type;
    @ManyToOne
    @JoinColumn(name = "sous_type_id")
    private SousTypeMateriel sousType;
    @ManyToOne
    @JoinColumn(name = "status_id")
    private StatusMateriel status;
    @ManyToOne
    @JoinColumn(name = "marque_id")
    private Marque marque;
    private String modele;
    private File photo;
    private File modeEmploie;
    private String remarque;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StatusMateriel getStatus() {
        return status;
    }

    public void setStatus(StatusMateriel status) {
        this.status = status;
    }

    public Marque getMarque() {
        return marque;
    }

    public void setMarque(Marque marque) {
        this.marque = marque;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
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

    public SousTypeMateriel getSousType() {
        return sousType;
    }

    public void setSousType(SousTypeMateriel sousType) {
        this.sousType = sousType;
    }

    public TypeMateriel getType() {
        return type;
    }

    public void setType(TypeMateriel type) {
        this.type = type;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
