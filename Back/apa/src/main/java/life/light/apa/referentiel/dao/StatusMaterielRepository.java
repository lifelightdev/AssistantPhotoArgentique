package life.light.apa.referentiel.dao;

import life.light.apa.referentiel.model.StatusMateriel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatusMaterielRepository extends JpaRepository<StatusMateriel, Long> {
    List<StatusMateriel> findAll();
}
