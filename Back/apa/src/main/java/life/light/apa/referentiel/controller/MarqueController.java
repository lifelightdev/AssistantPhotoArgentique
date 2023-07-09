package life.light.apa.referentiel.controller;

import life.light.apa.referentiel.dao.MarqueRepository;
import life.light.apa.referentiel.model.Marque;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
public class MarqueController {

    @Autowired
    private MarqueRepository marque;

    //Liste des matériels
    @GetMapping (value = "/marque")
    public Iterable<Marque> listeMarque(){
        return marque.findAll();
    }
}
