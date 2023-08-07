package life.light.apa.priseDeVue.dao;

import life.light.apa.priseDeVue.model.PriseDeVue;
import life.light.apa.priseDeVue.model.Vue;
import life.light.apa.referentiel.model.Materiel;
import life.light.apa.referentiel.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PriseDeVueRepository extends JpaRepository<PriseDeVue, Long>, JpaSpecificationExecutor<PriseDeVue> {

    @Query("select m from Materiel m join PriseDeVue p where p.id=:id")
    List<Materiel> findMaterielsById(Long id);

    @Query("select v from Vue v join PriseDeVue p where p.id=:id")
    List<Vue> findVueByPriseDeVueId(long id);

    @Query("select produit from Produit produit join PriseDeVue p where p.id=:id")
    List<Produit> findProduitsById(long id);

}
