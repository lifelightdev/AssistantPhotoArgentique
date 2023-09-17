package life.light.apa.referentiel.model;

import jakarta.persistence.*;

@Entity
@Table(name = "produit")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    @ManyToOne
    @JoinColumn(name = "type_id")
    private TypeProduit typeProduit;
    @ManyToOne
    @JoinColumn(name = "statut_id")
    private StatutProduit statutProduit;
    @ManyToOne
    @JoinColumn(name = "modele_id")
    private Modele modele;
    @Lob
    @Column(name = "photo", columnDefinition="MEDIUMBLOB")
    private byte[] photo;
    @Lob
    @Column(name = "mode_emploi", columnDefinition="MEDIUMBLOB")
    private byte[] modeEmploi;
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

    public TypeProduit getTypeProduit() {
        return typeProduit;
    }

    public void setTypeProduit(TypeProduit typeProduit) {
        this.typeProduit = typeProduit;
    }

    public StatutProduit getStatutProduit() {
        return statutProduit;
    }

    public void setStatutProduit(StatutProduit statutProduit) {
        this.statutProduit = statutProduit;
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
