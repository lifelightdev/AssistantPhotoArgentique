package life.light.apa.priseDeVue.dao;

import life.light.apa.priseDeVue.model.StatutPriseDeVue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface StatutPriseDeVueRepository extends JpaRepository<StatutPriseDeVue, Long> {
    @Query("select s from StatutPriseDeVue s")
    List<StatutPriseDeVue> findAll();
}
