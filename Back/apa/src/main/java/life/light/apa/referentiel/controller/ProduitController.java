package life.light.apa.referentiel.controller;

import life.light.apa.referentiel.model.*;
import life.light.apa.referentiel.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://127.0.0.1:4200")
@RestController
public class ProduitController {

    @Autowired
    private ProduitService produitService;

    @GetMapping(value = "/produit")
    @ResponseBody
    public Iterable<Produit> rechercheProduits(@RequestParam Map<String, String> allParams) {
        return produitService.rechercheProduits(allParams.get("nom"), allParams.get("typeProduit"),
                allParams.get("statutProduit"), allParams.get("marque"), allParams.get("modele"),
                allParams.get("remarque"));
    }

    @GetMapping(value = "/typeProduit")
    public Iterable<TypeProduit> listeTypeProduit() {
        return produitService.listeTousLesTypesDeProduits();
    }

    @GetMapping(value = "/statutProduit")
    public Iterable<StatutProduit> listeStatutProduit() {
        return produitService.listeDeTousLesStatutsProduits();
    }

    @RequestMapping(value = "/produit/{id}")
    public Optional<Produit> afficherUnProduit(@PathVariable long id) {
        return produitService.afficherUnProduit(id);
    }

    @RequestMapping(value = "/film/{id}")
    public Optional<Film> afficherUnFilm(@PathVariable long id) {
        return produitService.afficherUnFilm(id);
    }

    @GetMapping(value = "/film")
    @ResponseBody
    public Iterable<Film> listeFilmDisponible() {
        return produitService.listeDesFilmsDisponible();
    }

}
