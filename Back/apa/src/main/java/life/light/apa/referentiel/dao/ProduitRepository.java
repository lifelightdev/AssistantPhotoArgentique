package life.light.apa.referentiel.dao;

import life.light.apa.referentiel.model.Materiel;
import life.light.apa.referentiel.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProduitRepository extends JpaRepository<Produit, Long>, JpaSpecificationExecutor<Produit> {

    @Override
    Optional<Produit> findById(Long id);

    @Query("select p from Produit p")
    List<Produit> findAll();

}
