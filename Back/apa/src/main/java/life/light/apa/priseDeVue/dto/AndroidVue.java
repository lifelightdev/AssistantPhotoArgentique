package life.light.apa.priseDeVue.dto;

import life.light.apa.priseDeVue.model.Vue;
import life.light.apa.referentiel.model.Ouverture;
import life.light.apa.referentiel.model.Vitesse;

import java.util.List;
import java.util.stream.Collectors;

public class AndroidVue {

    private Long id;
    private String nomAppareilPhoto;
    private String sensibilite;
    private List<String> vitesses;
    private List<String> ouvertures;

    public AndroidVue(Vue vue) {
        super();
        this.id = vue.getId();
        this.nomAppareilPhoto = vue.getAppareilPhoto().getMateriel().getNom();
        this.sensibilite = vue.getFilm().getSensibilite().getNom();
        this.ouvertures = vue.getAppareilPhoto().getObjectif().getOuvertures().stream()
                .map(Ouverture::getNom)
                .collect(Collectors.toList());
        this.vitesses = vue.getAppareilPhoto().getObjectif().getVitesses().stream()
                .map(Vitesse::getNom)
                .collect(Collectors.toList());
    }

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
