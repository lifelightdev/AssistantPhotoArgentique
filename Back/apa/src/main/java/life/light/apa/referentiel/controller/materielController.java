package life.light.apa.referentiel.controller;

import life.light.apa.referentiel.dao.MaterielRepository;
import life.light.apa.referentiel.model.Materiel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
public class materielController {

    @Autowired
    private MaterielRepository materiel;

    //Liste des matériels
    @GetMapping (value = "/materiel")
    public Iterable<Materiel> listeMateriel(){
        return materiel.findAll();
    }
}
