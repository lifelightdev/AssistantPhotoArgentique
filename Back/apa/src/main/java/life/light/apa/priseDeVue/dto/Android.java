package life.light.apa.priseDeVue.dto;

import java.util.List;

public class Android {

    private List<AndroidVue> vues;

    public Android(List<AndroidVue> vues) {
        super();
        this.vues = vues;
    }

    public List<AndroidVue> getVues() {
        return vues;
    }

    public void setVues(List<AndroidVue> vues) {
        this.vues = vues;
    }
}
