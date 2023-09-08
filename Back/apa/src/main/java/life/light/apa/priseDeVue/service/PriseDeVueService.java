package life.light.apa.priseDeVue.service;

import life.light.apa.priseDeVue.PriseDeVueException;
import life.light.apa.priseDeVue.dao.*;
import life.light.apa.priseDeVue.dto.*;
import life.light.apa.priseDeVue.model.*;
import life.light.apa.referentiel.dao.*;
import life.light.apa.referentiel.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.springframework.data.jpa.domain.Specification.where;


@Service
public class PriseDeVueService {

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
    @Autowired
    private ProduitRepository produitRepository;
    @Autowired
    private MaterielRepository materielRepository;
    @Autowired
    private ChassisRepository chassisRepository;

    public Optional<Vue> afficherUneVue(long id) {
        return vueRepository.findById(id);
    }

    public List<Vue> listeDesVuesDUnePriseDeVue(long id) throws IOException {
        return vueRepository.findVuesByPriseDeVueId(id);
    }

    public Android getAndroid() {
        Vue vue = vueRepository.findVueByStatutVueId(1L);
        List<AndroidVue> listeAndroidVue = new ArrayList<>();
        AndroidVue androidVue = new AndroidVue();
        androidVue.setId(vue.getId());
        androidVue.setSensibilite(vue.getFilm().getSensibilite().getNom());
        androidVue.setNomAppareilPhoto(vue.getAppareilPhoto().getMateriel().getNom());
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
        listeAndroidVue.add(androidVue);
        Android android = new Android();
        android.setVues(listeAndroidVue);
        return android;
    }

    public Optional<PriseDeVue> afficherUnePriseDeVue(long id) {
        return priseDeVueRepository.findById(id);
    }

    public Iterable<Position> trouveToutesLesPositions() {
        return positionRepository.findAll();
    }

    public Iterable<StatutPriseDeVue> listeTousLesStatutsPrisesDeVues() {
        return statutPriseDeVueRepository.findAll();
    }

    public Iterable<PriseDeVue> listeDesPrisesDeVues(String nom, String statut, String date, String heure, String position, String remarque) {
        Set<PriseDeVue> liste = new HashSet<>();
        boolean trouver = false;
        if ((null != nom) && (!"undefined".equals(nom)) && (!nom.trim().isEmpty())) {
            liste.addAll(priseDeVueRepository.findAll(where(PriseDeVueSpecification.nomLike(nom))));
            trouver = true;
        }
        if ((null != statut) && (!"undefined".equals(statut)) && (!"0".equals(statut.trim()))) {
            liste.addAll(priseDeVueRepository.findAll(where(PriseDeVueSpecification.idStatutLike(Long.valueOf(statut)))));
            trouver = true;
        }
        if ((null != date) && (!"undefined".equals(date)) && (!date.trim().isEmpty())) {
            liste.addAll(priseDeVueRepository.findAll(where(PriseDeVueSpecification.dateLike(LocalDate.parse(date)))));
            trouver = true;
        }
        if ((null != heure) && (!"undefined".equals(heure)) && (!heure.trim().isEmpty())) {
            liste.addAll(priseDeVueRepository.findAll(where(PriseDeVueSpecification.heureLike(LocalTime.parse(heure)))));
            trouver = true;
        }
        if ((null != position) && (!"undefined".equals(position)) && (!"0".equals(position.trim()))) {
            liste.addAll(priseDeVueRepository.findAll(where(PriseDeVueSpecification.positionLike(position))));
            trouver = true;
        }
        if ((null != remarque) && (!"undefined".equals(remarque))) {
            liste.addAll(priseDeVueRepository.findAll(where(PriseDeVueSpecification.remarqueLike(remarque))));
            trouver = true;
        }
        if (!trouver) {
            liste.addAll(priseDeVueRepository.findAll());
        }
        return liste;
    }

