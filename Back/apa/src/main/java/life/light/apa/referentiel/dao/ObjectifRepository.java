package life.light.apa.referentiel.dao;

import life.light.apa.referentiel.model.Objectif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ObjectifRepository extends JpaRepository<Objectif, Long>, JpaSpecificationExecutor<Objectif> {

    @Override
    Optional<Objectif> findById(Long id);

    @Query("select o from Objectif o")
    List<Objectif> findAll();

    Optional<Objectif> findObjectifByMaterielId(long id);
}
