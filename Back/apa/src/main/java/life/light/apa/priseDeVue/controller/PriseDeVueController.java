package life.light.apa.priseDeVue.controller;

import life.light.apa.priseDeVue.dto.Android;
import life.light.apa.priseDeVue.model.Position;
import life.light.apa.priseDeVue.model.PriseDeVue;
import life.light.apa.priseDeVue.model.StatutPriseDeVue;
import life.light.apa.priseDeVue.model.Vue;
import life.light.apa.priseDeVue.service.PriseDeVueService;
import life.light.apa.referentiel.model.AppareilPhoto;
import life.light.apa.referentiel.model.Film;
import life.light.apa.referentiel.model.Materiel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://127.0.0.1:4200")
@RestController
public class PriseDeVueController {

    @Autowired
    private PriseDeVueService priseDeVueService;

    @GetMapping(value = "/priseDeVue")
    @ResponseBody
    public Iterable<PriseDeVue> recherchePriseDeVues(@RequestParam Map<String, String> allParams) {
        return priseDeVueService.listeDesPriseDeVue(allParams.get("nom"), allParams.get("statutPriseDeVue"),
                allParams.get("date"), allParams.get("position"), allParams.get("remarque"));
    }

    @GetMapping(value = "/statutPriseDeVue")
    public Iterable<StatutPriseDeVue> listeStatutPriseDeVue() {
        return priseDeVueService.listeTousLesStatutsPriseDeVue();
    }

    @RequestMapping(value = "/priseDeVue/materiel/{id}")
    public Iterable<Materiel> afficherLesMateriels(@PathVariable long id) {
        return priseDeVueService.afficherTousLesMaterielsdUnePriseDeVue(id);
    }

    @GetMapping(value = "/position")
    public Iterable<Position> listePosition() {
        return priseDeVueService.trouveToutesLesPosition();
    }

    @RequestMapping(value = "/priseDeVue/{id}")
    public Optional<PriseDeVue> afficherUnMaterielePriseDeVue(@PathVariable long id) {
        return priseDeVueService.afficherUnePriseDeVue(id);
    }

    @RequestMapping(value = "/vue/{id}")
    public Optional<Vue> afficherUneVue(@PathVariable long id) throws IOException {
        return priseDeVueService.afficherUneVue(id);
    }

    @RequestMapping(value = "/priseDeVue/{id}/vue")
    public Iterable<Vue> listeDesVuesDUnePriseDeVue(@PathVariable long id) throws IOException {
        return priseDeVueService.listeDesVuesDUnePriseDeVue(id);
    }

    @RequestMapping(value = "/priseDeVue/{id}/appareilPhoto")
    public List<Optional<AppareilPhoto>> listeDesAppareilsPhotoDUnePriseDeVue(@PathVariable long id) {
        return priseDeVueService.listeDesAppareilsPhotoDUnePriseDeVue(id);
    }

    @RequestMapping(value = "/priseDeVue/{id}/film")
    public List<Optional<Film>> listeDesFilmsDUnePriseDeVue(@PathVariable long id) {
        return priseDeVueService.listeDesFilmsDUnePriseDeVue(id);
    }

    @RequestMapping(value = "/android/vue")
    public Optional<Android> vueAndroid() {
        return Optional.of(priseDeVueService.getAndroid());
    }

    @PostMapping("/vue")
    public Vue ajouterVue(@RequestParam Map<String, String> allParams) {
        try {
            return priseDeVueService.ajouterUneVue(Long.valueOf(allParams.get("priseDeVue")),
                    Long.valueOf(allParams.get("appareilPhoto")),
                    Long.valueOf(allParams.get("film")));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/vue/{id}/photo")
    public void ajouterUnePhoto(@PathVariable long id, @RequestParam Map<String, String> allParams) {
        priseDeVueService.miseAJourVue(id,
                String.valueOf(allParams.get("valeurVitesse")),
                String.valueOf(allParams.get("valeurOuverture")),
                Long.valueOf(allParams.get("idStatut")));
    }

    @PostMapping("/priseDeVue/saisie")
    public void ajouterUnePriseDeVue(@RequestBody PriseDeVue priseDeVue) {
        priseDeVueService.EnregistreUnePriseDeVue(priseDeVue);
    }

}
