package life.light.apa.priseDeVue.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import life.light.apa.priseDeVue.controller.*;
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
import org.springframework.stereotype.Service;

import javax.imageio.stream.FileImageOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static life.light.apa.priseDeVue.dao.PriseDeVueSpecification.*;
import static org.springframework.data.jpa.domain.Specification.where;


@Service
public class PriseDeVueService {

    public static final String PATH_ASSETS_FRONT = "F:\\IdeaProjects\\AssistantPhotoArgentique\\Front\\apa\\src\\assets";
    @Autowired
    private PriseDeVueRepository priseDeVueRepository;
    @Autowired
    private VueRepository vueRepository;
    @Autowired
    private StatutPriseDeVueRepository statutPriseDeVueRepository;

    @Autowired
    private PositionRepository positionRepository;

    public Optional<Vue> afficherUneVue(long id) throws IOException {
        Vue vue = vueRepository.findVuebyId(id);
        copieLaPhotoSurLeFront(vue);
        return vueRepository.findById(id);
    }

    public List<Vue> listeDesVueDUnePriseDeVue(long id) throws IOException {
        // Envoie des photos sur le serveur front en dur
        List<Vue> vues = priseDeVueRepository.findVueByPriseDeVueId(id);
        for (Vue vue : vues) {
            copieLaPhotoSurLeFront(vue);
        }
        return vues;
    }

    private static void copieLaPhotoSurLeFront(Vue vue) throws IOException {
        // Envoie des photos sur le serveur front en dur
        if (null != vue.getPhoto()) {
            File file = new File(PATH_ASSETS_FRONT + "\\Images\\" + vue.getId() + "-" + vue.getNom() + ".jpg");
            file.createNewFile();
            FileImageOutputStream fos = new FileImageOutputStream(file);
            fos.write(vue.getPhoto());
            fos.close();
        }
    }

    public Vue ajouterVue(Vue nouvelleVue) {
        return vueRepository.save(nouvelleVue);
    }

    public Android getAndroid() {
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
        return android;
    }

    public Optional<PriseDeVue> findById(long id) {
        return priseDeVueRepository.findById(id);
    }

    public Iterable<Position> trouveToutesLesPosition() {
        return positionRepository.findAll();
    }

    public Iterable<Materiel> afficherTousLesMaterielsdUnePriseDeVue(Long id) {
        return priseDeVueRepository.findMaterielsById(id);
    }

    public Iterable<StatutPriseDeVue> listeTousLesStatutsPriseDeVue() {
        return statutPriseDeVueRepository.findAll();
    }

    public Iterable<PriseDeVue> listeDesPriseDeVue(String nom, String statut, String date, String position, String remarque) {
        List<PriseDeVue> liste = new ArrayList<>();
        boolean trouver = false;
        if ((null != nom) && (!"undefined".equals(nom)) && (!nom.trim().isEmpty())) {
            liste = ListUtils.union(liste, priseDeVueRepository.findAll(where(nomLike(nom))));
            trouver = true;
        }
        if ((null != statut) && (!"undefined".equals(statut)) && (!"0".equals(statut.trim()))) {
            liste = ListUtils.union(liste, priseDeVueRepository.findAll(where(idStatutLike(Long.valueOf(statut)))));
            trouver = true;
        }
        if ((null != date) && (!"undefined".equals(date)) && (!date.trim().isEmpty())) {
            liste = ListUtils.union(liste, priseDeVueRepository.findAll(where(dateLike(LocalDateTime.parse(date)))));
            trouver = true;
        }
        if ((null != position) && (!"undefined".equals(position)) && (!"0".equals(position.trim()))) {
            liste = ListUtils.union(liste, priseDeVueRepository.findAll(where(positionLike(position))));
            trouver = true;
        }
        if ((null != remarque) && (!"undefined".equals(remarque))) {
            liste = ListUtils.union(liste, priseDeVueRepository.findAll(where(remarqueLike(remarque))));
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
        String path = PATH_ASSETS_FRONT + "\\Data\\photo.geojson";
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(path), geoJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return liste;
    }
}
