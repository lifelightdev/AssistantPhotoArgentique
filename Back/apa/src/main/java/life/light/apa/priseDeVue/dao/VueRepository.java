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

    @Query("select v from Vue v")
    List<Vue> findAll();

    @Query("select o from Ouverture o join Vue v where v.id=:id")
    List<Ouverture> findOuverture(Long id);

    @Query("select o from Vitesse o join Vue v where v.id=:id")
    List<Vitesse> findVitesse(Long id);

}
