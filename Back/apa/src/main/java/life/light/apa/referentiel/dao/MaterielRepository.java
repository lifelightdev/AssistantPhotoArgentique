package life.light.apa.referentiel.dao;

import life.light.apa.referentiel.model.Materiel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MaterielRepository extends JpaRepository<Materiel, Long>, JpaSpecificationExecutor<Materiel> {

}
