package life.light.apa.referentiel.controller;

import life.light.apa.referentiel.dao.TypeMaterielRepository;
import life.light.apa.referentiel.model.TypeMateriel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
public class TypeMaterielController {

    @Autowired
    private TypeMaterielRepository typeMateriel;

    //Liste des matériels
    @GetMapping (value = "/typeMateriel")
    public Iterable<TypeMateriel> listeTypeMateriel(){
        return typeMateriel.findAll();
    }
}
