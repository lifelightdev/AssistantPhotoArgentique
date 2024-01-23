package life.light.apa.priseDeVue.dto;

import java.util.List;

public class Geometry {
    private String type = "Point";
    private List<Double> coordinates;

    public Geometry(double longitude, double lattitude) {
        super();
        setCoordinates(List.of(longitude, lattitude));
    }

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
