package life.light.apa.referentiel.dao;

import life.light.apa.referentiel.model.Materiel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MaterielRepository extends JpaRepository<Materiel, Long> {

    @Override
    Optional<Materiel> findById(Long id);

    @Query("select m from Materiel m")
    List<Materiel> findAll();

    @Query("SELECT m FROM Materiel m WHERE m.nom like %:nom% OR m.typeMateriel.id = :idType " +
            "OR m.sousType.id = :idSousType OR m.statutMateriel.id = :idStatut " +
            "OR m.remarque like  %:remarque% ")
    List<Materiel> search(String nom, Long idType, Long idSousType, Long idStatut, String remarque);
}
