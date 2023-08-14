package life.light.apa.referentiel.dao;

import life.light.apa.referentiel.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.Optional;

public interface FilmRepository extends JpaRepository<Film, Long>, JpaSpecificationExecutor<Film> {

    Optional<Film> findFilmByProduitId(long id);
}
