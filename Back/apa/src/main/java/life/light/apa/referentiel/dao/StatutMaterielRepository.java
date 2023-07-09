package life.light.apa.referentiel.dao;

import life.light.apa.referentiel.model.StatutMateriel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatutMaterielRepository extends JpaRepository<StatutMateriel, Long> {
    List<StatutMateriel> findAll();
}
