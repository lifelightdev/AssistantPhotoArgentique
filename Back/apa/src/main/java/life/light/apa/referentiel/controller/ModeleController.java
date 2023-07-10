package life.light.apa.referentiel.controller;

import life.light.apa.referentiel.dao.ModeleRepository;
import life.light.apa.referentiel.model.Modele;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
public class ModeleController {

    @Autowired
    private ModeleRepository modele;

    //Liste des matériels
    @GetMapping (value = "/modele")
    public Iterable<Modele> listeModele(){
        return modele.findAll();
    }
}
