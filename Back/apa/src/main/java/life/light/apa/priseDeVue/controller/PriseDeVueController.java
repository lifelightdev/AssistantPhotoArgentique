package life.light.apa.priseDeVue.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import life.light.apa.priseDeVue.dao.PriseDeVueRepository;
import life.light.apa.priseDeVue.dao.StatutPriseDeVueRepository;
import life.light.apa.priseDeVue.model.PriseDeVue;
import life.light.apa.priseDeVue.model.StatutPriseDeVue;
import life.light.apa.referentiel.model.Materiel;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static life.light.apa.priseDeVue.dao.PriseDeVueSpecification.*;
import static org.springframework.data.jpa.domain.Specification.where;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class PriseDeVueController {

    @Autowired
    private PriseDeVueRepository priseDeVueRepository;

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
                coordinates.add(priseDeVue.getLongitude());
                coordinates.add(priseDeVue.getLatitude());
                Geometry geometry = new Geometry();
                geometry.setCoordinates(coordinates);
                feature.setGeometry(geometry);
                FeatureProperties featureProperties = new FeatureProperties();
                featureProperties.setNom(priseDeVue.getPosition());
                featureProperties.setAdresse(priseDeVue.getAdresse());
                feature.setProperties(featureProperties);
                features.add(feature);
            }
            if (null != priseDeVue.getDate()){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
                priseDeVue.setDateTime(priseDeVue.getDate().format(formatter)); // "1986-04-08 12:30"
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
    private StatutPriseDeVueRepository statutPriseDeVue;

    @GetMapping(value = "/statutPriseDeVue")
    public Iterable<StatutPriseDeVue> listeStatutPriseDeVue() {
        return statutPriseDeVue.findAll();
    }

    @RequestMapping(value = "/priseDeVue/materiel/{id}")
    public Iterable<Materiel> afficherLesMateriels(@RequestParam Map<String, String> allParams) {
        return priseDeVueRepository.findMaterielsById(Long.valueOf(allParams.get("id")));
    }

}
