package life.light.apa.referentiel.controller;

import life.light.apa.referentiel.dao.*;
import life.light.apa.referentiel.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
public class MaterielController {

    @Autowired
    private MaterielRepository materiel;

    @GetMapping (value = "/materiel")
    public Iterable<Materiel> listeMateriel(){
        return materiel.findAll();
    }

    @Autowired
    private TypeMaterielRepository typeMateriel;

    @GetMapping (value = "/typeMateriel")
    public Iterable<TypeMateriel> listeTypeMateriel(){
        return typeMateriel.findAll();
    }

    @Autowired
    private SousTypeMaterielRepository sousTypeMateriel;

    @GetMapping (value = "/sousTypeMateriel")
    public Iterable<SousTypeMateriel> listeSousTypeMateriel(){
        return sousTypeMateriel.findAll();
    }

    @Autowired
    private ModeleRepository modele;

    @GetMapping (value = "/modele")
    public Iterable<Modele> listeModele(){
        return modele.findAll();
    }

    @Autowired
    private MarqueRepository marque;

    @GetMapping (value = "/marque")
    public Iterable<Marque> listeMarque(){
        return marque.findAll();
    }

    @Autowired
    private StatutMaterielRepository statutMateriel;

    @GetMapping (value = "/statut")
    public Iterable<StatutMateriel> listeStatutMateriel(){
        return statutMateriel.findAll();
    }

}
