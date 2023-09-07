package life.light.apa.referentiel.dao;

import life.light.apa.referentiel.model.Rotule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface RotuleRepository extends JpaRepository<Rotule, Long>, JpaSpecificationExecutor<Rotule> {

    Optional<Rotule> findRotuleByMaterielId(Long idMateriel);

}
