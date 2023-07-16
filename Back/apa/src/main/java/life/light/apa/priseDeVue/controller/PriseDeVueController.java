package life.light.apa.priseDeVue.controller;

import life.light.apa.priseDeVue.dao.PriseDeVueRepository;
import life.light.apa.priseDeVue.dao.StatutPriseDeVueRepository;
import life.light.apa.priseDeVue.model.PriseDeVue;
import life.light.apa.priseDeVue.model.StatutPriseDeVue;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static life.light.apa.priseDeVue.dao.PriseDeVueSpecification.*;
import static org.springframework.data.jpa.domain.Specification.where;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class PriseDeVueController {
    @Autowired
    private PriseDeVueRepository priseDeVueRepository;

    @GetMapping(value = "/priseDeVue")
    @ResponseBody
    public Iterable<PriseDeVue> recherchePriseDeVues(@RequestParam Map<String, String> allParams) {
        List<PriseDeVue> liste = new ArrayList<>();
        if (allParams.entrySet().isEmpty()) {
            liste = priseDeVueRepository.findAll();
        } else {
            if ((allParams.containsKey("nom")) && (!"undefined".equals(allParams.get("nom"))) && (!"".equals(allParams.get("nom").trim()))){
                liste = ListUtils.union(liste, priseDeVueRepository.findAll(where(nomLike(allParams.get("nom")))));
            }
            if ((allParams.containsKey("statutPriseDeVue")) && (!"undefined".equals(allParams.get("statutPriseDeVue"))) && (!"0".equals(allParams.get("statutPriseDeVue").trim()))){
                liste = ListUtils.union(liste, priseDeVueRepository.findAll(where(idStatutLike(Long.valueOf(allParams.get("statutPriseDeVue"))))));
            }
            if ((allParams.containsKey("date")) && (!"undefined".equals(allParams.get("date"))) && (!"0".equals(allParams.get("date").trim()))){
                liste = ListUtils.union(liste, priseDeVueRepository.findAll(where(dateLike(Date.valueOf(allParams.get("date"))))));
            }
            if ((allParams.containsKey("position")) && (!"undefined".equals(allParams.get("position"))) && (!"0".equals(allParams.get("position").trim()))){
                liste = ListUtils.union(liste, priseDeVueRepository.findAll(where(positionLike(allParams.get("position")))));
            }
            if ((allParams.containsKey("remarque")) && (!"undefined".equals(allParams.get("remarque")))){
                liste = ListUtils.union(liste, priseDeVueRepository.findAll(where(remarqueLike(allParams.get("remarque")))));
            }
        }
        return liste;
    }

    @Autowired
    private StatutPriseDeVueRepository statutPriseDeVue;

    @GetMapping(value = "/statutPriseDeVue")
    public Iterable<StatutPriseDeVue> listeStatutPriseDeVue() {
        return statutPriseDeVue.findAll();
    }

}
