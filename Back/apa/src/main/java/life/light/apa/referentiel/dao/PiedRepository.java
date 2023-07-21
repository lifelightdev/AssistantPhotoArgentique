package life.light.apa.referentiel.dao;

import life.light.apa.referentiel.model.Pied;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PiedRepository extends JpaRepository<Pied, Long>, JpaSpecificationExecutor<Pied> {

    @Override
    Optional<Pied> findById(Long id);

    @Query("select p from Pied p")
    List<Pied> findAll();

    Optional<Pied> findPiedByMaterielId(long id);
}
