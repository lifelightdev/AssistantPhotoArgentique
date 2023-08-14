package life.light.apa.priseDeVue.dao;

import life.light.apa.priseDeVue.model.Vue;
import life.light.apa.referentiel.model.Ouverture;
import life.light.apa.referentiel.model.Vitesse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface VueRepository extends JpaRepository<Vue, Long>, JpaSpecificationExecutor<Vue> {

    List<Vue> findVueByPriseDeVueId(Long id);

    @Query("select v from Vue v where v.statutVue.id =:id")
    Vue findVueByStatutVueId(Long id);

    @Query("select v from Vitesse v join Vue vue where vue.id =:id order by v.ordre")
    List<Vitesse> findVitesseByVueIdOrdreByVitesseOrdre(Long id);

    @Query("select o from Ouverture o join Vue v where v.id =:id order by o.ordre")
    List<Ouverture> findOuvertureByVueIdOrdreByOuvertureOrdre(Long id);

    @Query("select v from Vue v where v.priseDeVue.id =:id and v.statutVue.id = 1.0 ")
    List<Vue> findVueARealiserByPriseDeVueId(Long id);
}
