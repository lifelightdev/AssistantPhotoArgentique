package life.light.apa.priseDeVue.service;

import java.util.List;

public class GeoJson {

    private String type = "FeatureCollection";
    private List<Feature> features;

    public String getType() {
        return type;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }
}

