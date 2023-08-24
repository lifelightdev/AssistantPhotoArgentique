package life.light.apa.priseDeVue.service;

import life.light.apa.priseDeVue.PriseDeVueException;
import life.light.apa.priseDeVue.dao.*;
import life.light.apa.priseDeVue.dto.*;
import life.light.apa.priseDeVue.model.*;
import life.light.apa.referentiel.dao.*;
import life.light.apa.referentiel.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            if (appareilPhoto.isPresent()) {
                appareilsPhoto.add(appareilPhoto.get());
            }
        }
        return appareilsPhoto;
    }

    public Set<Film> listeDesFilmsDUnePriseDeVue(long id) {
        PriseDeVue priseDeVue = priseDeVueRepository.findById(id).get();
        Set<Film> films = new HashSet<>();
        for (Produit produit : priseDeVue.getProduits()) {
            if (filmRepository.findById(produit.getId()).isPresent()) {
                Film film = filmRepository.findById(produit.getId()).get();
                films.add(film);
            }
        }
        return films;
    }

    public void miseAJourVue(long id, String valeurVitesse, String valeurOuverture, Long idStatut) {
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
        vue = vueRepository.save(vue);
        PriseDeVue priseDeVue = miseAJourPriseDeVue(vue.getPriseDeVue().getId());
        priseDeVueRepository.save(priseDeVue);
    }

    public PriseDeVue EnregistreUnePriseDeVue(PriseDeVue priseDeVue) throws PriseDeVueException {
        try {
            return priseDeVueRepository.saveAndFlush(priseDeVue);
        } catch (Exception e) {
            throw new PriseDeVueException("Impossible d'ajouter une vue car il y a une erreur à la création d'un fichier.", e);
        }
    }

    public Iterable<Materiel> listeDesMaterielsDisponiblePourUnePriseDeVue(long id) {
        Set<Materiel> materiels = priseDeVueRepository.findById(id).get().getMateriels();
        materiels.addAll(materielRepository.findAll(where(MaterielSpecification.idStatutLike(StatutMateriel.DISPONIBLE))));
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
}
