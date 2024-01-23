package life.light.apa.priseDeVue.dto;

import life.light.apa.priseDeVue.model.Vue;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Geo {

    private String type = "FeatureCollection";
    private List<Feature> features;

    public Geo(Set<Vue> listeDesVues) {
        super();
        this.features = (listeDesVues.stream().map(Feature::new).collect(Collectors.toList()));
    }

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

