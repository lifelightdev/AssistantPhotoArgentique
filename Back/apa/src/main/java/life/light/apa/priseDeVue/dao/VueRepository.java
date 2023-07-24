package life.light.apa.priseDeVue.dao;

import life.light.apa.priseDeVue.model.Vue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface VueRepository extends JpaRepository<Vue, Long>, JpaSpecificationExecutor<Vue> {

    @Override
    Optional<Vue> findById(Long id);

    @Query("select v from Vue v")
    List<Vue> findAll();

}
