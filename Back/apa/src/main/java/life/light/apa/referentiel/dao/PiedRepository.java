package life.light.apa.referentiel.dao;

import life.light.apa.referentiel.model.Pied;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.Optional;

public interface PiedRepository extends JpaRepository<Pied, Long>, JpaSpecificationExecutor<Pied> {

    Optional<Pied> findPiedByMaterielId(long id);
}
