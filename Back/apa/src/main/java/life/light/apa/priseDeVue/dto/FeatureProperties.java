package life.light.apa.priseDeVue.dto;

public class FeatureProperties {
    private String nom;
    private String adresse;
    private String vue;

    public FeatureProperties(String nom, String adresse, String vue) {
        super();
        this.nom = nom;
        this.adresse = adresse;
        this.vue = vue;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getVue() {
        return vue;
    }

    public void setVue(String vue) {
        this.vue = vue;
    }
}
