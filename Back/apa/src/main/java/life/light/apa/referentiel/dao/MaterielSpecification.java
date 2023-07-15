package life.light.apa.referentiel.dao;

import jakarta.persistence.criteria.Join;
import life.light.apa.referentiel.model.*;
import org.springframework.data.jpa.domain.Specification;

public class MaterielSpecification {
    public static Specification<Materiel> nomLike(String nom) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("nom"), "%" + nom + "%");
    }

    public static Specification<Materiel> idTypeLike(Long idType) {
        return (root, query, criteriaBuilder) -> {
            Join<TypeMateriel, Materiel> typeMateriel = root.join("typeMateriel");
            return criteriaBuilder.equal(typeMateriel.get("id"), idType);
        };
    }

    public static Specification<Materiel> idSousTypeLike(Long idSousType) {
        return (root, query, criteriaBuilder) -> {
            Join<SousTypeMateriel, Materiel> sousTypeMateriel = root.join("sousType");
            return criteriaBuilder.equal(sousTypeMateriel.get("id"), idSousType);
        };
    }

    public static Specification<Materiel> idStatutLike(Long idStatut) {
        return (root, query, criteriaBuilder) -> {
            Join<StatutMateriel, Materiel> statutMateriel = root.join("statutMateriel");
            return criteriaBuilder.equal(statutMateriel.get("id"), idStatut);
        };
    }

    public static Specification<Materiel> idMarqueLike(Long idMarque) {
        return (root, query, criteriaBuilder) -> {
            Join<Marque, Materiel> marque = root.join("marque");
            return criteriaBuilder.equal(marque.get("id"), idMarque);
        };
    }

    public static Specification<Materiel> idModeleLike(Long idModele) {
        return (root, query, criteriaBuilder) -> {
            Join<Modele, Materiel> marque = root.join("modele");
            return criteriaBuilder.equal(marque.get("id"), idModele);
        };
    }

    public static Specification<Materiel> remarqueLike(String remarque) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("remarque"), "%" + remarque + "%");
    }
}
