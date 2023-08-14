package life.light.apa.priseDeVue.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import life.light.apa.priseDeVue.PriseDeVueException;
import life.light.apa.priseDeVue.dao.*;
import life.light.apa.priseDeVue.dto.*;
import life.light.apa.priseDeVue.model.*;
import life.light.apa.referentiel.dao.AppareilPhotoRepository;
import life.light.apa.referentiel.dao.FilmRepository;
import life.light.apa.referentiel.dao.OuvertureRepository;
import life.light.apa.referentiel.dao.VitesseRepository;
import life.light.apa.referentiel.model.*;
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
    private StatutVueRepository statutVueRepository;
    @Autowired
    private AppareilPhotoRepository appareilPhotoRepository;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private OuvertureRepository ouvertureRepository;
    @Autowired
    private VitesseRepository vitesseRepository;

    public Optional<Vue> afficherUneVue(long id) throws IOException {
        Optional<Vue> vue = vueRepository.findById(id);
        copieLaPhotoSurLeFront(vue.get());
        return vue;
    }

    public List<Vue> listeDesVuesDUnePriseDeVue(long id) throws IOException {
        List<Vue> vues = priseDeVueRepository.findVueByPriseDeVueId(id);
        for (Vue vue : vues) {
            copieLaPhotoSurLeFront(vue);
        }
        return vues;
    }

    private static void copieLaPhotoSurLeFront(Vue vue) throws IOException {
        //TODO A remplacé par du dynamique car l'envoie des photos est en dur sur le serveur front
        if (null != vue.getPhoto()) {
            File file = new File(PATH_ASSETS_FRONT + "\\Images\\" + vue.getId() + "-" + vue.getNom() + ".jpg");
            file.createNewFile();
            FileImageOutputStream fos = new FileImageOutputStream(file);
            fos.write(vue.getPhoto());
            fos.close();
        }
    }

    public Android getAndroid() {
        Vue vue = vueRepository.findVueByStatutVueId(1L);
        List<AndroidVue> listeAndroidVue = new ArrayList<>();
        AndroidVue androidVue = new AndroidVue();
        androidVue.setId(vue.getId());
        androidVue.setSensibilite(vue.getFilm().getSensibilite().getNom());
        androidVue.setNomAppareilPhoto(vue.getAppareilPhoto().getMateriel().getNom());
        List<String> ouvetures = new ArrayList<>();
        for (Ouverture o : vueRepository.findOuvertureByVueIdOrdreByOuvertureOrdre(vue.getId())) {
            ouvetures.add(o.getNom());
        }
        androidVue.setOuvertures(ouvetures);
        List<String> vitesses = new ArrayList<>();
        for (Vitesse v : vueRepository.findVitesseByVueIdOrdreByVitesseOrdre(vue.getId())) {
            vitesses.add(v.getNom());
        }
        androidVue.setVitesses(vitesses);
        listeAndroidVue.add(androidVue);
        Android android = new Android();
        android.setVues(listeAndroidVue);
        return android;
    }

    public Optional<PriseDeVue> afficherUnePriseDeVue(long id) {
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
        // TODO Déplacer la gestion de l'affichage de la carte
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
            // TODO Générer une vrai erreur
            e.printStackTrace();
        }
        return liste;
    }

    public List<Vue> ajouterVue(Long idPriseDeVue, Long idAppareilPhoto, Long idFilm) throws PriseDeVueException {
        List<Vue> vues;
        // Il ne peut y avoir qu'une seule vue à réaliser car l'application Android cherche la vue à réaliser
        if (aucuneVueARealiser()) {
            PriseDeVue priseDeVue = miseAJourPriseDeVue(idPriseDeVue);
            // Création de la vue au statut à réaliser
            Vue vue = new Vue();
            vue.setPriseDeVue(priseDeVue);
            vue.setAppareilPhoto(appareilPhotoRepository.findById(idAppareilPhoto).get());
            vue.setFilm(filmRepository.findById(idFilm).get());
            vue.setNom(generationDuNomDeLaVue(vue));
            vue.setStatutVue(statutVueRepository.findById(StatutVue.ARealiser).get());
            // Sauvegarde de la nouvelle vue
            vueRepository.save(vue);
            // Mise à jour de la prise de vue
            priseDeVueRepository.save(priseDeVue);
        } else {
            throw new PriseDeVueException("Impossible d'ajouter une vue car il y a déjà une vue au statut 'A réaliser'");
        }
        try {
            vues = listeDesVuesDUnePriseDeVue(idPriseDeVue);
        } catch (IOException e) {
            throw new PriseDeVueException("Impossible d'ajouter une vue car il y a une erreur à la création d'un fichier.", e);
        }
        return vues;
    }

    /*
     Création du nom de la vue avec le nom de la prise de vues
     suivie de la position de la vue sur le film
     suivie du nom de l'appareil photo
     */
    private String generationDuNomDeLaVue(Vue vue) {
        return vue.getPriseDeVue().getNom() +
                " - " + (vueRepository.findVueByPriseDeVueId(vue.getPriseDeVue().getId()).size() + 1) +
                " - " + vue.getAppareilPhoto().getMateriel().getNom();
    }

    /*
    Mise à jour de la date de prise de vue
     */
    private PriseDeVue miseAJourPriseDeVue(Long idPriseDeVue) {
        // Récupération de la prise de vue
        PriseDeVue priseDeVue = priseDeVueRepository.findById(idPriseDeVue).get();
        // Mise à jour de la date
        priseDeVue.setDate(LocalDateTime.now());
        return priseDeVue;
    }

    /*
    dit s'il y a une seule vue au statut à réaliser dans une prise de vue
     */
    boolean aucuneVueARealiser() {
        return vueRepository.findVueARealiser().size() == 0;
    }

    public List<Optional<AppareilPhoto>> listeDesAppareilsPhotoDUnePriseDeVue(long id) {
        Optional<PriseDeVue> priseDeVue = priseDeVueRepository.findById(id);
        List<Materiel> materiels = priseDeVue.get().getMateriels();
        List<Optional<AppareilPhoto>> appareilsPhoto = new ArrayList<>();
        for (Materiel materiel : materiels) {
            if (appareilPhotoRepository.findAppareilPhotoByMaterielId(materiel.getId()).isPresent()) {
                Optional<AppareilPhoto> appareilPhoto = appareilPhotoRepository.findById(materiel.getId());
                appareilsPhoto.add(appareilPhoto);
            }
        }
        return appareilsPhoto;
    }

    public List<Optional<Film>> listeDesFilmsDUnePriseDeVue(long id) {
        List<Produit> produits = priseDeVueRepository.findProduitsById(id);
        List<Optional<Film>> films = new ArrayList<>();
        for (Produit produit : produits) {
            if (filmRepository.findById(produit.getId()).isPresent()) {
                Optional<Film> film = filmRepository.findById(produit.getId());
                films.add(film);
            }
        }
        return films;
    }

    public void miseAJourVue(long id, Long idOuverture, Long idVitesse, Long idStatut) {
        Optional<Vue> vueOptional = vueRepository.findById(id);
        Vue vue = vueOptional.get();
        StatutVue statut = statutVueRepository.findById(idStatut).get();
        vue.setStatutVue(statut);
        Ouverture ouverture = ouvertureRepository.findById(idOuverture).get();
        vue.setOuverture(ouverture);
        Vitesse vitesse = vitesseRepository.findById(idVitesse).get();
        vue.setVitesse(vitesse);
        vue = vueRepository.save(vue);
        PriseDeVue priseDeVue = miseAJourPriseDeVue(vue.getPriseDeVue().getId());
        priseDeVueRepository.save(priseDeVue);
    }
}
