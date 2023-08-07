package life.light.apa.priseDeVue.service;

import java.util.List;

public class Geometry {
    private String type = "Point";
    private List<Double> coordinates;

    public String getType() {
        return type;
    }

    public List<Double> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
    }
}
