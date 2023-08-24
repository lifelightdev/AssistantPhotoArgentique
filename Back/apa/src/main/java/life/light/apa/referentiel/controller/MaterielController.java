package life.light.apa.referentiel.controller;

import life.light.apa.referentiel.model.*;
import life.light.apa.referentiel.service.MaterielService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://127.0.0.1:4200")
@RestController
public class MaterielController {

    @Autowired
    private MaterielService materielService;

    @RequestMapping(value = "/materiel/{id}")
    public Optional<Materiel> afficherUnMateriel(@PathVariable Long id) {
        return materielService.afficherUnMateriel(id);
    }

    @GetMapping(value = "/materiel")
    @ResponseBody
    public Iterable<Materiel> rechercheMateriels(@RequestParam Map<String, String> allParams) {
        return materielService.rechercheMateriels(allParams.get("nom"), allParams.get("typeMateriel"),
                allParams.get("sousType"), allParams.get("statutMateriel"), allParams.get("marque"),
                allParams.get("modele"), allParams.get("remarque"));
    }

    @GetMapping(value = "/typeMateriel")
    public Iterable<TypeMateriel> listeTypeMateriel() {
        return materielService.listeTypeMateriel();
    }

    @GetMapping(value = "/sousTypeMateriel")
    public Iterable<SousTypeMateriel> listeSousTypeMateriel() {
        return materielService.listeSousTypeMateriel();
    }

    @GetMapping(value = "/modele")
    public Iterable<Modele> listeModele() {
        return materielService.listeModele();
    }

    @GetMapping(value = "/marque")
    public Iterable<Marque> listeMarque() {
        return materielService.listeMarque();
    }

    @GetMapping(value = "/statutMateriel")
    public Iterable<StatutMateriel> listeStatutMateriel() {
        return materielService.listeStatutMateriel();
    }

    @RequestMapping(value = "/appareilPhoto/{id}")
    public Optional<AppareilPhoto> afficherUnAppareilPhoto(@PathVariable Long id) {
        return materielService.afficherUnAppareilPhoto(id);
    }

    @RequestMapping(value = "/objectif/{id}")
    public Optional<Objectif> afficherUnObjectif(@PathVariable Long id) {
        return materielService.afficherUnObjectif(id);
    }

    @RequestMapping(value = "/pied/{id}")
    public Optional<Pied> afficherUnPied(@PathVariable Long id) {
        return materielService.afficherUnPied(id);
    }

    @RequestMapping(value = "/objectif/{id}/ouvertures")
    public Iterable<Ouverture> listeOuvertureDUnObjectif(@PathVariable Long id) {
        return materielService.listeOuvertureDUnObjectif(id);
    }

    @RequestMapping(value = "/objectif/{id}/vitesses")
    public Iterable<Vitesse> listeVitesseDUnObjectif(@PathVariable Long id) {
        return materielService.listeVitesseDUnObjectif(id);
    }
}