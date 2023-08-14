package life.light.apa.referentiel.dao;

import life.light.apa.referentiel.model.Ouverture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface OuvertureRepository extends JpaRepository<Ouverture, Long>, JpaSpecificationExecutor<Ouverture> {

    @Query("select o from Ouverture o join Objectif objectif where objectif.id=:id")
    Iterable<Ouverture> findOuvertureByObjectif(long id);

}
