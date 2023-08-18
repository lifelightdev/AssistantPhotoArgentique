package life.light.apa.priseDeVue.dao;

import life.light.apa.priseDeVue.model.Vue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface VueRepository extends JpaRepository<Vue, Long>, JpaSpecificationExecutor<Vue> {

    List<Vue> findVuesByPriseDeVueId(Long id);

    @Query("select v from Vue v where v.statutVue.id =:id")
    Vue findVueByStatutVueId(Long id);

    @Query("select v from Vue v where v.statutVue.id = 1.0 ")
    List<Vue> findVueARealiser();
}
