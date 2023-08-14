package life.light.apa.referentiel.dao;

import life.light.apa.referentiel.model.AppareilPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.Optional;

public interface AppareilPhotoRepository extends JpaRepository<AppareilPhoto, Long>, JpaSpecificationExecutor<AppareilPhoto> {

    Optional<AppareilPhoto> findAppareilPhotoByMaterielId(long id);
}
