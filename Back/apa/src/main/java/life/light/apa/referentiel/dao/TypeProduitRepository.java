package life.light.apa.referentiel.dao;

import life.light.apa.referentiel.model.TypeProduit;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TypeProduitRepository extends JpaRepository<TypeProduit, Long> {
    List<TypeProduit> findAll();
}
