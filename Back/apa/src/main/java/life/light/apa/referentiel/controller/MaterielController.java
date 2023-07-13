package life.light.apa.referentiel.controller;

import life.light.apa.referentiel.dao.*;
import life.light.apa.referentiel.exceptions.MaterielIntrouvableException;
import life.light.apa.referentiel.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.stream.FileImageOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class MaterielController {

    @Autowired
    private MaterielRepository materielRepository;

    @RequestMapping(value = "/materiel/{id}")
    public Optional<Materiel> afficherUnMateriel(@PathVariable long id) throws MaterielIntrouvableException {
        Optional<Materiel> materiel = materielRepository.findById(id);
        if (materiel.isEmpty()) {
            throw new MaterielIntrouvableException("Le materiel photo " + id + " est introuvable.");
        }
        return materiel;
    }

    @GetMapping(value = "/materiel")
    @ResponseBody
    public Iterable<Materiel> rechercheMateriels(@RequestParam Map<String, String> allParams) throws IOException {
        System.out.println("Parameters are " + allParams.entrySet());
        List<Materiel> liste;
        if (allParams.entrySet().isEmpty()) {
            liste = materielRepository.findAll();
        } else {
            String nom = null;
            if (allParams.containsKey("nom")){
                nom = allParams.get("nom");
            }
            Long idType = null;
            if (allParams.containsKey("typeMateriel")){
                idType = Long.valueOf(allParams.get("typeMateriel"));
            }
            Long idSousType = null;
            if (allParams.containsKey("soustype")){
                idSousType = Long.valueOf(allParams.get("soustype"));
            }
            Long idStatut = null;
            if (allParams.containsKey("statutMateriel")){
                idStatut = Long.valueOf(allParams.get("statutMateriel"));
            }
            String remarque = null;
            if (allParams.containsKey("remarque")){
                remarque = allParams.get("remarque");
            }
            liste = materielRepository.search(nom, idType, idSousType, idStatut, remarque);
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

    @GetMapping(value = "/statut")
    public Iterable<StatutMateriel> listeStatutMateriel() {
        return statutMateriel.findAll();
    }

}
