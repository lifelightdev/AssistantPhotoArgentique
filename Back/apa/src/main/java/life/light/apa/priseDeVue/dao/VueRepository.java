package life.light.apa.priseDeVue.dao;

import life.light.apa.priseDeVue.model.Vue;
import life.light.apa.referentiel.model.Ouverture;
import life.light.apa.referentiel.model.Vitesse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface VueRepository extends JpaRepository<Vue, Long>, JpaSpecificationExecutor<Vue> {

    @Override
    Optional<Vue> findById(Long id);

    @Query("select v from Vue v where v.id =:id")
    Vue findVuebyId(Long id);

    @Query("select v from Vue v")
    List<Vue> findAll();

    List<Vue> findVueByPriseDeVueId(Long id);

    @Query("select v from Vue v join StatutVue s where s.id =:id")
    Vue findVueByStatutVueId(Long id);

    @Query("select v from Vitesse v join Vue vue where vue.id =:id order by v.ordre")
    List<Vitesse> findVitesseByVueIdOrdreByVitesseOrdre(Long id);

    @Query("select o from Ouverture o join Vue v where v.id =:id order by o.ordre")
    List<Ouverture> findOuvertureByVueIdOrdreByOuvertureOrdre(Long id);

}
