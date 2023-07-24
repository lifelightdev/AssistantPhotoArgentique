package life.light.apa.priseDeVue.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import life.light.apa.priseDeVue.dao.PositionRepository;
import life.light.apa.priseDeVue.dao.PriseDeVueRepository;
import life.light.apa.priseDeVue.dao.StatutPriseDeVueRepository;
import life.light.apa.priseDeVue.dao.VueRepository;
import life.light.apa.priseDeVue.model.Position;
import life.light.apa.priseDeVue.model.PriseDeVue;
import life.light.apa.priseDeVue.model.StatutPriseDeVue;
import life.light.apa.priseDeVue.model.Vue;
import life.light.apa.referentiel.model.Materiel;
import life.light.apa.referentiel.model.Ouverture;
import life.light.apa.referentiel.model.Vitesse;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static life.light.apa.priseDeVue.dao.PriseDeVueSpecification.*;
import static org.springframework.data.jpa.domain.Specification.where;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class PriseDeVueController {

    @Autowired
    private PriseDeVueRepository priseDeVueRepository;

    @Autowired
    private VueRepository vueRepository;

    @GetMapping(value = "/priseDeVue")
    @ResponseBody
    public Iterable<PriseDeVue> recherchePriseDeVues(@RequestParam Map<String, String> allParams) {
        List<PriseDeVue> liste = new ArrayList<>();
        boolean trouver = false;
        if ((allParams.containsKey("nom")) && (!"undefined".equals(allParams.get("nom"))) && (!"".equals(allParams.get("nom").trim()))) {
            liste = ListUtils.union(liste, priseDeVueRepository.findAll(where(nomLike(allParams.get("nom")))));
            trouver = true;
        }
        if ((allParams.containsKey("statutPriseDeVue")) && (!"undefined".equals(allParams.get("statutPriseDeVue"))) && (!"0".equals(allParams.get("statutPriseDeVue").trim()))) {
            liste = ListUtils.union(liste, priseDeVueRepository.findAll(where(idStatutLike(Long.valueOf(allParams.get("statutPriseDeVue"))))));
            trouver = true;
        }
        if ((allParams.containsKey("date")) && (!"undefined".equals(allParams.get("date"))) && (!"".equals(allParams.get("date").trim()))) {
            liste = ListUtils.union(liste, priseDeVueRepository.findAll(where(dateLike(LocalDateTime.parse(allParams.get("date"))))));
            trouver = true;
        }
        if ((allParams.containsKey("position")) && (!"undefined".equals(allParams.get("position"))) && (!"0".equals(allParams.get("position").trim()))) {
            liste = ListUtils.union(liste, priseDeVueRepository.findAll(where(positionLike(allParams.get("position")))));
            trouver = true;
        }
        if ((allParams.containsKey("remarque")) && (!"undefined".equals(allParams.get("remarque")))) {
            liste = ListUtils.union(liste, priseDeVueRepository.findAll(where(remarqueLike(allParams.get("remarque")))));
            trouver = true;
        }
        if (! trouver) {
            liste = priseDeVueRepository.findAll();
        }
        GeoJson geoJson = new GeoJson();
        List<Feature> features = new ArrayList<>();
        for (PriseDeVue priseDeVue : liste) {
            if (null != priseDeVue.getPosition()) {
                Feature feature = new Feature();
                List<Double> coordinates = new ArrayList<>();
                coordinates.add(priseDeVue.getPosition().getLongitude());
                coordinates.add(priseDeVue.getPosition().getLatitude());
                Geometry geometry = new Geometry();
                geometry.setCoordinates(coordinates);
                feature.setGeometry(geometry);
                FeatureProperties featureProperties = new FeatureProperties();
                featureProperties.setNom(priseDeVue.getPosition().getNom());
                featureProperties.setAdresse(priseDeVue.getPosition().getVille() + " - " + priseDeVue.getPosition().getCodePostal());
                feature.setProperties(featureProperties);
                features.add(feature);
            }
        }
        geoJson.setFeatures(features);
        String path = "D:\\IdeaProjects\\AssistantPhotoArgentique\\Front\\apa\\src\\assets\\Data\\photo.geojson";
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(path), geoJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return liste;
    }

    @Autowired
    private StatutPriseDeVueRepository statutPriseDeVueRepository;

    @Autowired
    private PositionRepository positionRepository;

    @GetMapping(value = "/statutPriseDeVue")
    public Iterable<StatutPriseDeVue> listeStatutPriseDeVue() {
        return statutPriseDeVueRepository.findAll();
    }

    @RequestMapping(value = "/priseDeVue/materiel/{id}")
    public Iterable<Materiel> afficherLesMateriels(@RequestParam Map<String, String> allParams) {
        return priseDeVueRepository.findMaterielsById(Long.valueOf(allParams.get("id")));
    }

    @GetMapping(value = "/position")
    public Iterable<Position> listePosition() {
        return positionRepository.findAll();
    }

    @RequestMapping(value = "/priseDeVue/{id}")
    public Optional<PriseDeVue> afficherUnMaterielePriseDeVue(@PathVariable long id) {
        return priseDeVueRepository.findById(id);
    }

    @RequestMapping(value = "/vue/{id}")
    public Optional<Vue> afficherUneVue(@PathVariable long id) {
        return vueRepository.findById(id);
    }

    @RequestMapping(value = "/ouvertures/{id}")
    public Iterable<Ouverture> listeOuverture(@PathVariable long vueId) {
        return vueRepository.findOuverture(vueId);
    }

    @RequestMapping(value = "/vitesses/{id}")
    public Iterable<Vitesse> listeVitesse(@PathVariable long vueId) {
        return vueRepository.findVitesse(vueId);
    }

}
