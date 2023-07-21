package life.light.apa.referentiel.controller;

import life.light.apa.referentiel.dao.*;
import life.light.apa.referentiel.model.*;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.stream.FileImageOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static life.light.apa.referentiel.dao.MaterielSpecification.*;
import static org.springframework.data.jpa.domain.Specification.where;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class MaterielController {

    @Autowired
    private MaterielRepository materielRepository;
    @Autowired
    private AppareilPhotoRepository appareilPhotoRepository;
    @Autowired
    private ObjectifRepository objectifPhotoRepository;

    @RequestMapping(value = "/materiel/{id}")
    public Optional<Materiel> afficherUnMateriel(@PathVariable long id) {
        Optional<Materiel> materiel = materielRepository.findById(id);
        if (materiel.isEmpty()) {
            return null;
        }
        return materiel;
    }

    @GetMapping(value = "/materiel")
    @ResponseBody
    public Iterable<Materiel> rechercheMateriels(@RequestParam Map<String, String> allParams) throws IOException {
        List<Materiel> liste = new ArrayList<>();
        if (allParams.entrySet().isEmpty()) {
            liste = materielRepository.findAll();
        } else {
            if ((allParams.containsKey("nom")) && (!"undefined".equals(allParams.get("nom"))) && (!"".equals(allParams.get("nom").trim()))){
                liste = ListUtils.union(liste, materielRepository.findAll(where(nomLike(allParams.get("nom")))));
            }
            if ((allParams.containsKey("typeMateriel")) && (!"undefined".equals(allParams.get("typeMateriel"))) && (!"0".equals(allParams.get("typeMateriel").trim()))){
                liste = ListUtils.union(liste, materielRepository.findAll(where(idTypeLike(Long.valueOf(allParams.get("typeMateriel"))))));
            }
            if ((allParams.containsKey("sousType")) && (!"undefined".equals(allParams.get("sousType"))) && (!"0".equals(allParams.get("sousType").trim()))){
                liste = ListUtils.union(liste, materielRepository.findAll(where(idSousTypeLike(Long.valueOf(allParams.get("sousType"))))));
            }
            if ((allParams.containsKey("statutMateriel")) && (!"undefined".equals(allParams.get("statutMateriel"))) && (!"0".equals(allParams.get("statutMateriel").trim()))){
                liste = ListUtils.union(liste, materielRepository.findAll(where(idStatutLike(Long.valueOf(allParams.get("statutMateriel"))))));
            }
            if ((allParams.containsKey("marque")) && (!"undefined".equals(allParams.get("marque"))) && (!"0".equals(allParams.get("marque").trim()))){
                liste = ListUtils.union(liste, materielRepository.findAll(where(idMarqueLike(Long.valueOf(allParams.get("marque"))))));
            }
            if ((allParams.containsKey("modele")) && (!"undefined".equals(allParams.get("modele"))) && (!"0".equals(allParams.get("modele").trim()))){
                liste = ListUtils.union(liste, materielRepository.findAll(where(idModeleLike(Long.valueOf(allParams.get("modele"))))));
            }
            if ((allParams.containsKey("remarque")) && (!"undefined".equals(allParams.get("remarque")))){
                liste = ListUtils.union(liste, materielRepository.findAll(where(remarqueLike(allParams.get("remarque")))));
            }
        }
        for (Materiel materiel : liste) {
            if (null != materiel.getPhoto()) {
                String imageFileName = materiel.getNom();
                String extension = ".jpg";
                File file = new File("D:\\IdeaProjects\\AssistantPhotoArgentique\\Front\\apa\\src\\assets\\Images\\" + imageFileName + extension);
                file.createNewFile();
                FileImageOutputStream fos = new FileImageOutputStream(file);
                if (materiel.getPhoto() != null) {
                    fos.write(materiel.getPhoto());
                }
                fos.close();
            }
            if (null != materiel.getModeEmploie()) {
                String modeEmploieFileName = materiel.getNom();
                String extension = ".pdf";
                File file = new File("D:\\IdeaProjects\\AssistantPhotoArgentique\\Front\\apa\\src\\assets\\ModeEmploie\\" + modeEmploieFileName + extension);
                file.createNewFile();
                FileImageOutputStream fos = new FileImageOutputStream(file);
                if (materiel.getModeEmploie() != null) {
                    fos.write(materiel.getModeEmploie());
                }
                fos.close();
            }
        }
        return liste;
    }

    @Autowired
    private TypeMaterielRepository typeMateriel;

    @GetMapping(value = "/typeMateriel")
    public Iterable<TypeMateriel> listeTypeMateriel() {
        return typeMateriel.findAll();
    }

    @Autowired
    private SousTypeMaterielRepository sousTypeMateriel;

    @GetMapping(value = "/sousTypeMateriel")
    public Iterable<SousTypeMateriel> listeSousTypeMateriel() {
        return sousTypeMateriel.findAll();
    }

    @Autowired
    private ModeleRepository modele;

    @GetMapping(value = "/modele")
    public Iterable<Modele> listeModele() {
        return modele.findAll();
    }

    @Autowired
    private MarqueRepository marque;

    @GetMapping(value = "/marque")
    public Iterable<Marque> listeMarque() {
        return marque.findAll();
    }

    @Autowired
    private StatutMaterielRepository statutMateriel;

    @GetMapping(value = "/statutMateriel")
    public Iterable<StatutMateriel> listeStatutMateriel() {
        return statutMateriel.findAll();
    }

    @RequestMapping(value = "/appareilPhoto/{id}")
    public Optional<AppareilPhoto> afficherUnAppareilPhoto(@PathVariable String id) {
        Optional<AppareilPhoto> appareilPhoto = appareilPhotoRepository.findAppareilPhotoByMaterielId(Long.parseLong(id));
        if (appareilPhoto.isEmpty()) {
            return null;
        }
        return appareilPhoto;
    }

    @RequestMapping(value = "/objectif/{id}")
    public Optional<Objectif> afficherUnobjectif(@PathVariable String id) {
        Optional<Objectif> objectif = objectifPhotoRepository.findObjectifByMaterielId(Long.parseLong(id));
        if (objectif.isEmpty()) {
            return null;
        }
        return objectif;
    }
}
