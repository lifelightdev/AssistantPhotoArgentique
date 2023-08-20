package life.light.apa.priseDeVue.service;

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

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static life.light.apa.priseDeVue.dao.PriseDeVueSpecification.*;
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

    public Optional<Vue> afficherUneVue(long id) throws IOException {
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

    public Iterable<Position> trouveToutesLesPosition() {
        return positionRepository.findAll();
    }

    public Iterable<Materiel> afficherTousLesMaterielsdUnePriseDeVue(Long id) {
        return priseDeVueRepository.findById(id).get().getMateriels();
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
            liste = ListUtils.union(liste, priseDeVueRepository.findAll(where(dateLike(LocalDate.parse(date)))));
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

    public List<Optional<AppareilPhoto>> listeDesAppareilsPhotoDUnePriseDeVue(long id) {
        Optional<PriseDeVue> priseDeVue = priseDeVueRepository.findById(id);
        List<Materiel> materiels = priseDeVue.get().getMateriels();
        List<Optional<AppareilPhoto>> appareilsPhoto = new ArrayList<>();
        for (Materiel materiel : materiels) {
            Optional<AppareilPhoto> appareilPhoto = appareilPhotoRepository.findAppareilPhotoByMaterielId(materiel.getId());
            if (appareilPhoto.isPresent()) {
                appareilsPhoto.add(appareilPhoto);
            }
        }
        return appareilsPhoto;
    }

    public List<Optional<Film>> listeDesFilmsDUnePriseDeVue(long id) {
        PriseDeVue priseDeVue = priseDeVueRepository.findById(id).get();
        List<Optional<Film>> films = new ArrayList<>();
        for (Produit produit : priseDeVue.getProduits()) {
            if (filmRepository.findById(produit.getId()).isPresent()) {
                Optional<Film> film = filmRepository.findById(produit.getId());
                films.add(film);
            }
        }
        return films;
    }

    public void miseAJourVue(long id, String valeurVitesse, String valeurOuverture, Long idStatut) {
        Vue vue = vueRepository.findById(id).get();
        if (vue.getStatutVue().getId() != idStatut) {
            StatutVue statut = statutVueRepository.findById(idStatut).get();
            vue.setStatutVue(statut);
            if (idStatut == StatutVue.Realiser) {
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
}
