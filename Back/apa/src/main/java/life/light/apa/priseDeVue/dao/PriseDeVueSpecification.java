package life.light.apa.priseDeVue.dao;

import jakarta.persistence.criteria.Join;
import life.light.apa.priseDeVue.model.PriseDeVue;
import life.light.apa.priseDeVue.model.StatutPriseDeVue;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDate;
import java.time.LocalTime;


public class PriseDeVueSpecification {

    public static Specification<PriseDeVue> nomLike(String nom) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("nom"), "%" + nom + "%");
    }

    public static Specification<PriseDeVue> dateLike(LocalDate date) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("date"), "%" + date + "%");
    }

    public static Specification<PriseDeVue> heureLike(LocalTime heure) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("date"), "%" + heure + "%");
    }

    public static Specification<PriseDeVue> positionLike(String position) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("position"), "%" + position + "%");
    }

    public static Specification<PriseDeVue> idStatutLike(Long idStatut) {
        return (root, query, criteriaBuilder) -> {
            Join<StatutPriseDeVue, PriseDeVue> statutPriseDeVue = root.join("statutPriseDeVue");
            return criteriaBuilder.equal(statutPriseDeVue.get("id"), idStatut);
        };
    }

    public static Specification<PriseDeVue> remarqueLike(String remarque) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("remarque"), "%" + remarque + "%");
    }
}
