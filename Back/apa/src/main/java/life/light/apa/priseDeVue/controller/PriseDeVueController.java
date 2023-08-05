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
import javax.imageio.stream.FileImageOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static life.light.apa.priseDeVue.dao.PriseDeVueSpecification.*;
import static org.springframework.data.jpa.domain.Specification.where;

@CrossOrigin(origins = "http://127.0.0.1:4200")
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
        if ((allParams.containsKey("nom")) && (!"undefined".equals(allParams.get("nom"))) && (!allParams.get("nom").trim().isEmpty())) {
            liste = ListUtils.union(liste, priseDeVueRepository.findAll(where(nomLike(allParams.get("nom")))));
            trouver = true;
        }
        if ((allParams.containsKey("statutPriseDeVue")) && (!"undefined".equals(allParams.get("statutPriseDeVue"))) && (!"0".equals(allParams.get("statutPriseDeVue").trim()))) {
            liste = ListUtils.union(liste, priseDeVueRepository.findAll(where(idStatutLike(Long.valueOf(allParams.get("statutPriseDeVue"))))));
            trouver = true;
        }
        if ((allParams.containsKey("date")) && (!"undefined".equals(allParams.get("date"))) && (!allParams.get("date").trim().isEmpty())) {
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
        if (!trouver) {
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
    public Optional<Vue> afficherUneVue(@PathVariable long id) throws IOException {
        // Envoie des photos sur le serveur front en dur
        Vue vue = vueRepository.findVuebyId(id);
        if (null != vue.getPhoto()) {
            String imageFileName = vue.getAppareilPhoto().getMateriel().getNom() + "-" + vue.getNom() + "-" + vue.getId();
            String extension = ".jpg";
            File file = new File("D:\\IdeaProjects\\AssistantPhotoArgentique\\Front\\apa\\src\\assets\\Images\\" + imageFileName + extension);
            file.createNewFile();
            FileImageOutputStream fos = new FileImageOutputStream(file);
            fos.write(vue.getPhoto());
            fos.close();
        }
        return vueRepository.findById(id);
    }

    @RequestMapping(value = "/priseDeVue/{id}/vue")
    public Iterable<Vue> afficherToutesLesVueDUnePriseDeVue(@PathVariable long id) throws IOException {
        // Envoie des photos sur le serveur front en dur
        List<Vue> vues = priseDeVueRepository.findVueByPriseDeVueId(id);
        for (Vue vue : vues) {
            if (null != vue.getPhoto()) {
                String imageFileName = vue.getAppareilPhoto().getMateriel().getNom() + "-" + vue.getNom() + "-" + vue.getId();
                String extension = ".jpg";
                File file = new File("D:\\IdeaProjects\\AssistantPhotoArgentique\\Front\\apa\\src\\assets\\Images\\" + imageFileName + extension);
                file.createNewFile();
                FileImageOutputStream fos = new FileImageOutputStream(file);
                fos.write(vue.getPhoto());
                fos.close();
            }
        }
        return vues;

    }

    @RequestMapping(value = "/android/vue")
    public Optional<Android> listeAndroidVue() {
        List<Vue> listeDeVue = vueRepository.findAll();
        List<AndroidVue> listeAndroidVue = new ArrayList<>();
        for (Vue vue : listeDeVue) {
            AndroidVue androidVue = new AndroidVue();
            androidVue.setId(vue.getId());
            if (vue.getPosition() != null) {
                androidVue.setLatitude(vue.getPosition().getLatitude());
                androidVue.setLongitude(vue.getPosition().getLongitude());
                if (vue.getAppareilPhoto() != null) {
                    androidVue.setNomAppareilPhoto(vue.getAppareilPhoto().getMateriel().getNom());
                    if (vue.getAppareilPhoto().getFilmCharge() != null) {
                        androidVue.setSensibilite(vue.getAppareilPhoto().getFilmCharge().getSensibilite().getNom());
                    } else if (vue.getAppareilPhoto().getChassis().getFilm() != null) {
                        System.out.println("Il n'y a pas de film chargé dans cet appareil photo " + vue.getAppareilPhoto().getMateriel().getNom());
                        androidVue.setSensibilite(vue.getAppareilPhoto().getChassis().getFilm().getSensibilite().getNom());
                        List<String> ouvetures = new ArrayList<>();
                        for (Ouverture o : vue.getAppareilPhoto().getObjectif().getOuvertures()) {
                            ouvetures.add(o.getNom());
                        }
                        androidVue.setOuvertures(ouvetures);
                        List<String> vitesses = new ArrayList<>();
                        for (Vitesse v : vue.getAppareilPhoto().getObjectif().getVitesses()) {
                            vitesses.add(v.getNom());
                        }
                        androidVue.setVitesses(vitesses);
                        if (androidVue.getSensibilite() != null
                                && !androidVue.getOuvertures().isEmpty()
                                && !androidVue.getVitesses().isEmpty()) {
                            listeAndroidVue.add(androidVue);
                            // Duplication par flaime de saisie d'une deuxieme vue
                            listeAndroidVue.add(androidVue);
                        }
                    } else {
                        System.out.println("Il n'y a pas de film chargé dans le chassis cet appareil photo " + vue.getAppareilPhoto().getMateriel().getNom());
                    }
                } else {
                    System.out.println("Il n'y a pas d'appareil photo dans cette vue " + vue.getId());
                }
            } else {
                System.out.println("Il n'y a pas de position dans cette vue " + vue.getId());
            }
        }
        System.out.println("Il y a " + listeAndroidVue.size() + " vue(s) ");
        Android android = new Android();
        android.setVues(listeAndroidVue);

        return Optional.of(android);
    }

}
