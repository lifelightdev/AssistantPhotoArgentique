package life.light.apa.priseDeVue.service;

import life.light.apa.priseDeVue.PriseDeVueException;
import life.light.apa.priseDeVue.dao.PriseDeVueRepository;
import life.light.apa.priseDeVue.dao.StatutVueRepository;
import life.light.apa.priseDeVue.dao.VueRepository;
import life.light.apa.priseDeVue.model.PriseDeVue;
import life.light.apa.priseDeVue.model.StatutVue;
import life.light.apa.priseDeVue.model.Vue;
import life.light.apa.referentiel.dao.AppareilPhotoRepository;
import life.light.apa.referentiel.dao.FilmRepository;
import life.light.apa.referentiel.model.AppareilPhoto;
import life.light.apa.referentiel.model.Film;
import life.light.apa.referentiel.model.Materiel;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.*;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    void uneVueDejaRealiser() {
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
    void uneSeuleVueARealiser() {
        // Given
        // When
        // Then
    }

    @Test
    void listeDesAppareilsPhotoDUnePriseDeVue() {
    }

    @Test
    void listeDesFilmsDUnePriseDeVue() {
    }


}