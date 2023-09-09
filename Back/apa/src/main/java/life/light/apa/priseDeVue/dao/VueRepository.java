package life.light.apa.priseDeVue.dao;

import life.light.apa.priseDeVue.model.Vue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.Set;

public interface VueRepository extends JpaRepository<Vue, Long>, JpaSpecificationExecutor<Vue> {

    Set<Vue> findVuesByPriseDeVueId(Long id);


    Vue findVueByStatutVueId(Long id);

    Set<Vue> findVuesByStatutVueId(Long id);

}
