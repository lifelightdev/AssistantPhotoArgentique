package life.light.apa.referentiel.controller;

import life.light.apa.referentiel.dao.SousTypeMaterielRepository;
import life.light.apa.referentiel.model.SousTypeMateriel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
public class SousTypeMaterielController {

    @Autowired
    private SousTypeMaterielRepository sousTypeMateriel;

    //Liste des matériels
    @GetMapping (value = "/sousTypeMateriel")
    public Iterable<SousTypeMateriel> listeTypeMateriel(){
        return sousTypeMateriel.findAll();
    }
}
