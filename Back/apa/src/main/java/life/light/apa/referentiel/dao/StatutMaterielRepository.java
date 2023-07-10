package life.light.apa.referentiel.dao;

import life.light.apa.referentiel.model.StatutMateriel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StatutMaterielRepository extends JpaRepository<StatutMateriel, Long> {
    @Query("select m from StatutMateriel m")
    List<StatutMateriel> findAll();
}
