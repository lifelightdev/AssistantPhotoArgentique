package life.light.apa.referentiel.service;

import life.light.apa.referentiel.dao.*;
import life.light.apa.referentiel.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static life.light.apa.referentiel.dao.MaterielSpecification.*;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class MaterielService {

    @Autowired
    private MaterielRepository materielRepository;
    @Autowired
    private AppareilPhotoRepository appareilPhotoRepository;
    @Autowired
    private ObjectifRepository objectifRepository;
    @Autowired
    private PiedRepository piedRepository;
    @Autowired
    private OuvertureRepository ouvertureRepository;
    @Autowired
    private VitesseRepository vitesseRepository;
    @Autowired
    private TypeMaterielRepository typeMateriel;
    @Autowired
    private SousTypeMaterielRepository sousTypeMateriel;
    @Autowired
    private ModeleRepository modele;
    @Autowired
    private MarqueRepository marque;
    @Autowired
    private StatutMaterielRepository statutMateriel;


    public Optional<Materiel> afficherUnMateriel(long id) {
        return materielRepository.findById(id);
    }

    public Iterable<Materiel> rechercheMateriels(String nom, String typeMateriel, String sousType, String statutMateriel,
                                                 String marque, String modele, String remarque) {
        Set<Materiel> liste = new HashSet<>();
        boolean trouver = false;
        if ((null != nom) && (!"undefined".equals(nom)) && (!nom.trim().isEmpty())) {
            liste.addAll(materielRepository.findAll(where(nomLike(nom))));trouver = true;
        }
        if ((null != typeMateriel) && (!"undefined".equals(typeMateriel)) && (!typeMateriel.trim().isEmpty())) {
            liste.addAll(materielRepository.findAll(where(idTypeLike(Long.valueOf(typeMateriel)))));trouver = true;
        }
        if ((null != sousType) && (!"undefined".equals(sousType)) && (!sousType.trim().isEmpty())) {
            liste.addAll(materielRepository.findAll(where(idSousTypeLike(Long.valueOf(sousType)))));trouver = true;
        }
        if ((null != statutMateriel) && (!"undefined".equals(statutMateriel)) && (!statutMateriel.trim().isEmpty())) {
            liste.addAll(materielRepository.findAll(where(idStatutLike(Long.valueOf(statutMateriel)))));trouver = true;
        }
        if ((null != marque) && (!"undefined".equals(marque)) && (!marque.trim().isEmpty())) {
            liste.addAll(materielRepository.findAll(where(idMarqueLike(Long.valueOf(marque)))));trouver = true;
        }
        if ((null != modele) && (!"undefined".equals(modele)) && (!modele.trim().isEmpty())) {
            liste.addAll(materielRepository.findAll(where(idModeleLike(Long.valueOf(modele)))));trouver = true;
        }
        if ((null != remarque) && (!"undefined".equals(remarque)) && (!remarque.trim().isEmpty())) {
            liste.addAll(materielRepository.findAll(where(remarqueLike(remarque))));trouver = true;
        }
        if (!trouver) {
            liste.addAll(materielRepository.findAll());
        }
        return liste;
    }

    public Iterable<TypeMateriel> listeTypeMateriel() {
        return typeMateriel.findAll();
    }

    public Iterable<SousTypeMateriel> listeSousTypeMateriel() {
        return sousTypeMateriel.findAll();
    }

    public Iterable<Modele> listeModele() {
        return modele.findAll();
    }

    public Iterable<Marque> listeMarque() {
        return marque.findAll();
    }

    public Iterable<StatutMateriel> listeStatutMateriel() {
        return statutMateriel.findAll();
    }

    public Optional<AppareilPhoto> afficherUnAppareilPhoto(Long id) {
        return appareilPhotoRepository.findAppareilPhotoByMaterielId(id);
    }

    public Optional<Objectif> afficherUnObjectif(Long id) {
        return objectifRepository.findObjectifByMaterielId(id);
    }

    public Optional<Pied> afficherUnPied(Long id) {
        return piedRepository.findPiedByMaterielId(id);
    }

    public Iterable<Ouverture> listeOuvertureDUnObjectif(Long id) {
        return ouvertureRepository.findOuvertureByObjectif(id);
    }

    public Iterable<Vitesse> listeVitesseDUnObjectif(Long id) {
        return vitesseRepository.findVitesseByObjectif(id);
    }
}