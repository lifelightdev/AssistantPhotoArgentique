package life.light.apa.priseDeVue.controller;

import life.light.apa.priseDeVue.model.Position;
import life.light.apa.priseDeVue.model.PriseDeVue;
import life.light.apa.priseDeVue.model.StatutPriseDeVue;
import life.light.apa.priseDeVue.model.Vue;
import life.light.apa.priseDeVue.service.PriseDeVueService;
import life.light.apa.referentiel.model.Materiel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    public Iterable<Materiel> afficherLesMateriels(@RequestParam Map<String, String> allParams) {
        return priseDeVueService.afficherTousLesMaterielsdUnePriseDeVue(Long.valueOf(allParams.get("id")));
    }

    @GetMapping(value = "/position")
    public Iterable<Position> listePosition() {
        return priseDeVueService.trouveToutesLesPosition();
    }

    @RequestMapping(value = "/priseDeVue/{id}")
    public Optional<PriseDeVue> afficherUnMaterielePriseDeVue(@PathVariable long id) {
        return priseDeVueService.findById(id);
    }

    @RequestMapping(value = "/vue/{id}")
    public Optional<Vue> afficherUneVue(@PathVariable long id) throws IOException {
        return priseDeVueService.afficherUneVue(id);
    }

    @RequestMapping(value = "/priseDeVue/{id}/vue")
    public Iterable<Vue> listeDesVueDUnePriseDeVue(@PathVariable long id) throws IOException {
        return priseDeVueService.listeDesVueDUnePriseDeVue(id);
    }

    @RequestMapping(value = "/android/vue")
    public Optional<Android> listeAndroidVue() {
        return Optional.of(priseDeVueService.getAndroid());
    }

    @PostMapping("/vue")
    public Vue ajouterVue(@RequestBody Vue nouvelleVue) {
        return priseDeVueService.ajouterVue(nouvelleVue);
    }

}
