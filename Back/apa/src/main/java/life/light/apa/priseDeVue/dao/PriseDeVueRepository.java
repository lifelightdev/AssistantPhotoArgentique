package life.light.apa.priseDeVue.dao;

import life.light.apa.priseDeVue.model.PriseDeVue;
import life.light.apa.priseDeVue.model.Vue;
import life.light.apa.referentiel.model.Materiel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PriseDeVueRepository extends JpaRepository<PriseDeVue, Long>, JpaSpecificationExecutor<PriseDeVue> {

    @Override
    Optional<PriseDeVue> findById(Long id);

    @Query("select p from PriseDeVue p")
    List<PriseDeVue> findAll();

    @Query("select m from Materiel m join PriseDeVue p where p.id=:id")
    List<Materiel> findMaterielsById(Long id);

    @Query("select v from Vue v join PriseDeVue p where p.id=:id")
    List<Vue> findVueByPriseDeVueId(long id);
}
