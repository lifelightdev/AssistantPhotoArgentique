package life.light.apa.referentiel.dao;

import life.light.apa.referentiel.model.Modele;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ModeleRepository extends JpaRepository<Modele, Long> {
    @Query("select m from Modele m")
    List<Modele> findAll();
}