    public Vue ajouterUneVue(Long idPriseDeVue, Long idAppareilPhoto, Long idFilm) throws PriseDeVueException {
        Vue vue;
        // Il ne peut y avoir qu'une seule vue à réaliser car l'application Android cherche la vue à réaliser
        if (vueRepository.findVueARealiser().isEmpty()) {
            PriseDeVue priseDeVue = miseAJourPriseDeVue(idPriseDeVue);
            // Création de la vue au statut à réaliser
            vue = new Vue();
            vue.setPriseDeVue(priseDeVue);
            vue.setAppareilPhoto(appareilPhotoRepository.findById(idAppareilPhoto).get());
            vue.setFilm(filmRepository.findById(idFilm).get());
            vue.setNom(generationDuNomDeLaVue(vue));
            vue.setStatutVue(statutVueRepository.findById(StatutVue.ARealiser).get());
            // Sauvegarde de la nouvelle vue
            vue = vueRepository.save(vue);
            // Mise à jour de la prise de vue
            priseDeVueRepository.save(priseDeVue);
        } else {
            throw new PriseDeVueException("Impossible d'ajouter une vue car il y a déjà une vue au statut 'A réaliser'");
        }
        try {
            listeDesVuesDUnePriseDeVue(idPriseDeVue);
        } catch (IOException e) {
            throw new PriseDeVueException("Impossible d'ajouter une vue car il y a une erreur à la création d'un fichier.", e);
        }
        return vue;
    }

