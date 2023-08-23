package life.light.apa.referentiel.service;

import life.light.apa.referentiel.dao.*;
import life.light.apa.referentiel.model.*;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    public Iterable<Materiel> rechercheMateriels(Map<String, String> allParams) {
        List<Materiel> liste = new ArrayList<>();
        if (allParams.entrySet().isEmpty()) {
            liste = materielRepository.findAll();
        } else {
            if ((allParams.containsKey("nom")) && (!"undefined".equals(allParams.get("nom"))) && (!allParams.get("nom").trim().isEmpty())) {
                liste = ListUtils.union(liste, materielRepository.findAll(where(nomLike(allParams.get("nom")))));
            }
            if ((allParams.containsKey("typeMateriel")) && (!"undefined".equals(allParams.get("typeMateriel"))) && (!"0".equals(allParams.get("typeMateriel").trim()))) {
                liste = ListUtils.union(liste, materielRepository.findAll(where(idTypeLike(Long.valueOf(allParams.get("typeMateriel"))))));
            }
            if ((allParams.containsKey("sousType")) && (!"undefined".equals(allParams.get("sousType"))) && (!"0".equals(allParams.get("sousType").trim()))) {
                liste = ListUtils.union(liste, materielRepository.findAll(where(idSousTypeLike(Long.valueOf(allParams.get("sousType"))))));
            }
            if ((allParams.containsKey("statutMateriel")) && (!"undefined".equals(allParams.get("statutMateriel"))) && (!"0".equals(allParams.get("statutMateriel").trim()))) {
                liste = ListUtils.union(liste, materielRepository.findAll(where(idStatutLike(Long.valueOf(allParams.get("statutMateriel"))))));
            }
            if ((allParams.containsKey("marque")) && (!"undefined".equals(allParams.get("marque"))) && (!"0".equals(allParams.get("marque").trim()))) {
                liste = ListUtils.union(liste, materielRepository.findAll(where(idMarqueLike(Long.valueOf(allParams.get("marque"))))));
            }
            if ((allParams.containsKey("modele")) && (!"undefined".equals(allParams.get("modele"))) && (!"0".equals(allParams.get("modele").trim()))) {
                liste = ListUtils.union(liste, materielRepository.findAll(where(idModeleLike(Long.valueOf(allParams.get("modele"))))));
            }
            if ((allParams.containsKey("remarque")) && (!"undefined".equals(allParams.get("remarque")))) {
                liste = ListUtils.union(liste, materielRepository.findAll(where(remarqueLike(allParams.get("remarque")))));
            }
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