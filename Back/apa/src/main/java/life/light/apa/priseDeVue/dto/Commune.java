package life.light.apa.priseDeVue.dto;

public class Commune {
    private String nom;
    private String[] codesPostaux;
    private String code;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String[] getCodesPostaux() {
        return codesPostaux;
    }

    public void setCodesPostaux(String[] codesPostaux) {
        this.codesPostaux = codesPostaux;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
