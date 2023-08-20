package life.light.apa.referentiel.dao;

import jakarta.persistence.criteria.Join;
import life.light.apa.referentiel.model.*;
import org.springframework.data.jpa.domain.Specification;

public class FilmSpecification {
    public static Specification<Film> idStatutFilmLike(Long idStatut) {
        return (root, query, criteriaBuilder) -> {
            Join<StatutFilm, Film> statutFilm = root.join("statutFilm");
            return criteriaBuilder.equal(statutFilm.get("id"), idStatut);
        };
    }

}
