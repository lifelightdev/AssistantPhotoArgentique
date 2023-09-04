package life.light.apa.priseDeVue.service;

import life.light.apa.priseDeVue.PriseDeVueException;
import life.light.apa.priseDeVue.dao.PriseDeVueRepository;
import life.light.apa.priseDeVue.dao.StatutVueRepository;
import life.light.apa.priseDeVue.dao.VueRepository;
import life.light.apa.priseDeVue.model.PriseDeVue;
import life.light.apa.priseDeVue.model.StatutVue;
import life.light.apa.priseDeVue.model.Vue;
import life.light.apa.referentiel.dao.AppareilPhotoRepository;
import life.light.apa.referentiel.dao.ChassisRepository;
import life.light.apa.referentiel.dao.FilmRepository;
import life.light.apa.referentiel.model.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.*;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PriseDeVueServiceTest {

    @InjectMocks
    PriseDeVueService service;

    @Mock
    VueRepository vueRepository;
    @Mock
    PriseDeVueRepository priseDeVueRepository;
    @Mock
    AppareilPhotoRepository appareilPhotoRepository;
    @Mock
    ChassisRepository chassisRepository;
    @Mock
    FilmRepository filmRepository;
    @Mock
    StatutVueRepository statutVueRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void ajouterUneVue() throws PriseDeVueException {

        Long idFilm = 1L;
        Long idPriseDeVue = 1L;
        Long idAppareilPhoto = 1L;

        PriseDeVue mockPriseDeVue = new PriseDeVue();
        mockPriseDeVue.setId(idPriseDeVue);
        mockPriseDeVue.setNom("Nom de la prise de vue");
        Film film = new Film();
        film.setId(idFilm);
        Materiel materiel = new Materiel();
        materiel.setNom("Nom de l'appareil photo");
        AppareilPhoto appareilPhoto = new AppareilPhoto();
        appareilPhoto.setId(idAppareilPhoto);
        appareilPhoto.setMateriel(materiel);
        StatutVue statutVue = new StatutVue();
        statutVue.setId(StatutVue.ARealiser);
        Vue mockVue = new Vue();
        mockVue.setFilm(film);
        mockVue.setPriseDeVue(mockPriseDeVue);
        mockVue.setAppareilPhoto(appareilPhoto);

        List<Vue> listVueVide = new ArrayList<>();

        when(vueRepository.findVueARealiser()).thenReturn(listVueVide);
        when(vueRepository.save(any(Vue.class))).thenReturn(mockVue);
        when(priseDeVueRepository.findById(idPriseDeVue)).thenReturn(Optional.of(mockPriseDeVue));
        when(appareilPhotoRepository.findById(idAppareilPhoto)).thenReturn(Optional.of(appareilPhoto));
        when(filmRepository.findById(idFilm)).thenReturn(Optional.of(film));
        when(statutVueRepository.findById(StatutVue.ARealiser)).thenReturn(Optional.of(statutVue));

        Vue vue = service.ajouterUneVue(idPriseDeVue, idAppareilPhoto, idFilm);

        assertThat(vue.getFilm().getId()).isEqualTo(idFilm);
    }

    @Test
    void ajouterUneVueDejaRealiser() {
        Long idFilm = 1L;
        Long idPriseDeVue = 1L;
        Long idAppareilPhoto = 1L;

        PriseDeVue mockPriseDeVue = new PriseDeVue();
        mockPriseDeVue.setId(idPriseDeVue);
        mockPriseDeVue.setNom("Nom de la prise de vue");
        Film film = new Film();
        film.setId(idFilm);
        Materiel materiel = new Materiel();
        materiel.setNom("Nom de l'appareil photo");
        AppareilPhoto appareilPhoto = new AppareilPhoto();
        appareilPhoto.setId(idAppareilPhoto);
        appareilPhoto.setMateriel(materiel);
        Vue mockVue = new Vue();
        mockVue.setFilm(film);
        mockVue.setPriseDeVue(mockPriseDeVue);
        mockVue.setAppareilPhoto(appareilPhoto);

        List<Vue> listVue = new ArrayList<>();
        listVue.add(mockVue);

        when(vueRepository.findVueARealiser()).thenReturn(listVue);

        Exception exception = assertThrows(PriseDeVueException.class, () -> service.ajouterUneVue(idPriseDeVue, idAppareilPhoto, idFilm));

        String expectedMessage = "Impossible d'ajouter une vue car il y a déjà une vue au statut 'A réaliser'";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        assertThat(actualMessage).isEqualTo(expectedMessage);

    }

    @Test
    void ajouterUnePriseDeVueAvecUnAgrandisseur() {
        PriseDeVue mockPriseDeVue = new PriseDeVue();
        mockPriseDeVue.setNom("Nom de la prise de vue");
        Materiel materiel = new Materiel();
        materiel.setNom("Nom de l'agrandisseur");
        TypeMateriel typeMateriel = new TypeMateriel();
        typeMateriel.setId(TypeMateriel.TIRAGE);
        materiel.setTypeMateriel(typeMateriel);
        Set<Materiel> materiels = new HashSet<>();
        materiels.add(materiel);
        mockPriseDeVue.setMateriels(materiels);

        Exception exception = assertThrows(PriseDeVueException.class, () -> service.EnregistreUnePriseDeVue(mockPriseDeVue));

        String expectedMessage = "Impossible d'ajouter la prise de vue car le matériel " + materiel.getNom() + " n'est pas de type prise de vue.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

    @Test
    void ajouterUnePriseDeVueSansAppareilPhoto() {
        PriseDeVue mockPriseDeVue = new PriseDeVue();
        mockPriseDeVue.setNom("Nom de la prise de vue");
        Materiel materiel = new Materiel();
        materiel.setNom("Nom du pied");
        TypeMateriel typeMateriel = new TypeMateriel();
        typeMateriel.setId(TypeMateriel.ID_PRISE_DE_VUE);
        materiel.setTypeMateriel(typeMateriel);
        SousTypeMateriel sousTypeMateriel = new SousTypeMateriel();
        sousTypeMateriel.setId(SousTypeMateriel.ID_PIED);
        materiel.setSousType(sousTypeMateriel);
        Set<Materiel> materiels = new HashSet<>();
        materiels.add(materiel);
        mockPriseDeVue.setMateriels(materiels);

        Exception exception = assertThrows(PriseDeVueException.class, () -> service.EnregistreUnePriseDeVue(mockPriseDeVue));

        String expectedMessage = "Impossible d'ajouter la prise de vue car il n'y a pas d'appareil photo dans la liste de matériel.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

    @Test
    void ajouterUnePriseDeVueSansFilm() {
        PriseDeVue mockPriseDeVue = new PriseDeVue();
        mockPriseDeVue.setNom("Nom de la prise de vue");
        Materiel materiel = new Materiel();
        materiel.setId(1L);
        materiel.setNom("Nom de l'appareil photo");
        TypeMateriel typeMateriel = new TypeMateriel();
        typeMateriel.setId(TypeMateriel.ID_PRISE_DE_VUE);
        materiel.setTypeMateriel(typeMateriel);
        SousTypeMateriel sousTypeMateriel = new SousTypeMateriel();
        sousTypeMateriel.setId(SousTypeMateriel.ID_APPAREIL_PHOTO_ARGENTIQUE);
        materiel.setSousType(sousTypeMateriel);
        AppareilPhoto appareilPhoto = new AppareilPhoto();
        appareilPhoto.setId(1L);
        appareilPhoto.setMateriel(materiel);
        Chassis chassis = new Chassis();
        TailleFilm tailleFilmAppareilPhoto = new TailleFilm();
        tailleFilmAppareilPhoto.setId(1L);
        chassis.setTailleFilm(tailleFilmAppareilPhoto);
        appareilPhoto.setChassis(chassis);
        Set<Materiel> materiels = new HashSet<>();
        materiels.add(materiel);
        mockPriseDeVue.setMateriels(materiels);

        when(appareilPhotoRepository.findAppareilPhotoByMaterielId(1L)).thenReturn(Optional.of(appareilPhoto));
        Exception exception = assertThrows(PriseDeVueException.class, () -> service.EnregistreUnePriseDeVue(mockPriseDeVue));

        String expectedMessage = "Impossible d'ajouter la prise de vue car il n'y a pas de film.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

    @Test
    void ajouterUnePriseDeVueAvecUnFilmIncompatible() {
        PriseDeVue mockPriseDeVue = new PriseDeVue();
        mockPriseDeVue.setNom("Nom de la prise de vue");
        Materiel materiel = new Materiel();
        materiel.setId(1L);
        materiel.setNom("Nom de l'appareil photo");
        TypeMateriel typeMateriel = new TypeMateriel();
        typeMateriel.setId(TypeMateriel.ID_PRISE_DE_VUE);
        materiel.setTypeMateriel(typeMateriel);
        SousTypeMateriel sousTypeMateriel = new SousTypeMateriel();
        sousTypeMateriel.setId(SousTypeMateriel.ID_APPAREIL_PHOTO_ARGENTIQUE);
        materiel.setSousType(sousTypeMateriel);
        AppareilPhoto appareilPhoto = new AppareilPhoto();
        appareilPhoto.setId(1L);
        appareilPhoto.setMateriel(materiel);
        Chassis chassis = new Chassis();
        TailleFilm tailleFilmAppareilPhoto = new TailleFilm();
        tailleFilmAppareilPhoto.setId(1L);
        chassis.setTailleFilm(tailleFilmAppareilPhoto);
        appareilPhoto.setChassis(chassis);
        Produit produit = new Produit();
        produit.setId(1L);
        produit.setNom("Nom du film");
        Film film = new Film();
        film.setId(1L);
        film.setProduit(produit);
        TailleFilm tailleFilm = new TailleFilm();
        tailleFilm.setId(2L);
        tailleFilm.setNom("Taille du film");
        film.setTailleFilm(tailleFilm);
        Set<Materiel> materiels = new HashSet<>();
        materiels.add(materiel);
        mockPriseDeVue.setMateriels(materiels);
        Set<Produit> produits = new HashSet<>();
        produits.add(produit);
        mockPriseDeVue.setProduits(produits);

        when(appareilPhotoRepository.findAppareilPhotoByMaterielId(1L)).thenReturn(Optional.of(appareilPhoto));
        when(filmRepository.findById(1L)).thenReturn(Optional.of(film));
        Exception exception = assertThrows(PriseDeVueException.class, () -> service.EnregistreUnePriseDeVue(mockPriseDeVue));

        String expectedMessage = "Impossible d'ajouter la prise de vue car l'appareil photo " + appareilPhoto.getMateriel().getNom()
                + " n'est pas compatible avec un film de taille " + film.getTailleFilm().getNom() + ".";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

    @Test
    void ajouterUnePriseDeVueAvecUnChassis() {

        PriseDeVue mockPriseDeVue = new PriseDeVue();
        mockPriseDeVue.setNom("Nom de la prise de vue");

        TypeMateriel typeMateriel = new TypeMateriel();
        typeMateriel.setId(TypeMateriel.ID_PRISE_DE_VUE);

        SousTypeMateriel sousTypeMateriel = new SousTypeMateriel();
        sousTypeMateriel.setId(SousTypeMateriel.ID_APPAREIL_PHOTO_ARGENTIQUE);

        SousTypeMateriel sousTypeMaterielChassis = new SousTypeMateriel();
        sousTypeMaterielChassis.setId(SousTypeMateriel.ID_CHASSIS_PRISE_DE_VUE);

        DimensionChassis dimensionChassis = new DimensionChassis();
        dimensionChassis.setId(1L);
        dimensionChassis.setNom("Nom de la dimension du chassis");

        Materiel materielAppareilPhoto = new Materiel();
        materielAppareilPhoto.setId(1L);
        materielAppareilPhoto.setNom("Nom de l'appareil photo avec un chassis");
        materielAppareilPhoto.setTypeMateriel(typeMateriel);
        materielAppareilPhoto.setSousType(sousTypeMateriel);

        AppareilPhoto appareilPhoto = new AppareilPhoto();
        appareilPhoto.setId(1L);
        appareilPhoto.setMateriel(materielAppareilPhoto);
        appareilPhoto.setDimensionChassis(dimensionChassis);

        Materiel materielChassis = new Materiel();
        materielChassis.setId(2L);
        materielChassis.setNom("Nom du chassis");
        materielChassis.setTypeMateriel(typeMateriel);
        materielChassis.setSousType(sousTypeMaterielChassis);

        Chassis chassis = new Chassis();
        TailleFilm tailleFilm = new TailleFilm();
        tailleFilm.setId(1L);
        tailleFilm.setNom("Taille du film");
        chassis.setTailleFilm(tailleFilm);
        chassis.setMateriel(materielChassis);
        chassis.setDimensionChassis(dimensionChassis);

        Set<Materiel> materiels = new HashSet<>();
        materiels.add(materielAppareilPhoto);
        materiels.add(materielChassis);
        mockPriseDeVue.setMateriels(materiels);

        Produit produit = new Produit();
        produit.setId(1L);
        produit.setNom("Nom du film");

        Film film = new Film();
        film.setId(1L);
        film.setProduit(produit);
        film.setTailleFilm(tailleFilm);

        Set<Produit> produits = new HashSet<>();
        produits.add(produit);
        mockPriseDeVue.setProduits(produits);

        when(appareilPhotoRepository.findAppareilPhotoByMaterielId(materielAppareilPhoto.getId())).thenReturn(Optional.of(appareilPhoto));
        when(chassisRepository.findChassisByMaterielId(materielChassis.getId())).thenReturn(Optional.of(chassis));
        when(filmRepository.findById(1L)).thenReturn(Optional.of(film));
        when(priseDeVueRepository.saveAndFlush(mockPriseDeVue)).thenReturn(mockPriseDeVue);

        PriseDeVue priseDeVueSave;
        try {
            priseDeVueSave = service.EnregistreUnePriseDeVue(mockPriseDeVue);
        } catch (PriseDeVueException e) {
            throw new RuntimeException(e);
        }
        assertThat(priseDeVueSave).isEqualTo(mockPriseDeVue);
    }

    @Test
    void ajouterUnePriseDeVueAvecUnChassisIncompatible() {

        PriseDeVue mockPriseDeVue = new PriseDeVue();
        mockPriseDeVue.setNom("Nom de la prise de vue");

        TypeMateriel typeMateriel = new TypeMateriel();
        typeMateriel.setId(TypeMateriel.ID_PRISE_DE_VUE);

        SousTypeMateriel sousTypeMateriel = new SousTypeMateriel();
        sousTypeMateriel.setId(SousTypeMateriel.ID_APPAREIL_PHOTO_ARGENTIQUE);

        SousTypeMateriel sousTypeMaterielChassis = new SousTypeMateriel();
        sousTypeMaterielChassis.setId(SousTypeMateriel.ID_CHASSIS_PRISE_DE_VUE);

        DimensionChassis dimensionChassis = new DimensionChassis();
        dimensionChassis.setId(1L);
        dimensionChassis.setNom("Nom de la dimension du chassis");

        DimensionChassis dimensionChassisAppareilPhoto = new DimensionChassis();
        dimensionChassisAppareilPhoto.setId(2L);
        dimensionChassisAppareilPhoto.setNom("Nom de la dimension du chassis de l'appareil photo");

        Materiel materielAppareilPhoto = new Materiel();
        materielAppareilPhoto.setId(1L);
        materielAppareilPhoto.setNom("Nom de l'appareil photo avec un chassis");
        materielAppareilPhoto.setTypeMateriel(typeMateriel);
        materielAppareilPhoto.setSousType(sousTypeMateriel);

        AppareilPhoto appareilPhoto = new AppareilPhoto();
        appareilPhoto.setId(1L);
        appareilPhoto.setMateriel(materielAppareilPhoto);
        appareilPhoto.setDimensionChassis(dimensionChassisAppareilPhoto);

        Materiel materielChassis = new Materiel();
        materielChassis.setId(2L);
        materielChassis.setNom("Nom du chassis");
        materielChassis.setTypeMateriel(typeMateriel);
        materielChassis.setSousType(sousTypeMaterielChassis);

        Chassis chassis = new Chassis();
        TailleFilm tailleFilm = new TailleFilm();
        tailleFilm.setId(1L);
        tailleFilm.setNom("Taille du film");
        chassis.setTailleFilm(tailleFilm);
        chassis.setMateriel(materielChassis);
        chassis.setDimensionChassis(dimensionChassis);

        Set<Materiel> materiels = new HashSet<>();
        materiels.add(materielAppareilPhoto);
        materiels.add(materielChassis);
        mockPriseDeVue.setMateriels(materiels);

        Produit produit = new Produit();
        produit.setId(1L);
        produit.setNom("Nom du film");

        Film film = new Film();
        film.setId(1L);
        film.setProduit(produit);
        film.setTailleFilm(tailleFilm);

        Set<Produit> produits = new HashSet<>();
        produits.add(produit);
        mockPriseDeVue.setProduits(produits);

        when(appareilPhotoRepository.findAppareilPhotoByMaterielId(materielAppareilPhoto.getId())).thenReturn(Optional.of(appareilPhoto));
        when(chassisRepository.findChassisByMaterielId(materielChassis.getId())).thenReturn(Optional.of(chassis));
        when(filmRepository.findById(1L)).thenReturn(Optional.of(film));

        Exception exception = assertThrows(PriseDeVueException.class, () -> service.EnregistreUnePriseDeVue(mockPriseDeVue));

        String expectedMessage = "Impossible d'ajouter la prise de vue car il manque un châssis pour cet appareil photo : " + appareilPhoto.getMateriel().getNom();
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }
}