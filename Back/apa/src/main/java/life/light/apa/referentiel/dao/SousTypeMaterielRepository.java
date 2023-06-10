package life.light.apa.referentiel.dao;

import life.light.apa.referentiel.model.SousTypeMateriel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SousTypeMaterielRepository extends JpaRepository<SousTypeMateriel, Long> {
    List<SousTypeMateriel> findAll();
}
