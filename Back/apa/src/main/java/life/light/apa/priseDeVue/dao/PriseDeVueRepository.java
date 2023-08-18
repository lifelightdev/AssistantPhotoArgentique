package life.light.apa.priseDeVue.dao;

import life.light.apa.priseDeVue.model.PriseDeVue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PriseDeVueRepository extends JpaRepository<PriseDeVue, Long>, JpaSpecificationExecutor<PriseDeVue> {

}
