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

    @RequestMapping(value = "/appareilPhoto/{idMateriel}")
    public Optional<AppareilPhoto> afficherUnAppareilPhoto(@PathVariable Long idMateriel) {
        return materielService.afficherUnAppareilPhoto(idMateriel);
    }

    @RequestMapping(value = "/objectif/{idMateriel}")
    public Optional<Objectif> afficherUnObjectif(@PathVariable Long idMateriel) {
        return materielService.afficherUnObjectif(idMateriel);
    }

    @RequestMapping(value = "/pied/{idMateriel}")
    public Optional<Pied> afficherUnPied(@PathVariable Long idMateriel) {
        return materielService.afficherUnPied(idMateriel);
    }

    @RequestMapping(value = "/objectif/{id}/ouvertures")
    public Iterable<Ouverture> listeOuvertureDUnObjectif(@PathVariable Long id) {
        return materielService.listeOuvertureDUnObjectif(id);
    }

    @RequestMapping(value = "/objectif/{id}/vitesses")
    public Iterable<Vitesse> listeVitesseDUnObjectif(@PathVariable Long id) {
        return materielService.listeVitesseDUnObjectif(id);
    }

    @RequestMapping(value = "/chassis/{idMateriel}")
    public Optional<Chassis> afficherUnChassis(@PathVariable Long idMateriel) {
        return materielService.afficherUnChassis(idMateriel);
    }

    @RequestMapping(value = "/rotule/{idMateriel}")
    public Optional<Rotule> afficherUneRotule(@PathVariable Long idMateriel) {
        return materielService.afficherUneRotule(idMateriel);
    }

}
