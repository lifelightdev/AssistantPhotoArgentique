package life.light.apa.referentiel.dao;

import life.light.apa.referentiel.model.AppareilPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AppareilPhotoRepository extends JpaRepository<AppareilPhoto, Long>, JpaSpecificationExecutor<AppareilPhoto> {

    @Override
    Optional<AppareilPhoto> findById(Long id);

    @Query("select a from AppareilPhoto a")
    List<AppareilPhoto> findAll();

    Optional<AppareilPhoto> findAppareilPhotoByMaterielId(long id);
}
