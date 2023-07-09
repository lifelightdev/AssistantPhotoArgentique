package life.light.apa.referentiel.dao;

import life.light.apa.referentiel.model.Marque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MarqueRepository extends JpaRepository<Marque, Long> {
    @Query("select m from Marque m")
    List<Marque> findAll();
}