    /*
     Création du nom de la vue avec le nom de la prise de vues
     suivie de la position de la vue sur le film
     suivie du nom de l'appareil photo
     */
    private String generationDuNomDeLaVue(Vue vue) {
        return vue.getPriseDeVue().getNom() +
                " - " + (vueRepository.findVuesByPriseDeVueId(vue.getPriseDeVue().getId()).size() + 1) +
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

    public Set<AppareilPhoto> listeDesAppareilsPhotoDUnePriseDeVue(long id) {
        PriseDeVue priseDeVue = priseDeVueRepository.findById(id).get();
        Set<Materiel> materiels = priseDeVue.getMateriels();
        Set<AppareilPhoto> appareilsPhoto = new HashSet<>();
        for (Materiel materiel : materiels) {
            Optional<AppareilPhoto> appareilPhoto = appareilPhotoRepository.findAppareilPhotoByMaterielId(materiel.getId());
            appareilPhoto.ifPresent(appareilsPhoto::add);
        }
        return appareilsPhoto;
    }

    public Set<Film> listeDesFilmsDUnePriseDeVue(long id) {
        PriseDeVue priseDeVue = priseDeVueRepository.findById(id).get();
        return getFilms(priseDeVue);
    }

    private Set<Film> getFilms(PriseDeVue priseDeVue) {
        Set<Film> films = new HashSet<>();
        for (Produit produit : priseDeVue.getProduits()) {
            if (filmRepository.findById(produit.getId()).isPresent()) {
                Film film = filmRepository.findById(produit.getId()).get();
                films.add(film);
            }
        }
        return films;
    }


    public Position recherchePosition(Double latitude, Double longitude) {
        if (latitude == 37.4226711 && longitude == -122.0849872) {
            // C'est une position fixe du GPS virtuel d'Android : Google au USA
            // Je la remplace par la position de Notre-Dame de Paris pour être en France
            // latitude = 48.8529371;
            // longitude = 2.3500501;
            // Je la remplace par le château de Blois pour la démonstration
            latitude = 47.5853918;
            longitude = 1.328008;
        }

        String uri = "https://geo.api.gouv.fr/communes?lat=" + latitude + "&lon=" + longitude + "&fields=nom,codesPostaux&format=json&geometry=centre";
        ResponseEntity<Commune[]> responseEntity = WebClient.create(uri)
                .get()
                .retrieve()
                .toEntity(Commune[].class)
                .block();

        Position position = new Position();
        position.setLatitude(latitude);
        position.setLongitude(longitude);

        if (responseEntity != null) {
            Commune[] communes = responseEntity.getBody();
            position.setCodePostal((communes != null ? communes[0].getCodesPostaux() : new String[0])[0]);
            position.setVille(communes[0].getNom());
            position.setNom(position.getVille());
        }

        return position;
    }

    public void miseAJourVue(long id, String valeurVitesse, String valeurOuverture, Long idStatut, Double longitude, Double latitude) {
        Position position = recherchePosition(latitude, longitude);
        position = positionRepository.save(position);
        Vue vue = vueRepository.findById(id).get();
        if (!vue.getStatutVue().getId().equals(idStatut)) {
            StatutVue statut = statutVueRepository.findById(idStatut).get();
            vue.setStatutVue(statut);
            if (idStatut.equals(StatutVue.Realiser)) {
                Film film = vue.getFilm();
                film.setNbVueExpose(film.getNbVueExpose() + 1);
                filmRepository.save(film);
            }
        }
        Ouverture ouverture = ouvertureRepository.findByNom(valeurOuverture);
        vue.setOuverture(ouverture);
        Vitesse vitesse = vitesseRepository.findByNom(valeurVitesse);
        vue.setVitesse(vitesse);
        vue.setPosition(position);
        vue = vueRepository.save(vue);
        PriseDeVue priseDeVue = miseAJourPriseDeVue(vue.getPriseDeVue().getId());
        priseDeVueRepository.save(priseDeVue);
    }

    public PriseDeVue EnregistreUnePriseDeVue(PriseDeVue priseDeVue) throws PriseDeVueException {

        String messageChampsObligatoire = "Impossible d'ajouter la prise de vues, car il manque : ";
        if (priseDeVue.getNom() == null) {
            messageChampsObligatoire += "le nom";
        }
        if (priseDeVue.getStatutPriseDeVue() == null) {
            if (!messageChampsObligatoire.endsWith(" ")) {
                messageChampsObligatoire += ", ";
            }
            messageChampsObligatoire += "le statut";
        }
        if (priseDeVue.getDate() == null) {
            if (!messageChampsObligatoire.endsWith(" ")) {
                messageChampsObligatoire += ", ";
            }
            messageChampsObligatoire += "la date";
        }
        if (priseDeVue.getPosition() == null) {
            if (!messageChampsObligatoire.endsWith(" ")) {
                messageChampsObligatoire += ", ";
            }
            messageChampsObligatoire += "la position";
        }
        if (priseDeVue.getMateriels().isEmpty()) {
            if (!messageChampsObligatoire.endsWith(" ")) {
                messageChampsObligatoire += ", ";
            }
            messageChampsObligatoire += "un appareil photo";
        }
        if (priseDeVue.getProduits().isEmpty()) {
            if (!messageChampsObligatoire.endsWith(" ")) {
                messageChampsObligatoire += " et ";
            }
            messageChampsObligatoire += "un film";
        }
        if (messageChampsObligatoire.length() > 55) {
            throw new PriseDeVueException(messageChampsObligatoire);
        }

        Set<AppareilPhoto> listeDesAppareilsPhotoSansChassisIntege = new HashSet<>();
        Set<AppareilPhoto> listeDesAppareilsPhotoAvecChassisIntege = new HashSet<>();
        Set<Chassis> listeDesChassisNonIntegre = new HashSet<>();
        Set<Chassis> listeDesChassisIntegre = new HashSet<>();

        // Vérification de la compatibilité des matériels
        for (Materiel materiel : priseDeVue.getMateriels()) {
            if (!estDeTypePriseDeVues(materiel)) {
                throw new PriseDeVueException("Impossible d'ajouter la prise de vues," +
                        " car le matériel " + materiel.getNom() + " n'est pas de type prise de vue.");
            } else if (estUnAppareilPhotoArgentiqueAvecChassisIntegre(materiel)) {
                AppareilPhoto appareilPhoto = appareilPhotoRepository.findAppareilPhotoByMaterielId(materiel.getId()).get();
                listeDesAppareilsPhotoAvecChassisIntege.add(appareilPhoto);
                listeDesChassisIntegre.add(appareilPhoto.getChassis());
            } else if (estUnChassisNonIntegre(materiel)) {
                Chassis chassis = chassisRepository.findChassisByMaterielId(materiel.getId()).get();
                listeDesChassisNonIntegre.add(chassis);
            } else if (estUnAppareilPhotoArgentiqueSansChassisIntegre(materiel)) {
                AppareilPhoto appareilPhoto = appareilPhotoRepository.findAppareilPhotoByMaterielId(materiel.getId()).get();
                listeDesAppareilsPhotoSansChassisIntege.add(appareilPhoto);
            }
        }

        if (listeDesAppareilsPhotoSansChassisIntege.isEmpty() && listeDesAppareilsPhotoAvecChassisIntege.isEmpty()) {
            throw new PriseDeVueException("Impossible d'ajouter la prise de vues, car il n'y a pas d'appareil photo dans la liste de matériel.");
        }

        Set<Chassis> listeDesChassisCompatibleMateriel = new HashSet<>();
        if (!listeDesChassisIntegre.isEmpty()) {
            listeDesChassisCompatibleMateriel.addAll(listeDesChassisIntegre);
        }

        // S'il y a des appareils photo sans châssis, on recherche les châssis compatible
        if (!listeDesAppareilsPhotoSansChassisIntege.isEmpty()) {
            for (AppareilPhoto appareilPhoto : listeDesAppareilsPhotoSansChassisIntege) {
                boolean trouveChassis = false;
                for (Chassis chassis : listeDesChassisNonIntegre) {
                    if (estDimensionChassisCompatible(chassis, appareilPhoto)) {
                        listeDesChassisCompatibleMateriel.add(chassis);
                        trouveChassis = true;
                        break;
                    }
                }
                if (!trouveChassis) {
                    throw new PriseDeVueException("Impossible d'ajouter la prise de vues, " +
                            "car cet appareil photo " + appareilPhoto.getMateriel().getNom() + " n'a pas de châssis.");
                }
            }
        }
        if (listeDesChassisCompatibleMateriel.isEmpty()) {
            throw new PriseDeVueException("Impossible d'ajouter la prise de vue, " +
                    "car il n'y a aucun appareil photo compatible avec les châssis.");
        }

        if (getFilms(priseDeVue).isEmpty()) {
            throw new PriseDeVueException("Impossible d'ajouter la prise de vue car il n'y a pas de film.");
        }

        // Vérification de la compatibilité des films avec les châssis
        for (Chassis chassis : listeDesChassisCompatibleMateriel) {
            boolean trouveFilm = false;
            for (Film film : getFilms(priseDeVue)) {
                if (estTailleDeFilmCompatible(chassis, film)) {
                    trouveFilm = true;
                }
            }
            if (!trouveFilm) {
                throw new PriseDeVueException("Impossible d'ajouter la prise de vues, " +
                        "car le chassis " + chassis.getMateriel().getNom() + " n'a pas de film compatible.");
            }
        }

        return priseDeVueRepository.saveAndFlush(priseDeVue);
    }

    public Iterable<Materiel> listeDesMaterielsDisponiblePourUnePriseDeVue(long id) {
        Set<Materiel> materiels = priseDeVueRepository.findById(id).get().getMateriels();
        materiels.addAll(materielRepository.findAll(where(MaterielSpecification.idStatutLike(StatutMateriel.ID_DISPONIBLE))));
        return materiels;
    }

    public Iterable<Film> listeDesFilmsDisponiblePourUnePriseDeVue(long id) {
        Set<Film> liste = listeDesFilmsDUnePriseDeVue(id);
        liste.addAll(filmRepository.findAll(where(FilmSpecification.idStatutFilmLike(StatutFilm.DISPONIBLE))));
        return liste;
    }

    public Iterable<Produit> listeDesProduitsDisponiblePourUnePriseDeVue(long id) {
        Set<Produit> produits = priseDeVueRepository.findById(id).get().getProduits();
        produits.addAll(produitRepository.findAll(where(ProduitSpecification.idStatutLike(StatutProduit.DISPONIBLE))));
        return produits;
    }

    Boolean estDeTypePriseDeVues(Materiel materiel) {
        return materiel.getTypeMateriel().getId() == TypeMateriel.ID_PRISE_DE_VUE;
    }

    Boolean estUnAppareilPhotoArgentiqueAvecChassisIntegre(Materiel materiel) {
        if (materiel.getSousType().getId() == SousTypeMateriel.ID_APPAREIL_PHOTO_ARGENTIQUE) {
            AppareilPhoto appareilPhoto = appareilPhotoRepository.findAppareilPhotoByMaterielId(materiel.getId()).get();
            if (appareilPhoto.getChassis() != null) {
                return appareilPhoto.getChassis().getMateriel().getStatutMateriel().getId().equals(StatutMateriel.ID_INDISSOCIABLE);
            }
        }
        return false;
    }

    Boolean estUnAppareilPhotoArgentiqueSansChassisIntegre(Materiel materiel) {
        if (materiel.getSousType().getId() == SousTypeMateriel.ID_APPAREIL_PHOTO_ARGENTIQUE) {
            AppareilPhoto appareilPhoto = appareilPhotoRepository.findAppareilPhotoByMaterielId(materiel.getId()).get();
            if (appareilPhoto.getChassis() == null) {
                return true;
            } else
                return (!appareilPhoto.getChassis().getMateriel().getStatutMateriel().getId().equals(StatutMateriel.ID_INDISSOCIABLE));
        }
        return false;
    }

    Boolean estUnChassisNonIntegre(Materiel materiel) {
        if (materiel.getSousType().getId() == SousTypeMateriel.ID_CHASSIS_PRISE_DE_VUE) {
            Chassis chassis = chassisRepository.findChassisByMaterielId(materiel.getId()).get();
            return !chassis.getMateriel().getStatutMateriel().getId().equals(StatutMateriel.ID_INDISSOCIABLE);
        }
        return false;
    }

    Boolean estDimensionChassisCompatible(Chassis chassis, AppareilPhoto appareilPhoto) {
        return chassis.getDimensionChassis().getId().equals(appareilPhoto.getDimensionChassis().getId());
    }

    Boolean estTailleDeFilmCompatible(Chassis chassis, Film film) {
        return Objects.equals(film.getTailleFilm().getId(), chassis.getTailleFilm().getId());
    }
}
