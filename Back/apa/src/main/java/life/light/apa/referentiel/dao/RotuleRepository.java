package life.light.apa.referentiel.dao;

import life.light.apa.referentiel.model.Rotule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RotuleRepository extends JpaRepository<Rotule, Long>, JpaSpecificationExecutor<Rotule> {

    @Override
    Optional<Rotule> findById(Long id);

    @Query("select r from Rotule r")
    List<Rotule> findAll();

    Optional<Rotule> findRotuleByMaterielId(long id);
}
