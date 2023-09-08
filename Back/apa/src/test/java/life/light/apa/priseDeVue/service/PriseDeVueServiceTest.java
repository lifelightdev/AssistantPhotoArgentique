package life.light.apa.priseDeVue.service;

import life.light.apa.priseDeVue.PriseDeVueException;
import life.light.apa.priseDeVue.dao.PriseDeVueRepository;
import life.light.apa.priseDeVue.dao.StatutVueRepository;
import life.light.apa.priseDeVue.dao.VueRepository;
import life.light.apa.priseDeVue.model.*;
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

import java.time.LocalDateTime;
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

    private static TypeMateriel typeDeMaterielPriseDeVue() {
        TypeMateriel typeMateriel = new TypeMateriel();
        typeMateriel.setId(TypeMateriel.ID_PRISE_DE_VUE);
        return typeMateriel;
    }

    private static SousTypeMateriel sousTypeMaterielPied() {
        SousTypeMateriel sousTypeMateriel = new SousTypeMateriel();
        sousTypeMateriel.setId(SousTypeMateriel.ID_PIED);
        return sousTypeMateriel;
    }

    private static TailleFilm tailleFilm() {
        TailleFilm tailleFilm = new TailleFilm();
        tailleFilm.setId(1L);
        tailleFilm.setNom("Taille du film");
        return tailleFilm;
    }

    private static SousTypeMateriel sousTypeMaterielAppareilPhotoArgentique() {
        SousTypeMateriel sousTypeMateriel = new SousTypeMateriel();
        sousTypeMateriel.setId(SousTypeMateriel.ID_APPAREIL_PHOTO_ARGENTIQUE);
        return sousTypeMateriel;
    }

    private static DimensionChassis dimensionChassis() {
        DimensionChassis dimensionChassis = new DimensionChassis();
        dimensionChassis.setId(1L);
        dimensionChassis.setNom("Nom de la dimension du chassis");
        return dimensionChassis;
    }

    private static SousTypeMateriel sousTypeMaterielChassisPriseDeVue() {
        SousTypeMateriel sousTypeMaterielChassis = new SousTypeMateriel();
        sousTypeMaterielChassis.setId(SousTypeMateriel.ID_CHASSIS_PRISE_DE_VUE);
        return sousTypeMaterielChassis;
    }

    private static StatutPriseDeVue statutPriseDeVuePreparatoire(){
        StatutPriseDeVue statutPriseDeVue = new StatutPriseDeVue();
        statutPriseDeVue.setId(StatutPriseDeVue.ID_PREPARATOIRE);
        statutPriseDeVue.setNom("Préparatoire");
        return statutPriseDeVue;
    }

    private static Position position(){
        Position position = new Position();
        position.setId(1L);
        position.setNom("Nom de la position");
        return position;
    }
    private static Film film(){
        Produit produit = new Produit();
        produit.setId(1L);
        produit.setNom("[Nom du film]");

        Film film = new Film();
        film.setId(1L);
        film.setProduit(produit);
        film.setTailleFilm(tailleFilm());
        return film;
    }

    private static StatutMateriel getStatutMaterielIndissociable() {
        StatutMateriel statutMateriel = new StatutMateriel();
        statutMateriel.setId(StatutMateriel.ID_INDISSOCIABLE);
        return statutMateriel;
    }

    private static Materiel getMaterielAppareilPhoto() {
        Materiel materiel = new Materiel();
        materiel.setId(1L);
        materiel.setNom("Nom de l'appareil photo");
        materiel.setTypeMateriel(typeDeMaterielPriseDeVue());
        materiel.setSousType(sousTypeMaterielAppareilPhotoArgentique());
        return materiel;
    }

    private static Materiel getMaterielChassisIndissociable() {
        Materiel materiel = new Materiel();
        materiel.setId(2L);
        materiel.setNom("Nom du châssis de l'appareil photo");
        materiel.setTypeMateriel(typeDeMaterielPriseDeVue());
        materiel.setSousType(sousTypeMaterielChassisPriseDeVue());
        materiel.setStatutMateriel(getStatutMaterielIndissociable());
        return materiel;
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
    void ajouterUnePriseDeVueSansDonneesObligatoire(){
        PriseDeVue mockPriseDeVue = new PriseDeVue();
        Exception exception = assertThrows(PriseDeVueException.class, () -> service.EnregistreUnePriseDeVue(mockPriseDeVue));

        String expectedMessage = "Impossible d'ajouter la prise de vues, car il manque : le nom, le statut, la date, la position, un appareil photo et un film";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        assertThat(actualMessage).isEqualTo(expectedMessage);

    }

    @Test
    void ajouterUnePriseDeVueAvecUnAgrandisseur() {
        PriseDeVue mockPriseDeVue = new PriseDeVue();
        mockPriseDeVue.setNom("[Nom de la prise de vue]");
        mockPriseDeVue.setStatutPriseDeVue(statutPriseDeVuePreparatoire());
        mockPriseDeVue.setPosition(position());
        mockPriseDeVue.setDate(LocalDateTime.now());
        Materiel materiel = new Materiel();
        materiel.setNom("[Nom de l'agrandisseur]");
        TypeMateriel typeMateriel = new TypeMateriel();
        typeMateriel.setId(TypeMateriel.ID_TIRAGE);
        materiel.setTypeMateriel(typeMateriel);
        Set<Materiel> materiels = new HashSet<>();
        materiels.add(materiel);
        mockPriseDeVue.setMateriels(materiels);

        Set<Produit> produits = new HashSet<>();
        produits.add(film().getProduit());
        mockPriseDeVue.setProduits(produits);

        Exception exception = assertThrows(PriseDeVueException.class, () -> service.EnregistreUnePriseDeVue(mockPriseDeVue));

        String expectedMessage = "Impossible d'ajouter la prise de vues, car le matériel " + materiel.getNom() + " n'est pas de type prise de vue.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

    @Test
    void ajouterUnePriseDeVueSansAppareilPhoto() {

        Materiel materiel = new Materiel();
        materiel.setNom("Nom du pied");
        materiel.setTypeMateriel(typeDeMaterielPriseDeVue());
        materiel.setSousType(sousTypeMaterielPied());

        Set<Materiel> materiels = new HashSet<>();
        materiels.add(materiel);

        Set<Produit> produits = new HashSet<>();
        produits.add(film().getProduit());

        PriseDeVue mockPriseDeVue = new PriseDeVue();
        mockPriseDeVue.setNom("Nom de la prise de vue");
        mockPriseDeVue.setMateriels(materiels);
        mockPriseDeVue.setStatutPriseDeVue(statutPriseDeVuePreparatoire());
        mockPriseDeVue.setPosition(position());
        mockPriseDeVue.setDate(LocalDateTime.now());
        mockPriseDeVue.setProduits(produits);

        Exception exception = assertThrows(PriseDeVueException.class, () -> service.EnregistreUnePriseDeVue(mockPriseDeVue));

        String expectedMessage = "Impossible d'ajouter la prise de vues, car il n'y a pas d'appareil photo dans la liste de matériel.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

    @Test
    void ajouterUnePriseDeVueSansFilm() {

        Materiel materiel = getMaterielAppareilPhoto();
        Materiel materielChassis = getMaterielChassisIndissociable();

        Chassis chassis = new Chassis();
        chassis.setTailleFilm(tailleFilm());
        chassis.setMateriel(materielChassis);

        AppareilPhoto appareilPhoto = new AppareilPhoto();
        appareilPhoto.setId(1L);
        appareilPhoto.setMateriel(materiel);
        appareilPhoto.setChassis(chassis);

        Set<Materiel> materiels = new HashSet<>();
        materiels.add(materiel);

        Produit produit = new Produit();
        produit.setId(1L);

        Set<Produit> produits = new HashSet<>();
        produits.add(produit);

        PriseDeVue mockPriseDeVue = new PriseDeVue();
        mockPriseDeVue.setNom("Nom de la prise de vue");
        mockPriseDeVue.setMateriels(materiels);
        mockPriseDeVue.setStatutPriseDeVue(statutPriseDeVuePreparatoire());
        mockPriseDeVue.setPosition(position());
        mockPriseDeVue.setDate(LocalDateTime.now());
        mockPriseDeVue.setProduits(produits);

        when(appareilPhotoRepository.findAppareilPhotoByMaterielId(1L)).thenReturn(Optional.of(appareilPhoto));
        Exception exception = assertThrows(PriseDeVueException.class, () -> service.EnregistreUnePriseDeVue(mockPriseDeVue));

        String expectedMessage = "Impossible d'ajouter la prise de vue car il n'y a pas de film.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

    @Test
    void ajouterUnePriseDeVueAvecUnFilmIncompatible() {

        Materiel materielChassisIndissociable = getMaterielChassisIndissociable();

        TailleFilm tailleFilmChassis = new TailleFilm();
        tailleFilmChassis.setId(2L);
        tailleFilmChassis.setNom("[Taille du film]");

        Chassis chassis = new Chassis();
        chassis.setTailleFilm(tailleFilmChassis);
        chassis.setMateriel(materielChassisIndissociable);

        Materiel materielAppareilPhoto = getMaterielAppareilPhoto();

        AppareilPhoto appareilPhoto = new AppareilPhoto();
        appareilPhoto.setId(1L);
        appareilPhoto.setMateriel(materielAppareilPhoto);
        appareilPhoto.setChassis(chassis);

        Set<Materiel> materiels = new HashSet<>();
        materiels.add(materielChassisIndissociable);
        materiels.add(materielAppareilPhoto);

        Film film = film();

        Set<Produit> produits = new HashSet<>();
        produits.add(film.getProduit());

        PriseDeVue mockPriseDeVue = new PriseDeVue();
        mockPriseDeVue.setNom("Nom de la prise de vue");
        mockPriseDeVue.setMateriels(materiels);
        mockPriseDeVue.setStatutPriseDeVue(statutPriseDeVuePreparatoire());
        mockPriseDeVue.setPosition(position());
        mockPriseDeVue.setDate(LocalDateTime.now());
        mockPriseDeVue.setProduits(produits);

        when(appareilPhotoRepository.findAppareilPhotoByMaterielId(appareilPhoto.getMateriel().getId())).thenReturn(Optional.of(appareilPhoto));
        when(filmRepository.findById(film.getId())).thenReturn(Optional.of(film));
        when(chassisRepository.findChassisByMaterielId(materielChassisIndissociable.getId())).thenReturn(Optional.of(chassis));
        when(appareilPhotoRepository.findAppareilPhotoByMaterielId(materielAppareilPhoto.getId())).thenReturn(Optional.of(appareilPhoto));

        Exception exception = assertThrows(PriseDeVueException.class, () -> service.EnregistreUnePriseDeVue(mockPriseDeVue));

        String expectedMessage = "Impossible d'ajouter la prise de vues, " +
                "car le chassis " + chassis.getMateriel().getNom() + " n'a pas de film compatible.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

    @Test
    void ajouterUnePriseDeVueAvecUnChassis() {

        Materiel materielAppareilPhoto = getMaterielAppareilPhoto();
        Materiel materielChassis = getMaterielChassisIndissociable();

        Chassis chassis = new Chassis();
        chassis.setTailleFilm(tailleFilm());
        chassis.setMateriel(materielChassis);
        chassis.setDimensionChassis(dimensionChassis());

        AppareilPhoto appareilPhoto = new AppareilPhoto();
        appareilPhoto.setId(1L);
        appareilPhoto.setMateriel(materielAppareilPhoto);
        appareilPhoto.setChassis(chassis);
        appareilPhoto.setDimensionChassis(dimensionChassis());

        Set<Materiel> materiels = new HashSet<>();
        materiels.add(materielChassis);
        materiels.add(materielAppareilPhoto);

        Produit produit = new Produit();
        produit.setId(1L);
        produit.setNom("Nom du film");

        Film film = new Film();
        film.setId(1L);
        film.setProduit(produit);
        film.setTailleFilm(tailleFilm());

        Set<Produit> produits = new HashSet<>();
        produits.add(produit);

        PriseDeVue mockPriseDeVue = new PriseDeVue();
        mockPriseDeVue.setNom("Nom de la prise de vue");
        mockPriseDeVue.setMateriels(materiels);
        mockPriseDeVue.setStatutPriseDeVue(statutPriseDeVuePreparatoire());
        mockPriseDeVue.setPosition(position());
        mockPriseDeVue.setDate(LocalDateTime.now());
        mockPriseDeVue.setProduits(produits);

        when(appareilPhotoRepository.findAppareilPhotoByMaterielId(materielAppareilPhoto.getId())).thenReturn(Optional.of(appareilPhoto));
        when(chassisRepository.findChassisByMaterielId(materielChassis.getId())).thenReturn(Optional.of(chassis));
        when(filmRepository.findById(film.getId())).thenReturn(Optional.of(film));
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

        DimensionChassis dimensionChassisAppareilPhoto = new DimensionChassis();
        dimensionChassisAppareilPhoto.setId(2L);
        dimensionChassisAppareilPhoto.setNom("Nom de la dimension du chassis de l'appareil photo");

        Materiel materielAppareilPhoto = getMaterielAppareilPhoto();

        AppareilPhoto appareilPhoto = new AppareilPhoto();
        appareilPhoto.setId(1L);
        appareilPhoto.setMateriel(materielAppareilPhoto);
        appareilPhoto.setDimensionChassis(dimensionChassisAppareilPhoto);

        Materiel materielChassis = getMaterielChassisIndissociable();

        Chassis chassis = new Chassis();
        TailleFilm tailleFilm = tailleFilm();
        chassis.setTailleFilm(tailleFilm);
        chassis.setMateriel(materielChassis);
        chassis.setDimensionChassis(dimensionChassis());

        Set<Materiel> materiels = new HashSet<>();
        materiels.add(materielAppareilPhoto);
        materiels.add(materielChassis);

        Produit produit = new Produit();
        produit.setId(1L);
        produit.setNom("Nom du film");

        Film film = new Film();
        film.setId(1L);
        film.setProduit(produit);
        film.setTailleFilm(tailleFilm);

        Set<Produit> produits = new HashSet<>();
        produits.add(produit);

        PriseDeVue mockPriseDeVue = new PriseDeVue();
        mockPriseDeVue.setNom("Nom de la prise de vue");
        mockPriseDeVue.setMateriels(materiels);
        mockPriseDeVue.setStatutPriseDeVue(statutPriseDeVuePreparatoire());
        mockPriseDeVue.setPosition(position());
        mockPriseDeVue.setDate(LocalDateTime.now());
        mockPriseDeVue.setProduits(produits);

        when(appareilPhotoRepository.findAppareilPhotoByMaterielId(materielAppareilPhoto.getId())).thenReturn(Optional.of(appareilPhoto));
        when(chassisRepository.findChassisByMaterielId(materielChassis.getId())).thenReturn(Optional.of(chassis));

        Exception exception = assertThrows(PriseDeVueException.class, () -> service.EnregistreUnePriseDeVue(mockPriseDeVue));

        String expectedMessage = "Impossible d'ajouter la prise de vues, car cet appareil photo " + appareilPhoto.getMateriel().getNom() + " n'a pas de châssis.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

}