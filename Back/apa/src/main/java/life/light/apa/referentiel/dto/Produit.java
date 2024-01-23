package life.light.apa.referentiel.dto;


import life.light.apa.referentiel.model.Modele;
import life.light.apa.referentiel.model.StatutProduit;
import life.light.apa.referentiel.model.TypeProduit;

public abstract class Produit {

    protected Long id;
    protected String nom;
    protected TypeProduit typeProduit;
    protected StatutProduit statutProduit;
    protected Modele modele;
    protected byte[] photo;
    protected byte[] modeEmploi;
    protected String remarque;

    public Produit(String nom, TypeProduit typeProduit, StatutProduit statutProduit, Modele modele, byte[] photo,
                   byte[] modeEmploi, String remarque) {
        super();
        this.nom = nom;
        this.typeProduit = typeProduit;
        this.statutProduit = statutProduit;
        this.modele = modele;
        this.photo = photo;
        this.modeEmploi = modeEmploi;
        this.remarque = remarque;
    }

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

    public String fullDetails() {
        String builder = "Produit [" + id + ", " + nom + ", " +
                typeProduit.getNom() + ", " + statutProduit.getNom() + ", " +
                modele.getNom() + ", " + remarque + "]";
        return builder;
    }

    public String shortDetails() {
        return "'" + nom + "'";
    }

    @Override
    public String toString() {
        return fullDetails();
    }
}
