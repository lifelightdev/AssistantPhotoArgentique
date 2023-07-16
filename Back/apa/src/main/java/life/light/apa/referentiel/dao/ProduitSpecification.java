package life.light.apa.referentiel.dao;

import jakarta.persistence.criteria.Join;
import life.light.apa.referentiel.model.*;
import org.springframework.data.jpa.domain.Specification;

public class ProduitSpecification {
    public static Specification<Produit> nomLike(String nom) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("nom"), "%" + nom + "%");
    }

    public static Specification<Produit> idTypeLike(Long idType) {
        return (root, query, criteriaBuilder) -> {
            Join<TypeProduit, Produit> typeProduit = root.join("typeProduit");
            return criteriaBuilder.equal(typeProduit.get("id"), idType);
        };
    }

    public static Specification<Produit> idStatutLike(Long idStatut) {
        return (root, query, criteriaBuilder) -> {
            Join<StatutProduit, Produit> statutProduit = root.join("statutProduit");
            return criteriaBuilder.equal(statutProduit.get("id"), idStatut);
        };
    }

    public static Specification<Produit> idMarqueLike(Long idMarque) {
        return (root, query, criteriaBuilder) -> {
            Join<Marque, Produit> marque = root.join("marque");
            return criteriaBuilder.equal(marque.get("id"), idMarque);
        };
    }

    public static Specification<Produit> idModeleLike(Long idModele) {
        return (root, query, criteriaBuilder) -> {
            Join<Modele, Produit> marque = root.join("modele");
            return criteriaBuilder.equal(marque.get("id"), idModele);
        };
    }

    public static Specification<Produit> remarqueLike(String remarque) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("remarque"), "%" + remarque + "%");
    }
}
