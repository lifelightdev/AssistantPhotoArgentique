package life.light.apa.referentiel.dao;

import life.light.apa.referentiel.model.Objectif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.Optional;

public interface ObjectifRepository extends JpaRepository<Objectif, Long>, JpaSpecificationExecutor<Objectif> {

    Optional<Objectif> findObjectifByMaterielId(long id);
}
