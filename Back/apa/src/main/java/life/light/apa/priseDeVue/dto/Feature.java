package life.light.apa.priseDeVue.dto;

import life.light.apa.priseDeVue.model.Vue;

public class Feature {
    private String type = "Feature";
    private Geometry geometry;
    private FeatureProperties properties;

    public Feature(Vue vue) {
        super();
        this.geometry = new Geometry(vue.getPosition().getLongitude(), vue.getPosition().getLatitude());
        this.properties = new FeatureProperties(vue.getPosition().getNom(),
                vue.getPosition().getVille() + " - " + vue.getPosition().getCodePostal(),
                vue.getNom());
    }

    public String getType() {
        return type;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public FeatureProperties getProperties() {
        return properties;
    }

    public void setProperties(FeatureProperties properties) {
        this.properties = properties;
    }
}
