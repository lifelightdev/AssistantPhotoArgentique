package life.light.apa.referentiel.controller;

import life.light.apa.referentiel.dao.*;
import life.light.apa.referentiel.model.*;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static org.springframework.data.jpa.domain.Specification.where;

@CrossOrigin(origins = "http://127.0.0.1:4200")
@RestController
public class ProduitController {

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private FilmRepository filmRepository;

    @GetMapping(value = "/produit")
    @ResponseBody
    public Iterable<Produit> rechercheProduits(@RequestParam Map<String, String> allParams) {
        List<Produit> liste = new ArrayList<>();
        if (allParams.entrySet().isEmpty()) {
            liste = produitRepository.findAll();
        } else {
            if ((allParams.containsKey("nom")) && (!"undefined".equals(allParams.get("nom"))) && (!allParams.get("nom").trim().isEmpty())) {
                liste = ListUtils.union(liste, produitRepository.findAll(where(ProduitSpecification.nomLike(allParams.get("nom")))));
            }
            if ((allParams.containsKey("typeProduit")) && (!"undefined".equals(allParams.get("typeProduit"))) && (!"0".equals(allParams.get("typeProduit").trim()))) {
                liste = ListUtils.union(liste, produitRepository.findAll(where(ProduitSpecification.idTypeLike(Long.valueOf(allParams.get("typeProduit"))))));
            }
            if ((allParams.containsKey("statutProduit")) && (!"undefined".equals(allParams.get("statutProduit"))) && (!"0".equals(allParams.get("statutProduit").trim()))) {
                liste = ListUtils.union(liste, produitRepository.findAll(where(ProduitSpecification.idStatutLike(Long.valueOf(allParams.get("statutProduit"))))));
            }
            if ((allParams.containsKey("marque")) && (!"undefined".equals(allParams.get("marque"))) && (!"0".equals(allParams.get("marque").trim()))) {
                liste = ListUtils.union(liste, produitRepository.findAll(where(ProduitSpecification.idMarqueLike(Long.valueOf(allParams.get("marque"))))));
            }
            if ((allParams.containsKey("modele")) && (!"undefined".equals(allParams.get("modele"))) && (!"0".equals(allParams.get("modele").trim()))) {
                liste = ListUtils.union(liste, produitRepository.findAll(where(ProduitSpecification.idModeleLike(Long.valueOf(allParams.get("modele"))))));
            }
            if ((allParams.containsKey("remarque")) && (!"undefined".equals(allParams.get("remarque")))) {
                liste = ListUtils.union(liste, produitRepository.findAll(where(ProduitSpecification.remarqueLike(allParams.get("remarque")))));
            }
        }
        return liste;
    }

    @Autowired
    private TypeProduitRepository typeProduit;

    @GetMapping(value = "/typeProduit")
    public Iterable<TypeProduit> listeTypeProduit() {
        return typeProduit.findAll();
    }

    @Autowired
    private StatutProduitRepository statutProduit;

    @GetMapping(value = "/statutProduit")
    public Iterable<StatutProduit> listeStatutProduit() {
        return statutProduit.findAll();
    }

    @RequestMapping(value = "/produit/{id}")
    public Optional<Produit> afficherUnProduit(@PathVariable long id) {
        return produitRepository.findById(id);
    }

    @RequestMapping(value = "/film/{id}")
    public Optional<Film> afficherUnFilm(@PathVariable long id) {
        return filmRepository.findFilmByProduitId(id);
    }

    @GetMapping(value = "/film")
    @ResponseBody
    public Iterable<Film> listeFilmDisponible() {
        return filmRepository.findAll(where(FilmSpecification.idStatutFilmLike(1L)));
    }

}
