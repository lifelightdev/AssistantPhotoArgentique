package life.light.apa.referentiel.dao;

import life.light.apa.referentiel.model.TypeMateriel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypeMaterielRepository extends JpaRepository<TypeMateriel, Long> {
    List<TypeMateriel> findAll();

}
