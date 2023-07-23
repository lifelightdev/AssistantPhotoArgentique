package life.light.apa.referentiel.dao;

import life.light.apa.referentiel.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface FilmRepository extends JpaRepository<Film, Long>, JpaSpecificationExecutor<Film> {

    @Override
    Optional<Film> findById(Long id);

    @Query("select f from Film f")
    List<Film> findAll();

    Optional<Film> findFilmByProduitId(long id);
}
