package life.light.apa.referentiel.controller;

import life.light.apa.referentiel.dao.*;
import life.light.apa.referentiel.model.*;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.stream.FileImageOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static life.light.apa.referentiel.dao.ProduitSpecification.*;
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
    public Iterable<Produit> rechercheProduits(@RequestParam Map<String, String> allParams) throws IOException {
        List<Produit> liste = new ArrayList<>();
        if (allParams.entrySet().isEmpty()) {
            liste = produitRepository.findAll();
        } else {
            if ((allParams.containsKey("nom")) && (!"undefined".equals(allParams.get("nom"))) && (!allParams.get("nom").trim().isEmpty())) {
                liste = ListUtils.union(liste, produitRepository.findAll(where(nomLike(allParams.get("nom")))));
            }
            if ((allParams.containsKey("typeProduit")) && (!"undefined".equals(allParams.get("typeProduit"))) && (!"0".equals(allParams.get("typeProduit").trim()))) {
                liste = ListUtils.union(liste, produitRepository.findAll(where(idTypeLike(Long.valueOf(allParams.get("typeProduit"))))));
            }
            if ((allParams.containsKey("statutProduit")) && (!"undefined".equals(allParams.get("statutProduit"))) && (!"0".equals(allParams.get("statutProduit").trim()))) {
                liste = ListUtils.union(liste, produitRepository.findAll(where(idStatutLike(Long.valueOf(allParams.get("statutProduit"))))));
            }
            if ((allParams.containsKey("marque")) && (!"undefined".equals(allParams.get("marque"))) && (!"0".equals(allParams.get("marque").trim()))) {
                liste = ListUtils.union(liste, produitRepository.findAll(where(idMarqueLike(Long.valueOf(allParams.get("marque"))))));
            }
            if ((allParams.containsKey("modele")) && (!"undefined".equals(allParams.get("modele"))) && (!"0".equals(allParams.get("modele").trim()))) {
                liste = ListUtils.union(liste, produitRepository.findAll(where(idModeleLike(Long.valueOf(allParams.get("modele"))))));
            }
            if ((allParams.containsKey("remarque")) && (!"undefined".equals(allParams.get("remarque")))) {
                liste = ListUtils.union(liste, produitRepository.findAll(where(remarqueLike(allParams.get("remarque")))));
            }
        }
        for (Produit produit : liste) {
            if (null != produit.getPhoto()) {
                String imageFileName = produit.getNom();
                String extension = ".jpg";
                File file = new File("D:\\IdeaProjects\\AssistantPhotoArgentique\\Front\\apa\\src\\assets\\Images\\" + imageFileName + extension);
                file.createNewFile();
                FileImageOutputStream fos = new FileImageOutputStream(file);
                if (produit.getPhoto() != null) {
                    fos.write(produit.getPhoto());
                }
                fos.close();
            }
            if (null != produit.getModeEmploie()) {
                String modeEmploieFileName = produit.getNom();
                String extension = ".pdf";
                File file = new File("D:\\IdeaProjects\\AssistantPhotoArgentique\\Front\\apa\\src\\assets\\ModeEmploie\\" + modeEmploieFileName + extension);
                file.createNewFile();
                FileImageOutputStream fos = new FileImageOutputStream(file);
                if (produit.getModeEmploie() != null) {
                    fos.write(produit.getModeEmploie());
                }
                fos.close();
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
        System.out.println("Recherche du film appartir du produit " + id);
        return filmRepository.findFilmByProduitId(id);
    }

}
