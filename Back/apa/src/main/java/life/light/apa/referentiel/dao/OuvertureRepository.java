package life.light.apa.referentiel.dao;

import life.light.apa.referentiel.model.Ouverture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OuvertureRepository extends JpaRepository<Ouverture, Long>, JpaSpecificationExecutor<Ouverture> {

    @Override
    Optional<Ouverture> findById(Long id);

    @Query("select o from Ouverture o")
    List<Ouverture> findAll();

    @Query("select o from Ouverture o join Objectif objectif where objectif.id=:id")
    Iterable<Ouverture> findOuvertureByObjectif(long id);

}
