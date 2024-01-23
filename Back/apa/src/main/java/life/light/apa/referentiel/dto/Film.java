package life.light.apa.referentiel.dto;

import life.light.apa.priseDeVue.model.Vue;
import life.light.apa.referentiel.model.*;

import java.util.HashSet;
import java.util.Set;

public class Film extends Produit{

    private Long id;
    private StatutFilm statutFilm;
    private TailleFilm tailleFilm;
    private TypeFilm typeFilm;
    private Integer nbVuePossible;
    private Integer nbVueExpose;
    private Sensibilite sensibilite;
    private TailleVue tailleVue;
    private Set<Vue> vues = new HashSet<>();

    public Film(String nom, TypeProduit typeProduit, StatutProduit statutProduit, Modele modele, byte[] photo,
                byte[] modeEmploi, String remarque) {
        super(nom, typeProduit, statutProduit, modele, photo, modeEmploi, remarque);
    }

}
