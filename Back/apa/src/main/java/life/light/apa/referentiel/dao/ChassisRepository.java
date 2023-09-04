package life.light.apa.referentiel.dao;

import life.light.apa.referentiel.model.Chassis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ChassisRepository extends JpaRepository<Chassis, Long>, JpaSpecificationExecutor<Chassis> {

    Optional<Chassis> findChassisByMaterielId(long id);

}
