package life.light.apa.referentiel.dao;

import life.light.apa.referentiel.model.StatutProduit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StatutProduitRepository extends JpaRepository<StatutProduit, Long> {
    @Query("select s from StatutProduit s")
    List<StatutProduit> findAll();
}
