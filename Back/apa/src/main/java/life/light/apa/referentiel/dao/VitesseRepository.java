package life.light.apa.referentiel.dao;

import life.light.apa.referentiel.model.Vitesse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface VitesseRepository extends JpaRepository<Vitesse, Long>, JpaSpecificationExecutor<Vitesse> {

    @Query("select v from Vitesse v join Objectif objectif where objectif.id=:id")
    Iterable<Vitesse> findVitesseByObjectif(long id);

}
