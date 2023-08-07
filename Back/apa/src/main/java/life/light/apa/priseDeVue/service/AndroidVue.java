package life.light.apa.priseDeVue.service;

import java.util.List;

public class AndroidVue {

    private Long id;
    private String nomAppareilPhoto;
    private Double latitude;
    private Double longitude;
    private String sensibilite;
    private List<String> vitesses;
    private List<String> ouvertures;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomAppareilPhoto() {
        return nomAppareilPhoto;
    }

    public void setNomAppareilPhoto(String nomAppareilPhoto) {
        this.nomAppareilPhoto = nomAppareilPhoto;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getSensibilite() {
        return sensibilite;
    }

    public void setSensibilite(String sensibilite) {
        this.sensibilite = sensibilite;
    }

    public List<String> getVitesses() {
        return vitesses;
    }

    public void setVitesses(List<String> vitesses) {
        this.vitesses = vitesses;
    }

    public List<String> getOuvertures() {
        return ouvertures;
    }

    public void setOuvertures(List<String> ouvertures) {
        this.ouvertures = ouvertures;
    }
}
