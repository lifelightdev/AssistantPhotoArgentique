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
import java.time.LocalDate;
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
        if ((allParams.containsKey("nom")) && (!"undefined".equals(allParams.get("nom"))) && (!"".equals(allParams.get("nom").trim()))) {
            liste = ListUtils.union(liste, priseDeVueRepository.findAll(where(nomLike(allParams.get("nom")))));
        }
        if ((allParams.containsKey("statutPriseDeVue")) && (!"undefined".equals(allParams.get("statutPriseDeVue"))) && (!"0".equals(allParams.get("statutPriseDeVue").trim()))) {
            liste = ListUtils.union(liste, priseDeVueRepository.findAll(where(idStatutLike(Long.valueOf(allParams.get("statutPriseDeVue"))))));
        }
        if ((allParams.containsKey("date")) && (!"undefined".equals(allParams.get("date"))) && (!"0".equals(allParams.get("date").trim()))) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate localDate = LocalDate.parse(allParams.get("date"), formatter);
            liste = ListUtils.union(liste, priseDeVueRepository.findAll(where(dateLike(localDate))));
        }
        if ((allParams.containsKey("position")) && (!"undefined".equals(allParams.get("position"))) && (!"0".equals(allParams.get("position").trim()))) {
            liste = ListUtils.union(liste, priseDeVueRepository.findAll(where(positionLike(allParams.get("position")))));
        }
        if ((allParams.containsKey("remarque")) && (!"undefined".equals(allParams.get("remarque")))) {
            liste = ListUtils.union(liste, priseDeVueRepository.findAll(where(remarqueLike(allParams.get("remarque")))));
        }
        if (liste.isEmpty()) {
            liste = priseDeVueRepository.findAll();
        }
        for (PriseDeVue priseDeVue : liste) {
            GeoJson geoJson = new GeoJson();
            List<Feature> features = new ArrayList<>();
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
            geoJson.setFeatures(features);
            String path = "D:\\IdeaProjects\\AssistantPhotoArgentique\\Front\\apa\\src\\assets\\Data\\photo.geojson";
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.writeValue(new File(path), geoJson);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
