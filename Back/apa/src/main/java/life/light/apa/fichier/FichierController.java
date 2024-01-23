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
import life.light.apa.referentiel.model.CopiableSousFormeDeFichier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.stream.FileImageOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

// TODO trouver une meilleur solution la mise à disposition de ces fichiers
@CrossOrigin(origins = "http://127.0.0.1:4200")
@RestController
public class FichierController {

    public static final Path PATH_ASSETS_FRONT = Path.of("D:\\IdeaProjects\\AssistantPhotoArgentique\\Front\\apa\\src\\assets");
    public static final String MODE_EMPLOI = "ModeEmploi";
    public static final String IMAGES = "Images";
    public static final String JPG = ".jpg";

    @Autowired
    private MaterielRepository materielRepository;
    @Autowired
    private ProduitRepository produitRepository;
    @Autowired
    private VueRepository vueRepository;

    @GetMapping(value = "/fichiers")
    public void copieLesFichiersSurLeFront() {
        copieFichierMateriel();
        copieFichierProduit();
        vueRepository.findVuesByStatutVueId(StatutVue.Realiser)
                .forEach(this::ecrireUnePhotoSurLeFront);
    }

    private void copieFichierProduit() {
        produitRepository.findAll()
                .forEach(this::ecrireUnePhotoSurLeFront);
        produitRepository.findAll()
                .forEach(this::ecrireUnModeEmploiSurLeFront);
    }

    private void copieFichierMateriel() {
        materielRepository.findAll()
                .forEach(this::ecrireUnePhotoSurLeFront);
        materielRepository.findAll()
                .forEach(this::ecrireUnModeEmploiSurLeFront);
    }

    @GetMapping(value = "/materiel/photo/{id}")
    public void copieLaPhotoDuMaterielSurLeFront(@PathVariable long id) {
        ecrireUnePhotoSurLeFront(materielRepository.findById(id).orElseThrow());
    }

    @GetMapping(value = "/materiel/modeEmploi/{id}")
    public void copieLeModeEmploiDuMaterielSurLeFront(@PathVariable long id) {
        ecrireUnModeEmploiSurLeFront(materielRepository.findById(id).orElseThrow());
    }

    @GetMapping(value = "/produit/photo/{id}")
    public void copieLaPhotoDuProduitSurLeFront(@PathVariable long id) {
        ecrireUnePhotoSurLeFront(produitRepository.findById(id).orElseThrow());
    }

    @GetMapping(value = "/produit/modeEmploi/{id}")
    public void copieLeModeEmploiDuProduitSurLeFront(@PathVariable long id) {
        ecrireUnModeEmploiSurLeFront(produitRepository.findById(id).orElseThrow());
    }

    private void ecrireUnePhotoSurLeFront(CopiableSousFormeDeFichier copiableSousFormeDeFichier) {
        if (null != copiableSousFormeDeFichier.getPhoto()) {
            ecrireUnFichierSurLeFront(IMAGES, copiableSousFormeDeFichier.getNom() + JPG, copiableSousFormeDeFichier.getPhoto());
        }
    }

    private void ecrireUnModeEmploiSurLeFront(CopiableSousFormeDeFichier copiableSousFormeDeFichier) {
        if (null != copiableSousFormeDeFichier.getModeEmploi()) {
            ecrireUnFichierSurLeFront(MODE_EMPLOI, copiableSousFormeDeFichier.getNom() + ".pdf", copiableSousFormeDeFichier.getModeEmploi());
        }
    }

    private void ecrireUnePhotoSurLeFront(Vue vue) {
        ecrireUnFichierSurLeFront(IMAGES, vue.getNom() + ".jpg", vue.getPhoto());
    }

    private void ecrireUnFichierSurLeFront(String path, String nomDuFichier, byte[] fichier) {
        File file = PATH_ASSETS_FRONT.resolve(path).resolve(nomDuFichier).toFile();
        try (FileImageOutputStream fos = new FileImageOutputStream(file)) {
            file.createNewFile();
            fos.write(fichier);
        } catch (Exception e) {
            System.out.println("impossible d'écrire le fichier " + nomDuFichier + " sur le serveur front.");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping(value = "/carte")
    public void copieLesCartesSurLeFront() {
        Set<Vue> liste = vueRepository.findVuesByStatutVueId(StatutVue.Realiser);
        GeoJson geoJson = new GeoJson();
        List<Feature> features = new ArrayList<>();
        for (Vue vue : liste) {
            // TODO => Creer des constructeurs
            Geometry geometry = new Geometry(vue.getPosition().getLongitude(), vue.getPosition().getLatitude());
            Feature feature = new Feature();
            feature.setGeometry(geometry);
            FeatureProperties featureProperties = new FeatureProperties();
            featureProperties.setNom(vue.getPosition().getNom());
            featureProperties.setAdresse(vue.getPosition().getVille() + " - " + vue.getPosition().getCodePostal());
            featureProperties.setVue(vue.getNom());
            feature.setProperties(featureProperties);
            features.add(feature);
        }
        geoJson.setFeatures(features);
        Path path = PATH_ASSETS_FRONT.resolve("Data").resolve("photo.geojson");
        ObjectMapper mapper = new ObjectMapper();
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            mapper.writeValue(writer, geoJson);
        } catch (Exception e) {
            System.out.println("impossible d'écrire le fichier photo.geojson sur le serveur front.");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
