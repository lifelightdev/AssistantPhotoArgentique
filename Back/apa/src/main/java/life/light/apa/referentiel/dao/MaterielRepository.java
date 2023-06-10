package life.light.apa.referentiel.dao;

import life.light.apa.referentiel.model.Materiel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MaterielRepository extends JpaRepository<Materiel, Long> {

    @Override
    Optional<Materiel> findById(Long id);

    @Query("select m from Materiel m")
    List<Materiel> findAll();

}
