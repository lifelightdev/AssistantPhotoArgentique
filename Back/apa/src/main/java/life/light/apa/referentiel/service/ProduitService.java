package life.light.apa.referentiel.service;

import life.light.apa.referentiel.dao.*;
import life.light.apa.referentiel.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class ProduitService {

    @Autowired
    private ProduitRepository produitRepository;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private TypeProduitRepository typeProduit;
    @Autowired
    private StatutProduitRepository statutProduit;

    public Iterable<Produit> rechercheProduits(String nom, String typeProduit, String statutProduit, String marque, String modele, String remarque) {
        Set<Produit> liste = new HashSet<>();
        boolean trouver = false;
        if ((null != nom) && (!"undefined".equals(nom)) && (!nom.trim().isEmpty())) {
            liste.addAll(produitRepository.findAll(where(ProduitSpecification.nomLike(nom))));
            trouver = true;
        }
        if ((null != typeProduit) && (!"undefined".equals(typeProduit)) && (!typeProduit.trim().isEmpty())) {
            liste.addAll(produitRepository.findAll(where(ProduitSpecification.idTypeLike(Long.valueOf(typeProduit)))));
            trouver = true;
        }
        if ((null != statutProduit) && (!"undefined".equals(statutProduit)) && (!statutProduit.trim().isEmpty())) {
            liste.addAll(produitRepository.findAll(where(ProduitSpecification.idStatutLike(Long.valueOf(statutProduit)))));
            trouver = true;
        }
        if ((null != marque) && (!"undefined".equals(marque)) && (!marque.trim().isEmpty())) {
            liste.addAll(produitRepository.findAll(where(ProduitSpecification.idMarqueLike(Long.valueOf(marque)))));
            trouver = true;
        }
        if ((null != modele) && (!"undefined".equals(modele)) && (!modele.trim().isEmpty())) {
            liste.addAll(produitRepository.findAll(where(ProduitSpecification.idModeleLike(Long.valueOf(modele)))));
            trouver = true;
        }
        if ((null != remarque) && (!"undefined".equals(remarque)) && (!remarque.trim().isEmpty())) {
            liste.addAll(produitRepository.findAll(where(ProduitSpecification.remarqueLike(remarque))));
            trouver = true;
        }
        if (!trouver) {
            liste.addAll(produitRepository.findAll());
        }
        return liste;
    }

    public Iterable<TypeProduit> listeTousLesTypesDeProduits() {
        return typeProduit.findAll();
    }

    public Iterable<StatutProduit> listeDeTousLesStatutsProduits() {
        return statutProduit.findAll();
    }

    public Optional<Produit> afficherUnProduit(long id) {
        return produitRepository.findById(id);
    }

    public Optional<Film> afficherUnFilm(long id) {
        return filmRepository.findFilmByProduitId(id);
    }

    public Iterable<Film> listeDesFilmsDisponible() {
        return filmRepository.findAll(where(FilmSpecification.idStatutFilmLike(StatutFilm.DISPONIBLE)));
    }
}