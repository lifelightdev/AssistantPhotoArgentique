package life.light.apa.fichier;

import com.fasterxml.jackson.databind.ObjectMapper;
import life.light.apa.priseDeVue.dao.VueRepository;
import life.light.apa.priseDeVue.dto.Feature;
import life.light.apa.priseDeVue.dto.FeatureProperties;
import life.light.apa.priseDeVue.dto.GeoJson;
import life.light.apa.priseDeVue.dto.Geometry;
import life.light.apa.priseDeVue.model.StatutVue;
import life.light.apa.priseDeVue.model.Vue;
import life.light.apa.referentiel.dao.MaterielRepository;
import life.light.apa.referentiel.dao.ProduitRepository;
import life.light.apa.referentiel.model.Materiel;
import life.light.apa.referentiel.model.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.stream.FileImageOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "http://127.0.0.1:4200")
@RestController
public class FichierController {

    public static final String PATH_ASSETS_FRONT = "D:\\IdeaProjects\\AssistantPhotoArgentique\\Front\\apa\\src\\assets";

    @Autowired
    private MaterielRepository materielRepository;
    @Autowired
    private ProduitRepository produitRepository;
    @Autowired
    private VueRepository vueRepository;

    @GetMapping(value = "/fichiers")
    public void copieLesFichiersSurLeFront() {
        List<Materiel> listeMateriel = materielRepository.findAll();
        for (Materiel materiel : listeMateriel) {
            if (null != materiel.getPhoto()) {
                ecrireUnFichierSurLeFront("\\Images\\", materiel.getNom() + ".jpg", materiel.getPhoto());
            }
            if (null != materiel.getModeEmploi()) {
                ecrireUnFichierSurLeFront("\\ModeEmploi\\", materiel.getNom() + ".pdf", materiel.getModeEmploi());
            }
        }
        List<Produit> listeProduit = produitRepository.findAll();
        for (Produit produit : listeProduit) {
            if (null != produit.getPhoto()) {
                ecrireUnFichierSurLeFront("\\Images\\", produit.getNom() + ".jpg", produit.getPhoto());
            }
            if (null != produit.getModeEmploi()) {
                ecrireUnFichierSurLeFront("\\ModeEmploi\\", produit.getNom() + ".pdf", produit.getModeEmploi());
            }
        }
        Set<Vue> listeVue = vueRepository.findVuesByStatutVueId(StatutVue.Realiser);
        for (Vue vue : listeVue){
            if (null != vue.getPhoto()){
                ecrireUnFichierSurLeFront("\\Images\\", vue.getNom() + ".jpg", vue.getPhoto());
            }
        }
    }

    @GetMapping(value = "/materiel/photo/{id}")
    public void copieLaPhotoDuMaterielSurLeFront(@PathVariable long id) {
        Materiel materiel = materielRepository.findById(id).get();
        if (null != materiel.getPhoto()) {
            ecrireUnFichierSurLeFront("\\Images\\", materiel.getNom() + ".jpg", materiel.getPhoto());
        }
    }

    @GetMapping(value = "/materiel/modeEmploi/{id}")
    public void copieLeModeEmploiDuMaterielSurLeFront(@PathVariable long id) {
        Materiel materiel = materielRepository.findById(id).get();
        if (null != materiel.getModeEmploi()) {
            ecrireUnFichierSurLeFront("\\ModeEmploi\\", materiel.getNom() + ".pdf", materiel.getModeEmploi());
        }
    }

    @GetMapping(value = "/produit/photo/{id}")
    public void copieLaPhotoDuProduitSurLeFront(@PathVariable long id) {
        Produit produit = produitRepository.findById(id).get();
        if (null != produit.getPhoto()) {
            ecrireUnFichierSurLeFront("\\Images\\", produit.getNom() + ".jpg", produit.getPhoto());
        }
    }

    @GetMapping(value = "/produit/modeEmploi/{id}")
    public void copieLeModeEmploiDuProduitSurLeFront(@PathVariable long id) {
        Produit produit = produitRepository.findById(id).get();
        if (null != produit.getModeEmploi()) {
            ecrireUnFichierSurLeFront("\\ModeEmploi\\", produit.getNom() + ".pdf", produit.getModeEmploi());
        }
    }

    private void ecrireUnFichierSurLeFront(String path, String nomDuFichier, byte[] fichier) {
        try {
            File file = new File(PATH_ASSETS_FRONT + path + nomDuFichier);
            if (lesFichiersSontSimilaire(fichier, file)) {
                file.createNewFile();
                FileImageOutputStream fos = new FileImageOutputStream(file);
                fos.write(fichier);
                fos.close();
            }
        } catch (Exception e) {
            System.out.println("impossible d'écrire le fichier " + nomDuFichier + " sur le serveur front.");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private static boolean lesFichiersSontSimilaire(byte[] fichier, File file) {
        boolean creerLeFichier = false;
        if (file.exists()) {
            if (file.length() != fichier.length) {
                creerLeFichier = true;
            }
        } else {
            creerLeFichier = true;
        }
        return creerLeFichier;
    }


    @GetMapping(value = "/carte")
    public void copieLesCartesSurLeFront() {
        Set<Vue> liste = vueRepository.findVuesByStatutVueId(StatutVue.Realiser);
        GeoJson geoJson = new GeoJson();
        List<Feature> features = new ArrayList<>();
        for (Vue vue : liste) {
            Feature feature = new Feature();
            List<Double> coordinates = new ArrayList<>();
            coordinates.add(vue.getPosition().getLongitude());
            coordinates.add(vue.getPosition().getLatitude());
            Geometry geometry = new Geometry();
            geometry.setCoordinates(coordinates);
            feature.setGeometry(geometry);
            FeatureProperties featureProperties = new FeatureProperties();
            featureProperties.setNom(vue.getPosition().getNom());
            featureProperties.setAdresse(vue.getPosition().getVille() + " - " + vue.getPosition().getCodePostal());
            featureProperties.setVue(vue.getNom());
            feature.setProperties(featureProperties);
            features.add(feature);
        }
        geoJson.setFeatures(features);
        String path = PATH_ASSETS_FRONT + "\\Data\\photo.geojson";
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(path), geoJson);
        } catch (Exception e) {
            System.out.println("impossible d'écrire le fichier photo.geojson sur le serveur front.");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
